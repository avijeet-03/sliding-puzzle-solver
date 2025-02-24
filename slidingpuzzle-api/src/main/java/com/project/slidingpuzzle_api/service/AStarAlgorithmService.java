package com.project.slidingpuzzle_api.service;

import com.project.slidingpuzzle_api.exception.PuzzleTimeoutException;
import com.project.slidingpuzzle_api.model.MovementConstants;
import com.project.slidingpuzzle_api.model.Puzzle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class AStarAlgorithmService {

    @Autowired
    MovementService movementService;

    private final String[] MOVEMENT_DECIDER = {MovementConstants.UP, MovementConstants.RIGHT, MovementConstants.DOWN, MovementConstants.LEFT};

    public List<int[][]> getShortestPathSolution(Puzzle puzzle) {
        // declare a priority queue here
        PriorityQueue<Puzzle> pq = new PriorityQueue<>();
        pq.add(puzzle);
        // at the end we will be having our solution object
        // from this solution object, we trace down all the
        Puzzle targetPuzzleState = null;

        while (!pq.isEmpty()) {
            Puzzle currState = pq.poll();
            if (currState.isRelaxed()) {
                continue;
            }
            currState.setRelaxed(true);
            if (currState.getHeuristicValue() == 0) {
                targetPuzzleState = new Puzzle(currState);
                break;
            }
            if (currState.getFarFromSource() > 15) {
                throw new PuzzleTimeoutException("Puzzle is VERY HARD for a normal computer to solve");
            }
            // now for each of its adjacent, add it to the
            for (int dir = 0; dir < 4; dir++) {
                Puzzle adjacentState = new Puzzle(currState);
                if (movementService.getToTheNextState(adjacentState, dir, -1)) {
                    // Puzzle transformed to adjacent stage
                    adjacentState.setParentPuzzle(currState);
                    adjacentState.setMoveType(MOVEMENT_DECIDER[dir]);
                    // all other fields got updated in the movement service
                    pq.add(adjacentState);
                }
            }
        }

        // now we need to trace back the steps from the final State

        return getListOfTransitionDetails(targetPuzzleState);
    }

    private List<int[][]> getListOfTransitionDetails(Puzzle targetPuzzleState) {
        List<int[][]> results = new ArrayList<>();
        while (null != targetPuzzleState) {
            // add this to the result
            results.add(targetPuzzleState.getGrid());
            // trace back to the parent of this current state
            targetPuzzleState = targetPuzzleState.getParentPuzzle();
        }

        // reverse the list, because we need from source to target
        Collections.reverse(results);

        return results;
    }
}
