package GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.DMS;
import logic.Intersection;

import static GUI.TrafficGUI.setImageView;

import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;

/**
 * For the Intersection PopUp Window
 */
public class PopUpWindow {

    /**
     * DMS message states
     */
    private enum Messages {
        EMERGENCY("EMERGENCY VEHICLE\nAPPROACHING\nUSE CAUTION!"),
        REGULAR("NO SLOW DOWNS\nFOUND AHEAD\nPLEASE DRIVE SAFE!"),
        CONSTRUCTION("ROAD WORK AHEAD!\nON <STREET> FROM\n<START> TO <END"),
        SLOWDOWN("ACCIDENT ON <STREET>\n<NUM> MIN SLOWDOWN\nALT ROUTE <STREET>");
        private final String message;

        Messages(String message) {
            this.message = message;
        }
    }

    private final StackPane popUp;
    private final double size;
    private final Font font;
    private final IntersectionGUI intersectionGUI;
    private final Intersection[] intArray;

    private final DMS dmsLogic;
    private final Timer timer;
    private final String location = "Denver";

    /**
     * Pop Up Window (For zoomed in intersection view)
     *
     * @param size Calculated size
     */
    public PopUpWindow(double size, Intersection[] intArray) {
        this.size = size;
        this.intArray = intArray;
        this.font = Font.loadFont(getClass().getResourceAsStream(
                "../fonts/advanced-led-board-7.regular.ttf"), this.size / 34); // original: 32.5
        this.intersectionGUI = new IntersectionGUI();
        this.popUp = makePopUp();

        this.dmsLogic = new DMS(location);
        this.timer = new Timer();
    }

    /**
     * Get (and update) the popup window
     *
     * @param index Intersection index
     * @return Stackpane for the Popup
     */
    public StackPane getPopUp(int index) {
        update(index);
        startTimer();
        return this.popUp;
    }

    public void update(int index) {
        if(intArray[index].getEWState().equals(Intersection.LightColor.RED)) {
            intersectionGUI.updateRegularLight("red-light.png", Directions.EAST);
            intersectionGUI.updateRegularLight("red-light.png", Directions.WEST);
            intersectionGUI.updateRegularLight("light.png", Directions.NORTH);
            intersectionGUI.updateRegularLight("light.png", Directions.SOUTH);
        }
        if(intArray[index].getEWState().equals(Intersection.LightColor.YELLOW)) {
            intersectionGUI.updateRegularLight("yellow-light.png", Directions.EAST);
            intersectionGUI.updateRegularLight("yellow-light.png", Directions.WEST);
            intersectionGUI.updateRegularLight("red-light.png", Directions.NORTH);
            intersectionGUI.updateRegularLight("red-light.png", Directions.SOUTH);
        }
        if(intArray[index].getNSState().equals(Intersection.LightColor.YELLOW)) {
            intersectionGUI.updateRegularLight("red-light.png", Directions.EAST);
            intersectionGUI.updateRegularLight("red-light.png", Directions.WEST);
            intersectionGUI.updateRegularLight("yellow-light.png", Directions.NORTH);
            intersectionGUI.updateRegularLight("yellow-light.png", Directions.SOUTH);
        }
        if(intArray[index].getEWState().equals(Intersection.LightColor.GREEN)) {
            intersectionGUI.updateRegularLight("light.png", Directions.EAST);
            intersectionGUI.updateRegularLight("light.png", Directions.WEST);
            intersectionGUI.updateRegularLight("red-light.png", Directions.NORTH);
            intersectionGUI.updateRegularLight("red-light.png", Directions.SOUTH);
        }
        //update(index);
    }

    /**
     * Sets DMS message based on logic state
     * Default: cycle default message and weather conditions
     */
    private void updateDMSDisplay() {

        String message;

        switch(dmsLogic.state) {

            case DMS.State.DEFAULT:
            default:
                message = Messages.REGULAR.message;
                dmsLogic.state = DMS.State.WEATHER;
                break;
            case DMS.State.ACCIDENT:
                message = Messages.SLOWDOWN.message;
                break;
            case DMS.State.CONSTRUCTION:
                message = Messages.CONSTRUCTION.message;
                break;
            case DMS.State.EMERGENCY:
                message = Messages.EMERGENCY.message;
                break;
            case DMS.State.WEATHER:
                message = dmsLogic.wxMessage;
                dmsLogic.state = DMS.State.DEFAULT;
                break;
        }

        intersectionGUI.updateDMS(message, Directions.WEST);
        intersectionGUI.updateDMS(message, Directions.NORTH);
        intersectionGUI.updateDMS(message, Directions.SOUTH);
        intersectionGUI.updateDMS(message, Directions.EAST);
    }

    /**
     * Initializes popup stackpane
     *
     * @return Stackpane for popup
     */
    private StackPane makePopUp() {
        HBox horizontalPopUp = new HBox();
        horizontalPopUp.setSpacing(-1);
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

        /* DMS objects */
        StackPane DMS = makeDMS(size);
        DMS.setTranslateX(size * -0.5323);
        intersectionGUI.setDMSLabel((Label) DMS.getChildren().get(0), Directions.WEST);

        StackPane DMS2 = makeDMS(size);
        DMS2.setTranslateX(size * 0.5323);
        intersectionGUI.setDMSLabel((Label) DMS2.getChildren().get(0), Directions.EAST);

        StackPane DMS3 = makeDMS(size);
        DMS3.setTranslateY(size * -0.4250);
        intersectionGUI.setDMSLabel((Label) DMS3.getChildren().get(0), Directions.NORTH);

        StackPane DMS4 = makeDMS(size);
        DMS4.setTranslateY(size * 0.377);
        intersectionGUI.setDMSLabel((Label) DMS4.getChildren().get(0), Directions.SOUTH);

        ImageView lightOne = setImageView("light.png", size / 9);
        lightOne.setTranslateX(size * -0.5323 + size / 9);
        lightOne.setTranslateY(size * 0.0920);

        ImageView turnOne = setImageView("green-arrow.png", size / 9);
        turnOne.setTranslateX(size * -0.5323 - size / 9);
        turnOne.setTranslateY(size * 0.0920);

        intersectionGUI.setRegularLight(lightOne, Directions.WEST);
        intersectionGUI.setTurnLight(turnOne, Directions.WEST);

        ImageView lightTwo = setImageView("light.png", size / 9);
        lightTwo.setTranslateX(size * 0.5323 + size / 9);
        lightTwo.setTranslateY(size * 0.0920);

        ImageView turnTwo = setImageView("green-arrow.png", size / 9);
        turnTwo.setTranslateX(size * 0.5323 - size / 9);
        turnTwo.setTranslateY(size * 0.0920);

        intersectionGUI.setRegularLight(lightTwo, Directions.EAST);
        intersectionGUI.setTurnLight(turnTwo, Directions.EAST);

        ImageView lightThree = setImageView("light.png", size / 9);
        lightThree.setTranslateX(size / 9);
        lightThree.setTranslateY(size * -0.4250 + size * 0.0920);

        ImageView turnThree = setImageView("green-arrow.png", size / 9);
        turnThree.setTranslateX(-size / 9);
        turnThree.setTranslateY(size * -0.4250 + size * 0.0920);

        intersectionGUI.setRegularLight(lightThree, Directions.NORTH);
        intersectionGUI.setTurnLight(turnThree, Directions.NORTH);

        ImageView lightFour = setImageView("light.png", size / 9);
        lightFour.setTranslateX(size / 9);
        lightFour.setTranslateY(size * 0.3770 + size * 0.0920);

        ImageView turnFour = setImageView("green-arrow.png", size / 9);
        turnFour.setTranslateX(-size / 9);
        turnFour.setTranslateY(size * 0.3770 + size * 0.0920);

        intersectionGUI.setRegularLight(lightFour, Directions.SOUTH);
        intersectionGUI.setTurnLight(turnFour, Directions.SOUTH);

        return new StackPane(horizontalPopUp, DMS, DMS2, DMS3, DMS4,
                             lightOne, turnOne, lightTwo, turnTwo,
                             lightThree, turnThree, lightFour, turnFour);
    }

    /**
     * Creates the DMS sign with default message
     *
     * @param size Height of window
     * @return StackPane of DMS
     */
    private StackPane makeDMS(double size) {
        StackPane sign = new StackPane();
        Label signText = new Label(Messages.REGULAR.message);
        signText.setFont(font);
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
     * Timer for DMS display cycling
     */
    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateDMSDisplay());
            }
        }, 0, 8000); // update every 8000ms = 8s
    }
}
