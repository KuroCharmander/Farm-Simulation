package farmyard;

import javafx.scene.paint.Color;

/** Fox den for the fox to recuperate in. */
class FoxDen extends FarmObject {
    /** The default appearance of this fox den. */
    private static String DEFAULT_APPEARANCE = "/#########\\";

    static int xDen = 140;
    static int yDen = 60;

    /** Constructs a new fox den at static coordinates (xDen, yDen). */
    FoxDen() {
        super(DEFAULT_APPEARANCE, Color.BLUE, xDen, yDen);
    }
}
