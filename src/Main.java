import GUI.TrafficGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Traffic Light Controller");
        primaryStage.getIcons().add(new Image("light.png"));


        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);

        // Rows and cols might not be great on other values; 6 intersections
        TrafficGUI gui = new TrafficGUI(stackPane, scene, 3, 5);
        gui.setUp();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
