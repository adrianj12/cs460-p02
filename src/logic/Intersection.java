package logic;

public class Intersection implements Runnable {
    private final long greenRedDuration = 5000; // green and red light have minimum of 5 second duration
    private final long yellowDuration = 2000; // yellow light minimum of 2 second duration
    private long lightChangeTime; // time of previous light change
    private final int intersectionNumber; //
    private LightDirection northSouthDir; // direction of north, south lights
    private LightDirection eastWestDir; // direction fo east, west lights
    private LightColor northSouthColor; // color of the north and south lights
    private LightColor eastWestColor; // color of east and west lights
    private int eastWestAcc;
    private int northSouthAcc;
    private double x0INTX1, xFINTX1;
    private double y0INTX1, yFINTX1;
    private double cxINTX1, cyINTX1;

    // assuming that there will be some sort of number assigned to an intersection so that we can differentiate btwn them
    public Intersection(int intersectionNumber, double x0INTX1, double xFINTX1, double y0INTX1, double yFINTX1, double cxINTX1, double cyINTX1) {
        this.lightChangeTime = System.currentTimeMillis();
        this.intersectionNumber = intersectionNumber;
        this.northSouthDir = LightDirection.NORTHSOUTH;
        this.eastWestDir = LightDirection.EASTWEST;
        this.northSouthColor = LightColor.GREEN;
        this.eastWestColor = LightColor.RED;
        this.x0INTX1 = x0INTX1;
        this.xFINTX1 = xFINTX1;
        this.y0INTX1 = y0INTX1;
        this.yFINTX1 = yFINTX1;
        this.cxINTX1 = cxINTX1;
        this.cyINTX1 = cyINTX1;
        this.eastWestAcc = 0; //traffic accumulator
        this.northSouthAcc = 0;
    }

    private enum LightColor {
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

    }
    private LightColor oppositeLight(LightColor color){
        if (color == LightColor.GREEN){
            return LightColor.YELLOW;
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
        } else if (direction == LightDirection.EASTWEST) {
            eastWestColor = newColor;
            northSouthColor = oppositeLight(newColor);
        }

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
        if ((eastWestColor == LightColor.GREEN || eastWestColor == LightColor.RED) && (currentTime-lightChangeTime>= greenRedDuration)){
            changeLight(eastWestDir, oppositeLight(eastWestColor));
        }
        if(eastWestColor == LightColor.YELLOW  && currentTime-lightChangeTime>= yellowDuration){
            eastWestColor = LightColor.RED;
            northSouthColor = LightColor.GREEN;
            lightChangeTime = System.currentTimeMillis();
        }
        else if(northSouthColor == LightColor.YELLOW  && currentTime-lightChangeTime>= yellowDuration){
            northSouthColor = LightColor.RED;
            eastWestColor = LightColor.GREEN;
            lightChangeTime = System.currentTimeMillis();
        }
    }
}
