package com.boardcamp.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boardcamp.api.models.GameModel;

@Repository
public interface GameRepository extends JpaRepository<GameModel, UUID> {
    GameModel findByName(String name);
}
