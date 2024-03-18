import processing.core.PImage;

import java.util.List;

public abstract class Behaves extends Entity{
    private double behaviorPeriod;
    public Behaves(String id, Point position, List<PImage> images, double behaviorPeriod){
        super(id, position, images);
        this.behaviorPeriod = behaviorPeriod;

    }

    /** Schedules a single behavior update for the entity. */
    public void scheduleBehavior(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Behavior(this, world, imageLibrary), behaviorPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary){
        scheduleBehavior(scheduler, world, imageLibrary);
    }

    public void executeBehavior(World world, ImageLibrary imageLibrary, EventScheduler scheduler){};

    public double getBehaviorPeriod(){
        return behaviorPeriod;
    }

}
