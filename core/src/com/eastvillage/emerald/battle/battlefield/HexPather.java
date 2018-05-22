package com.eastvillage.emerald.battle.battlefield;

import java.util.*;
import java.util.function.Function;

/** The HexPather class is used to find all moves a unit can do. It will use dijkstra's algorithm to find the shortest
 * path to all the hexes within range. An obstacle function can be defined to marked hexes as impassable. */
public class HexPather {

    private Hex start;
    private int depth;
    private Function<Hex, Boolean> isObstacleFunc;

    private HashMap<Hex, Integer> stepsTaken;
    private HashMap<Hex, Hex> cameFrom;

    /** The HexPather class is used to find all moves a unit can do. It will use dijkstra's algorithm to find the shortest
     * path to all the hexes within range. An obstacle function can be defined to marked hexes as impassable.
     * @param start the start hex.
     * @param depth the maximum number of steps taken. If negative, the algorithm will only stop if world surrounded
     * by impassable hexes.
     * @param isObstacleFunc a function that defines what hexes are impassable. */
    public HexPather(Hex start, int depth, Function<Hex, Boolean> isObstacleFunc) {
        this.start = start;
        this.depth = depth;
        this.isObstacleFunc = isObstacleFunc;

        dijkstra();
    }

    /** Map all reachable hexes using dijkstra's algorithm. Results are stored in {@code stepsTaken} and {@code cameFrom}. */
    private void dijkstra() {
        stepsTaken = new HashMap<>();
        cameFrom = new HashMap<>();

        HashSet<Hex> closedSet = new HashSet<>();
        TreeSet<Hex> openSet = new TreeSet<>((a, b) -> {
            if (a == b) return 0;
            int diff = stepsTaken.get(a) - stepsTaken.get(b);
            return diff > 0 ? 1 : -1;
        });

        openSet.add(start);
        stepsTaken.put(start, 0);

        while (!openSet.isEmpty()) {
            Hex cur = openSet.pollFirst();

            closedSet.add(cur);

            // Last step?
            if (stepsTaken.get(cur) == depth)
                continue;

            for (HexDirection direction : HexDirection.values()) {
                Hex neighbor = cur.add(direction.asHex());

                // Discard if occupied by obstacle
                if (isObstacleFunc.apply(neighbor)) {
                    closedSet.add(neighbor);
                    continue;
                }

                // Already evaluated?
                if (closedSet.contains(neighbor))
                    continue;

                // A new node found!
                if (!openSet.contains(neighbor))
                    openSet.add(neighbor);

                // A better exists path?
                int steps = stepsTaken.get(cur) + 1;
                if (steps >= stepsTaken.getOrDefault(neighbor, Integer.MAX_VALUE))
                    continue;

                // This is the best path to neighbor
                cameFrom.put(neighbor, cur);
                stepsTaken.put(neighbor, steps);
            }
        }
    }

    /** Retrace a path to a hex. The path will be a LinkedList, where the first element is the start hex and the last
     * node is {@code destination}.
     * If {@code destination} is unreachable this method returns null. */
    public LinkedList<Hex> getPath(Hex destination) {
        if (!isReachable(destination))
            return null;

        LinkedList<Hex> path = new LinkedList<>();
        Hex prev = cameFrom.get(destination);
        while (prev != null) {
            path.addFirst(destination);
            destination = prev;
            prev = cameFrom.get(destination);
        }

        path.addFirst(destination);

        return path;
    }

    public Hex getStart() {
        return start;
    }

    /** Returns the maximum depth this pather used to find its paths. */
    public int getDepth() {
        return depth;
    }

    /** Returns true if the pather found a path to a given hex. The start node is always reachable (even if
     * is was impassable by definition). */
    public boolean isReachable(Hex hex) {
        return start == hex || cameFrom.containsKey(hex);
    }

    /** Returns the number of steps required to reach {@code target}. Returns {@code Integer.MAX_VALUE} if target
     * was unreachable. */
    public int getStepsTo(Hex target) {
        return stepsTaken.getOrDefault(target, Integer.MAX_VALUE);
    }

    /** Returns the Hex that would be the previous step when pathing to hex. Returns null if hex was unreachable or
     * the start hex. */
    public Hex getPrev(Hex hex) {
        return cameFrom.get(hex);
    }
}
