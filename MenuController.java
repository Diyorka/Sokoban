import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Play":
                break;
            case "Level":
                break;
            case "Settings":
                break;
            case "Exit":
                System.exit(0);
                break;
        }
    }
}
