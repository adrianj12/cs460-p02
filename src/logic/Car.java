package logic;

import javafx.scene.image.ImageView;

import java.awt.*;

public class Car extends Vehicle {

    private boolean EMS_inbound;
    private boolean running;


    public Car(Point p, Direction dir, Lane lane, int tileSize){
        this.setPoint(p);
        this.setDirection(dir);
        this.setLane(lane);

        this.imageView = new ImageView("car_1.png");
        imageView.setPreserveRatio(true);
        if(imageView.getFitHeight() >= imageView.getFitWidth()) {
            imageView.setFitHeight(tileSize * 0.133);
        }
        else {
            imageView.setFitWidth(tileSize * 0.133);
        }

        this.imageView.setX(p.getX());
        this.imageView.setY(p.getY());
    }


    public void flipEMS_inbound(){
        this.EMS_inbound = !this.EMS_inbound;
    }


    // returns true if the car moves
    public boolean move() {
        return false;
    }

    @Override
    public void run() {
        while (running) {
            move();

            // do stuff
            break;

        }
    }

    public void stop() {
        this.running = false;
        Thread.currentThread().interrupt();
    }

}