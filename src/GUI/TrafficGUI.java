package GUI;

import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import logic.Intersection;
import java.util.Random;

/**
 * Main class for the GUI
 */
// TODO Animation Timer
public class TrafficGUI {

    private final BorderPane borderPane;
    private final StackPane stackPane;
    private final Scene scene;
    private final Rectangle2D screenSize = Screen.getPrimary().getBounds();
    public static ImageView[] images = new ImageView[6];
    private final Stage popUp = new Stage();
    private final int rows;
    private final int cols;
    public static Intersection[] intArray = new Intersection[6];
    private final PopUpWindow popUpWindow;

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
        this.popUpWindow = new PopUpWindow(screenSize.getHeight() / 1.33);

        //startTimer();
    }
    public Intersection[] getIntArray(){
        return intArray;
    }

    /**
     * AnimationTimer for cars
     * TODO: Cars (aka vroom vroom)
     */
    private void startTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };
        timer.start();
    }

    /**
     * Set up the GUI and clickable elements
     */
    public void setUp() {
        double size = screenSize.getHeight() / (rows + 1);
        stackPane.setMinSize(5*size, 3*size);
        stackPane.setMaxSize(5*size, 3*size);
        VBox vBox = new VBox();
        int interIndex = -1;

        Random randy = new Random();
        popUp.setTitle("Intersection");
        popUp.getIcons().add(new Image("intersection (three-quarter).png"));

        Intersection.LightColor[] colors = {Intersection.LightColor.RED, Intersection.LightColor.GREEN};
        for(int i = 0; i < rows; i++) {
            HBox hBox = new HBox();
            boolean inter = true;
            for (int j = 0; j < cols; j++) {
                ImageView imageView;

                // TODO: This sets alternating roads and intersections, later won't be so boring (time permitting)
                if(i % 2 == 0) {
                    if (inter) {
                        int rand = randy.nextInt(2);
                        interIndex++;
                        intArray[interIndex]= new Intersection(interIndex, 1, 2, 3, 4,
                                5, 6,colors[rand], colors[1-rand]);
                        Thread intersectionThread= new Thread(intArray[interIndex]);
                        intersectionThread.start();
                        imageView = setImageView("redgreen.png", size);
                        images[interIndex]= imageView;
                        inter = false;
                    }
                    else {
                        imageView = setImageView("east-west (three-quarter).png", size);
                        inter = true;
                    }
                }

                else {
                    if(inter) {
                        imageView = setImageView("north-south (three-quarter).png", size);
                        inter = false;
                    }
                    else {
                        imageView = new ImageView();
                        inter = true;
                    }
                }

                ImageView grass = setImageView("grass.png", size);

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
                    if(popUp.isShowing()) {
                        popUp.close();
                    }
                    if(finalI % 2 == 0 && !finalInter) {
                        BorderPane popUpBorder = new BorderPane();
                        popUpBorder.setBackground(new Background(new BackgroundFill(green, null , null)));
                        popUpBorder.setCenter(popUpWindow.getPopUp(finalInterIndex));
                        popUp.setScene(new Scene(popUpBorder));
                        popUp.show();
                    }
                });

                // Overlay and cursor
                stackPane.setOnMouseEntered((MouseEvent e) -> {
                    if(finalI % 2 == 0 && !finalInter) {
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



        Pane vehiclePane = new Pane();
        vehiclePane.setPrefSize(screenSize.getWidth(), screenSize.getHeight());
        vehiclePane.setStyle("-fx-background-color: transparent;");
        vehiclePane.setMouseTransparent(true);

        // Create ImageView objects and set their positions
        ImageView imageView1 = setImageView("car_1.png", size * 0.133);
        imageView1.setTranslateX(-size * 0.133/4);
        imageView1.setTranslateY(-size * 0.133/2);
        imageView1.setRotate(45);
        imageView1.setLayoutX(900 * (size/200));
        imageView1.setLayoutY(100 * (size/200));
        System.out.println(size * 0.133);

        ImageView imageView2 = setImageView("car_1.png", size * 0.133);
        imageView2.setLayoutX(300);
        imageView2.setLayoutY(100);

        // Add the ImageView objects to the StackPane
        vehiclePane.getChildren().addAll(imageView1, imageView2);

        // Add additional images as needed

        // Add the StackPane to your layout
        //borderPane.getChildren().add(overlayPane);

        // Add the overlay pane on top of your existing layout
        this.stackPane.getChildren().addAll(borderPane, vehiclePane);

        // Set the stackPane as the root of your scene
    }

    /**
     * TODO: This will take params later for the DMS and lights, and other things
     *       that make this show something that isn't just the intersection image
     *
     * @return StackPane of zoom in
     */
    private StackPane getPopUp() {
        HBox horizontalPopUp = new HBox();
        horizontalPopUp.setSpacing(-1);
        double size = screenSize.getHeight() / 1.33;
        for (int i = 0; i < 3; i++) {
            StackPane stackRoad = new StackPane();
            if(i % 2 != 0) {
                stackRoad.getChildren().add(setImageView("grass.png", size));
                stackRoad.getChildren().add(setImageView("intersection lights (three-quarter).png", size));
            }
            else {
                stackRoad.getChildren().add(setImageView("grass zoom.png", size));
                stackRoad.getChildren().add(setImageView("east-west zoom (three-quarter).png", size));
            }
            horizontalPopUp.getChildren().add(stackRoad);
        }

        StackPane DMS = makeDMS(size);
        DMS.setTranslateX(size * -0.5323);

        StackPane DMS2 = makeDMS(size);
        DMS2.setTranslateX(size * 0.5323);

        StackPane DMS3 = makeDMS(size);
        DMS3.setTranslateY(size * -0.4010);

        StackPane DMS4 = makeDMS(size);
        DMS4.setTranslateY(size * 0.4010);

        return new StackPane(horizontalPopUp, DMS, DMS2, DMS3, DMS4);
    }

    /**
     * Creates the DMS sign with message
     * TODO might make a DMS GUI class
     *
     * @param size Height of window
     * @return StackPane of DMS
     */
    private StackPane makeDMS(double size) {
        StackPane sign = new StackPane();
        Label signText = new Label("LINE ONE\nLINE TWO\nLINE THREE");
        signText.setFont(ledFont);
        signText.setAlignment(Pos.TOP_LEFT);
        signText.setBackground(Background.fill(Color.BLACK));
        signText.setPadding(new Insets(0, 5, 0, 5));
        signText.setTextFill(Color.web("0xFFFF73", 1.0));
        signText.setPrefWidth(size * 0.375);
        signText.setPrefHeight(size * 0.125);
        sign.getChildren().add(signText);
        return sign;
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
