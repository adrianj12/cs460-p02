package logic;

public class Car extends Vehicle {

    boolean EMS_inbound;


    public void flipEMS_inbound(){
        this.EMS_inbound = !this.EMS_inbound;
    }
}