package logic;

import java.awt.*;

public abstract class Vehicle implements Runnable {
    private Direction currentDir;
    private int speed = 0;
    private float transparency = 0;
    private boolean intersectionFlag = false;
    private int minBufferDistance = 10;
    private Point location = new Point();
    private boolean running = false;

    public Direction getDirection(){
        return currentDir;
    }

    public void setDirection(Direction dir){
        this.currentDir = dir;
    }

    public int getSpeed(){
        return this.speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public float getTransparency(){
        return this.transparency;
    }

    public void setTransparency(float transparency){
        this.transparency = transparency;
    }

    public boolean getIntersectionFlag(){
        return this.intersectionFlag;
    }

    public void flipIntersectionFlag(){
        this.intersectionFlag = !this.intersectionFlag;
    }

    public Point getLocation(){
        return this.location;
    }

    public void setPoint(Point p){
        this.location = p;
    }

    public abstract boolean move();

    @Override
    public abstract void run();

    public abstract void stop();

}
