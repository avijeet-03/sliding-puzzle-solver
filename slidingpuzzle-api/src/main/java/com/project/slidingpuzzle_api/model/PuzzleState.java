package com.project.slidingpuzzle_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PuzzleState {
    private int rows;
    private int[][] grid;

    public PuzzleState(int n) {
        this.rows = n;
        grid = new int[n][n];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j] = i * rows + j + 1;
            }
        }
        grid[rows - 1][rows - 1] = 0;
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
