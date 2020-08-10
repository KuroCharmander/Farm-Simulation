package farmyard;

import javafx.scene.paint.Color;

/** A Chicken */
public class Chicken extends FarmAnimal {
  /** The default appearance of this chicken. */
  private static String DEFAULT_APPEARANCE = "/'/>";

  /**
   * Constructs a new Chicken.
   *
   * @param x the x coordinate of this Chicken.
   * @param y the x coordinate of this Chicken.
   */
  public Chicken(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.RED, x, y);
  }

  /**
   * Randomly pick a piece of food to move towards or randomly move around. Sometimes will lay eggs.
   */
  @Override
  public void move() {
    super.move();
    // Every now and then lay an egg. (1.8% chance of laying an egg)
    if (Math.random() < 0.018) layEgg();
  }

  /** Lay an egg. */
  private void layEgg() {
    new Egg(this.x, this.y);
  }
}
