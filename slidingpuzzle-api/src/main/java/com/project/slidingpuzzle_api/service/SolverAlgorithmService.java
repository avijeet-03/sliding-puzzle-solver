package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.model.PuzzleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SolverAlgorithmService {
    @Autowired
    MovementService movementService;

    public PuzzleState getRandomShuffledState(int size) {
        // do all the moves randomly
        PuzzleState currentPuzzleState = new PuzzleState(size);
        currentPuzzleState.setGrid(movementService.getRandomStartingState(currentPuzzleState.getGrid()));
        return currentPuzzleState;
    }

}
