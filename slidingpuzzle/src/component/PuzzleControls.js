import React from "react";

const PuzzleControls = ({ shuffle, solve, retry}) => {
  return (
    <div className="flex gap-4 mt-4">
      <button onClick={shuffle} className="bg-yellow-400 px-4 py-2 rounded">🔀 Start a new Game</button>
      <button onClick={retry} className="bg-yellow-400 px-4 py-2 rounded">🔄 Retry Current Game</button>
      <button onClick={solve} className="bg-green-500 px-4 py-2 rounded text-white">🧩 Check Solution</button>
    </div>
  );
};

export default PuzzleControls;