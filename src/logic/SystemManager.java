package logic;

import javafx.stage.Screen;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SystemManager implements Runnable{

    private double size, xLane1, xLane2, xLane3, xLane4, xLane5, xLane6, xLane7, xLane8;
    private double yLane1, yLane2, yLane3, yLane4, yLane5, yLane6, yLane7, yLane8, yLane9, yLane10,yLane11, yLane12;
    private double x0INTX1, xFINTX1, x0INTX2, xFINTX2, x0INTX3, xFINTX3, x0INTX4, xFINTX4, x0INTX5, xFINTX5, x0INTX6, xFINTX6;
    private double y0INTX1, yFINTX1, y0INTX2, yFINTX2, y0INTX3, yFINTX3, y0INTX4, yFINTX4, y0INTX5, yFINTX5, y0INTX6, yFINTX6;
    private double cxINTX1, cyINTX1, cxINTX2, cyINTX2, cxINTX3, cyINTX3, cxINTX4, cyINTX4, cxINTX5, cyINTX5, cxINTX6, cyINTX6;
    private static int numIntersection = 6;
    protected static int sleepDelay = 10;
    private static int currentCars = 0;
    private static int maxNumCars = 100;
    protected static int createVehicleProbability = 20;
    protected static int createEMSProbability = 5;
    private static String direction[] = {"North", "South", "East", "West"};
    private double East_Lanes[];
    private double West_Lanes[];
    private double North_Lanes[];

    private double South_Lanes[];

    private double intersectionX_Coordinates[];
    private double intersectionY_Coordinates[];

    protected CopyOnWriteArrayList<String> testCarList = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<String> testIntersectionList = new CopyOnWriteArrayList<>();

    //protected CopyOnWriteArrayList<Car> Cars = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Thread> CarThreads = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Intersection> intersections = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Thread> IntersectionThreads = new CopyOnWriteArrayList<>();


    public SystemManager(int rows){
         size = Screen.getPrimary().getBounds().getHeight() / (rows + 1);
    }

    private void createLanes(){
        //xLanes store y coordiantes. East-West Traffic.
        xLane1 = size/2.85;
        xLane2 = size/2.2;

        xLane3 = size/1.825;
        xLane4 = size/1.55;

        xLane5 = (size*2)+xLane1;
        xLane6 = (size*2)+xLane2;

        xLane7 = (size*2)+xLane3;
        xLane8 = (size*2)+xLane4;


        //yLanes store x coordinates. North-South Traffic
        yLane1 = size/2.85;
        yLane2 = size/2.2;

        yLane3 = size/1.825;
        yLane4 = size/1.55;

        yLane5 = (size*2)+yLane1;
        yLane6 = (size*2)+yLane2;

        yLane7 = (size*2)+yLane3;
        yLane8 = (size*2)+yLane4;

        yLane9 = (size*4)+yLane1;
        yLane10 = (size*4)+yLane2;

        yLane11 = (size*4)+yLane3;
        yLane12 = (size*4)+yLane4;

        East_Lanes = new double[]{xLane3, xLane4, xLane7, xLane8};
        West_Lanes = new double[]{xLane1, xLane2, xLane5, xLane6};
        North_Lanes = new double[]{yLane3, yLane4, yLane7, yLane8, yLane11, yLane12};
        South_Lanes = new double[]{yLane1, yLane2, yLane5, yLane6, yLane9, yLane10};

    }

    private void createIntersection(){
        int i = 0;

        //Intersection 1
        x0INTX1 = size/5;
        xFINTX1 = size/1.25;
        y0INTX1 = size/5;
        yFINTX1 = size/1.25;
        cxINTX1 = size/2;
        cyINTX1 = size/2;

//Intersection 2
        x0INTX2 = (size*2)+ (size/5);
        xFINTX2 = (size*2)+ (size/1.25);
        y0INTX2 = size/5;
        yFINTX2 = size/1.25;
        cxINTX2 = (size*2)+ (size/2);
        cyINTX2 = size/2;

//Intersection 3
        x0INTX3 = (size*4)+ (size/5);
        xFINTX3 = (size*4)+ (size/1.25);
        y0INTX3 = size/5;
        yFINTX3 = size/1.25;
        cxINTX3 = (size*4)+ (size/2);
        cyINTX3 = size/2;

//Intersection 4
        x0INTX4 = size/5;
        xFINTX4 = size/1.25;
        y0INTX4 = (size*2)+(size/5);
        yFINTX4 = (size*2)+(size/1.25);
        cyINTX4 = size/2;
        cyINTX4 = (size*2)+(size/2);

//Intersection 5
        x0INTX5 = (size*2)+(size/5);
        xFINTX5 = (size*2)+(size/1.25);
        y0INTX5 = (size*2)+(size/5);
        yFINTX5 = (size*2)+(size/1.25);
        cxINTX5 = (size*2)+(size/2);
        cyINTX5 = (size*2)+(size/2);

//Intersection = 6
        x0INTX6 = (size*4)+(size/5);
        xFINTX6 = (size*4)+(size/1.25);
        y0INTX6 = (size*2)+(size/5);
        yFINTX6 = (size*2)+(size/1.25);
        cxINTX6 = (size*4)+(size/2);
        cyINTX6 = (size*2)+(size/2);

        String INTX1 = "INTX 1- x0: " + x0INTX1 + " xF: " +xFINTX1 + " y0: " + y0INTX1 + " cx: " + cxINTX1
                ;


    }

    private void createVehicle(){
        //RNG EMS vehicle creation.
        //add to list
        currentCars++;
    }

    private void createDMS(){}

    private int RNGCarRoll(){
        Random rand = new Random();
        int NumOfCarsToCreate = 0;
        int probability;

        while(true){
            probability = (int)(Math.random()*100);
            if(NumOfCarsToCreate + currentCars >= maxNumCars) {
                break;
            }
            if(probability < createVehicleProbability){
                NumOfCarsToCreate++;
            }

            else {
                break;
            }
        }
        return NumOfCarsToCreate;
    }

    private void removeVehicles(){
        //currentCars--;
    }

    @Override
    public void run() {
        int numCarCreate = 0;
        createLanes();
        createIntersection();
        createDMS();

        while(true){
            try {
                //Add if statement for max number of cars
                numCarCreate = RNGCarRoll();
                for(int i = 0; i < numCarCreate; i++){
                    createVehicle();
                    TimeUnit.SECONDS.sleep(2);
                }
                Thread.sleep(sleepDelay);
                removeVehicles();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
