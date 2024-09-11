package org.example.dto;

import java.util.Map;

public record HistoryDTO(
        String imageName,
        String imageCropped,
        String imageRectangle,
        String jsonData) {
}
