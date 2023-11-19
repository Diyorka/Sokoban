import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerListener implements ActionListener {
    private JLabel label;
    private Client client;
    private CanvasForTwoPlayers canvasForTwoPlayers;
    private String canvasType;
    private int count = 30;
    private Viewer viewer;
    private Timer timer;
    public TimerListener(JLabel label, Client client, CanvasForTwoPlayers canvasForTwoPlayers, String canvasType, Viewer viewer) {
        this.label = label;
        this.client = client;
        this.canvasForTwoPlayers = canvasForTwoPlayers;
        this.viewer = viewer;
        this.canvasType = canvasType;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer = ((Timer) e.getSource());
        System.out.println("Timer in action");
        System.out.println(client.hasConnectionToServer());
        if(client.hasConnectionToServer()) {
            if (count > 0) {
                label.setText(count + "s");
                count--;
            } else {
                resetTimer();
            }
        } else {
            resetTimer();

        }

    }

    private void resetTimer() {
        if(canvasType.equals("enemyCanvas")) { // if we set timer on enemyCanvas (we set disable our canvas )
            viewer.enableMyCanvas();
        }
        timer.stop();
        // label.setText("Time's up!");
        canvasForTwoPlayers.removeTimer();
    }
}
