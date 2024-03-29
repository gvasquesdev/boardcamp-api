package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.exceptions.ExceptionConflict;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

@Service
public class GameService {
    final GameRepository gamesRepository;

    GameService(GameRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public List<GameModel> findAll() {
        return gamesRepository.findAll();
    }

    public GameModel save(GameDTO dto) {
        GameModel game = new GameModel(dto);

        if (gamesRepository.existsByName(dto.getName())) {
            throw new ExceptionConflict("This game already exists!");
        }

        return gamesRepository.save(game);
    }
}
