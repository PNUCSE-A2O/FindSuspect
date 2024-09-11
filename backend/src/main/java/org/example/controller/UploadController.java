package org.example.controller;

import org.example.dto.HistoryDTO;
import org.example.dto.ResultDTO;
import java.util.*;

import org.example.service.ImageService;
import org.example.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
    @Autowired
    VideoService videoService;
    @Autowired
    ImageService imageService;

    @GetMapping("/")
    public String mainPage(){
        return "upload";
    }

    @PostMapping("/api/upload/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile imageFile) {
        imageService.uploadImage(imageFile);

        return ResponseEntity.ok("정상 업로드");
    }

    @PostMapping("/api/upload/video")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile videoFile) {
        videoService.uploadVideo(videoFile);
        return ResponseEntity.ok("정상 업로드");
    }

    @GetMapping("/api/result")
    public ResponseEntity<Map<String, ResultDTO>> getResult(){
        Map<String, ResultDTO> data = imageService.getResult();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/api/video")
    public ResponseEntity<String> deleteVideo(@RequestParam("name")String name){
        videoService.deleteVideo(name);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("/api/history")
    public ResponseEntity<List<HistoryDTO>> getHistory(){
        List<HistoryDTO> result = imageService.getHistory();
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
