import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuController implements ActionListener {

    private Viewer viewer;
    private Model model;

    public MenuController(Viewer viewer, Model model) {
        this.viewer = viewer;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Play":
                model.changeLevel("Level 1");
                break;
            case "Level":
                viewer.showLevelChooser();
                break;
            case "Settings":
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }
}
