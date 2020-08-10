package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

/** A Fox that steals eggs. */
public class Fox extends LivingThing {
  /** The default appearance of this fox. */
  private static final String DEFAULT_APPEARANCE = "-^^,--,~";
  /** The appearance of the fox when it is trapped in an EggTrap. */
  private static final String TRAPPED_APPEARANCE = "-<$]==]~";

  /** Whether this Fox is trapped by an EggTrap. */
  private boolean trapped;
  /** How long this fox needs to recuperate in the fox den after being trapped by an EggTrap. */
  private int recuperate;

  /**
   * The fox den this fox lives in when the simulation starts and where it goes back to to
   * recuperate after being trapped by an EggTrap.
   */
  private FoxDen foxDen;

  /** The number of Eggs the fox stole. */
  private static int numberOfStolenEggs;

  /** Constructs a new Fox. */
  public Fox() {
    super(DEFAULT_APPEARANCE, Color.BROWN, FoxDen.xDen, FoxDen.yDen);
    foxDen = new FoxDen();
  }

  /** Causes this Fox to move in the farm-pen simulation. */
  @Override
  public void move() {
    // If the fox is trapped, then check if a Human released it or stay put.
    if (this.trapped) {
      trappedFox();
    } else {
      // If this fox doesn't have a target..
      if (this.target == null) {
        // If this fox still needs time to recuperate, stay there and decrease the time.
        if (this.recuperate > 0) {
          this.recuperate--;
          return;
        }
        // If there are Eggs in the simulation, try to steal one or stay/go back to the fox den.
        if (Egg.uncollectedEggs.size() > 0) {
          int random = ThreadLocalRandom.current().nextInt(Egg.uncollectedEggs.size());
          this.target = Egg.uncollectedEggs.get(random);
        } else {
          this.target = this.foxDen;
        }
      } else { // The fox has a target so move closer to it.
        moveCloserToFarmObject(this.target, 2);
      }

      int xT = this.target.x;
      int yT = this.target.y;
      // If this fox is within 1 unit of the target...
      if (this.target != null
          && (this.x == xT || this.x == xT + 1 || this.x == xT - 1)
          && (this.y == yT || this.y == yT + 1 || this.y == yT - 1)) {
        // If it is an egg trap, the trap activates and this fox will be trapped.
        if (this.target instanceof EggTrap) {
          EggTrap.eggTraps.get(EggTrap.eggTraps.indexOf(this.target)).trapActive = true;
          trappedFox();
          // If it is a regular egg, then steal it and move on.
        } else if (this.target instanceof Egg && !Human.myBasket.contains(this.target)) {
          Egg.uncollectedEggs.remove(this.target);
          numberOfStolenEggs++;
          removeThisFarmObject(this.target);
          this.target = null;
        } else { // If it is at the fox den then stay at the coordinates of the fox den.
          // Need to set the coordinates of the target or else the fox may not be hidden since it
          // moves 2 units at a time.
          setLocation(this.target.x, this.target.y);
          this.target = null;
        }
      }
      if (Math.random() < 0.1) turnAround();
    }
  }

  /** The fox is now trapped and checks if a Human has released it. */
  private void trappedFox() {
    this.appearance = TRAPPED_APPEARANCE;
    // Need to be the coordinates of the target or else the fox may not look like it is trapped
    // since it moves 2 units at a time.
    setLocation(this.target.x, this.target.y);

    // Check if a human is nearby to release this fox.
    for (int i = 0; i < FarmObject.allFarmObjects.size(); i++) {
      if (FarmObject.allFarmObjects.get(i) instanceof Human) {
        Human human = (Human) FarmObject.allFarmObjects.get(i);

        // If a human is within 1 unit of this fox, then the fox is released from its trap.
        // The trap is then removed and the fox goes back to its fox den to recuperate.
        if (this.target instanceof EggTrap
            && (this.x == human.x || this.x == human.x + 1 || this.x == human.x - 1)
            && (this.y == human.y || this.y == human.y + 1 || this.y == human.y - 1)) {
          this.appearance = DEFAULT_APPEARANCE;
          EggTrap.eggTraps.get(EggTrap.eggTraps.indexOf(this.target)).trapActive = false;
          EggTrap.eggTraps.remove(this.target);
          Egg.uncollectedEggs.remove(this.target);
          removeThisFarmObject(this.target);
          this.trapped = false;
          this.recuperate = 20;
          this.target = foxDen;
          return;
        }
      }
    }
  }

  /** Report the number of eggs stolen by this fox in the simulation. */
  public static void reportStolenEggs(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillText("Stolen Eggs: " + String.valueOf(numberOfStolenEggs), xBound / 6 * 10, yBound / 10);
  }
}
