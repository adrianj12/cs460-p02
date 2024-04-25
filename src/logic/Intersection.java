package logic;

public class Intersection extends Thread {
    private final long greenRedDuration = 5000; // green and red light have minimum of 5 second duration
    private final long yellowDuration = 2000; // yellow light minimum of 2 second duration
    private long lightChangeTime; // time of previous light change
    private final int intersectionNumber; //
    private LightDirection northSouthDir; // direction of north, south lights
    private LightDirection eastWestDir; // direction fo east, west lights
    private LightColor northSouthColor; // color of the north and south lights
    private LightColor eastWestColor; // color of east and west lights


    // assuming that there will be some sort of number assigned to an intersection so that we can differentiate btwn them
    public Intersection(int intersectionNumber) {
        this.lightChangeTime = System.currentTimeMillis();
        this.intersectionNumber = intersectionNumber;
        this.northSouthDir = LightDirection.NORTHSOUTH;
        this.eastWestDir = LightDirection.EASTWEST;
        this.northSouthColor = LightColor.GREEN;
        this.eastWestColor = LightColor.RED;
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

    // just changes color of the lights
    private void changeLight(LightDirection direction, LightColor newColor) {
        if (direction == LightDirection.NORTHSOUTH) {
            northSouthColor = newColor;
        } else if (direction == LightDirection.EASTWEST) {
            eastWestColor = newColor;
        }

        lightChangeTime = System.currentTimeMillis();
    }

    // changes state of intersection based on time, will call changeLight method
    private void updateIntersection() {
        long currentTime = System.currentTimeMillis();
    }
}
