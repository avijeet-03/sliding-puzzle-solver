package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.model.PuzzleState;
import org.springframework.stereotype.Service;

@Service
public class MovementService {
    private int rows;
    private static final Integer TOTAL_RANDOM_MOVES = 1000;
    private int blankPosRow;
    private int blankPosCol;

    public int[][] getRandomStartingState(int[][] grid) {
        // here the incoming grid will be at the starting state only
        rows = grid.length;
        blankPosRow = rows - 1;
        blankPosCol = rows - 1;

        // keep track of the last move, so we don't end up cancelling the same move in next step
        // e.g. UP and then DOWN
        // 0: UP, 1: DOWN, 2: LEFT, 3: RIGHT

        for (int moves = 0; moves < TOTAL_RANDOM_MOVES; moves++) {
            int randomDirection = (int)(Math.random() * 4);
            int lastValidMove = -1;

            if (randomDirection == 0 && lastValidMove != 1 && moveUp(grid)) {
                lastValidMove = 0;
            } else if (randomDirection == 1 && lastValidMove != 0 && moveDown(grid)) {
                lastValidMove = 1;
            } else if (randomDirection == 2 && lastValidMove != 3 && moveLeft(grid)) {
                lastValidMove = 2;
            } else if (randomDirection == 3 && lastValidMove != 2 && moveRight(grid)) {
                lastValidMove = 3;
            }
        }
        return grid;
    }

    private boolean moveUp(int[][] grid) {
        // blank space is moved up
        if (blankPosRow == 0) return false;
        swap(grid, blankPosRow - 1, blankPosCol);
        return true;
    }

    private boolean moveDown(int[][] grid) {
        // blank space is moved down
        if (blankPosRow == rows - 1) return false;
        swap(grid, blankPosRow + 1, blankPosCol);
        return true;
    }

    private boolean moveLeft(int[][] grid) {
        // blank space is moved left
        if (blankPosCol == 0) return false;
        swap(grid, blankPosRow, blankPosCol - 1);
        return true;
    }

    private boolean moveRight(int[][] grid) {
        // blank space is moved right
        if (blankPosCol == rows - 1) return false;
        swap(grid, blankPosRow, blankPosCol + 1);
        return true;
    }

    private void swap(int[][] grid, int targetRow, int targetCol) {
        int temp = grid[blankPosRow][blankPosCol];
        grid[blankPosRow][blankPosCol] = grid[targetRow][targetCol];
        grid[targetRow][targetCol] = temp;

        // update the blank space position
        blankPosRow = targetRow;
        blankPosCol = targetCol;
    }
}
