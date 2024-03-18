import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AStarPathingStrategy implements PathingStrategy {

    /**
     * Return a list containing a single point representing the next step toward a goal
     * If the start is within reach of the goal, the returned list is empty.
     *
     * @param start the point to begin the search from
     * @param end the point to search for a point within reach of
     * @param canPassThrough a function that returns true if the given point is traversable
     * @param withinReach a function that returns true if both points are within reach of each other
     * @param potentialNeighbors a function that returns the neighbors of a given point, as a stream
     */
    public List<Point> computePath(
            Point start,
            Point end,
            Predicate<Point> canPassThrough,
            BiPredicate<Point, Point> withinReach,
            Function<Point, Stream<Point>> potentialNeighbors
    ) {
        List<Point> openSet = new ArrayList<>();
        List<Point> closedSet = new ArrayList<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Map<Point, Integer> gscore = new HashMap<>();

        if (withinReach.test(start, end)) {
            return new ArrayList<>();
        }

        openSet.add(start);
        cameFrom.put(start, null);
        gscore.put(start, 0);
        Point currentPoint = start;

        while (!openSet.isEmpty()){
            if (currentPoint.adjacentTo(end)){
                break;
            }
            Point minPoint = openSet.get(0);
            int minf = minPoint.manhattanDistanceTo(end) + gscore.get(currentPoint) + 1;
            openSet.remove(currentPoint);

            List<Point> neighbors = potentialNeighbors.apply(currentPoint)
                    .filter(canPassThrough)
                    .filter(p -> !closedSet.contains(p))
                    .toList();

            for (Point p : neighbors){
                int g = gscore.get(currentPoint) + 1;
                if (openSet.contains(p)) {
                    if (g < gscore.get(p)){
                        gscore.put(p, g);
                        cameFrom.put(p, currentPoint);
                    }
                } else {
                    gscore.put(p, g);
                    cameFrom.put(p, currentPoint);
                    openSet.add(p);
                }
            }
            for (Point p : openSet){
                if (p.manhattanDistanceTo(end) + gscore.get(currentPoint) + 1 < minf){
                    minf = p.manhattanDistanceTo(end) + gscore.get(currentPoint) + 1;
                    minPoint = p;
                }
            }
            closedSet.add(currentPoint);

            currentPoint = minPoint;
        }

        List<Point> path = new ArrayList<>();
        if (currentPoint.adjacentTo(end)) {
            while (cameFrom.get(currentPoint) != null){
                path.add(currentPoint);
                currentPoint = cameFrom.get(currentPoint);
            }
        }
        return path.reversed();
    }
}
