package org.example.controller;

import org.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class UploadController {
    @Autowired
    UploadService uploadService;

    private final String UPLOAD_DIR_VIDEO = "src/main/frontend/uploads/video/";
    private final String UPLOAD_DIR_IMAGE = "src/main/frontend/uploads/image/";

    @GetMapping("/")
    public String mainPage(){
        return "upload";
    }

    @PostMapping("/api/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        try {
            saveImage(imageFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 저장할 수 없음");
        }
        uploadService.script();

        return ResponseEntity.ok("정상 업로드");
    }

    @PostMapping("/api/upload/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("videoFile") MultipartFile videoFile) {

        try {
            saveVideo(videoFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일을 저장할 수 없음");
        }
        return ResponseEntity.ok("정상 업로드");
    }

    @GetMapping("/api/get/image")
    public ResponseEntity<String> getImagePath() {
        try {
            Path imageDir = Paths.get(UPLOAD_DIR_IMAGE);
            Path imagePath = Files.list(imageDir)
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No image file found"));
            String returnPath = imagePath.toString();
            int index = returnPath.indexOf("uploads");
            return new ResponseEntity<>(returnPath.substring(index), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get image path", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/get/video")
    public ResponseEntity<String> getVideoPath() {
        try {
            Path viedoDir = Paths.get(UPLOAD_DIR_VIDEO);
            Path viedoPath = Files.list(viedoDir)
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No image file found"));
            String returnPath = viedoPath.toString();
            int index = returnPath.indexOf("uploads");

            return new ResponseEntity<>(returnPath.substring(index), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get image path", HttpStatus.NOT_FOUND);
        }
    }


    private void saveVideo(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetPath = Paths.get(UPLOAD_DIR_VIDEO + fileName);
        Path oldFilePath = null;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(UPLOAD_DIR_IMAGE))) {
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

    private void saveImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path targetPath = Paths.get(UPLOAD_DIR_IMAGE + fileName);
        Path oldFilePath = null;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(UPLOAD_DIR_IMAGE))) {
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

}
