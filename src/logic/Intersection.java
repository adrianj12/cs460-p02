package logic;

import GUI.TrafficGUI;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Intersection implements Runnable {
    private final long greenRedDuration = 8000; // green and red light have minimum of 5 second duration
    private final long yellowDuration = 3500; // yellow light minimum of 2 second duration
    private final long accLimit = 10;// number of cars required to change light
    private final long minLength = 4000; //minimum time spent during red or green light
    private long lightChangeTime; // time of previous light change
    private final int intersectionNumber; //
    private LightDirection northSouthDir; // direction of north, south lights
    private LightDirection eastWestDir; // direction fo east, west lights
    private LightColor northSouthColor; // color of the north and south lights
    private LightColor eastWestColor; // color of east and west lights

    private ImageView[] images;
    private int eastWestAcc;
    private int northSouthAcc;
    private double x0INTX1, xFINTX1;
    private double y0INTX1, yFINTX1;
    private double cxINTX1, cyINTX1;

    // assuming that there will be some sort of number assigned to an intersection so that we can differentiate btwn them
    public Intersection(int intersectionNumber, double x0INTX1, double xFINTX1, double y0INTX1, double yFINTX1, double cxINTX1, double cyINTX1, LightColor eastLight, LightColor northLight) {
        this.lightChangeTime = System.currentTimeMillis();
        this.intersectionNumber = intersectionNumber;
        this.northSouthDir = LightDirection.NORTHSOUTH;
        this.eastWestDir = LightDirection.EASTWEST;
        this.northSouthColor =northLight;
        this.eastWestColor = eastLight;
        this.x0INTX1 = x0INTX1;
        this.xFINTX1 = xFINTX1;
        this.y0INTX1 = y0INTX1;
        this.yFINTX1 = yFINTX1;
        this.cxINTX1 = cxINTX1;
        this.cyINTX1 = cyINTX1;
        this.eastWestAcc = 0; //traffic accumulator
        this.northSouthAcc = 0;
        this.images = TrafficGUI.images;

    }

    public enum LightColor {
        RED,
        YELLOW,
        GREEN
    }

    private enum LightDirection {
        NORTHSOUTH,
        EASTWEST
    }

    @Override
    public void run() {
        while (true) {
            updateIntersection();

            try {
                Thread.sleep(100);//ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void setImages(){
        if (images != null) {
            if (eastWestColor == LightColor.GREEN && northSouthColor == LightColor.RED) {
                images[intersectionNumber].setImage(new Image("greenRed.png"));
            }
            if (eastWestColor == LightColor.RED && northSouthColor == LightColor.GREEN) {
                images[intersectionNumber].setImage(new Image("redgreen.png"));
            }
            if (eastWestColor == LightColor.RED && northSouthColor == LightColor.YELLOW) {
                images[intersectionNumber].setImage(new Image("redyellow.png"));
            }
            if (eastWestColor == LightColor.YELLOW && northSouthColor == LightColor.RED) {
                images[intersectionNumber].setImage(new Image("yellowred.png"));
            }
        }
    }
    private LightColor oppositeLight(LightColor color){
        if (color == LightColor.GREEN){
            return LightColor.YELLOW;
        }
        else if(color == LightColor.YELLOW){//keeps the other light red for duration of yellow
            return LightColor.RED;
        }
        else{
            return LightColor.GREEN;
        }
    }
    // just changes color of the lights
    private void changeLight(LightDirection direction, LightColor newColor) {
        if (direction == LightDirection.NORTHSOUTH) {
            northSouthColor = newColor;
            eastWestColor = oppositeLight(newColor);
        }
        else if (direction == LightDirection.EASTWEST) {
            if(oppositeLight(newColor) == LightColor.YELLOW && newColor == LightColor.GREEN){
                eastWestColor = LightColor.RED;
                northSouthColor = oppositeLight(newColor);
            }
            else {
                eastWestColor = newColor;
                northSouthColor = oppositeLight(newColor);
            }
        }
        setImages();
        lightChangeTime = System.currentTimeMillis();
    }
    public LightColor getEWState(){
        return eastWestColor;
    }
    public LightColor getNSState(){
        return northSouthColor;
    }
    public int getID(){
        return intersectionNumber;
    }
    public double[] getCoords(){
        return new double[]{x0INTX1,xFINTX1, y0INTX1, yFINTX1, cxINTX1, cyINTX1};

    }
    // changes state of intersection based on time, will call changeLight method
    private void updateIntersection() {
        long currentTime = System.currentTimeMillis();
        if ((((eastWestColor == LightColor.GREEN || eastWestColor == LightColor.RED) ||
                        (northSouthColor == LightColor.GREEN || northSouthColor == LightColor.RED))
                        && (currentTime-lightChangeTime>= greenRedDuration))
        || (eastWestAcc>=accLimit && currentTime-lightChangeTime >= minLength)){

            changeLight(eastWestDir, oppositeLight(eastWestColor));

        }
       /* if (((northSouthColor == LightColor.GREEN || northSouthColor == LightColor.RED) && (currentTime-lightChangeTime>= greenRedDuration))
                || (northSouthAcc>=accLimit && currentTime-lightChangeTime >= minLength)){
            changeLight(northSouthDir, oppositeLight(northSouthColor));
            //System.out.printf("red or green %d\n", intersectionNumber);
        }*/
        if(northSouthAcc>=accLimit&& currentTime-lightChangeTime >= minLength){
            changeLight(northSouthDir, oppositeLight(northSouthColor));
        }

        //handles yellow light timing (unaffected by volume)
        if(eastWestColor == LightColor.YELLOW  && currentTime-lightChangeTime>= yellowDuration){
            eastWestColor = LightColor.RED;
            northSouthColor = LightColor.GREEN;
            setImages();
            lightChangeTime = System.currentTimeMillis();
        }
        else if(northSouthColor == LightColor.YELLOW  && currentTime-lightChangeTime>= yellowDuration){
            northSouthColor = LightColor.RED;
            eastWestColor = LightColor.GREEN;
            setImages();
            lightChangeTime = System.currentTimeMillis();
        }

    }
}
