package logic;

import javafx.stage.Screen;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SystemManager implements Runnable{
    Car car;
    EMS ems;
    int row;
    private double size, outputSize, xLane1, xLane2, xLane3, xLane4, xLane5, xLane6, xLane7, xLane8;
    private double yLane1, yLane2, yLane3, yLane4, yLane5, yLane6, yLane7, yLane8, yLane9, yLane10,yLane11, yLane12;
    private double x0INTX1, xFINTX1, x0INTX2, xFINTX2, x0INTX3, xFINTX3, x0INTX4, xFINTX4, x0INTX5, xFINTX5, x0INTX6, xFINTX6;
    private double y0INTX1, yFINTX1, y0INTX2, yFINTX2, y0INTX3, yFINTX3, y0INTX4, yFINTX4, y0INTX5, yFINTX5, y0INTX6, yFINTX6;
    private double cxINTX1, cyINTX1, cxINTX2, cyINTX2, cxINTX3, cyINTX3, cxINTX4, cyINTX4, cxINTX5, cyINTX5, cxINTX6, cyINTX6;
    private static int carID = 0;
    protected static int sleepDelay = 3000;
    private static int currentCars = 0;
    private static int maxNumCars = 100;
    protected static int createVehicleProbability = 75;
    protected static int createEMSProbability = 25;
    private static String direction[] = {"North", "South", "East", "West"};
    private double East_Lanes[];
    private double West_Lanes[];
    private double North_Lanes[];
    private double South_Lanes[];

    public static CopyOnWriteArrayList<Car> cars = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Thread> carThreads = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<EMS> emsVehicles = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Thread> emsThreads = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Intersection> intersections = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Thread> intersectionThreads = new CopyOnWriteArrayList<>();


    public SystemManager(int Size){
        size = Size;
    }

    private void createLanes(){
        //xLanes store y coordiantes. East-West Traffic.
        xLane1 = size/2.85;
        xLane2 = size/2.2;

        xLane3 = size/1.825;
        xLane4 = size/1.55;

        xLane5 = (size*2)+(size/2.85);
        xLane6 = (size*2)+(size/2.2);

        xLane7 = (size*2)+(size/1.825);
        xLane8 = (size*2)+(size/1.55);


        //yLanes store x coordinates. North-South Traffic
        yLane1 = size/2.85;
        yLane2 = size/2.2;

        yLane3 = size/1.825;
        yLane4 = size/1.55;

        yLane5 = (size*2)+(size/2.85);
        yLane6 = (size*2)+(size/2.2);

        yLane7 = (size*2)+(size/1.825);
        yLane8 = (size*2)+(size/1.55);

        yLane9 = (size*4)+(size/2.85);
        yLane10 = (size*4)+(size/2.2);

        yLane11 = (size*4)+(size/1.825);
        yLane12 = (size*4)+(size/1.55);

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
        cxINTX4 = size/2;
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

//        String INTX1 = "INTX 1- x0: " + x0INTX1 + " xF: " +xFINTX1 + " y0: " + y0INTX1 + " cx: " + cxINTX1
//                +" cy: " + cyINTX1 + " Nlane1: " + North_Lanes[0] + " Nlane2: " + North_Lanes[1] +
//                " Slane1: " + South_Lanes[0] + " Slane2: " + South_Lanes[1] +
//                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
//                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];
//
//        String INTX2 = "INTX 2- x0: " + x0INTX2 + " xF: " +xFINTX2 + " y0: " + y0INTX2 + " cx: " + cxINTX2
//                +" cy: " + cyINTX2 + " Nlane1: " + North_Lanes[2] + " Nlane2: " + North_Lanes[3] +
//                " Slane1: " + South_Lanes[2] + " Slane2: " + South_Lanes[3] +
//                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
//                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];
//
//        String INTX3 = "INTX 3- x0: " + x0INTX3 + " xF: " +xFINTX3 + " y0: " + y0INTX3 + " cx: " + cxINTX3
//                +" cy: " + cyINTX3 + " Nlane1: " + North_Lanes[4] + " Nlane2: " + North_Lanes[5] +
//                " Slane1: " + South_Lanes[4] + " Slane2: " + South_Lanes[5] +
//                " Elane1: " + East_Lanes[0] + " Elane2: " + East_Lanes[1] +
//                " Wlane1: " + West_Lanes[0] + " Wlane2: " + West_Lanes[1];
//
//        String INTX4 = "INTX 4- x0: " + x0INTX4 + " xF: " +xFINTX4 + " y0: " + y0INTX4 + " cx: " + cxINTX4
//                +" cy: " + cyINTX4 + " Nlane1: " + North_Lanes[0] + " Nlane2: " + North_Lanes[1] +
//                " Slane1: " + South_Lanes[0] + " Slane2: " + South_Lanes[1] +
//                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
//                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];
//
//        String INTX5 = "INTX 5- x0: " + x0INTX5 + " xF: " +xFINTX5 + " y0: " + y0INTX5 + " cx: " + cxINTX5
//                +" cy: " + cyINTX5 + " Nlane1: " + North_Lanes[2] + " Nlane2: " + North_Lanes[3] +
//                " Slane1: " + South_Lanes[2] + " Slane2: " + South_Lanes[3] +
//                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
//                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];
//
//        String INTX6 = "INTX 6- x0: " + x0INTX6 + " xF: " +xFINTX6 + " y0: " + y0INTX6 + " cx: " + cxINTX6
//                +" cy: " + cyINTX6 + " Nlane1: " + North_Lanes[4] + " Nlane2: " + North_Lanes[5] +
//                " Slane1: " + South_Lanes[4] + " Slane2: " + South_Lanes[5] +
//                " Elane1: " + East_Lanes[2] + " Elane2: " + East_Lanes[3] +
//                " Wlane1: " + West_Lanes[2] + " Wlane2: " + West_Lanes[3];

//        Intersection INTX1 = new Intersection(1,x0INTX1, xFINTX1, y0INTX1, yFINTX1, cxINTX1, cyINTX1);
//        intersections.add(INTX1);
//        Thread INTX1Thread = new Thread(INTX1);
//        intersectionThreads.add(INTX1Thread);
//
//        Intersection INTX2 = new Intersection(2,x0INTX2, xFINTX2, y0INTX2, yFINTX2, cxINTX2, cyINTX2);
//        intersections.add(INTX2);
//        Thread INTX2Thread = new Thread(INTX2);
//        intersectionThreads.add(INTX2Thread);
//
//        Intersection INTX3 = new Intersection(3,x0INTX3, xFINTX3, y0INTX3, yFINTX3, cxINTX3, cyINTX3);
//        intersections.add(INTX3);
//        Thread INTX3Thread = new Thread(INTX3);
//        intersectionThreads.add(INTX3Thread);
//
//        Intersection INTX4 = new Intersection(4,x0INTX4, xFINTX4, y0INTX4, yFINTX4, cxINTX4, cyINTX4);
//        intersections.add(INTX4);
//        Thread INTX4Thread = new Thread(INTX4);
//        intersectionThreads.add(INTX4Thread);
//
//        Intersection INTX5 = new Intersection(5,x0INTX5, xFINTX5, y0INTX5, yFINTX5, cxINTX5, cyINTX5);
//        intersections.add(INTX5);
//        Thread INTX5Thread = new Thread(INTX5);
//        intersectionThreads.add(INTX5Thread);
//
//        Intersection INTX6 = new Intersection(6,x0INTX6, xFINTX6, y0INTX6, yFINTX6, cxINTX6, cyINTX6);
//        intersections.add(INTX6);
//        Thread INTX6Thread = new Thread(INTX6);
//        intersectionThreads.add(INTX6Thread);
//        for(int j = 0; j < 6; i++){
//            intersectionThreads.get(i).start();
//        }
    }

    private void createVehicle(){
        //RNG EMS vehicle creation.
        Point carCoordinate = new Point();
        int EMSprobability;
        int directionRNG;
        int laneRNG;
        //String car;
        String Lane = "";
        EMSprobability = (int)(Math.random()*100);
        directionRNG = (int)(Math.random()*(4));
        double startingX = 0;
        double startingY = 0;
        String getDirection = direction[directionRNG];

        if(getDirection.equals("North")){
            laneRNG = (int)(Math.random()*(North_Lanes.length-1));
            startingY = size*3;
            startingX = North_Lanes[laneRNG];
        }
        else if(getDirection.equals("South")){
            laneRNG = (int)(Math.random()*(South_Lanes.length-1));
            startingY = 0;
            startingX = South_Lanes[laneRNG];
        }
        else if(getDirection.equals("East")){
            laneRNG = (int)(Math.random()*(East_Lanes.length-1));
            startingY = East_Lanes[laneRNG];
            startingX = 0;
        }
        else if(getDirection.equals("West")){
            laneRNG = (int)(Math.random()*(West_Lanes.length-1));
            startingY = West_Lanes[laneRNG];
            startingX = size*5;
        }
        if(getDirection.equals("North") || getDirection.equals("East")){
            if(directionRNG%2 == 0){
                Lane = "Left";
            }
            else{
                Lane = "Right";
            }
        }

        if(getDirection.equals("South") || getDirection.equals("West")){
            if(directionRNG%2 == 0){
                Lane = "Right";
            }
            else{
                Lane = "Left";
            }
        }

        carCoordinate.setLocation(startingX, startingY);
        if(EMSprobability < createEMSProbability){
            //car = "EMS ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY +
            //        " Lane: " + Lane;
            //ems = new EMS(carID, getDirection, carCoordinate, row, Lane);
            emsVehicles.add(ems);
            Thread emsThread = new Thread(ems);
            emsThreads.add(emsThread);
            emsThread.start();
        }
        else{
            //car = "Car ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY +
            //        " Lane: " + Lane;
            //car = new Car(carID, getDirection, carCoordinate , outputSize, Lane);
            cars.add(car);
            Thread carThread = new Thread(car);
            carThreads.add(carThread);
            carThread.start();
        }
        //System.out.println(car);
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
        int numCarThreads = carThreads.size();
        int numEMSThreads = emsThreads.size();
        if(numCarThreads > 0){
            for(int i = 0; i < numCarThreads-1; i++){
                if(!carThreads.get(i).isAlive()){
                    //System.out.println("should remove car");
                    carThreads.remove(i);
                    cars.remove(i);
                    currentCars--;
                    break;
                }
            }
        }
        if(numEMSThreads > 0){
            for(int i = 0; i < numEMSThreads-1; i++){
                if(!emsThreads.get(i).isAlive()){
                    //System.out.println("should remove car");
                    emsThreads.remove(i);
                    emsVehicles.remove(i);
                    currentCars--;
                    break;
                }
            }
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
                for(int j = 0; j < cars.size(); j++){
                    removeVehicles();
                }
                Thread.sleep(sleepDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
