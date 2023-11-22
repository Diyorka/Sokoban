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
    private EnemyModel enemyModel;
    private Model model;
    private Timer timer;
    private String absoluteWinner;

    public TimerListener(JLabel label, Client client, CanvasForTwoPlayers canvasForTwoPlayers, String canvasType, Viewer viewer) {
        this.label = label;
        this.client = client;
        this.canvasForTwoPlayers = canvasForTwoPlayers;
        this.viewer = viewer;
        this.enemyModel = viewer.getEnemyModel();
        this.model = viewer.getModel();
        this.canvasType = canvasType;
        absoluteWinner = canvasType.equals("enemyCanvas") ? "me" : "enemy";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer = ((Timer) e.getSource());

        boolean isPlayerCompleteGame = canvasType.equals("enemyCanvas") ? enemyModel.getIsEnemyCompletedGame() : model.getIsPlayerCompleteGame();
        System.out.println(client.hasConnectionToServer());
        System.out.println(isPlayerCompleteGame);
        if (client.hasConnectionToServer() && !isPlayerCompleteGame) {
            if (count > 0) {
                label.setText(count + "s");
                count--;
            } else {
                label.setText("Time's up!");
                resetTimer();
                finishGame(absoluteWinner);
            }

        } else {
            absoluteWinner = null;
            resetTimer();
            finishGame(absoluteWinner);
        }

    }

    private void resetTimer() {
        timer.stop();
        canvasForTwoPlayers.removeTimer();

        if (canvasType.equals("enemyCanvas")) {
            viewer.enableMyCanvas();
            viewer.updateEnemyCanvas();
        }

        viewer.updateMyCanvas();
    }

    private void finishGame(String absoluteWinner) {
        viewer.resultsOnlineGameDialog(absoluteWinner);
        viewer.showMenu();
        client.closeClient();
    }
}
