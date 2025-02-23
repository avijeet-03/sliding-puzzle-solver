const BASE_URL = "http://localhost:9000/api/puzzle";

// Fetch a random shuffled puzzle from the backend
export const fetchShuffledGrid = async () => {
    const response = await fetch(`${BASE_URL}/shuffle`);
    return response.json();
}

// fetch the solution steps from backend
export const fetchSolution = async (grid) => {
    const payload = {
        rows: grid.length,
        grid: grid
    };

    const response = await fetch(`${BASE_URL}/solve`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload) // send the current puzzle state
    });
    return response.json();
}