package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.HistoryDTO;
import org.example.dto.ResultDTO;
import org.example.entity.History;
import org.example.exception.BadRequestException;
import org.example.repository.HistoryRepository;
import org.example.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.StringEscapeUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImageService {
    private final String UPLOAD_DIR_IMAGE = "/data/FindSuspect/backend/src/main/frontend/public/image/";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<Map.Entry<String, ResultDTO>> result;

    @Autowired
    HistoryRepository historyRepository;
    private Util util = new Util();
    private final int FPS = 30;
    private String finalImage;

    private void saveImage(String dir, MultipartFile file) {
        String fileName = file.getOriginalFilename();

        File dirPath = new File(dir+fileName);
        if (dirPath.exists()) {
            util.deleteFolder(dirPath);  // 폴더와 그 안의 모든 내용을 삭제
        }

        if (!dirPath.mkdirs())
            throw new BadRequestException("folder make fail");

        Path targetPath = Paths.get(dirPath.toString(), fileName);

        // 해당 path 에 파일의 스트림 데이터를 저장
        try (OutputStream os = Files.newOutputStream(targetPath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("image save fail");
        }
    }
    
    public void uploadImage(MultipartFile imageFile) {
        saveImage(UPLOAD_DIR_IMAGE, imageFile);
        imagePython(imageFile.getOriginalFilename());
    }

    public List<Map.Entry<String, ResultDTO>> getResult(){
        return this.result;
    }
    
    private void imagePython(String fileName){
        if (!util.checkCuda()) throw new BadRequestException("cuda not use");
        String command = "python algorithm/image_upload.py " + fileName; // Windows 사용 시 "python script.py"로 변경

        int exitCode = util.newProcess(command);
        if(exitCode != 0) throw new BadRequestException("image feature error");
        finalImage = fileName;
        readResultJson(fileName);
        //saveHistory(fileName);
    }

     public void saveHistory(String imageName) {
        String originalFile = "image/"+ finalImage +"/"+finalImage;
        String croppedFile = originalFile +"_cropped.jpg";
        String rectangleFile = originalFile +"_rectangle.jpg";
        ResultDTO resultDTO = null;
        if(this.result == null)
            throw new BadRequestException("result is null");
        for(Map.Entry<String,ResultDTO> mp : result){
            if(mp.getKey().equals(imageName)){
                resultDTO= mp.getValue();
            }
        }
        if(resultDTO == null) throw new BadRequestException("imageName not found");
        History history = new History(originalFile,croppedFile,rectangleFile,imageName,resultDTO);
        historyRepository.save(history);
    }

     private void readResultJson(String fileName) {
        //result.json읽어 파싱 후 List로
        String dirPath = "/data/FindSuspect/backend/src/main/resources/data/"+fileName+"_cropped.jpg";
        File dir = new File(dirPath);
        File[] jsonFiles = dir.listFiles((d, name) -> name.endsWith(".json"));

        Map<String, ResultDTO> resultMap = new HashMap<>();

        assert jsonFiles != null;
        for (File jsonFile : jsonFiles) {
            // JSON 파일을 Map<String, Object>로 파싱
            Map<String, Object> fileData = null;
            try {
                fileData = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new BadRequestException("result json read fail");
            }

            // 파싱된 데이터를 SaveDataDto 객체로 변환
            for (Map.Entry<String, Object> entry : fileData.entrySet()) {
                ResultDTO dto = objectMapper.convertValue(entry.getValue(), ResultDTO.class);
                resultMap.put(entry.getKey(), dto);
            }
        }

        // frame 번호를 추출하여 time 설정
        Pattern pattern = Pattern.compile("frame(\\d+)");
        for (Map.Entry<String, ResultDTO> entry : resultMap.entrySet()) {
            String key = entry.getKey();
            ResultDTO dto = entry.getValue();

            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                int frameNumber = Integer.parseInt(matcher.group(1));
                int minute = frameNumber / (60 * FPS);
                int second = (frameNumber / FPS) % 60;
                String strTime = String.format("%d:%02d", minute, second);
                dto.setTime(strTime);
            }
        }

        result = new ArrayList<>(resultMap.entrySet());
        result.sort((e1, e2) -> Double.compare(e2.getValue().getSimilarity(), e1.getValue().getSimilarity()));

    }

    public List<HistoryDTO> getHistory() {
        List<History> histories = historyRepository.findAll();
        //History his = histories.get(0);
        
        List<HistoryDTO> result = histories.stream().map(History::toDTO).toList();
        return  result;
    }
    
    public String getPath(){
        return "image/" + finalImage +"/" + finalImage;
    }
}
