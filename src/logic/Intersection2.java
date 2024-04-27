package logic;

import java.awt.*;
import java.util.ArrayList;

public class Intersection2 {

    private Point center;
    // northStop = boundary incoming cars from NORTH may not cross when RED
    // north/south refer to a Y coordinate, east/west X coordinate
    private int northStop, southStop, eastStop, westStop;
    private int northBarrier, southBarrier, eastBarrier, westBarrier;
    private ArrayList<Point> exits = new ArrayList<>();


    public Intersection2(Point center){
        this.center = center;

        this.northStop = (int) center.getY() -60;
        this.southStop = (int) center.getY() +59;
        this.westStop =(int) center.getX() -60;
        this.eastStop =(int) center.getX() +59;

        this.northBarrier = (int) center.getY() -40;
        this.southBarrier = (int) center.getY() +39;
        this.eastBarrier =(int) center.getX() +39;
        this.westBarrier =(int) center.getX() -40;

        this.exits.add(new Point( (int)center.getX() + 9,
                                    this.northBarrier ));//northLeft
        this.exits.add(new Point((int) center.getX() + 28,
                                    this.northBarrier)); //northRight

        this.exits.add(new Point( this.eastBarrier,
                                  (int) center.getY() - 10));//eastLeft
        this.exits.add(new Point( this.eastBarrier,
                                  (int) center.getY() - 30));//eastRight

        this.exits.add(new Point((int) center.getX() - 10,
                                  this.southBarrier)); //southLeft
        this.exits.add(new Point((int) center.getX() - 30,
                                    this.southBarrier)); // southRight

        this.exits.add(new Point( this.westBarrier,
                                   (int) center.getY() + 9 ));//westLeft
        this.exits.add(new Point( this.westBarrier,
                                    (int) center.getY() + 28));//westRight

        // adding in clockwise order, important for future indexed get(i) calls

    }

    public int getNorthStop(){
        return this.northStop;
    }
    public int getSouthStop(){
        return this.southStop;
    }
    public int getEastStop(){
        return this.eastStop;
    }
    public int getWestStop(){
        return this.westStop;
    }

    public int getNorthBarrier(){
        return this.northBarrier;
    }
    public int getSouthBarrier(){
        return this.southBarrier;
    }
    public int getEastBarrier(){
        return this.eastBarrier;
    }
    public int getWestBarrier(){
        return this.westBarrier;
    }

    // Parameter: dir = current direction of incoming car
    public Point getLeftTurn(Direction dir){
        switch (dir) {
            case NORTH:
                // code block
                return exits.get(6);
            case SOUTH:
                // code block
                return exits.get(2);

            case EAST:
                // code block
                return exits.get(0);
            case WEST:
                // code block
                return exits.get(4);
            // add more cases as needed
        }
        return null;

    }

    // Parameter: dir = current direction of incoming car
    public Point getRightTurn(Direction dir){
        switch (dir) {
            case NORTH:
                // code block
                return exits.get(3);
            case SOUTH:
                // code block
                return exits.get(7);

            case EAST:
                // code block
                return exits.get(5);
            case WEST:
                // code block
                return exits.get(1);
            // add more cases as needed
        }
        return null;
    }

}
