package GUI;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class IntersectionGUI {
    private final Map<Directions, Integer> countdowns = new HashMap<>();
    private final Map<Directions, Label> DMSLabels = new HashMap<>();
    private final Map<Directions, ImageView> turnLights = new HashMap<>();
    private final Map<Directions, ImageView> regularLights = new HashMap<>();

    public void setDMSLabel(Label DMSLabel, Directions directions) {
        DMSLabels.put(directions, DMSLabel);
    }

    public void setTurnLight(ImageView turnLight, Directions directions) {
        turnLights.put(directions, turnLight);
    }

    public void setRegularLight(ImageView regularLight, Directions directions) {
        regularLights.put(directions, regularLight);
    }

    public void setCountdown(Integer countdown, Directions directions) {
        countdowns.put(directions, countdown);
    }

    public void updateDMS(String str, Directions directions) {
        DMSLabels.get(directions).setText(str);
    }

    public void updateTurnLight(String str, Directions directions) {
        turnLights.get(directions).setImage(new Image(str));
    }

    public void updateRegularLight(String str, Directions directions) {
        regularLights.get(directions).setImage(new Image(str));
    }
}
