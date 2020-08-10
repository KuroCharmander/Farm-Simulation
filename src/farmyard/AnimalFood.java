package farmyard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/** Animal Food */
public class AnimalFood extends FarmObject {
  /** Stores all AnimalFood in this farmyard. */
  static ArrayList<AnimalFood> scatteredAnimalFood = new ArrayList<>();

  /** The default appearance of this AnimalFood. */
  private static String DEFAULT_APPEARANCE = "%";

  /**
   * Constructs a new AnimalFood.
   *
   * @param x the x coordinate of this AnimalFood.
   * @param y the y coordinate of this AnimalFood.
   */
  public AnimalFood(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.GRAY, x, y);
    scatteredAnimalFood.add(this);
  }

  /**
   * Causes this AnimalFood to be blown 1 unit up, down, left, right, or diagonal in the direction
   * that the wind is blowing.
   */
  public void blown() {
    setLocation(this.x + Wind.windBlowingRight(), this.y + Wind.windBlowingDown());
  }
}
