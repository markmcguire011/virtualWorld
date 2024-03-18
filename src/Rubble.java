import processing.core.PImage;

import java.util.List;

public class Rubble extends Entity{
    public static final String RUBBLE_KEY = "rubble";

    public static final int RUBBLE_PARSE_PROPERTY_COUNT = 0;

    public Rubble(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
