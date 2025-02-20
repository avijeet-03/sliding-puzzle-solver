package com.project.slidingpuzzle_api.model;

import com.project.slidingpuzzle_api.dto.PuzzleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Puzzle implements Comparable<Puzzle> {
    private int rows;
    private int[][] grid;
    private int[] emptySpaceLocation;
    private int heuristicValue;
    // this denotes the original distance of the current state from the source
    private int farFromSource;

    public Puzzle(int n) {
        this.rows = n;
        grid = new int[n][n];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = i * rows + j + 1;
            }
        }
        grid[rows - 1][rows - 1] = 0;
        emptySpaceLocation = new int[2];
        emptySpaceLocation[0] = emptySpaceLocation[1] = rows - 1;

        // get the heuristic value and this heuristic value gets updated everytime a move is made
        // because we are starting from solved state, heuristic value at this stage will be 0
        this.heuristicValue = 0;
        this.farFromSource = 0;
    }

    public Puzzle(PuzzleDTO puzzleDTO) {
        this.rows = puzzleDTO.getRows();
        // do a deep copy of the grid inside puzzleDTO
        this.grid = new int[rows][rows];
        for (int i = 0; i < rows; i++)
            grid[i] = Arrays.copyOf(puzzleDTO.getGrid()[i], puzzleDTO.getGrid()[i].length);
        emptySpaceLocation = new int[2];

        this.heuristicValue = calculateManhattanDistance();
        // this is not zero, and it will be updated during the transition of the A* algorithm
        this.farFromSource = 0;
    }

    private int calculateManhattanDistance() {
        int total = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                int val = grid[i][j];
                // No need to calculate if it is the empty space
                if (val == 0) {
                    emptySpaceLocation[0] = i;
                    emptySpaceLocation[1] = j;
                    continue;
                }

                int actualRow = (val - 1) / rows;
                int actualCol = (val - 1) % rows;

                total += Math.abs(i - actualRow) + Math.abs(j - actualCol);
            }
        }
        return total;
    }

    @Override
    public int compareTo(Puzzle other) {
        return Integer.compare(this.farFromSource + this.heuristicValue, other.getFarFromSource() + other.getHeuristicValue());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < rows; i++) {
            sb.append('[');
            for (int j = 0; j < rows; j++) {
                sb.append(grid[i][j]);
                if (j != rows - 1)
                    sb.append(", ");
            }
            sb.append(']');
        }
        sb.append(']');

        return sb.toString();
    }
}
