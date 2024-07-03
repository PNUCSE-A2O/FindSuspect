package org.example.controller;

import org.example.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {
    @Autowired
    UploadService uploadService;
    private static String UPLOAD_DIR = "uploads/";
    @GetMapping("/")
    public String index(){
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartFile imageFile, MultipartFile videoFile, RedirectAttributes redirectAttributes) {
        if (imageFile.isEmpty() || videoFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:uploadStatus";
        }

        try {
            saveFile(imageFile);
            saveFile(videoFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadService.script();
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded both files!");

        return "redirect:/uploadStatus";
    }


    private void saveFile(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.write(path, bytes);
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
