package logic;

import java.util.Random;

public class SystemManager implements Runnable{

    private static int numIntersection = 12;
    protected static int sleepDelay = 10;
    private static int numCarCreate = 0;
    private static int maxNumCars = 100;

    protected static int createCarProbability = 20;

    //protected List<Car> Cars = new ArrayList<Car>();
    //protected List<Thread> Cars = new ArrayList<Thread>();

    //protected static Intersection intersections[];
    //protected static Thread intersections[];



    private void createVehicle(){}
    private void createIntersection(){}

    private void createDMS(){}

    private int RNGCarRoll(){
        Random rand = new Random();
        int NumOfCarsToCreate = 0;
        int probability;

        while(true){
            probability = (int)(Math.random()*100);
            if(probability <= createCarProbability){
                NumOfCarsToCreate++;
            }
            else {
                break;
            }
        }
        return NumOfCarsToCreate;
    }


    @Override
    public void run() {
        createIntersection();
        createDMS();

        while(true){
            try {
                //Add if statement for max number of cars
                numCarCreate = RNGCarRoll();
                for(int i = 0; i < numCarCreate; i++){
                    createVehicle();
                }
                Thread.sleep(sleepDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
