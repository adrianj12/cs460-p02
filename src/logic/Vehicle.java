package logic;
public abstract class Vehicle implements Runnable {
    private Direction currentDir;
    private int speed = 0;
    private float transparency = 0;
    private boolean intersectionFlag = false;
    private int minBufferDistance = 10;
    private int X_loc, Y_loc;
    private Thread thread;

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


    public int getX_loc(){
        return this.X_loc;
    }

    public int getY_loc(){
        return this.Y_loc;
    }

    public void setX_loc(int X_loc){
        this.X_loc = X_loc;
    }

    public void setY_loc(int Y_loc){
        this.Y_loc = Y_loc;
    }

    public abstract void move();
}
