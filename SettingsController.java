import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsController implements ActionListener {

    private Model model;
    private Viewer viewer;

    public SettingsController(Viewer viewer, Model model) {
        this.model = model;
        this.viewer = viewer;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Default Skins":
                break;
            case "Santa Skin":
                break;
            case "Premium Skin":
                break;
            case "Default Music":
                break;
            case "Jingle Bells":
                break;
            case "Back":
                viewer.showMenu();
                break;

        }
    }

}
