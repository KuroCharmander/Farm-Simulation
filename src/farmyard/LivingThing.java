package farmyard;

import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

/** A Living Thing, i.e. an organism that moves and can interact with other FarmObjects. */
public abstract class LivingThing extends FarmObject {
  /** Indicates whether this LivingThing is moving right. */
  private boolean goingRight;
  /** The forward appearance of this LivingThing. */
  private String forward;
  /** The reverse appearance of this LivingThing. */
  private String reverse;
  /** This LivingThing will move to collect this target FarmObject. */
  FarmObject target;

  /**
   * Constructs a new LivingThing.
   *
   * @param appearance the appearance of this LivingThing.
   * @param colour the colour of this LivingThing.
   * @param x the x coordinate of this Egg.
   * @param y the x coordinate of this Egg.
   */
  public LivingThing(String appearance, Color colour, int x, int y) {
    super(appearance, colour, x, y);
    this.goingRight = true;
    this.forward = this.appearance;
    this.reverse = reverseAppearance();
  }

  /** Build and initialize this LivingThing's default reverse appearance. */
  private String reverseAppearance() {
    StringBuilder reverse = new StringBuilder();
    for (int i = appearance.length() - 1; i >= 0; i--) {
      switch (appearance.charAt(i)) {
        case '\\':
          reverse.append('/');
          break;
        case '/':
          reverse.append('\\');
          break;
        case ')':
          reverse.append('(');
          break;
        case '(':
          reverse.append(')');
          break;
        case '>':
          reverse.append('<');
          break;
        case '<':
          reverse.append('>');
          break;
        case '}':
          reverse.append('{');
          break;
        case '{':
          reverse.append('}');
          break;
        case '[':
          reverse.append(']');
          break;
        case ']':
          reverse.append('[');
          break;
        default:
          reverse.append(this.appearance.charAt(i));
          break;
      }
    }
    return reverse.toString();
  }

  /** Turns this LivingThing around, causing it to reverse direction. */
  void turnAround() {
    this.goingRight = !goingRight;
    // If this LivingThing is going right, then reverse its appearance;
    // otherwise change appearance back to its forward appearance.
    this.appearance = (this.goingRight) ? this.reverse : this.forward;
  }

  /** Cause this LivingThing to move. */
  public abstract void move();

  /**
   * This LivingThing will move 1 unit closer to the FarmObject.
   *
   * @param farmObject the FarmObject this LivingThing wants to move closer to.
   */
  void moveCloserToFarmObject(FarmObject farmObject) {
    moveCloserToFarmObject(farmObject, 1);
  }

  /**
   * This LivingThing will move speed units closer to the FarmObject.
   *
   * @param farmObject the FarmObject this LivingThing wants to move closer to.
   * @param speed the speed this LivingThing is moving at.
   */
  void moveCloserToFarmObject(FarmObject farmObject, int speed) {
    if (this.x > farmObject.x) {
      this.x -= speed;
    } else if (this.x < farmObject.x) {
      this.x += speed;
    }
    if (this.y > farmObject.y) {
      this.y -= speed;
    } else if (this.y < farmObject.y) {
      this.y += speed;
    }
  }

  /** Randomly move this LivingThing 1 unit left, right, up, down, or diagonally. */
  void randomMovement() {
    int newX, newY;
    do {
      // random int between -1 (inclusive) and 2 (exclusive)
      newX = ThreadLocalRandom.current().nextInt(-1, 2) + this.x;
      newY = ThreadLocalRandom.current().nextInt(-1, 2) + this.y;
      // (int)(640/6) columns, (int)(480/10) rows. Move in the bounds of the window.
    } while (newX < 0 || newX > xBound || newY < 0 || newY > yBound);
    setLocation(newX, newY);
  }
}
