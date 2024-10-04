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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.example.dto.PageHistory;

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

    @GetMapping("/api/get/image")
    public ResponseEntity<String> getImagePath(){
        String path = imageService.getPath();
        return new ResponseEntity<>(path, HttpStatus.OK);
    }

    @GetMapping("/api/get/video")
    public ResponseEntity<List<String>> getVideoPath(){
        List<String> videos = videoService.getPath();
        return new ResponseEntity<>(videos, HttpStatus.OK);
    }

    @GetMapping("/api/result")
    public ResponseEntity<List<Map.Entry<String, ResultDTO>>> getResult(){
        List<Map.Entry<String, ResultDTO>> data = imageService.getResult();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/api/result/videoName")
    public ResponseEntity<List<Map.Entry<String, ResultDTO>>> getResultByVideoName(){
        List<Map.Entry<String, ResultDTO>> data = imageService.getResultByVideoName();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @DeleteMapping("/api/video")
    public ResponseEntity<String> deleteVideo(@RequestParam("video_name")String name){
        videoService.deleteVideo(name);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("/api/history")
    public ResponseEntity<PageHistory> getHistory(@RequestParam(value = "page", defaultValue = "0") int pageNum,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortString){
        Pageable pageable = PageRequest.of(pageNum,size, Sort.Direction.DESC, sortString);
        PageHistory result = imageService.getHistory(pageable);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/api/history")
    public ResponseEntity<String> saveHistory(@RequestParam("image_name") String imageName){
        imageService.saveHistory(imageName);
        return new ResponseEntity<>("",HttpStatus.OK);

    }
}
