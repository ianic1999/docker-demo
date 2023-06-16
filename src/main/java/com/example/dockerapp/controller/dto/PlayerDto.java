package com.example.dockerapp.controller.dto;

import com.example.dockerapp.domain.PlayerPosition;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlayerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int number;
    private PlayerPosition position;
}
