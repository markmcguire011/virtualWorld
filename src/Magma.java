import processing.core.PImage;

import java.util.List;

public class Magma extends Entity{
    public static final String MAGMA_KEY = "magma";

    public static final int MAGMA_PARSE_PROPERTY_COUNT = 0;

    public Magma(String id, Point position, List<PImage> images) {
        super(id, position, images);
    }
}
