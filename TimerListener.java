import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
    private JLabel label;
    private int count = 30;
    public TimerListener(JLabel label) {
        this.label = label;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Timer in action");

        if (count > 0) {
            label.setText(count + "s");
            count--;
        } else {
            ((Timer) e.getSource()).stop();
            label.setText("Time's up!");
        }
    }
}
