package com.example.dockerapp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Data
public class TeamRequest {
    private String name;
    private MultipartFile logo;
}
