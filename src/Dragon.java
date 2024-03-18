import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Dragon extends Animates{
    public static final String DRAGON_KEY = "dragon";

    public static final int DRAGON_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int DRAGON_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int DRAGON_PARSE_PROPERTY_COUNT = 2;
    public static final double DRAGON_BEHAVIOR_PERIOD = 1.0;
    public static final double DRAGON_ANIMATION_PERIOD = 1.0;

    public Dragon(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod) {
        super(id, position, images, behaviorPeriod, animationPeriod);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        Optional<Entity> dragonTarget = findDragonTarget(world);
        if (world.getBackgroundCell(this.getPosition()).getId().contains("grass")){
            world.setBackgroundCell(this.getPosition(), new Background("scorched", imageLibrary.get("scorched"), 0));
        } else if (world.getBackgroundCell(this.getPosition()).getId().contains("water")) {
            world.setBackgroundCell(this.getPosition(), new Background("magma", imageLibrary.get("magma"), NumberUtil.getRandomInt(0, 3)));
        }
        if (dragonTarget.isEmpty() || !moveToDragon(world, dragonTarget.get(), scheduler, imageLibrary) || !transformDragon(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public Optional<Entity> findDragonTarget(World world) {
        List<Class<?>> potentialTargets;

        potentialTargets = List.of(House.class);


        return world.findNearest(getPosition(), potentialTargets);
    }

    public boolean moveToDragon(World world, Entity target, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (getPosition().adjacentTo(target.getPosition())) {
            if (target instanceof House house) {
                int health = house.getHealth();
                if (health >= 1) {
                    house.setHealth(house.getHealth() - 1);
                    house.setImageIndex(house.getImageIndex() + 1);
                }
            }
            return true;
        } else {
            Point nextPos = nextPositionDragon(world, target.getPosition());

            if (!getPosition().equals(nextPos)) {
                world.moveEntity(scheduler, this, nextPos);
            }

            return false;
        }
    }

    public boolean transformDragon(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        Dragon dragon = new Dragon(getId(), getPosition(), imageLibrary.get(DRAGON_KEY), getAnimationPeriod(), getBehaviorPeriod());

        world.removeEntity(scheduler, this);

        world.addEntity(dragon);
        dragon.scheduleActions(scheduler, world, imageLibrary);

        return true;
    }

    public Point nextPositionDragon(World world, Point destination) {
        // Differences between the destination and current position along each axis
        PathingStrategy pathingStrategy = new AStarPathingStrategy();

        List<Point> path = pathingStrategy.computePath(
                this.getPosition(),
                destination,
                p -> world.inBounds(p) && (!world.isOccupied(p) || (world.getOccupant(p).isPresent() && world.getOccupant(p).get().getClass() == Water.class)),
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
