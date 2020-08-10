package farmyard;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/** An egg trap to trap the fox. */
public class EggTrap extends Egg {
  /** Stores all the egg traps. */
  static ArrayList<EggTrap> eggTraps = new ArrayList<>();

  /** The trap is active if it caught the fox. */
  boolean trapActive;

  /**
   * Constructs an egg trap for the fox.
   *
   * @param x the x coordinate of this EggTrap.
   * @param y the x coordinate of this EggTrap.
   */
  public EggTrap(int x, int y) {
    super(Color.GREEN, x, y);
    eggTraps.add(this);
  }
}
