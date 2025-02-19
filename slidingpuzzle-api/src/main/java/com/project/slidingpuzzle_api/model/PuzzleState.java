package com.project.slidingpuzzle_api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuzzleState {
    private int rows;
    private int[][] grid;

    public PuzzleState(int n) {
        this.rows = n;
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
