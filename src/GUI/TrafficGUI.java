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
    private final Scene scene;
    private final Rectangle2D screenSize = Screen.getPrimary().getBounds();
    public static ImageView[] images = new ImageView[6];
<<<<<<< HEAD
    private final Font ledFont = Font.loadFont(getClass().getResourceAsStream(
            "../fonts/advanced-led-board-7.regular.ttf"), screenSize.getHeight() / 1.33 / 32.5);
    private Intersection[] intArray = new Intersection[6];

=======
>>>>>>> 5d0854ea0ac20618bf86a9106b1130bbc0f59544
    private final Stage popUp = new Stage();
    private final int rows;
    private final int cols;
    private final PopUpWindow popUpWindow;

    /**
     * GUI for the program
     *
     * @param borderPane BorderPane to use
     * @param scene main scene
     * @param rows num rows
     * @param cols num cols
     */
    public TrafficGUI(BorderPane borderPane, Scene scene,  int rows, int cols) {
        this.borderPane = borderPane;
        this.scene = scene;
        this.rows = rows;
        this.cols = cols;
        this.popUpWindow = new PopUpWindow(screenSize.getHeight() / 1.33);

        //startTimer();
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
    }

    /**
<<<<<<< HEAD
     * TODO: This will take params later for the DMS and lights, and other things
     *       that make this show something that isn't just the intersection image
     *
     * @return StackPane of zoom in
     */
    private StackPane getPopUp() {
        HBox horizontalPopUp = new HBox();
        horizontalPopUp.setSpacing(-1);
        int k =0;
        double size = screenSize.getHeight() / 1.33;
        for (int i = 0; i < 3; i++) {
            StackPane stackRoad = new StackPane();
            if(i % 2 != 0) {
                stackRoad.getChildren().add(setImageView("grass.png", size));
                stackRoad.getChildren().add(setImageView(intArray[k].getImage(), size));
                k++;
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
=======
>>>>>>> 5d0854ea0ac20618bf86a9106b1130bbc0f59544
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
