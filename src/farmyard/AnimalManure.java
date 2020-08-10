package farmyard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/** Animal Manure */
public class AnimalManure extends FarmObject {
  /** Stores all manure created in this farmyard. */
  static ArrayList<AnimalManure> animalManures = new ArrayList<>();

  /** The default appearance of this AnimalManure. */
  private static String DEFAULT_APPEARANCE = ".";

  /**
   * Constructs a new feces.
   *
   * @param x the x coordinate of this AnimalManure.
   * @param y the x coordinate of this AnimalManure.
   */
  public AnimalManure(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.BLACK, x, y);
    animalManures.add(this);
  }

  /**
   * Constructs a new feces.
   *
   * @param appearance the appearance of this AnimalManure.
   * @param x the x coordinate of this AnimalManure.
   * @param y the x coordinate of this AnimalManure.
   */
  public AnimalManure(String appearance, int x, int y) {
    super(appearance, Color.BLACK, x, y);
    animalManures.add(this);
  }
}
