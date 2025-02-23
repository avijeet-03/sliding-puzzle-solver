import React, { useState, useEffect } from "react";
import "../styles/PuzzleGrid.css"; // Import CSS for additional animations

const TILE_SIZE = 60; // Adjust tile size based on UI

const PuzzleGrid = ({ grid, onTileClick }) => {
  const [positions, setPositions] = useState({});

  useEffect(() => {
    // Calculate positions dynamically based on grid
    const newPositions = {};
    grid.flat().forEach((num, index) => {
      const row = Math.floor(index / 3);
      const col = index % 3;
      newPositions[num] = { top: row * TILE_SIZE, left: col * TILE_SIZE };
    });
    setPositions(newPositions);
  }, [grid]); // Recalculate positions when grid changes

  return (
    <div className="puzzle-container">
      {grid.flat().map((num, index) => {
        if (num === 0) return null; // Don't render the empty tile (0)

        return (
          <div
            key={num}
            className="tile"
            style={{
              transform: `translate(${positions[num]?.left}px, ${positions[num]?.top}px)`,
            }}
            onClick={() => onTileClick(Math.floor(index / 3), index % 3)}
          >
            {num}
          </div>
        );
      })}
    </div>
  );
};

export default PuzzleGrid;