import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuController implements ActionListener {
    private MenuPanel menuPanel;
    private Viewer viewer;
    private Model model;
    private EnemyModel enemyModel;

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
                model.changeLevel("Level 1", 1);
                break;
            case "PlayWithEnemy":
                System.out.println("play with enemy");
                model.changeLevel("Level 1", 2);
                /////
                enemyModel.changeLevel("Level 1");

                break;
            case "Level":
                viewer.showLevelChooser();
                break;
            case "Settings":
                viewer.showSettings();
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }
}
