import processing.core.PImage;

import java.util.List;
public class Fire extends Animates{
    public static final String FIRE_KEY = "fire";
    public static final int FIRE_PARSE_BEHAVIOR_PERIOD_INDEX = 0;

    public static final int FIRE_PARSE_ANIMATION_PERIOD_INDEX = 1;
    public static final int FIRE_PARSE_PROPERTY_COUNT = 2;

    private int time;
    public Fire(String id, Point position, List<PImage> images, double behaviorPeriod, double animationPeriod) {
        super(id, position, images, behaviorPeriod, animationPeriod);
        this.time = 0;
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        time = time + 1;
        if (!transformFire(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }

    public boolean transformFire(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (time == 7) {
            Entity rubble = new Rubble(Rubble.RUBBLE_KEY + "_" + getId(), getPosition(), imageLibrary.get(Rubble.RUBBLE_KEY));

            world.removeEntity(scheduler, this);

            world.addEntity(rubble);

            return true;
        }

        return false;
    }

    public void updateImage(){
        setImageIndex(getImageIndex() + 1);
    }
}
