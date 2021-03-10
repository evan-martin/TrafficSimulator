import javax.swing.*;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Car extends SwingWorker<Void, Void> {

    int position = 0;
    int speed = 10; // meters/second
    int distance = 3050; //meters
    JTextField textField;

    ArrayList<TrafficLight> lightArrayList;

    Car(JTextField textField, ArrayList<TrafficLight> trafficLights) {
        this.textField = textField;
        this.lightArrayList = trafficLights;
    }

    @Override
    public Void doInBackground() throws Exception {
        while (position < distance) {
            if (position == 1000) {
                while (lightArrayList.get(0).getColor().equals(TrafficLightColor.RED)) {
                    sleep(100);
                }
            }
            if (position == 2000) {
                while (lightArrayList.get(1).getColor().equals(TrafficLightColor.RED)) {
                    sleep(100);
                }
            }
            if (position == 3000) {
                while (lightArrayList.get(2).getColor().equals(TrafficLightColor.RED)) {
                    sleep(100);
                }
            }
            if (Main.isPaused) {
                sleep(100);
            }else {
                position = position + speed;
                textField.setText(String.valueOf(position));

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected synchronized void done() {
        textField.setText("");
    }
}