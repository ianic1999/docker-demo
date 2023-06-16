package com.example.dockerapp.service;

import com.example.dockerapp.controller.dto.TeamDto;
import com.example.dockerapp.controller.dto.TeamRequest;
import com.example.dockerapp.domain.Team;
import com.example.dockerapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private static final String IMAGE_PREFIX = "/images/";

    private final TeamRepository teamRepository;
    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<TeamDto> get() {
        return teamRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamDto getById(Long id) {
        return teamRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("No team with id " + id));
    }

    @Transactional
    public TeamDto add(TeamRequest request) throws IOException {
        String imagePath = null;
        if (request.getLogo() != null) {
            imagePath = imageService.upload(request.getLogo());
        }
        Team team = new Team(request.getName(), imagePath);
        return toDto(teamRepository.save(team));
    }

    @Transactional
    public TeamDto update(Long id, TeamRequest request) throws IOException {
        Team team = teamRepository.findById(id)
                                  .orElseThrow(() -> new RuntimeException("No team with id " + id));
        if (request.getName() != null)
            team.setName(request.getName());
        if (request.getLogo() != null) {
            imageService.remove(team.getLogoPath());
            String imagePath = imageService.upload(request.getLogo());
            team.setLogoPath(imagePath);
        }
        return toDto(team);
    }

    @Transactional
    public void remove(Long id) {
        teamRepository.deleteById(id);
    }

    private TeamDto toDto(Team team) {
        return new TeamDto(team.getId(),
                           team.getName(),
                           team.getLogoPath() != null ? IMAGE_PREFIX + team.getLogoPath() : null);
    }
}
