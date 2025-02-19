package com.project.slidingpuzzle_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/puzzle")
public class SolverController {

    @PostMapping("/solve")
    public ResponseEntity<Object> getSolvedSolution() {
        return ResponseEntity.ok("API setup is done");
    }

    @GetMapping("/shuffle")
    public ResponseEntity<Object> getRandomShuffledState() {
        return ResponseEntity.ok("Getting a shuffled state");
    }
}
