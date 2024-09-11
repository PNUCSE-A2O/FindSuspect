package org.example.dto;

import java.util.List;

public class ResultDTO {
    
    public String video_name;
    public double similarity;
    public List<Double> original_top5;
    public List<Double> file_top5;
    public List<String> attr_words;
    public String time;

    public void setTime(String time) {
        this.time = time;
    }
}
