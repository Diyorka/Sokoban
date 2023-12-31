import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuController implements ActionListener {
    private MenuPanel menuPanel;
    private Viewer viewer;
    private Model model;
    private EnemyModel enemyModel;
    private Client client;

    public MenuController(MenuPanel menuPanel, Viewer viewer, Model model, EnemyModel enemyModel) {
        this.viewer = viewer;
        this.model = model;
        this.menuPanel = menuPanel;
        this.enemyModel = enemyModel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Set name":
                String nickname = menuPanel.getNicknameText();
                model.setPlayer(nickname);
                viewer.getLevelChooser().initCoins();
                break;
            case "Play":
                client = new Client(viewer, "alone");
                model.setClient(client);
                model.startNotPassedLevel();
                break;
            case "PlayWithEnemy":
                client = new Client(viewer, "battle");
                if (client.hasConnectionToServer()) {
                    model.setClient(client);
                    model.changeLevel();
                    enemyModel.setClient(client);
                    enemyModel.changeLevel();
                    EnemyFieldController enemyFieldController = new EnemyFieldController(client, viewer, enemyModel);
                    enemyFieldController.go();
                }
                break;
            case "Level":
                viewer.showLevelChooser();
                break;
            case "Settings":
                viewer.showSettings();
                break;
            case "Exit":
                if (client != null) {
                    client.closeClient();
                }

                System.exit(0);
                break;
        }
    }
}
