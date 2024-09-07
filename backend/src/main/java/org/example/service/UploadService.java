package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.example.dto.ResponseDto;
import org.example.dto.SaveDataDto;
import org.example.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
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
import java.io.OutputStream;

@Service
@Validated
public class UploadService {
    private final String UPLOAD_DIR_VIDEO = "/data/FindSuspect/backend/src/main/frontend/public/video/";
    private final String UPLOAD_DIR_IMAGE = "/data/FindSuspect/backend/src/main/frontend/public/image/";
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<ResponseDto> result = new ArrayList<>();

    private final int FPS = 30;

    private boolean checkCuda() {
        try {
            // CUDA 확인을 위한 Python 스크립트 실행 명령어
            String command = "python algorithm/cuda.py";

            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            Process process = processBuilder.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            boolean cudaAvailable = false;
            
            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                if (line.trim().equalsIgnoreCase("True")) {
                    cudaAvailable = true;
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("CUDA 확인 중 오류 발생");
                return false;
            }

            return cudaAvailable;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void videoPython(){
        try {
            // Python 스크립트 실행 명령어
            if (!checkCuda()) throw new Exception("cuda사용 안됨");
            String command = "python algorithm/video_upload.py"; // Windows 사용 시 "python script.py"로 변경

            // 프로세스 빌더 설정
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            // 프로세스 시작
            Process process = processBuilder.start();
            // // 프로세스 종료 대기
            int exitCode = process.waitFor();
            if(exitCode != 0) throw new Exception("영상 feature 오류");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void imagePython(){
        try {
            // Python 스크립트 실행 명령어
            if (!checkCuda()) throw new Exception("cuda사용 안됨");
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

    private void saveFile(String dir, MultipartFile file) {
        String fileName = file.getOriginalFilename();
        CheckFolder(fileName);
        Path targetPath = Paths.get(dir + fileName);

        // 해당 path 에 파일의 스트림 데이터를 저장
        try (OutputStream os = Files.newOutputStream(targetPath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadImage(MultipartFile imageFile) {
        saveFile(UPLOAD_DIR_IMAGE, imageFile);
        imagePython();

    }

    private void CheckFolder(String fileName){
        String path = "/data/FindSuspect/backend/src/main/frontend/public/video/"+fileName;
        File folder = new File(path);
        if (folder.exists() && folder.isDirectory()) {
            throw new BadRequestException("같은 이름의 영상이 이미 존재합니다");
        }
    }


    public void uploadVideo(MultipartFile videoFile){
        saveFile(UPLOAD_DIR_VIDEO, videoFile);
        videoPython();
    }

    private String getPath(String dir) {
        Path viedoDir = Paths.get(dir);
        Path viedoPath = null;
        try {
            viedoPath = Files.list(viedoDir)
                    .filter(Files::isRegularFile)
                    .findFirst().orElseThrow(()->new BadRequestException("파일 없음"));
        } catch (IOException e) {
            throw new BadRequestException("파일 없음");
        }

        String returnPath = viedoPath.toString();
        return returnPath;
    }

    public String getImagePath() {
        String returnPath = getPath(UPLOAD_DIR_IMAGE);
        int index = returnPath.indexOf("/image");
        return returnPath.substring(index);
    }

    public String getVideoPath() {
        String returnPath = getPath(UPLOAD_DIR_VIDEO);
        int index = returnPath.indexOf("/video");
        return returnPath.substring(index);
    }

    public List<ResponseDto> getResult(){
        return this.result;
    }

}
