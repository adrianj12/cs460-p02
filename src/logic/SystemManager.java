package logic;

import javafx.stage.Screen;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SystemManager implements Runnable{
    IdiotCar idcar;

    int row;

    private double size, xLane1, xLane2, xLane3, xLane4, xLane5, xLane6, xLane7, xLane8;
    private double yLane1, yLane2, yLane3, yLane4, yLane5, yLane6, yLane7, yLane8, yLane9, yLane10,yLane11, yLane12;
    private double x0INTX1, xFINTX1, x0INTX2, xFINTX2, x0INTX3, xFINTX3, x0INTX4, xFINTX4, x0INTX5, xFINTX5, x0INTX6, xFINTX6;
    private double y0INTX1, yFINTX1, y0INTX2, yFINTX2, y0INTX3, yFINTX3, y0INTX4, yFINTX4, y0INTX5, yFINTX5, y0INTX6, yFINTX6;
    private double cxINTX1, cyINTX1, cxINTX2, cyINTX2, cxINTX3, cyINTX3, cxINTX4, cyINTX4, cxINTX5, cyINTX5, cxINTX6, cyINTX6;
    private static int carID = 0;
    protected static int sleepDelay = 3000;
    private static int currentCars = 0;
    private static int maxNumCars = 100;
    protected static int createVehicleProbability = 70;
    protected static int createEMSProbability = 25;
    private static String direction[] = {"North", "South", "East", "West"};
    private double East_Lanes[];
    private double West_Lanes[];
    private double North_Lanes[];
    private double South_Lanes[];

    protected static CopyOnWriteArrayList<String> testCarList = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<String> testIntersectionList = new CopyOnWriteArrayList<>();

    public static CopyOnWriteArrayList<IdiotCar> testCars = new CopyOnWriteArrayList<>();
    protected static CopyOnWriteArrayList<Thread> testCarThread = new CopyOnWriteArrayList<>();

    //protected CopyOnWriteArrayList<Car> Cars = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Thread> CarThreads = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Intersection> intersections = new CopyOnWriteArrayList<>();
    //protected CopyOnWriteArrayList<Thread> IntersectionThreads = new CopyOnWriteArrayList<>();


    public SystemManager(int rows){
         size = Screen.getPrimary().getBounds().getHeight() / (rows + 1);
         row = rows;
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
                +" cy: " + cyINTX1 + " Nlane1: " + North_Lanes[0] + " Nlane2: " + North_Lanes[1] +
                " Slane1: " + South_Lanes[0] + " Slane2: " + South_Lanes[1] +
                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];

        String INTX2 = "INTX 2- x0: " + x0INTX2 + " xF: " +xFINTX2 + " y0: " + y0INTX2 + " cx: " + cxINTX2
                +" cy: " + cyINTX2 + " Nlane1: " + North_Lanes[2] + " Nlane2: " + North_Lanes[3] +
                " Slane1: " + South_Lanes[2] + " Slane2: " + South_Lanes[3] +
                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];

        String INTX3 = "INTX 3- x0: " + x0INTX3 + " xF: " +xFINTX3 + " y0: " + y0INTX3 + " cx: " + cxINTX3
                +" cy: " + cyINTX3 + " Nlane1: " + North_Lanes[4] + " Nlane2: " + North_Lanes[5] +
                " Slane1: " + South_Lanes[4] + " Slane2: " + South_Lanes[5] +
                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];

        String INTX4 = "INTX 4- x0: " + x0INTX4 + " xF: " +xFINTX4 + " y0: " + y0INTX4 + " cx: " + cxINTX4
                +" cy: " + cyINTX4 + " Nlane1: " + North_Lanes[0] + " Nlane2: " + North_Lanes[1] +
                " Slane1: " + South_Lanes[0] + " Slane2: " + South_Lanes[1] +
                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];

        String INTX5 = "INTX 5- x0: " + x0INTX5 + " xF: " +xFINTX5 + " y0: " + y0INTX5 + " cx: " + cxINTX5
                +" cy: " + cyINTX5 + " Nlane1: " + North_Lanes[2] + " Nlane2: " + North_Lanes[3] +
                " Slane1: " + South_Lanes[2] + " Slane2: " + South_Lanes[3] +
                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];

        String INTX6 = "INTX 6- x0: " + x0INTX6 + " xF: " +xFINTX6 + " y0: " + y0INTX6 + " cx: " + cxINTX6
                +" cy: " + cyINTX6 + " Nlane1: " + North_Lanes[4] + " Nlane2: " + North_Lanes[5] +
                " Slane1: " + South_Lanes[4] + " Slane2: " + South_Lanes[5] +
                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];

        testIntersectionList.add(INTX1);
        testIntersectionList.add(INTX2);
        testIntersectionList.add(INTX3);
        testIntersectionList.add(INTX4);
        testIntersectionList.add(INTX5);
        testIntersectionList.add(INTX6);


    }

    private void createVehicle(){
        //RNG EMS vehicle creation.
        int EMSprobability;
        int directionRNG;
        String car;
        EMSprobability = (int)(Math.random()*100);
        directionRNG = (int)(Math.random()*(4));
        double startingX = 0;
        double startingY = 0;
        String getDirection = direction[directionRNG];

        if(getDirection.equals("North")){
            directionRNG = (int)(Math.random()*(North_Lanes.length-1));
            startingY = size*3;
            startingX = North_Lanes[directionRNG];
        }
        else if(getDirection.equals("South")){
            directionRNG = (int)(Math.random()*(South_Lanes.length-1));
            startingY = 0;
            startingX = South_Lanes[directionRNG];
        }
        else if(getDirection.equals("East")){
            directionRNG = (int)(Math.random()*(East_Lanes.length-1));
            startingY = East_Lanes[directionRNG];
            startingX = 0;
        }
        else if(getDirection.equals("West")){
            directionRNG = (int)(Math.random()*(West_Lanes.length-1));
            startingY = West_Lanes[directionRNG];
            startingX = size*5;
        }

        if(EMSprobability < createEMSProbability){
            //car = "EMS ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY;
            idcar = new IdiotCar(carID, getDirection, startingX, startingY, row);
        }
        else{
            //car = "Car ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY;
            idcar = new IdiotCar(carID, getDirection, startingX, startingY, row);
        }
        //System.out.println(car);
        //testCarList.add(car);
        testCars.add(idcar);
        Thread IDcar = new Thread(idcar);
        testCarThread.add(IDcar);
        IDcar.start();
        currentCars++;
        carID++;
    }

    private void createDMS(){}

    private int RNGCarRoll(){
        Random rand = new Random();
        int NumOfCarsToCreate = 0;
        int probability;

        while(true){
            probability = (int)(Math.random()*100);
            //System.out.println("RNGCarRoll: " + probability + " max prop: " + createVehicleProbability);
            if(NumOfCarsToCreate + currentCars >= maxNumCars) {
                break;
            }

            if(probability < createVehicleProbability){
                NumOfCarsToCreate++;
                //System.out.println("number of cars to make: " + NumOfCarsToCreate);
            }

            else if(probability > createVehicleProbability){
                break;
            }
        }
        //System.out.println("number of cars to make: " + NumOfCarsToCreate);
        return NumOfCarsToCreate;
    }

    private void removeVehicles(){
        int testprobability = (int)(Math.random()*100);
        size = testCarList.size();
        if(testprobability < 25 && size > 0){
            int removeRNG = (int)(Math.random()*size);
            //System.out.println("Removed Car: " + testCarList.get(removeRNG));
            testCarList.remove(removeRNG);
            currentCars--;
        }
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
                    TimeUnit.SECONDS.sleep(3);
                }
                removeVehicles();
                Thread.sleep(sleepDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
