package com.example.dockerapp.service;

import com.example.dockerapp.controller.dto.PlayerDto;
import com.example.dockerapp.controller.dto.PlayerRequest;
import com.example.dockerapp.domain.Player;
import com.example.dockerapp.domain.Team;
import com.example.dockerapp.repository.PlayerRepository;
import com.example.dockerapp.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<PlayerDto> getByTeam(Long teamId) {
        final Team team = teamRepository.findById(teamId)
                                        .orElseThrow(() -> new RuntimeException("No team with id " + teamId));

        return playerRepository.findByTeam(team)
                               .stream()
                               .map(this::toDto)
                               .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PlayerDto getById(Long id) {
        return playerRepository.findById(id)
                               .map(this::toDto)
                               .orElseThrow(() -> new RuntimeException("No player with id " + id));
    }

    @Transactional
    public PlayerDto add(PlayerRequest request) {
        final Team team = teamRepository.findById(request.getTeamId())
                                        .orElseThrow(() -> new RuntimeException("No team with id " + request.getTeamId()));

        Player player = new Player(request.getFirstName(), request.getLastName(), request.getNumber(), request.getPosition());
        team.addPlayer(player);

        return toDto(player);
    }

    @Transactional
    public PlayerDto update(Long playerId, PlayerRequest request) {
        Player player = playerRepository.findById(playerId)
                                        .orElseThrow(() -> new RuntimeException("No player with id " + playerId));
        player.setFirstName(request.getFirstName());
        player.setLastName(request.getLastName());
        player.setNumber(request.getNumber());
        player.setPosition(request.getPosition());

        return toDto(player);
    }

    @Transactional
    public void remove(Long id) {
        Player player = playerRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("No player with id " + id));
        final Team team = player.getTeam();
        team.removePlayer(player);
        playerRepository.deleteById(id);
    }

    private PlayerDto toDto(Player player) {
        return new PlayerDto(player.getId(),
                             player.getFirstName(),
                             player.getLastName(),
                             player.getNumber(),
                             player.getPosition());
    }
}
