package org.example.dto;

import java.util.List;

public record SaveDataDto (String imageName, String time, List<Double> vector) {
}
