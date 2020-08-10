package farmyard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/** An egg that a farmer collects. */
public class Egg extends FarmObject {
  /** Stores all uncollected eggs. */
  static ArrayList<Egg> uncollectedEggs = new ArrayList<>();

  /** The default appearance of this Egg. */
  private static String DEFAULT_APPEARANCE = "o";

  /**
   * Constructs an egg.
   *
   * @param x the x coordinate of this Egg.
   * @param y the x coordinate of this Egg.
   */
  public Egg(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.ROSYBROWN, x, y);
    uncollectedEggs.add(this);
  }

  /**
   * Constructs an egg.
   *
   * @param colour the colour of this Egg.
   * @param x the x coordinate of this Egg.
   * @param y the x coordinate of this Egg.
   */
  public Egg(Color colour, int x, int y) {
    super(DEFAULT_APPEARANCE, colour, x, y);
    setLocation(x, y);
    uncollectedEggs.add(this);
  }
}
