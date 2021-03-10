// CMSC 335
// Project 3
// Evan Martin
// March 9, 2021

//Car.java
// This class defines the car as a runnable SwingWorker
// the worker thread executes the defined behavior of a traffic light

import javax.swing.JTextField;
import javax.swing.SwingWorker;
import java.awt.Color;

import static java.lang.Thread.sleep;

enum TrafficLightColor {
    RED, GREEN, YELLOW
}

class TrafficLight extends SwingWorker<Void, Void> {
    JTextField color;
    private TrafficLightColor trafficLightColor;

    TrafficLight(TrafficLightColor init, JTextField color) {
        this.color = color;
        trafficLightColor = init;
        color.setText(getColor().toString());
        switch (getColor().toString()){
            case "RED" -> color.setBackground(Color.RED);
            case "GREEN" -> color.setBackground(Color.GREEN);
            case "YELLOW" -> color.setBackground(Color.YELLOW);
        }
    }

    public Void doInBackground() throws InterruptedException {
        while (Thread.currentThread().isAlive()) {

                try {
                    switch (trafficLightColor) {
                        case GREEN -> sleep(10000); // green for 10 seconds
                        case YELLOW -> sleep(2000);  // yellow for 2 seconds
                        case RED -> sleep(12000); // red for 12 seconds
                    }
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            if (Main.isPaused) {
                sleep(100);
            }else{
                changeColor();
                color.setText(getColor().toString());
            }
        }
        return null;
    }

    synchronized void changeColor() {
        switch (trafficLightColor) {
            case RED -> {
                trafficLightColor = TrafficLightColor.GREEN;
                color.setBackground(Color.GREEN);
            }
            case YELLOW -> {
                trafficLightColor = TrafficLightColor.RED;
                color.setBackground(Color.RED);
            }
            case GREEN -> {
                trafficLightColor = TrafficLightColor.YELLOW;
                color.setBackground(Color.YELLOW);
            }
        }
    }

    synchronized TrafficLightColor getColor() {
        return trafficLightColor;
    }
}