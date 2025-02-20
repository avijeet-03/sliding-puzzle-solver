package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.model.Puzzle;
import org.springframework.stereotype.Service;

@Service
public class MovementService {
    // this class should import the Puzzle and tweak the heuristic value with each move
    private static final Integer TOTAL_RANDOM_MOVES = 1000;
    private int dimension;
    private static final int[][] directions = {
            {-1, 0}, // UP
            {0, 1}, // RIGHT
            {1, 0}, // DOWN
            {0, -1} // LEFT
    };

    public Puzzle getRandomSolvableState(int size) {
        this.dimension = size;
        // since all random states are not solvable
        // we have to go from the starting state and do random moves backwards

        // keep track of the last move to avoid nullifying the previous move
        Puzzle puzzle = new Puzzle(size);
        int last = -1;
        for (int moves = 0; moves < TOTAL_RANDOM_MOVES; moves++) {
            int dir = (int)(Math.random() * 4);
            if (getToTheNextState(puzzle, dir, last)) {
                // update the last direction if this movement is successful
                last = dir;
            }
        }

        return puzzle;
    }

    public boolean getToTheNextState(Puzzle puzzle, int dir, int lastDir) {
        this.dimension = puzzle.getRows();

        if (lastDir != -1 && (dir != lastDir) && (dir % 2 == lastDir % 2)) {
            // this direction and last direction are opposite
            return false;
        }

        int emptyRow = puzzle.getEmptySpaceLocation()[0];
        int emptyCol = puzzle.getEmptySpaceLocation()[1];

        int nextEmptyRow = emptyRow + directions[dir][0];
        int nextEmptyCol = emptyCol + directions[dir][1];

        if (nextEmptyRow < 0 || nextEmptyRow >= puzzle.getRows() || nextEmptyCol < 0 || nextEmptyCol >= puzzle.getRows()) {
            return false;
        }
        // swapping can be done now
        int[] sourcePosition = new int[]{emptyRow, emptyCol};
        int[] targetPosition = new int[]{nextEmptyRow, nextEmptyCol};
        swap(puzzle.getGrid(), sourcePosition, targetPosition);

        // now update all the info of the received puzzle
        // update new empty space location
        puzzle.setEmptySpaceLocation(targetPosition);
        // update the heuristic value
        int difference = getHeuristicDelta(puzzle, sourcePosition, targetPosition);
        puzzle.setHeuristicValue(puzzle.getHeuristicValue() + difference);
        // update the far from source, we get one distance ahead
        puzzle.setFarFromSource(puzzle.getFarFromSource() + 1);

        return true;
    }

    private int getIndividualHeuristicContribution(int val, int row, int col) {
        int orgRow = (val - 1) / this.dimension;
        int orgCol = (val - 1) % this.dimension;

        return Math.abs(row - orgRow) + Math.abs(col - orgCol);
    }

    private int getHeuristicDelta(Puzzle puzzle, int[] sourcePosition, int[] targetPosition) {
        // 0 is at source and non-zero is at target
        // to calculate change of individual heuristic contribution to the total only for the non-zero state

        // swapping has been done, now the non-zero value is at source
        int val = puzzle.getGrid()[sourcePosition[0]][sourcePosition[1]];
        int previousContribution = getIndividualHeuristicContribution(val, targetPosition[0], targetPosition[1]);
        int currentContribution = getIndividualHeuristicContribution(val, sourcePosition[0], sourcePosition[1]);

        return currentContribution - previousContribution;
    }

    private void swap(int[][] grid, int[] sourcePosition, int[] targetPosition) {
        int temp = grid[sourcePosition[0]][sourcePosition[1]];
        grid[sourcePosition[0]][sourcePosition[1]] = grid[targetPosition[0]][targetPosition[1]];
        grid[targetPosition[0]][targetPosition[1]] = temp;
    }
}
