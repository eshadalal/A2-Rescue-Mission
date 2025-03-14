package ca.mcmaster.se2aa4.island.team38;

import java.util.*;

public class PathFinder {

    // Directions for moving in a 2D grid: Right, Down, Left, Up
    private static final int[][] DIRECTIONS = { {0,1}, {1,0}, {0,-1}, {-1,0} };

    /**
     * Finds the shortest path from the emergency site to the nearest creek using BFS.
     * @param start - The position of the emergency site.
     * @param creeks - List of all creek positions.
     * @param obstacles - Set of obstacles (e.g., water, mountains).
     * @return List of positions representing the shortest path, or null if no path exists.
     */
    public static List<Position> findShortestPath(Position start, List<Position> creeks, Set<Position> obstacles) {
        Queue<List<Position>> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();

        // Initialize BFS with the starting position
        queue.add(Collections.singletonList(start));
        visited.add(start);

        while (!queue.isEmpty()) {
            List<Position> path = queue.poll();
            Position current = path.get(path.size() - 1);

            // If we reached a creek, return the path
            if (creeks.contains(current)) {
                return path;
            }

            // Explore all possible moves
            for (int[] dir : DIRECTIONS) {
                Position next = new Position(current.getX() + dir[0], current.getY() + dir[1]);

                if (!visited.contains(next) && !obstacles.contains(next)) {
                    visited.add(next);
                    List<Position> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(newPath);
                }
            }
        }

        return null; // No valid path found
    }
}
