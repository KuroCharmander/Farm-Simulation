package farm_simulation;

import farmyard.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.ThreadLocalRandom;

/** Our take on the "classical" game Farm Ville */
public class Main extends Application {

  /** The width of a character. */
  private static final int charWidth = 4;
  /** The height of a character. */
  private static final int charHeight = 9;
  /** The number of columns in the simulation. */
  private static final int column = 640 / charWidth;
  /** The number of rows in the simulation. */
  private static final int row = 480 / charHeight;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("FarmVille");

    Group root = new Group();
    Scene theScene = new Scene(root);
    primaryStage.setScene(theScene);
    Canvas canvas = new Canvas(1024, 720);
    root.getChildren().add(canvas);

    GraphicsContext gc = canvas.getGraphicsContext2D();

    constructLivingThings();
    drawShapes(gc);

    Timeline gameLoop = new Timeline();
    gameLoop.setCycleCount(Timeline.INDEFINITE);
    //    final long timeStart = System.currentTimeMillis();

    KeyFrame kf =
        new KeyFrame(
            Duration.seconds(0.2),
            // new EventHandler<ActionEvent>()
            ae -> {
              //              double t = (System.currentTimeMillis() - timeStart) / 1000.0;
              moveLivingThingsAndFood();

              // Clear the canvas
              gc.clearRect(0, 0, 1024, 720);
              drawShapes(gc);
            });

    gameLoop.getKeyFrames().add(kf);
    gameLoop.play();
    primaryStage.show();
  }

  /** Create all the LivingThings in the simulation. */
  private void constructLivingThings() {
    // Create random int of each LivingThing so every simulation is different.
    // Random int between 5 (inclusive) and 7 (exclusive), i.e. 5 or 6 Chickens.
    int numberOfChickens = ThreadLocalRandom.current().nextInt(5, 7);
    // Random int between 3 (inclusive) and 6 (exclusive), i.e. 3 - 5 Pigs.
    int numberOfPigs = ThreadLocalRandom.current().nextInt(3, 6);
    // Random int between 3 (inclusive) and 5 (exclusive), i.e. 3 or 4 Humans.
    int numberOfHumans = ThreadLocalRandom.current().nextInt(3, 5);

    new Fox();

    for (int i = 0; i < numberOfChickens; i++) {
      // Random placement of each Chicken.
      int x = ThreadLocalRandom.current().nextInt(column);
      int y = ThreadLocalRandom.current().nextInt(row);
      new Chicken(x, y);
    }

    for (int i = 0; i < numberOfPigs; i++) {
      // Random placement of each Pig.
      int x = ThreadLocalRandom.current().nextInt(column);
      int y = ThreadLocalRandom.current().nextInt(row);
      new Pig(x, y);
    }
    for (int i = 0; i < numberOfHumans; i++) {
      // Random placement of each Human.
      int x = ThreadLocalRandom.current().nextInt(column);
      int y = ThreadLocalRandom.current().nextInt(row);
      new Human(x, y);
    }
  }

  /** Cause all LivingThings and AnimalFood to move. */
  private void moveLivingThingsAndFood() {
    for (int i = 0; i < FarmObject.allFarmObjects.size(); i++) {
      FarmObject f = FarmObject.allFarmObjects.get(i);
      if (f instanceof AnimalFood) ((AnimalFood) f).blown();
      if (f instanceof LivingThing) ((LivingThing) f).move();
    }
  }

  /** Draw all the FarmObjects in the simulation. */
  private void drawShapes(GraphicsContext gc) {
    // Tell all the farmyard items to draw themselves.
    for (int i = 0; i < FarmObject.allFarmObjects.size(); i++) {
      FarmObject f = FarmObject.allFarmObjects.get(i);
      f.draw(gc);
    }
    Human.reportStock(gc);
    Fox.reportStolenEggs(gc);
  }
}
