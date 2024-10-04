package org.example.util;

import org.example.exception.BadRequestException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public boolean checkCuda() {
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

    public void deleteFolder(File folder) {
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

    public int newProcess(String command){
        int exitCode;
        try{
            // 프로세스 빌더 설정
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            processBuilder.directory(new java.io.File(".")); // 프로젝트 루트 디렉토리 설정

            // 프로세스 시작
            Process process = processBuilder.start();
            exitCode = process.waitFor();
        }catch(Exception e){
            throw new BadRequestException("파일 실행 실패");
        }
        return exitCode;
    }

    public void copyFile(String src, String dest) throws IOException {
        try{
            Path sourcePath = Paths.get(src);
            Path destinationPath = Paths.get(dest);

            // dest가 폴더인 경우 해당 폴더에 파일을 복사       
            if (Files.isDirectory(destinationPath)) {
                destinationPath = destinationPath.resolve(sourcePath.getFileName());
            }
            
            if (Files.exists(potentialDestFile)) {
                System.out.println("파일이 이미 존재합니다: " + potentialDestFile);
                return; // 파일이 이미 존재하면 함수 종료
            }

            // 파일 복사
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            throw new BadRequestException("파일 복사 실패");
        }
        
    }
    // public void init(){
    //     File data = new File("/data/FindSuspect/backend/src/main/resources/data");
    //     File image = new File("/data/FindSuspect/backend/src/main/frontend/public/image");

    //     deleteFolder(data);
    //     deleteFolder(image);

    //     if (!data.mkdirs())
    //         throw new BadRequestException("folder make fail");
    //     if (!image.mkdirs())
    //         throw new BadRequestException("folder make fail");
    // }
}
