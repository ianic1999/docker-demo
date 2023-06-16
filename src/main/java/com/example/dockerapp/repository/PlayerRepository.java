package com.example.dockerapp.repository;

import com.example.dockerapp.domain.Player;
import com.example.dockerapp.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByTeam(Team team);
}
