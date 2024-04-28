package GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

/**
 * Single intersection GUI (used to update smaller aspects)
 */
public class IntersectionGUI {
    private final Map<Directions, Integer> countdowns = new HashMap<>();
    private final Map<Directions, Label> DMSLabels = new HashMap<>();
    private final Map<Directions, ImageView> turnLights = new HashMap<>();
    private final Map<Directions, ImageView> regularLights = new HashMap<>();

    /**
     * Set the DMS label for direction given
     *
     * @param DMSLabel DMS Label
     * @param directions Cardinal direction
     */
    public void setDMSLabel(Label DMSLabel, Directions directions) {
        DMSLabels.put(directions, DMSLabel);
    }

    /**
     * Set the current turnLight for direction given
     *
     * @param turnLight Turn Light Image
     * @param directions Cardinal direction
     */
    public void setTurnLight(ImageView turnLight, Directions directions) {
        turnLights.put(directions, turnLight);
    }

    /**
     * Set the current regular light for direction given
     *
     * @param regularLight Regular Light Image
     * @param directions Cardinal direction
     */
    public void setRegularLight(ImageView regularLight, Directions directions) {
        regularLights.put(directions, regularLight);
    }

    /**
     * Set the countdown // TODO more than one?
     *
     * @param countdown Current countdown
     * @param directions Cardinal direction
     */
    public void setCountdown(Integer countdown, Directions directions) {
        countdowns.put(directions, countdown);
    }

    /**
     * Set the DMS Text
     *
     * @param str Text string
     * @param directions Cardinal direction
     */
    public void updateDMS(String str, Directions directions) {
        DMSLabels.get(directions).setText(str);
    }

    /**
     * Update the turn light image
     *
     * @param str Image file
     * @param directions Cardinal direction
     */
    public void updateTurnLight(String str, Directions directions) {
        turnLights.get(directions).setImage(new Image(str));
    }

    /**
     * Update regular light image
     *
     * @param str Image file
     * @param directions Cardinal direction
     */
    public void updateRegularLight(String str, Directions directions) {
        regularLights.get(directions).setImage(new Image(str));
    }
}
