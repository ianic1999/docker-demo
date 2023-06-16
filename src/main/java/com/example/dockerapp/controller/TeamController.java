package com.example.dockerapp.controller;

import com.example.dockerapp.controller.dto.PlayerDto;
import com.example.dockerapp.controller.dto.TeamDto;
import com.example.dockerapp.controller.dto.TeamRequest;
import com.example.dockerapp.service.PlayerService;
import com.example.dockerapp.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAll() {
        return ResponseEntity.ok(teamService.get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getById(id));
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<PlayerDto>> getPlayers(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getByTeam(id));
    }

    @PostMapping
    public ResponseEntity<TeamDto> add(@RequestParam String name,
                                       @RequestParam(required = false) MultipartFile logo) throws IOException {
        TeamRequest request = new TeamRequest(name, logo);
        return new ResponseEntity<>(teamService.add(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TeamDto> update(@PathVariable Long id,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) MultipartFile logo) throws IOException {
        TeamRequest request = new TeamRequest(name, logo);
        return ResponseEntity.ok(teamService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
