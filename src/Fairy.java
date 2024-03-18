import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Fairy extends Animates {
    public static final String FAIRY_KEY = "fairy";

    public static final int FAIRY_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int FAIRY_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int FAIRY_PARSE_PROPERTY_COUNT = 2;

    public Fairy(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod, animationPeriod);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest(getPosition(), new ArrayList<>(List.of(Stump.class)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().getPosition();

            if (moveToFairy(world, fairyTarget.get(), scheduler)) {
                Behaves sapling = new Sapling(Sapling.SAPLING_KEY + "_" + fairyTarget.get().getId(), tgtPos, imageLibrary.get(Sapling.SAPLING_KEY));

                world.addEntity(sapling);
                sapling.scheduleActions(scheduler, world, imageLibrary);
            }
        }

        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public boolean moveToFairy(World world, Entity target, EventScheduler scheduler) {
        if (getPosition().adjacentTo(target.getPosition())) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = nextPositionFairy(world, target.getPosition());
            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPositionFairy(World world, Point destination) {

        PathingStrategy pathingStrategy = new AStarPathingStrategy();

        List<Point> path = pathingStrategy.computePath(
                this.getPosition(),
                destination,
                p -> world.inBounds(p) && !world.isOccupied(p),
                Point::adjacentTo,
                PathingStrategy.CARDINAL_NEIGHBORS);
        if (!path.isEmpty()){
            return path.getFirst();
        } else{
            return this.getPosition();
        }
    }

    public void updateImage(){
        setImageIndex(getImageIndex() + 1);
    }
}
