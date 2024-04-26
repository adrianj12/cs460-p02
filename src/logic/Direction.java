package logic;

import java.awt.*;

public enum Direction {
    NORTH(new Point(0,-1)),
    SOUTH(new Point(0,1)),
    EAST(new Point(1,0)),
    WEST(new Point(-1,0));

    private Point deltaDirection = new Point();


    Direction(Point point) {
        this.deltaDirection = point;
    }

    public Point getDeltaDirection() {
        return this.deltaDirection;
    }

}
