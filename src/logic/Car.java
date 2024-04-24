package logic;

public class Car extends Vehicle {

    private boolean EMS_inbound;
    private boolean running;


    public void flipEMS_inbound(){
        this.EMS_inbound = !this.EMS_inbound;
    }


    public void move() {
    }

    @Override
    public void run() {
        while (running) {

            // do stuff
            break;

        }
    }

    public void stop() {
        this.running = false;
        Thread.currentThread().interrupt();
    }

}