package com.example.dockerapp.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TeamDto {
    private Long id;
    private String name;
    private String logo;
}
