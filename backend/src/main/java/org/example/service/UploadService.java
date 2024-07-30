package org.example.service;

import org.apache.coyote.Response;
import org.example.dto.ResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    private final String UPLOAD_DIR_VIDEO = "src/main/frontend/uploads/video/";
    private final String UPLOAD_DIR_IMAGE = "src/main/frontend/uploads/image/";
    private List<ResponseDto> script(){
        StringBuilder output = new StringBuilder();
        List<ResponseDto> response = new ArrayList<>();
        try {
            // Python 스크립트 실행 명령어
            String command = "python script.py"; // Windows 사용 시 "python script.py"로 변경

            // 프로세스 빌더 설정
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            // 프로세스 시작
            Process process = processBuilder.start();

            // 프로세스 출력 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                System.out.println(line);
                List<String> str = Arrays.stream(line.split(",")).toList();
                response.add(new ResponseDto(str.get(0),Integer.parseInt(str.get(1))));
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            output.append("Error: ").append(e.getMessage());
        }

        System.out.println(output);
        return response;
    }

    public void uploadImage(MultipartFile imageFile) throws IOException {
        try {
            saveFile(UPLOAD_DIR_IMAGE, imageFile);
        } catch (IOException e) {
            throw new IOException("이미지 저장 실패");
        }
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
    }

    public String getImagePath() throws IOException {
        return getPath(UPLOAD_DIR_IMAGE);
    }

    public String getVideoPath() throws IOException {
        return getPath(UPLOAD_DIR_VIDEO);
    }

    private String getPath(String dir) throws IOException {
        Path viedoDir = Paths.get(dir);
        Path viedoPath = Files.list(viedoDir)
                .filter(Files::isRegularFile)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No image file found"));
        String returnPath = viedoPath.toString();
        int index = returnPath.indexOf("uploads");
        return returnPath.substring(index);
    }

    public List<ResponseDto> getResult() {
        return script();
    }
}
