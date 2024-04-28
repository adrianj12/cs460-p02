package logic;

import javafx.stage.Screen;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SysMan2 implements Runnable{

    private double tileSize;
    private static int carID = 0;
    protected static int sleepDelay = 3000;
    private static int currentCars = 0;
    private static int maxNumCars = 100;
    protected static int createVehicleProbability = 55;
    protected static int createEMSProbability = 25;
    private static Direction directions[] = {Direction.NORTH, Direction.SOUTH
            , Direction.EAST,
            Direction.WEST};
    private int eastLanes[];
    private int westLanes[];
    private int northLanes[];
    private int southLanes[];

    protected static CopyOnWriteArrayList<Car> carList =
            new CopyOnWriteArrayList<>();
    protected static CopyOnWriteArrayList<Intersection2> intersectionList =
            new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<Thread> carThreads =
            new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Intersection> intersectionThreads =
            new CopyOnWriteArrayList<>();


    public SysMan2(){

    }

    private void createLanes(){


//        East_Lanes = new double[]{xLane3, xLane4, xLane7, xLane8};
//        West_Lanes = new double[]{xLane1, xLane2, xLane5, xLane6};
//        North_Lanes = new double[]{yLane3, yLane4, yLane7, yLane8, yLane11, yLane12};
//        South_Lanes = new double[]{yLane1, yLane2, yLane5, yLane6, yLane9, yLane10};

    }

    private void createIntersections() {
        int id = 0;
        for (int i = 0; i < 3; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                intersectionList.add(new Intersection2(
                        id,
                        new Point((i * 200) + 100, (j * 200) + 100))
                );
                id++;
            }
        }
    }


    private void createVehicle(){
//        //RNG EMS vehicle creation.
//        int EMSprobability;
//        int directionRNG;
//        String car;
//        EMSprobability = (int)(Math.random()*100);
//        directionRNG = (int)(Math.random()*(4));
//        double startingX = 0;
//        double startingY = 0;
//        String getDirection = direction[directionRNG];
//
//        if(getDirection.equals("North")){
//            directionRNG = (int)(Math.random()*(North_Lanes.length-1));
//            startingY = size*3;
//            startingX = North_Lanes[directionRNG];
//        }
//        else if(getDirection.equals("South")){
//            directionRNG = (int)(Math.random()*(South_Lanes.length-1));
//            startingY = 0;
//            startingX = South_Lanes[directionRNG];
//        }
//        else if(getDirection.equals("East")){
//            directionRNG = (int)(Math.random()*(East_Lanes.length-1));
//            startingY = East_Lanes[directionRNG];
//            startingX = 0;
//        }
//        else if(getDirection.equals("West")){
//            directionRNG = (int)(Math.random()*(West_Lanes.length-1));
//            startingY = West_Lanes[directionRNG];
//            startingX = size*5;
//        }
//
//        if(EMSprobability < createEMSProbability){
//            car = "EMS ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY;
//
//        }
//        else{
//            car = "Car ID: " + carID + " direction: " + getDirection + " staringX: " + startingX + " startingY " + startingY;
//        }
//        //System.out.println(car);
//        testCarList.add(car);
//        currentCars++;
//        carID++;
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
//        int testprobability = (int)(Math.random()*100);
//        size = testCarList.size();
//        if(testprobability < 25 && size > 0){
//            int removeRNG = (int)(Math.random()*size);
//            //System.out.println("Removed Car: " + testCarList.get(removeRNG));
//            testCarList.remove(removeRNG);
//            currentCars--;
//        }
    }

    @Override
    public void run() {
        int numCarCreate = 0;
        createLanes();
        createIntersections();
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
