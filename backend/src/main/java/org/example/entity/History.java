package org.example.entity;

import jakarta.persistence.*;
import org.example.dto.HistoryDTO;
import org.example.dto.ResultDTO;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String imageName;
    String imageCropped;
    String imageRectangle;
    @CreatedDate
    LocalDateTime createdAt;

    String videoImage;
    String videoName;
    double similarity;
    @ElementCollection
    List<Double> original_top5;
    @ElementCollection
    List<Double> file_top5;
    @ElementCollection
    List<String> attr_words;
    String time;

    public History(String imageName, String imageCropped, String imageRectangle, String video_image, ResultDTO result) {
        this.imageName = imageName;
        this.imageCropped = imageCropped;
        this.imageRectangle = imageRectangle;
        this.videoImage = video_image;
        this.videoName = result.getVideoName();
        this.similarity = result.getSimilarity();
        this.original_top5 = result.getOriginalTop5();
        this.file_top5=result.getFileTop5();
        this.attr_words = result.getAttrWords();
        this.time = result.getTime();
    }

    public History() {

    }

    public String getImageName() {
        return imageName;
    }

    public String getImageCropped() {
        return imageCropped;
    }

    public String getImageRectangle() {
        return imageRectangle;
    }


    public HistoryDTO toDTO(){
        return new HistoryDTO(id, imageName, imageCropped, imageRectangle,
                 videoImage, videoName, similarity,
                 original_top5, file_top5, attr_words, time);
    }
}
