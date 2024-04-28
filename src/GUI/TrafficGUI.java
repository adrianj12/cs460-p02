package GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import logic.Intersection;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Random;

/**
 * Main class for the GUI
 */
// TODO Animation Timer
public class TrafficGUI {

    private final BorderPane borderPane;
    private final StackPane stackPane;
    private static Pane vehiclePane;
    private final Scene scene;
    private final Rectangle2D screenSize = Screen.getPrimary().getBounds();
    public static ImageView[] images = new ImageView[6];
    private final Stage popUp = new Stage();
    private final int rows;
    private final int cols;
    private int currentClicked = 0;
    private final PopUpWindow popUpWindow;
    private final Intersection[] intArray = new Intersection[6];
    ImageView imageView1;
    ImageView imageView2;
    static double size;

    /**
     * GUI for the program
     * TODO: Might have play/pause but will have an animation timer for cars
     * @param stackPane stackpane to use
     * @param scene main scene
     * @param rows num rows
     * @param cols num cols
     */
    public TrafficGUI(StackPane stackPane, Scene scene,  int rows, int cols) {
        this.borderPane = new BorderPane();
        this.stackPane = stackPane;
        this.scene = scene;
        this.rows = rows;
        this.cols = cols;
        this.popUpWindow = new PopUpWindow(screenSize.getHeight() / 1.33, intArray);


    }


    /**
     * Set up the GUI and clickable elements
     */
    public void setUp() {
        size = screenSize.getHeight() / (rows + 1);
        stackPane.setMinSize(5 * size, 3 * size);
        stackPane.setMaxSize(5 * size, 3 * size);
        VBox vBox = new VBox();
        int interIndex = -1;

        Random randy = new Random();
        popUp.setTitle("Intersection");
        popUp.getIcons().add(new Image("intersection (three-quarter).png"));
        //Intersection[] intArray = new Intersection[6];
        Intersection.LightColor[] colors =
                {Intersection.LightColor.RED, Intersection.LightColor.GREEN};
        for (int i = 0; i < rows; i++) {
            HBox hBox = new HBox();
            boolean inter = true;
            for (int j = 0; j < cols; j++) {
                ImageView imageView;

                // TODO: This sets alternating roads and intersections, later won't be so boring (time permitting)
                if (i % 2 == 0) {
                    if (inter) {
                        int rand = randy.nextInt(2);
                        interIndex++;
                        intArray[interIndex] =
                                new Intersection(interIndex, 1, 2, 3, 4,
                                                 5, 6, colors[rand],
                                                 colors[1 - rand]);
                        Thread intersectionThread =
                                new Thread(intArray[interIndex]);
                        intersectionThread.start();
                        imageView = setImageView("redgreen.png", size);
                        images[interIndex] = imageView;
                        inter = false;
                    } else {
                        imageView =
                                setImageView("east-west (three-quarter).png",
                                             size);
                        inter = true;
                    }
                } else {
                    if (inter) {
                        imageView =
                                setImageView("north-south (three-quarter).png",
                                             size);
                        inter = false;
                    } else {
                        imageView = new ImageView();
                        inter = true;
                    }
                }


                ImageView grass = setImageView("grassTrees.png", size);

                StackPane stackPane = new StackPane(grass, imageView);
                Circle overlay = new Circle(size / 2 - 5);
                overlay.setFill(Color.LIGHTBLUE);
                overlay.setOpacity(0.33);

                int finalI = i;
                boolean finalInter = inter;
                Color green = Color.web("0x468D34", 1.0);

                // For the popup window
                int finalInterIndex = interIndex;
                stackPane.setOnMouseClicked((MouseEvent e) -> {
                    if (popUp.isShowing()) {
                        popUp.close();
                    }
                    if (finalI % 2 == 0 && !finalInter) {
                        BorderPane popUpBorder = new BorderPane();
                        popUpBorder.setBackground(new Background(
                                new BackgroundFill(green, null, null)));
                        popUpBorder.setCenter(
                                popUpWindow.getPopUp(finalInterIndex));
                        currentClicked = finalInterIndex;
                        popUp.setScene(new Scene(popUpBorder));
                        popUp.show();
                    }
                });

                // Overlay and cursor
                stackPane.setOnMouseEntered((MouseEvent e) -> {
                    if (finalI % 2 == 0 && !finalInter) {
                        stackPane.getChildren().add(overlay);
                        scene.setCursor(Cursor.HAND);
                    }
                });

                // Remove overlay and cursor
                stackPane.setOnMouseExited((MouseEvent e) -> {
                    stackPane.getChildren().remove(overlay);
                    scene.setCursor(Cursor.DEFAULT);
                });

                hBox.getChildren().add(stackPane);
            }
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        vBox.setAlignment(Pos.CENTER);

        StackPane roads = new StackPane(vBox);
        // TODO Vroom vroom
        roads.getChildren().add(setImageView("car_1.png", size * 0.133));
        roads.getChildren().add(setImageView("ambulance.png", size * 0.133));
        borderPane.setCenter(roads);
        //discard roads usage, sorry i was trying to make it work, but it was
        // always centered and being weird if not centered

        vehiclePane = new Pane();
        vehiclePane.setPrefSize(screenSize.getWidth(), screenSize.getHeight());
        vehiclePane.setMouseTransparent(true);

        // Create ImageView objects and set their positions
        imageView1 = setImageView("car_1.png", size * 0.133);
        imageView1.setTranslateX(-size * 0.133 / 4);
        imageView1.setTranslateY(-size * 0.133 / 2); //testing here
        imageView1.setRotate(45);


        imageView1.setLayoutX((900) * (size / 200));
        imageView1.setLayoutY(100 * (size / 200));


        imageView2 = setImageView("car_1.png", size * 0.133);
        imageView2.setLayoutX(300);
        imageView2.setLayoutY(100);

        vehiclePane.getChildren().addAll(imageView1, imageView2);

        this.stackPane.getChildren().addAll(borderPane, vehiclePane);


    }

    public static void addCar(ImageView imageView){
        vehiclePane.getChildren().add(imageView);
    }

    public static void removeCar(ImageView imageView){
        vehiclePane.getChildren().remove(imageView);
    }

    public void looper() {
        new Thread(() -> {
            final int[] x = {900}; // Using an array to hold the mutable value
            final int y = 100; // y can remain as is

            while (true) {
                x[0]--; // Decrement x
                if (x[0] < 10){
                    x[0] = 1000;
                }

                // Update the position of imageView1
                Platform.runLater(() -> {
                    imageView1.setLayoutX(x[0]); // Using x[0]
                    imageView1.setLayoutY(y);
                });

                try {
                    Thread.sleep(100); // Sleep for 1 second before updating
                    // again
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static double getTileSize(){
        return size;
    }



    /**
     * Sets ImageView
     *
     * @param file File string
     * @param size Size of ImageView
     * @return Sized ImageView
     */
    public static ImageView setImageView(String file, double size) {
        ImageView imageView = new ImageView(file);
        imageView.setPreserveRatio(true);
        if(imageView.getFitHeight() >= imageView.getFitWidth()) {
            imageView.setFitHeight(size);
        }
        else {
            imageView.setFitWidth(size);
        }
        return imageView;
    }
}
