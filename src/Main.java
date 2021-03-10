// CMSC 335
// Project 3
// Evan Martin
// March 9, 2021

//Main.java
// This class creates the base GUI and uses the actionListener interface
// to generate the desired components.


import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main extends JFrame implements ActionListener {

    public static boolean isPaused = false;
    private static JFrame frame;
    int count = 0;
    JTextField clockField;
    JTextField light1;
    JTextField light2;
    JTextField light3;
    JTextField car1;
    JTextField car2;
    JTextField car3;
    JButton start;
    JButton addCar;
    JButton pause;
    JButton resume;
    JButton restart;
    JButton stop;
    TrafficLight tl1;
    TrafficLight tl2;
    TrafficLight tl3;
    Car car;
    ArrayList<TrafficLight> lightArrayList = new ArrayList<>();

    Main() throws InterruptedException {

        //main frame
        frame = new JFrame("Traffic Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        //content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridLayout(4, 1));

        //clock panel
        JPanel clockPane = new JPanel();
        clockField = new JTextField(25);
        JLabel clockLabel = new JLabel("Current Time: ");
        clockPane.add(clockLabel);
        clockPane.add(clockField);
        contentPane.add(clockPane);

        //light panel
        JPanel lightPanel = new JPanel();
        JLabel lightLabel1 = new JLabel("Light 1 (1000m) : ");
        JLabel lightLabel2 = new JLabel("Light 2 (2000m) : ");
        JLabel lightLabel3 = new JLabel("Light 3 (3000m) : ");
        light1 = new JTextField(5);
        light2 = new JTextField(5);
        light3 = new JTextField(5);
        lightPanel.add(lightLabel1);
        lightPanel.add(light1);
        lightPanel.add(lightLabel2);
        lightPanel.add(light2);
        lightPanel.add(lightLabel3);
        lightPanel.add(light3);
        contentPane.add(lightPanel);

        //car panel
        JPanel carPanel = new JPanel();
        JLabel carLabel1 = new JLabel("Car 1: ");
        JLabel carLabel2 = new JLabel("Car 2: ");
        JLabel carLabel3 = new JLabel("Car 3: ");
        car1 = new JTextField(5);
        car2 = new JTextField(5);
        car3 = new JTextField(5);
        carPanel.add(carLabel1);
        carPanel.add(car1);
        carPanel.add(carLabel2);
        carPanel.add(car2);
        carPanel.add(carLabel3);
        carPanel.add(car3);
        contentPane.add(carPanel);

        //control panel
        JPanel controlPanel = new JPanel();
        start = new JButton("Start");
        addCar = new JButton("Add Car");
        pause = new JButton("Pause");
        resume = new JButton("Resume");
        restart = new JButton("Restart");
        stop = new JButton("Stop");
        addCar.setEnabled(false);
        pause.setEnabled(false);
        resume.setEnabled(false);
        restart.setEnabled(false);
        stop.setEnabled(false);
        controlPanel.add(start);
        controlPanel.add(addCar);
        controlPanel.add(pause);
        controlPanel.add(resume);
        controlPanel.add(restart);
        controlPanel.add(stop);
        contentPane.add(controlPanel);

        //button action listeners
        start.addActionListener(this);
        addCar.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        restart.addActionListener(this);
        stop.addActionListener(this);

        //set visible
        frame.add(contentPane);
        frame.setVisible(true);
    }

    public static void makeFrame() {
        SwingUtilities.invokeLater(() -> {
            try {
                new Main();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        makeFrame();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Start")) {
            Clock clock = new Clock(clockField);
            Thread clockThread = new Thread(clock);
            clockThread.start();
            tl1 = new TrafficLight(TrafficLightColor.GREEN, light1);
            tl2 = new TrafficLight(TrafficLightColor.YELLOW, light2);
            tl3 = new TrafficLight(TrafficLightColor.RED, light3);
            lightArrayList.add(tl1);
            lightArrayList.add(tl2);
            lightArrayList.add(tl3);
            Thread lightThread1 = new Thread(tl1);
            Thread lightThread2 = new Thread(tl2);
            Thread lightThread3 = new Thread(tl3);
            lightThread1.start();
            lightThread2.start();
            lightThread3.start();
            start.setEnabled(false);
            addCar.setEnabled(true);
            pause.setEnabled(true);
            resume.setEnabled(false);
            restart.setEnabled(true);
            stop.setEnabled(true);
        }
        if (ae.getActionCommand().equals("Add Car")) {

            if (car1.getText().equals("")) {
                count = 1;
            } else if (car2.getText().equals("")) {
                count = 2;
            } else if (car3.getText().equals("")) {
                count = 3;
            }

            switch (count) {
                case 1 -> car = new Car(car1, lightArrayList);
                case 2 -> car = new Car(car2, lightArrayList);
                case 3 -> car = new Car(car3, lightArrayList);
            }
            count = 0;

            Thread carThread = new Thread(car);
            carThread.start();
        }
        if (ae.getActionCommand().equals("Pause")) {
            isPaused = true;
            addCar.setEnabled(false);
            resume.setEnabled(true);
        }
        if (ae.getActionCommand().equals("Resume")) {
            isPaused = false;
            addCar.setEnabled(true);
        }
        if (ae.getActionCommand().equals("Restart")) {
            restart();
        }
        if (ae.getActionCommand().equals("Stop")) {
            isPaused = true;
            addCar.setEnabled(false);
            pause.setEnabled(false);
            resume.setEnabled(false);
        }
    }

    public void restart() {
        Main.frame.setVisible(false);
        dispose();
        count = 0;
        isPaused = false;
        makeFrame();
    }
}
