package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.example.dto.ResponseDto;
import org.example.dto.SaveDataDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UploadService {
    private final String UPLOAD_DIR_VIDEO = "/data/FindSuspect/backend/src/main/frontend/public/video/";
    private final String UPLOAD_DIR_IMAGE = "/data/FindSuspect/backend/src/main/frontend/public/image/";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<ResponseDto> result = new ArrayList<>();

    private final int FPS = 30;

    private void videoPython(){
        try {
            // Python 스크립트 실행 명령어
            String command = "python algorithm/video_upload.py"; // Windows 사용 시 "python script.py"로 변경

            // 프로세스 빌더 설정
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            // 프로세스 시작
            Process process = processBuilder.start();

            // // 프로세스 출력 읽기
            // BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            // String line;

            // while ((line = reader.readLine()) != null) {
            //     output.append(line).append("\n");
            //     System.out.println(line);
            //     List<String> str = Arrays.stream(line.split(",")).toList();
            //     response.add(new ResponseDto(str.get(0),Integer.parseInt(str.get(1))));
            // }

            // // 프로세스 종료 대기
            int exitCode = process.waitFor();
            if(exitCode != 0) throw new Exception("영상 feature 오류");
        } catch (Exception e) {
            e.printStackTrace();
//            output.append("Error: ").append(e.getMessage());
        }

    }

    private void imagePython(){
        try {
            // Python 스크립트 실행 명령어
            String command = "python algorithm/image_upload.py"; // Windows 사용 시 "python script.py"로 변경

            // 프로세스 빌더 설정
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            // 프로세스 시작
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if(exitCode != 0) throw new Exception("이미지 feature 오류");

            //result.json읽어 파싱 후 List로 local에 저장
            List<SaveDataDto> saveDataDtoList = new ArrayList<>();;
            try {
                // JSON 파일을 읽어 Map으로 변환합니다.
                Map<String, Double> map = objectMapper.readValue(new File("/data/FindSuspect/backend/src/main/resources/data/result.json"), Map.class);

                // SaveDataDto 리스트로 변환합니다.

                for (Map.Entry<String, Double> entry : map.entrySet()) {
                    saveDataDtoList.add(new SaveDataDto(entry.getKey(), (int) (entry.getValue()*100)));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //responseDto로 변환 후 result에 저장
            result = saveDataDtoList.stream().map(saveDataDto -> {
                Pattern pattern = Pattern.compile("frame(\\d+)");
                Matcher matcher = pattern.matcher(saveDataDto.imageName());
                int frameNumber;
                if (matcher.find()) {
                    frameNumber = Integer.parseInt(matcher.group(1));
                    // frameNumber 사용 코드
                } else {
                    throw new IllegalArgumentException("Invalid image name format: " + saveDataDto.imageName());
                }
                int minute = frameNumber/60/FPS, second = frameNumber/FPS%60;
                String strTime = minute+":"+second;
                return new ResponseDto(saveDataDto.imageName(),strTime,saveDataDto.accuracy());
            }).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
//            output.append("Error: ").append(e.getMessage());
        }

    }

    public void uploadImage(MultipartFile imageFile) throws IOException {
        try {
            saveFile(UPLOAD_DIR_IMAGE, imageFile);
        } catch (IOException e) {
            throw new IOException("이미지 저장 실패");
        }
        imagePython();

    }

    private void saveFile(String dir, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetPath = Paths.get(dir + fileName);
        Path oldFilePath = null;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
            for (Path path : stream) {
                if (Files.isRegularFile(path)) {
                    oldFilePath = path;
                    break;
                }
            }
        }

        try {
            byte[] bytes = file.getBytes();
            Files.write(targetPath, bytes);

            if (oldFilePath != null && !oldFilePath.equals(targetPath)) {
                Files.delete(oldFilePath);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public void uploadVideo(MultipartFile videoFile) throws IOException{
        try{
            saveFile(UPLOAD_DIR_VIDEO, videoFile);
        }catch(IOException e){
            throw new IOException("저장 실패");
        }
        videoPython();
    }

    public String getImagePath() throws IOException {
        String returnPath = getPath(UPLOAD_DIR_IMAGE);
        int index = returnPath.indexOf("/image");
        return returnPath.substring(index);
    }

    public String getVideoPath() throws IOException {
        String returnPath = getPath(UPLOAD_DIR_VIDEO);
        int index = returnPath.indexOf("/video");
        return returnPath.substring(index);
    }

    private String getPath(String dir) throws IOException {
        Path viedoDir = Paths.get(dir);
        Path viedoPath = Files.list(viedoDir)
                .filter(Files::isRegularFile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No image file found"));
        String returnPath = viedoPath.toString();
        return returnPath;
    }

    public List<ResponseDto> getResult(){
        return this.result;
    }

}
