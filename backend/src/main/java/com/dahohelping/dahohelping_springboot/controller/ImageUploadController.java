package com.dahohelping.dahohelping_springboot.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageUploadController {

    private static final String UPLOAD_AVT = "C:/Users/DungManh Laptop/Downloads/JavaDoAn/webapp-nt-1/src/assets/avatars/";
    private static final String UPLOAD_PIC = "C:/Users/DungManh Laptop/Downloads/JavaDoAn/webapp-nt-1/src/assets/CardImages/";

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_AVT + file.getOriginalFilename());
            Files.write(path, bytes);
            return "Upload successful";
        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }

    @PostMapping("/uploads")
    public String uploadImages(@RequestParam("files") List<MultipartFile> files) {
        try {
            for (MultipartFile file : files) {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(UPLOAD_PIC + file.getOriginalFilename());
                Files.write(path, bytes);
            }
            return "Upload successful";
        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }
}
