import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
    private JLabel label;
    private Client client;
    private int count = 30;
    public TimerListener(JLabel label, Client client) {
        this.label = label;
        this.client = client;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Timer timer = ((Timer) e.getSource());
        System.out.println("Timer in action");
        System.out.println(client.hasConnectionToServer());
        if(client.hasConnectionToServer()) {
            if (count > 0) {
                label.setText(count + "s");
                count--;
            } else {
                timer.stop();
                label.setText("Time's up!");
            }
        } else {
            timer.stop();
        }

    }
}
