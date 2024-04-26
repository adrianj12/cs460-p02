package logic;

import javafx.stage.Screen;

import java.util.concurrent.CopyOnWriteArrayList;

public class Dummy_Thread implements Runnable{
    private CopyOnWriteArrayList<String> list = SystemManager.testCarList;
    int id;
    int sleeper;
    public Dummy_Thread(int ID, int sleepTime){
        id = ID;
        sleeper = sleepTime;
    }

    public void printList(){
        int size = list.size();
        for(int i = 0; i < size; i++) {
            System.out.println("Dummy :"+ id + " print list: " + list.get(i));
        }

    }
    @Override
    public void run() {
        System.out.println("hello");
        while (true){
            try {
                printList();
                System.out.println("");
                Thread.sleep(sleeper);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
