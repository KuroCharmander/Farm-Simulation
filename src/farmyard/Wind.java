package farmyard;

/**
 * If the wind was last blowing up then it is 30% likely to keep blowing up. Same for left/right.
 */
class Wind {

  // Was the wind last blowing up or down?
  private static int lastDown = 0;
  // Was the wind last blowing left or right?
  private static int lastRight = 0;

  /**
   * Determine if the wind is blowing or not. Keep blowing in the same direction 30% the time, turn
   * around 10% of the time or otherwise no wind.
   *
   * @return -1, 0 or 1 to determine the direction of the wind blowing.
   */
  private static int windBlowingDirection(int direction) {
    double random = Math.random();
    if (direction != 0) { // If the wind was already blowing...
      if (random < 0.1) { // Turn around 10% of the time.
        direction = -direction;
      } else if (random > 0.4) { // Stop blowing 60% of the time. (100% - 10% - 30%)
        direction = 0;
      } // Nothing reassigned for else (i.e. wind blew in the same direction)
    } else { // If the wind was not blowing...
      if (random < 0.1) { // Wind blows left 10% of the time.
        direction = -1;
      } else if (random > 0.9) { // Wind blows right 10% of the time.
        direction = 1;
      } // Nothing reassigned for else (i.e. wind will stop blowing)
    }
    return direction;
  }

  /**
   * Determine if the wind is blowing down or not. Keep blowing in the same direction 30% the time,
   * turn around 10% of the time or otherwise no wind.
   *
   * @return 1 if the wind is blowing down, -1 if the wind is blowing up, or 0 if the wind isn't
   *     blowing.
   */
  static int windBlowingDown() {
    lastDown = windBlowingDirection(lastDown);
    return lastDown;
  }

  /**
   * Determine if the wind is blowing right or not. Keep blowing in the same direction 30% the time,
   * turn around 10% of the time or otherwise no wind.
   *
   * @return 1 if the wind is blowing right, -1 if the wind is blowing left, or 0 if the wind isn't
   *     blowing.
   */
  static int windBlowingRight() {
    lastRight = windBlowingDirection(lastRight);
    return lastRight;
  }
}
