package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PageHistory;
import org.example.service.ImageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HistoryController {
    private final ImageService imageService;
    @GetMapping("/api/history")
    public ResponseEntity<PageHistory> getHistory(@RequestParam(value = "page", defaultValue = "0") int pageNum,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "id") String sortString){
        Pageable pageable = PageRequest.of(pageNum,size, Sort.Direction.DESC, sortString);
        PageHistory result = imageService.getHistory(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/api/history")
    public ResponseEntity<String> saveHistory(@RequestParam("image_name") String imageName){
        imageService.saveHistory(imageName);
        return new ResponseEntity<>("",HttpStatus.OK);

    }

    @DeleteMapping("/api/history/{historyId}")
    public ResponseEntity<?> deleteHistory(@PathVariable("historyId") int historyId){
        imageService.deleteHistory(historyId);
        return new ResponseEntity<>("",HttpStatus.OK);
    }
}
