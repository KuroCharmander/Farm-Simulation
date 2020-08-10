package farmyard;

import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

/** A Farm Animal, i.e. a Living Thing that moves and digests food. */
public abstract class FarmAnimal extends LivingThing {
  /** The number of food this FarmAnimal ate. */
  int numberOfEatenFood;

  /**
   * Constructs a new LivingThing.
   *
   * @param appearance the appearance of this LivingThing.
   * @param colour the colour of this LivingThing.
   */
  public FarmAnimal(String appearance, Color colour, int x, int y) {
    super(appearance, colour, x, y);
    this.numberOfEatenFood = 0;
  }

  /** Randomly pick a piece of food to move towards or randomly move around. */
  public void move() {
    // If this FarmAnimal doesn't have a target food, then assign one randomly or randomly move
    // around.
    if (this.target == null) {
      if (AnimalFood.scatteredAnimalFood.size() > 0) {
        int random = ThreadLocalRandom.current().nextInt(0, AnimalFood.scatteredAnimalFood.size());
        this.target = AnimalFood.scatteredAnimalFood.get(random);
      } else {
        randomMovement();
      }
    } else { // This FarmAnimal has a target.
      moveCloserToFarmObject(this.target);
    }

    // If I reach the target, collect it and remove it from the corresponding uncollected Lists.
    if (this.target != null
        && this.target instanceof AnimalFood
        && this.x == this.target.x
        && this.y == this.target.y) {
      this.numberOfEatenFood++;
      AnimalFood.scatteredAnimalFood.remove(this.target);
      removeThisFarmObject(this.target);
      this.target = null;
    }

    // Figure out if this FarmAnimal will digest the food with a 10% chance if it ate something.
    if (this.numberOfEatenFood > 0 && Math.random() < 0.1) digest();
    if (Math.random() < 0.1) turnAround(); // Figure out if this FarmAnimal will turn around.
  }

  /** Digest a piece of food eaten and make manure out of it. */
  void digest() {
    new AnimalManure(this.x, this.y);
    this.numberOfEatenFood--;
  }
}
