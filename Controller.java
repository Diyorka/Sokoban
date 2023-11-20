import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements KeyListener, ActionListener {
    private Model model;
    private Viewer viewer;

    public Controller(Viewer viewer, Model model) {
        this.viewer = viewer;
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        model.doAction(key);
    }

    public void keyTyped(KeyEvent event) {}

    public void keyReleased(KeyEvent event) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command) {
            case "Next level":
                model.getNextLevel();
                break;
            case "Exit to menu":
                System.out.println("Exit to menu ");
                model.getClient().closeClient();
                viewer.showMenu();
                break;
            case "Restart":
                model.restart();
                viewer.showCanvas();
                break;
            case "GiveUp":
                System.out.println("Give up");
                model.giveUp();
        }
    }
}
