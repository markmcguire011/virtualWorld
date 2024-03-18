public class Animation implements Action{
    /** Number of animation repeats. A zero indicates indefinite repeats. */
    private Animates animate;
    private int repeatCount;
    public Animation(Animates animate, int repeatCount) {
        this.animate = animate;
        this.repeatCount = repeatCount;
    }

    public void execute(EventScheduler scheduler) {
        animate.updateImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(animate, new Animation(animate, Math.max(this.repeatCount - 1, 0)), animate.getAnimationPeriod());
        }
    }
}
