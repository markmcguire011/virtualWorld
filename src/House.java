import processing.core.PImage;

import java.util.List;

public class House extends Animates{
    public static final String HOUSE_KEY = "house";

    public static final int HOUSE_PARSE_PROPERTY_BEHAVIOR_PERIOD_INDEX = 0;
    public static final int HOUSE_PARSE_PROPERTY_ANIMATION_PERIOD_INDEX = 1;
    public static final int HOUSE_PARSE_PROPERTY_HEALTH_INDEX = 2;
    public static final int HOUSE_PARSE_PROPERTY_COUNT = 3;
    private static final int HOUSE_HEALTH = 4;

    private int health;

    public House(String id, Point position, List<PImage> images, double animationPeriod, double behaviorPeriod, int health) {
        super(id, position, images, behaviorPeriod, animationPeriod);
        this.health = HOUSE_HEALTH;
    }
    public boolean transformHouse(World world, EventScheduler scheduler, ImageLibrary imageLibrary) {
        if (health <= 0) {
            Entity fire = new Fire(Fire.FIRE_KEY + "_" + getId(), getPosition(), imageLibrary.get(Fire.FIRE_KEY), 1.0, 1.0);

            world.removeEntity(scheduler, this);

            world.addEntity(fire);

            fire.scheduleActions(scheduler, world, imageLibrary);

            return true;
        }

        return false;
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler) {
        if (!transformHouse(world, scheduler, imageLibrary)) {
            scheduleBehavior(scheduler, world, imageLibrary);
        }
    }
    public void updateImage(){
        setImageIndex(this.getImageIndex());
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
