package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.dto.PuzzleDTO;
import com.project.slidingpuzzle_api.model.Puzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolverAlgorithmService {
    @Autowired
    MovementService movementService;

    public PuzzleDTO getRandomShuffledState(int size) {
        Puzzle puzzle = movementService.getRandomSolvableState(size);
        return new PuzzleDTO(size, puzzle.getGrid());
    }

}
