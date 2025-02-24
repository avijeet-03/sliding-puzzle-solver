package com.project.slidingpuzzle_api.controller;

import com.project.slidingpuzzle_api.dto.PuzzleDTO;
import com.project.slidingpuzzle_api.exception.PuzzleTimeoutException;
import com.project.slidingpuzzle_api.service.SolverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/puzzle")
public class SolverController {
    @Autowired
    SolverService solverService;

    @PostMapping("/solve")
    public ResponseEntity<Object> getSolvedSolution(@RequestBody PuzzleDTO puzzleDTO) {
        // if invalid state, return 400 error
        printMemoryUsage("Before Solving");
        if (!solverService.checkValidPuzzle(puzzleDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid configuration of incoming puzzle. Please check the Puzzle");
        }
        try {
            List<int[][]> solutionPath = solverService.getSolutionPath(puzzleDTO);
            printMemoryUsage("After solving");
            return ResponseEntity.ok(solutionPath);
        } catch (PuzzleTimeoutException e) {
            printMemoryUsage("After timeout error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            printMemoryUsage("After unexpected error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred in the server. ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/shuffle")
    public ResponseEntity<Object> getRandomShuffledState(@RequestParam(defaultValue = "3") Integer size) {
        printMemoryUsage("Before shuffling");
        try {
            printMemoryUsage("After shuffling");
            PuzzleDTO randomState = solverService.getRandomShuffledState(size);
            return ResponseEntity.ok(randomState);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to generate a random puzzle. ERROR: " + e.getMessage());
        }
    }

    @GetMapping("/test-application-health")
    public ResponseEntity<String> getApplicationHealth() {
        log.info("Application is up");
        return ResponseEntity.ok("The Application is up and Running");
    }

    private void printMemoryUsage(String stage) {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory() / (1024 * 1024); // Convert to MB
        long freeMemory = runtime.freeMemory() / (1024 * 1024);   // Convert to MB
        long usedMemory = totalMemory - freeMemory;

        log.info("🔹 Memory Usage [" + stage + "]");
        log.info("   🔸 Total Memory: " + totalMemory + " MB");
        log.info("   🔸 Free Memory: " + freeMemory + " MB");
        log.info("   🔸 Used Memory: " + usedMemory + " MB");
        log.info("-------------------------------------");
    }
}
