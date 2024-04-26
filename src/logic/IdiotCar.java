package logic;

import javafx.scene.shape.Circle;
import javafx.stage.Screen;

public class IdiotCar implements Runnable {
    private double size;
    protected boolean running = true;
    int id;
    String direction;
    int x;
    int y;
    int driveDistance = 10;
    int sleepTime = 10;
    public IdiotCar(int ID, String dir, int xCoor, int yCoor, int rows){
        id = ID;
        direction = dir;
        x = xCoor;
        y = yCoor;
        size = Screen.getPrimary().getBounds().getHeight() / (rows + 1);
    }

    private void drive(){
        if(direction.equals("North")){
            x = x - driveDistance;
        }
        else if(direction.equals("South")){
            x = x + driveDistance;
        }
        else if(direction.equals("East")){
            y = y + driveDistance;
        }
        else if(direction.equals("West")){
            y = y - driveDistance;
        }
        cah().setCenterX(x);
        cah().setCenterY(y);
    }

    protected Circle cah(){
        Circle carGUI = new Circle();
        carGUI.setRadius(6.0f);
        carGUI.setCenterX(x);
        carGUI.setCenterY(y);
        return carGUI;
    }

    private void stop(){
        if(direction.equals("North")){
            if(y < size*(-2)){
                running = false;
            }
        }
        else if(direction.equals("South")) {
            if(y > size*(5)){
                running = false;
            }
        }
        else if(direction.equals("East")){
            if(x > size*(3)){
                running = false;
            }
        }
        else if(direction.equals("West")){
            if(x < size*(-2)){
                running = false;
            }
        }

    }
    @Override
    public void run() {
        while(running) {
            try {
                drive();
                stop();
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
