package logic;

import javafx.stage.Screen;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class SystemManager implements Runnable{

    private double size, xLane1, xLane2, xLane3, xLane4, xLane5, xLane6, xLane7, xLane8;
    private double yLane1, yLane2, yLane3, yLane4, yLane5, yLane6, yLane7, yLane8, yLane9, yLane10,yLane11, yLane12;

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
