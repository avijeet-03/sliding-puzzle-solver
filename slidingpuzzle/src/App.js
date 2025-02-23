import React, { useState, useEffect } from "react";
import { fetchShuffledGrid, fetchSolution } from "./api/SolverApi";
import PuzzleControls from "./component/PuzzleControls";
import PuzzleGrid from "./component/PuzzleGrid";
import "./App.css";
import Popup from "./component/Popup";


const SOLVED_GRID = [
  [1, 2, 3],
  [4, 5, 6],
  [7, 8, 0]
]

const App = () => {
  
  const [startingGrid, setStartingGrid] = useState([]);
  const [grid, setGrid] = useState([]);
  const [isSolved, setIsSolved] = useState(false);
  const [solutionSteps, setSolutionSteps] = useState([]);
  const [solutionStepIndex, setSolutionStepIndex] = useState(0);
  const [movesUsed, setMovesUsed] = useState(0);
  const [error, setError] = useState(null);

  useEffect(() => {
    handleShuffle(); // fetch a shuffled puzzle on load
  }, [])

  // fetch and set shuffled grid
  const handleShuffle = async () => {
    try {
      const shuffledGrid = await fetchShuffledGrid();
      setStartingGrid(shuffledGrid.grid);
      setGrid(shuffledGrid.grid);
      setIsSolved(false);
      setSolutionSteps([]);
      setSolutionStepIndex(0);
      setMovesUsed(0);
    } catch (error) {
      handleApiError(error);
    }
  }

  // Retry the same Game
  const handleRetryGame = async () => {
    setGrid(startingGrid);
    setMovesUsed(0);
  }

  // fetch solution and store all the steps
  const handleSolve = async () => {
    try {
      const steps = await fetchSolution(grid);
      setSolutionSteps(steps);
      setSolutionStepIndex(0);
    } catch (error) {
      handleApiError(error);
    }
  }

  // Navigate Left
  const handlePrevStep = async () => {
    if(solutionStepIndex > 0) {
      setSolutionStepIndex(solutionStepIndex - 1);
    }
  }

  // Navigate Right
  const handleNextStep = async () => {
    if (solutionStepIndex < solutionSteps.length - 1) {
      setSolutionStepIndex(solutionStepIndex + 1);
    }
  }

  // handle API error
  const handleApiError = async (error) => {
    if (error.response) {
      // server responded with an error
      const errorMessage = await error.response.text();
      setError(`Error: ${errorMessage}`);
    } else {
      setError("An unknown error occurred");
    }
  };

  // handle tile click for manual interaction 
  const handleTileClick = (row, col) => {
    let newGrid = [...grid.map((r) => [...r])]; // clone grid
    const [zeroRow, zeroCol] = findZero(newGrid);

    if ((Math.abs(zeroRow - row) === 1 && zeroCol === col) || (Math.abs(zeroCol - col) === 1 && zeroRow === row)) {
      // swap the cells
      setMovesUsed(movesUsed + 1);
      [newGrid[zeroRow][zeroCol], newGrid[row][col]] = [newGrid[row][col], newGrid[zeroRow][zeroCol]];
      setGrid(newGrid);
      checkIfSolved(newGrid);
    }
  }

  // find position of '0' the empty space
  const findZero = (grid) => {
    for (let i = 0; i < grid.length; i++) {
      for (let j = 0; j < grid[i].length; j++) {
        if (grid[i][j] === 0)
          return [i, j];
      }
    }
    return [-1, -1];
  }

  // check if puzzle is solved
  const checkIfSolved = (grid) => {
    if (JSON.stringify(grid) === JSON.stringify(SOLVED_GRID)) {
      setIsSolved(true);
    }
  }

  return (
    <div className="text-center mt-10">
      <div className="flex justify-center">
        <h1 className="text-2xl font-bold">Sliding Puzzle</h1>
      </div>
      <p className="text-green-500 font-bold text-xl">Moves Used: {movesUsed}</p>
      {isSolved && <p className="text-green-500 font-bold text-xl">🎉 Puzzle Solved! 🎉</p>}
      {error && <Popup message={error} onClose={() => setError(null)} />}
      <PuzzleGrid grid={grid} onTileClick={handleTileClick} />
      <PuzzleControls shuffle={handleShuffle} solve={handleSolve} retry={handleRetryGame}/>

      {solutionSteps.length > 0 && solutionSteps[solutionStepIndex] && (
        <>
          <h2 className="text-lg font-bold mt-6">Solution Steps</h2>
          <PuzzleGrid grid={solutionSteps[solutionStepIndex]} onTileClick={() => {}} />

          {/* Navigation Buttons */}
          <div className="mt-4 flex justify-center gap-4">
            <button onClick={handlePrevStep} disabled={solutionStepIndex === 0} className="px-4 py-2 bg-gray-400 rounded">
              ⬅️ Left
            </button>
            <button onClick={handleNextStep} disabled={solutionStepIndex === solutionSteps.length - 1} className="px-4 py-2 bg-blue-500 text-white rounded">
              ➡️ Right
            </button>
            <button> {solutionStepIndex} / {solutionSteps.length - 1} </button>
          </div>
        </>
      )}
    </div>
  );
};

export default App;