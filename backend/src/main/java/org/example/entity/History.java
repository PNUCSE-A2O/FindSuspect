package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.example.dto.HistoryDTO;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String imageName;
    String imageCropped;
    String imageRectangle;
    @Lob
    String jsonData;
    @CreatedDate
    LocalDateTime createdAt;

    public History(String imageName, String imageCropped, String imageRectangle, String jsonData) {
        this.imageName = imageName;
        this.imageCropped = imageCropped;
        this.imageRectangle = imageRectangle;
        this.jsonData = jsonData;
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

    public String getJsonData() {
        return jsonData;
    }

    public HistoryDTO toDTO(){
        return new HistoryDTO(this.imageName, this.imageCropped, this.imageRectangle, this.jsonData);
    }
}
