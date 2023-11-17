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
                model.initPlayer(nickname);
                viewer.updateSettings(model.getPlayer());
                break;
            case "Play":
                client = new Client(viewer, "alone");
                model.setClient(client);
                model.changeLevel("Level 1");
                break;
            case "PlayWithEnemy":
                System.out.println("play with enemy");
                client = new Client(viewer, "battle");
                model.setClient(client);
                model.changeLevel("Level 7");

                enemyModel.setClient(client);
                enemyModel.changeLevel();

                System.out.println("creating new Thread enemyFieldController");
                EnemyFieldController enemyFieldController = new EnemyFieldController(client, viewer, enemyModel);
                enemyFieldController.go();
                break;
            case "Level":
                viewer.showLevelChooser();
                break;
            case "Settings":
                viewer.showSettings();
                break;
            case "Exit":
                if(client != null) {
                    client.closeClient();
                }
                System.exit(0);
                break;
        }
    }
}
