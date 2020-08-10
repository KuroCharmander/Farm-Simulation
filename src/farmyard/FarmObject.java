package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/** FarmObjects in this simulation. */
public abstract class FarmObject {
  /** Stores all the FarmObject objects in this farmyard. */
  public static ArrayList<FarmObject> allFarmObjects = new ArrayList<>();

  /** How this FarmObject appears on the screen. */
  String appearance;

  /** This FarmObject's first coordinate (horizontal movement). */
  int x;
  /** This FarmObject's second coordinate (vertical movement). */
  int y;

  /** The colour of this FarmObject. */
  private Color colour;

  // (int)(640/6) columns, (int)(480/10) rows.
  /** x bound of the farm. */
  static int xBound = 480;
  /** y bound of the farm. */
  static int yBound = 640;

  /**
   * Constructs a new FarmObject.
   *
   * @param appearance the appearance of this FarmObject.
   * @param colour the colour of this FarmObject.
   * @param x the x coordinate of this FarmObject.
   * @param y the y coordinate of this FarmObject.
   */
  public FarmObject(String appearance, Color colour, int x, int y) {
    this.appearance = appearance;
    this.colour = colour;
    setLocation(x, y);
    allFarmObjects.add(this);
  }

  /**
   * Set this FarmObject's location.
   *
   * @param x the x coordinate of this FarmObject.
   * @param y the y coordinate of this FarmObject.
   */
  void setLocation(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Draws the given string in the given graphics context at its given location.
   *
   * @param g the graphics context in which to draw the string.
   */
  public void draw(GraphicsContext g) {
    g.setFill(this.colour);
    // (int)(640/6) columns, (int)(480/10) rows of the coordinate system.
    g.fillText(this.appearance, this.x * 6, this.y * 10);
  }

  /**
   * Remove FarmObject farmObject from the List of FarmObject in this farmyard.
   *
   * @param farmObject the FarmObject that will be removed from the FarmObject list.
   */
  void removeThisFarmObject(FarmObject farmObject) {
    FarmObject.allFarmObjects.remove(farmObject);
  }
}
