package farmyard;

import javafx.scene.paint.Color;

/** A Pig */
public class Pig extends FarmAnimal {
  /** The default appearance of this pig. */
  private static String DEFAULT_APPEARANCE = ":(8)";

  /**
   * Constructs a new Pig.
   *
   * @param x the x coordinate of this Pig.
   * @param y the x coordinate of this Pig.
   */
  public Pig(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.PINK.darker().darker().darker(), x, y);
  }

  @Override
  void digest() { // Inherit Javadoc from FarmAnimal.
    if (this.numberOfEatenFood > 0) {
      // Manure looks different than default.
      new AnimalManure("*", this.x, this.y);
      this.numberOfEatenFood--;
    }
  }
}
