import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsController implements ActionListener {

    private Model model;
    private Viewer viewer;
    private SettingsPanel settingsPanel;

    public SettingsController(SettingsPanel settingsPanel, Viewer viewer, Model model) {
        this.model = model;
        this.viewer = viewer;
        this.settingsPanel = settingsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Default Skin":
                model.updateCurrentSkin(command);
                viewer.updateSkin();
                settingsPanel.updateButtonStates(command);
                break;
            case "Santa Skin":
                model.updateCurrentSkin(command);
                viewer.updateSkin();
                settingsPanel.updateButtonStates(command);
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
