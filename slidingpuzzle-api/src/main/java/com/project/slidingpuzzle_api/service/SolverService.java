package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.dto.PuzzleDTO;
import com.project.slidingpuzzle_api.model.Puzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class SolverAlgorithmService {
    @Autowired
    MovementService movementService;

    public PuzzleDTO getRandomShuffledState(int size) {
        Puzzle puzzle = movementService.getRandomSolvableState(size);
        return new PuzzleDTO(size, puzzle.getGrid());
    }

    public boolean checkValidPuzzle(PuzzleDTO puzzleDTO) {
        HashSet<Integer> hashSet = new HashSet<>();
        int dimension = puzzleDTO.getRows();
        int lowestValue = dimension * dimension + 1;
        int greatestValue = -1;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int val = puzzleDTO.getGrid()[i][j];
                hashSet.add(val);
                lowestValue = Math.min(lowestValue, val);
                greatestValue = Math.max(greatestValue, val);
            }
        }
        return (lowestValue == 0 && greatestValue == dimension * dimension - 1 && hashSet.size() == dimension * dimension);
    }
}
