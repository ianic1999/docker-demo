package com.example.dockerapp.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    private String logoPath;

    public Team(String name,
                String logo) {
        this.name = name;
        this.logoPath = logo;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Player> players = new ArrayList<>();

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        player.setTeam(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
