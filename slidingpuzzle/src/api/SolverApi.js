const BASE_API_URL = 
    process.env.NODE_ENV === "production"
    ? "https://sliding-puzzle-solver-api.onrender.com"
    : "http://localhost:9000"

// Fetch a random shuffled puzzle from the backend
export const fetchShuffledGrid = async () => {
    const response = await fetch(`${BASE_API_URL}/shuffle`);
    return response.json();
}

// fetch the solution steps from backend
export const fetchSolution = async (grid) => {
    const payload = {
        rows: grid.length,
        grid: grid
    };

    const response = await fetch(`${BASE_API_URL}/solve`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload) // send the current puzzle state
    });
    return response.json();
}