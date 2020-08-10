package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/** A Human */
public class Human extends LivingThing {
  /** Basket to put all the collected Eggs in. */
  static ArrayList<Egg> myBasket = new ArrayList<>();
  /** All the collected manure. */
  private static ArrayList<AnimalManure> collectedManure = new ArrayList<>();

  /** The default appearance of this human. */
  private static String DEFAULT_APPEARANCE = "human";

  /**
   * Constructs a new Human.
   *
   * @param x the x coordinate of this Human.
   * @param y the x coordinate of this Human.
   */
  public Human(int x, int y) {
    super(DEFAULT_APPEARANCE, Color.SANDYBROWN, x, y);
  }

  /** Causes human to drop down 4 pieces of food all around. */
  private void dropFood() {
    // Drops food in the 4 corners of this human's coordinates.
    for (int i = -1; i <= 1; i += 2) {
      for (int j = -1; j <= 1; j += 2) {
        new AnimalFood(this.x + i, this.y + j);
      }
    }
  }

  /** Causes this Human to move in the farm-pen simulation. */
  @Override
  public void move() {
    // If I don't have a target, then get a target or randomly move around.
    if (this.target == null) {
      // If there are traps set up and if I randomly want to check...
      if (EggTrap.eggTraps.size() > 0 && Math.random() < 0.15) {
        // If there is an active trap, then go to the trap and release the fox.
        for (int i = 0; i < EggTrap.eggTraps.size(); i++) {
          EggTrap trap = EggTrap.eggTraps.get(i);
          if (trap.trapActive) this.target = trap;
        }
      } else { // There are no traps set up or I don't want to check...
        int numberOfManure =
            (AnimalManure.animalManures != null) ? AnimalManure.animalManures.size() : 0;
        int numberOfUncollectedEggs =
            (Egg.uncollectedEggs != null) ? Egg.uncollectedEggs.size() : 0;

        // Too much manure around or if there are no eggs left to collect, randomly pick a manure to
        // clean up.
        if (numberOfManure > 10 || (numberOfUncollectedEggs == 0 && numberOfManure > 0)) {
          int random = ThreadLocalRandom.current().nextInt(0, numberOfManure);
          this.target = AnimalManure.animalManures.get(random);
        } else if (numberOfUncollectedEggs > 0) { // Collect the eggs
          int random = ThreadLocalRandom.current().nextInt(0, numberOfUncollectedEggs);
          this.target = Egg.uncollectedEggs.get(random);
        } else { // If there's nothing to do, randomly move around.
          randomMovement();
        }
      }
    } else { // I have a target so move closer to it.
      moveCloserToFarmObject(this.target);
    }

    // If I reach the target...
    if (this.target != null && this.x == this.target.x && this.y == this.target.y) {
      // If the target is not an egg trap, then collect it and remove it from the corresponding
      // uncollected Lists.
      if (!(this.target instanceof EggTrap)) {
        if (this.target instanceof Egg) {
          Egg.uncollectedEggs.remove(this.target);
          myBasket.add((Egg) this.target);
        } else if (this.target instanceof AnimalManure) {
          AnimalManure.animalManures.remove(this.target);
          collectedManure.add((AnimalManure) this.target);
        }
        removeThisFarmObject(this.target);
      }
      // I already collected the target or it is just an egg trap so I don't do anything with it.
      this.target = null;
    }

    // Drop food only if there isn't a lot of food, manure AND eggs around since the place needs to
    // be cleaned up first.
    if (AnimalFood.scatteredAnimalFood.size() < 10
        && AnimalManure.animalManures.size() < 15
        && Egg.uncollectedEggs.size() < 8
        && Math.random() < 0.02) dropFood();
    if (Math.random() < 0.2) turnAround(); // Figure out if I should turn around.
    // If there aren't a lot of traps, then figure out if I should set one up.
    if (EggTrap.eggTraps.size() < 5 && Math.random() < 0.02){
      new EggTrap(this.x, this.y);
    }
  }

  /** Report the number of collected eggs or manure in the simulation. */
  public static void reportStock(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillText("Eggs: " + myBasket.size(), xBound / 6, yBound / 10);
    gc.fillText("Manure: " + collectedManure.size(), xBound / 6, yBound / 8);
  }
}
