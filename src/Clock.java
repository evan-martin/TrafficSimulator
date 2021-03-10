// CMSC 335
// Project 3
// Evan Martin
// March 9, 2021

//Clock.java
// This class defines the clock as a runnable SwingWorker
// the worker thread executes the defined behavior of a clock

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.util.Date;

public class Clock extends SwingWorker<Void, Void>{

    JTextField time;

    public Clock(JTextField textField){
        time = textField;
    }

    @Override
    public Void doInBackground() {
        while(Thread.currentThread().isAlive()) {
            if(!Main.isPaused) {
                Date d1 = new Date();
                time.setText(d1.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return null;
    }
}