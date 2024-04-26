import GUI.TrafficGUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

//remove group
import javafx.scene.Group;
import logic.Dummy_Thread;
import logic.SystemManager;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main extends Application {

    //Remove later
    protected static Group root = new Group();
    Circle circle = new Circle(6.0f);
    double size = Screen.getPrimary().getBounds().getHeight() / (4);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Traffic Light Controller");
        primaryStage.getIcons().add(new Image("light.png"));

        circle.setCenterX(30);
        circle.setCenterY(size/2.85);

        BorderPane borderPane = new BorderPane();
        root.getChildren().add(borderPane);
        root.getChildren().add(circle);
        Scene scene = new Scene(root);
        // Rows and cols might not be great on other values; 6 intersections
        TrafficGUI gui = new TrafficGUI(borderPane, scene, 3, 5);
        gui.setUp();

        //System.out.println(Screen.getPrimary().getBounds().getHeight() / (4));
        primaryStage.setScene(scene);
        primaryStage.show();

        SystemManager sysmgr = new SystemManager(3);
        Thread sysmgrThread = new Thread(sysmgr);
        sysmgrThread.start();
        CopyOnWriteArrayList<String> list =  sysmgr.testIntersectionList;
        //System.out.println(list.size());
        Dummy_Thread dummy = new Dummy_Thread(1, 5000);
        Thread dummyThread = new Thread(dummy);
        dummyThread.start();

        Dummy_Thread dummy2 = new Dummy_Thread(2, 3500);
        Thread dummyThread2 = new Thread(dummy2);
        dummyThread2.start();

        Dummy_Thread dummy3 = new Dummy_Thread(3, 4125);
        Thread dummyThread3 = new Thread(dummy3);
        dummyThread3.start();

        Dummy_Thread dummy4 = new Dummy_Thread(4, 6168);
        Thread dummyThread4 = new Thread(dummy4);
        dummyThread4.start();

        Dummy_Thread dummy5 = new Dummy_Thread(5, 7892);
        Thread dummyThread5 = new Thread(dummy5);
        dummyThread5.start();

    }
}
