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

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        // Rows and cols might not be great on other values
        TrafficGUI gui = new TrafficGUI(borderPane, scene, 5, 7);
        gui.setUp();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
