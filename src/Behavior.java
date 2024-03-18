public class Behavior implements Action{

    private Behaves behave;
    private final World world;
    /** Image data that may be used by the action. */
    private final ImageLibrary imageLibrary;
    public Behavior(Behaves behave, World world, ImageLibrary imageLibrary) {
        this.behave = behave;
        this.world = world;
        this.imageLibrary = imageLibrary;
    }

    public void execute(EventScheduler scheduler) {
        behave.executeBehavior(world, imageLibrary, scheduler);
    }
}
