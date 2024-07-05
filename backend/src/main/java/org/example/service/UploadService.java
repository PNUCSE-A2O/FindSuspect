package org.example.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class UploadService {
    public void script(){
        StringBuilder output = new StringBuilder();

        try {
            // Python 스크립트 실행 명령어
            String command = "python3 script.py"; // Windows 사용 시 "python script.py"로 변경

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
            }

            // 프로세스 종료 대기
            int exitCode = process.waitFor();
            output.append("Exited with code: ").append(exitCode);

        } catch (Exception e) {
            e.printStackTrace();
            output.append("Error: ").append(e.getMessage());
        }

        System.out.println(output);
    }
}
