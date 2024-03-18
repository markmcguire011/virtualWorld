import processing.core.PImage;

import java.util.List;

public abstract class Animates extends Behaves{
    private double animationPeriod;

    public Animates(String id, Point position, List<PImage> images, double behaviorPeriod, double animationPeriod){
        super(id, position, images, behaviorPeriod);
        this.animationPeriod = animationPeriod;
    }

    public void scheduleActions(EventScheduler scheduler, World world, ImageLibrary imageLibrary){
        scheduleAnimation(scheduler, world, imageLibrary);
        scheduleBehavior(scheduler, world, imageLibrary);
    }
    public void scheduleAnimation(EventScheduler scheduler, World world, ImageLibrary imageLibrary) {
        scheduler.scheduleEvent(this, new Animation(this, 0), animationPeriod);
    }

    public void updateImage(){};
    public double getAnimationPeriod() {
        return animationPeriod;
    }
}
