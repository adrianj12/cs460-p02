package logic;
public class EMS extends Vehicle {

private boolean running;

    public boolean move() {
        return false;
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