package com.project.slidingpuzzle_api.controller;

import com.project.slidingpuzzle_api.model.PuzzleState;
import com.project.slidingpuzzle_api.service.SolverAlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/puzzle")
public class SolverController {

    @Autowired
    SolverAlgorithmService solverAlgorithmService;

    @PostMapping("/solve")
    public ResponseEntity<Object> getSolvedSolution(@RequestBody PuzzleState puzzleState) {
        // if invalid state, return 400 error
        // if server error, return 500 error
        return ResponseEntity.ok("API setup is done");
    }

    @GetMapping("/shuffle")
    public ResponseEntity<Object> getRandomShuffledState(@RequestParam Integer size) {
        try {
            PuzzleState randomState = solverAlgorithmService.getRandomShuffledState(size);
            return ResponseEntity.ok(randomState);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process the request" + e.getMessage());
        }
    }
}
