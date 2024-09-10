package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.example.dto.ResponseDto;
import org.example.dto.SaveDataDto;
import org.example.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Validated
public class UploadService {
    private final String UPLOAD_DIR_VIDEO = "/data/FindSuspect/backend/src/main/frontend/public/video/";
    private final String UPLOAD_DIR_IMAGE = "/data/FindSuspect/backend/src/main/frontend/public/image/";
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, SaveDataDto> jsonData = new HashMap<>();

    private final int FPS = 30;

    private void videoPython(String fileName){
        try {
            // Python 스크립트 실행 명령어
            if (!checkCuda()) throw new Exception("cuda사용 안됨");
            String command = "python algorithm/video_upload.py " + fileName; // Windows 사용 시 "python script.py"로 변경

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
            throw new BadRequestException("영상 저장 실패");
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
            String dirPath = "/data/FindSuspect/backend/src/main/resources/data";
            File dir = new File(dirPath);
            File[] jsonFiles = dir.listFiles((d, name) -> name.endsWith(".json"));

            jsonData = new HashMap<>();
                   
            assert jsonFiles != null;
            for (File jsonFile : jsonFiles) {
                // JSON 파일을 Map<String, Object>로 파싱
                Map<String, Object> fileData = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

                // 파싱된 데이터를 SaveDataDto 객체로 변환
                for (Map.Entry<String, Object> entry : fileData.entrySet()) {
                    SaveDataDto dto = objectMapper.convertValue(entry.getValue(), SaveDataDto.class);
                    jsonData.put(entry.getKey(), dto);
                }
            }

            // frame 번호를 추출하여 time 설정
            Pattern pattern = Pattern.compile("frame(\\d+)");
            for (Map.Entry<String, SaveDataDto> entry : jsonData.entrySet()) {
                String key = entry.getKey();
                SaveDataDto dto = entry.getValue();

                Matcher matcher = pattern.matcher(key);
                if (matcher.find()) {
                    int frameNumber = Integer.parseInt(matcher.group(1));
                    int minute = frameNumber / (60 * FPS);
                    int second = (frameNumber / FPS) % 60;
                    String strTime = String.format("%d:%02d", minute, second);
                    dto.setTime(strTime);
                }
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException("이미지 처리 실패");
        }

    }

    private void saveImage(String dir, MultipartFile file) {
        String fileName = file.getOriginalFilename();

        File directory = new File(dir);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    if (!f.delete()) {
                        throw new BadRequestException("파일 삭제 실패");
                    }
                }
            }
        }

        Path targetPath = Paths.get(dir+fileName);

        // 해당 path 에 파일의 스트림 데이터를 저장
        try (OutputStream os = Files.newOutputStream(targetPath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("파일 저장 실패");
        }
    }

    private void deleteFolder(File folder) {
        // 폴더 안의 모든 파일과 하위 디렉토리 삭제
        File[] files = folder.listFiles();
        if (files != null) {  // 파일이 있을 때만 실행
            for (File file : files) {
                if (file.isDirectory()) {
                    // 하위 폴더가 있으면 재귀적으로 삭제
                    deleteFolder(file);
                } else {
                    // 파일 삭제
                    if (!file.delete()) {
                        throw new RuntimeException("파일 삭제 실패: " + file.getAbsolutePath());
                    }
                }
            }
        }
        // 폴더 자체 삭제
        if (!folder.delete()) {
            throw new RuntimeException("폴더 삭제 실패: " + folder.getAbsolutePath());
        }
    }

    private void saveVideo(String dir, MultipartFile file) {
        // 원본 파일 이름
        String fileName = file.getOriginalFilename();
        
        // 1. sample.mp4라는 폴더 경로 설정
        Path folderPath = Paths.get(dir + "/" + fileName);
        File folder = new File(folderPath.toString());
        
        // 폴더가 존재하면 삭제
        if (folder.exists()) {
            deleteFolder(folder);  // 재귀적으로 폴더 삭제
        }
        
        // 폴더가 존재하지 않으면 생성
        if (!folder.exists()) {
            boolean isCreated = folder.mkdirs();
            if (!isCreated) {
                throw new BadRequestException("폴더 생성에 실패했습니다: " + folderPath);
            }
        }

        // 2. sample.mp4 폴더 안에 sample.mp4 파일 경로 설정
        Path targetPath = Paths.get(folderPath.toString() + "/" + fileName);


        // 해당 path 에 파일의 스트림 데이터를 저장
        try (OutputStream os = Files.newOutputStream(targetPath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new BadRequestException("파일 저장 실패");
        }
    }

    public void uploadImage(MultipartFile imageFile) {
        saveImage(UPLOAD_DIR_IMAGE, imageFile);
        imagePython();

    }

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
                throw new BadRequestException("CUDA 확인 중 오류 발생");
            }

            return cudaAvailable;
        } catch (Exception e) {
            throw new BadRequestException("python 실행 실패");
        }
    }

    public void uploadVideo(MultipartFile videoFile){
        saveVideo(UPLOAD_DIR_VIDEO, videoFile);
        videoPython(videoFile.getOriginalFilename());
    }

    private String getPath(String dir) {
        Path Dir = Paths.get(dir);
        Path path = null;
        try {
            path = Files.list(Dir)
                    .filter(Files::isRegularFile)
                    .findFirst().orElseThrow(()->new BadRequestException("파일 없음"));
        } catch (IOException e) {
            throw new BadRequestException("파일 없음");
        }

        String returnPath = path.toString();
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

    public Map<String, SaveDataDto> getResult(){
        return this.jsonData;
    }

    public void deleteVideo(String name) {
        Path path1 = (Paths.get("/data/FindSuspect/backend/src/main/frontend/public/video/"+name));
        Path path2 = (Paths.get("/data/FindSuspect/backend/algorithm/VTFPAR++/features/"+name+".json"));
        //System.out.println(path2.toString());

        try {
            //path1 폴더와 그 안의 파일/폴더 삭제
            File folder = path1.toFile();
            if (folder.exists() && folder.isDirectory()) {
                deleteFolder(folder);
            } else {
                throw new BadRequestException("not exist folder: " + path1.toString());
            }

            // path2에 해당하는 파일 삭제
            if (Files.exists(path2)) {
                Files.delete(path2);
            } else {
                throw new BadRequestException("not exist file: " + path2.toString());
            }
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            throw new BadRequestException("파일 삭제 실패");
        }
    }
}
