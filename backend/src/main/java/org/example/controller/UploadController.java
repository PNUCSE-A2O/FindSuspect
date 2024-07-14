package org.example.controller;

import org.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {
    @Autowired
    UploadService uploadService;

    private final String UPLOAD_DIR = "src/main/uploads/";

    @GetMapping("/")
    public String index(){
        return "upload";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile imageFile, MultipartFile videoFile, RedirectAttributes redirectAttributes) {
        if (imageFile.isEmpty() || videoFile.isEmpty()) {
            //redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("파일 없음");
        }

        try {
            saveFile(imageFile);
            saveFile(videoFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 저장할 수 없음");
        }
        uploadService.script();

        return ResponseEntity.ok("정상 업로드");
    }

    private void saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, bytes);
    }

    @GetMapping("/uploadStatus")
    public ResponseEntity<String> uploadStatus() {
        return ResponseEntity.ok().body(""); // 실제 업로드 상태를 처리하는 로직을 여기에 추가
    }
}
