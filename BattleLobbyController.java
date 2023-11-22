import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BattleLobbyController implements ActionListener {

    private Model model;
    private Viewer viewer;
    private BattleLobbyPanel lobby;

    public BattleLobbyController(BattleLobbyPanel lobby, Viewer viewer, Model model) {
        this.model = model;
        this.viewer = viewer;
        this.lobby = lobby;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        System.out.println(command);
        switch (command) {
            case "Cancel":
                Client client = model.getClient();
                System.out.println(client);
                viewer.showMenu();
                break;
        }
    }
}
