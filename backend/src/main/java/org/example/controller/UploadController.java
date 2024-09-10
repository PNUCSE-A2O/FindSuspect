package org.example.controller;

import org.example.dto.SaveDataDto;
import java.util.*;
import org.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class UploadController {
    @Autowired
    UploadService uploadService;

    @GetMapping("/")
    public String mainPage(){
        return "upload";
    }

    @PostMapping("/api/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile imageFile) {
        uploadService.uploadImage(imageFile);

        return ResponseEntity.ok("정상 업로드");
    }

    @PostMapping("/api/upload/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile videoFile) {
        uploadService.uploadVideo(videoFile);
        return ResponseEntity.ok("정상 업로드");
    }

    @GetMapping("/api/get/image")
    public ResponseEntity<String> getImagePath() {
        String imagePath = uploadService.getImagePath();

        return new ResponseEntity<>(imagePath, HttpStatus.OK);
    }

    @GetMapping("/api/get/video")
    public ResponseEntity<String> getVideoPath() {
        String videoPath= uploadService.getVideoPath();
        return new ResponseEntity<>(videoPath, HttpStatus.OK);
    }

    @GetMapping("/api/result")
    public ResponseEntity<Map<String, SaveDataDto>> getResult(){
        Map<String, SaveDataDto> data = uploadService.getResult();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/api/video")
    public ResponseEntity<String> deleteVideo(@RequestParam("name")String name){
        uploadService.deleteVideo(name);
        return new ResponseEntity<>("",HttpStatus.OK);
    }


}
