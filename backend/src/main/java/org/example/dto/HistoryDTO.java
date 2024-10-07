package org.example.dto;

import jakarta.persistence.ElementCollection;
import org.springframework.data.annotation.CreatedDate;

import java.util.List;
import java.util.Map;

public record HistoryDTO(
        int id,
        String imageName,
        String imageCropped,
        String imageRectangle,
        String video_image,
        String video_name,
        double similarity,
        List<Double> original_top5,
        List<Double> file_top5,
        List<String> attr_words,
        String time){
}
