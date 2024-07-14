package org.example.dto;

import java.io.File;

public record responseDto(File image, String time, int accuracy) {
}
