import javax.swing.*;
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