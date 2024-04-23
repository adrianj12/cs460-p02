package logic;
public class EMS extends Vehicle {

private boolean running;

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