package com.example.dockerapp.controller;

import com.example.dockerapp.controller.dto.PlayerDto;
import com.example.dockerapp.controller.dto.PlayerRequest;
import com.example.dockerapp.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDto> add(@RequestBody PlayerRequest request) {
        return new ResponseEntity<>(
                playerService.add(request),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> update(@PathVariable Long id,
                                            @RequestBody PlayerRequest request) {
        return ResponseEntity.ok(playerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        playerService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
