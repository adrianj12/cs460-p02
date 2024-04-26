package logic;

public class Car extends Vehicle {

    private boolean EMS_inbound;
    private boolean running;


    public void flipEMS_inbound(){
        this.EMS_inbound = !this.EMS_inbound;
    }


    // returns true if the car moves
    public boolean move() {
        return false;
    }

    @Override
    public void run() {
        while (running) {
            move();

            // do stuff
            break;

        }
    }

    public void stop() {
        this.running = false;
        Thread.currentThread().interrupt();
    }

}