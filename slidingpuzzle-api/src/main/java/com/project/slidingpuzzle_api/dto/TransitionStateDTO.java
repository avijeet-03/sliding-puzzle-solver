package com.project.slidingpuzzle_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransitionStateDTO {
    private String moveType;
    private int[][] sourceGrid;
    private int[][] targetGrid;
}
