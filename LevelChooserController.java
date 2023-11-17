import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LevelChooserController implements ActionListener {
    private Model model;
    private Viewer viewer;
    private Client client;

    public LevelChooserController(Viewer viewer, Model model) {
        this.model = model;
        this.viewer = viewer;
        client = new Client("alone");
        model.setClient(client);
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch(command) {
            case "Level 1":
            case "Level 2":
            case "Level 3":
            case "Level 4":
            case "Level 5":
            case "Level 6":
            case "Level 7":
            case "Level 8":
            case "Level 9":
                model.changeLevel(command);
                break;
            case "Back":
                viewer.showMenu();
                break;
            default:
                System.out.println("Invalid value");
                break;
        }
    }

}
