package com.project.slidingpuzzle_api.controller;

import com.project.slidingpuzzle_api.dto.PuzzleDTO;
import com.project.slidingpuzzle_api.exception.PuzzleTimeoutException;
import com.project.slidingpuzzle_api.service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/puzzle")
public class SolverController {

    @Autowired
    SolverService solverService;

    @PostMapping("/solve")
    public ResponseEntity<Object> getSolvedSolution(@RequestBody PuzzleDTO puzzleDTO) {
        // if invalid state, return 400 error
        if (!solverService.checkValidPuzzle(puzzleDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid configuration of incoming puzzle. Please check the Puzzle");
        }
        try {
            List<int[][]> solutionPath = solverService.getSolutionPath(puzzleDTO);
            return ResponseEntity.ok(solutionPath);
        } catch (PuzzleTimeoutException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred in the server. ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/shuffle")
    public ResponseEntity<Object> getRandomShuffledState(@RequestParam(defaultValue = "3") Integer size) {
        try {
            PuzzleDTO randomState = solverService.getRandomShuffledState(size);
            return ResponseEntity.ok(randomState);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to generate a random puzzle. ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/test-application-health")
    public ResponseEntity<String> getApplicationHealth() {
        return ResponseEntity.ok("The Application is up and Running");
    }
}
