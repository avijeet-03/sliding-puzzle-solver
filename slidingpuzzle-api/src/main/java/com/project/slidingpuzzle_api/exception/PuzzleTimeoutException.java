package com.project.slidingpuzzle_api.exception;

public class PuzzleTimeoutException extends RuntimeException {
    public PuzzleTimeoutException(String message) {
        super(message);
    }
}
