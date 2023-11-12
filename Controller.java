import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Controller implements KeyListener {

    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        System.out.println(key);
        key = Character.toLowerCase(key);
        model.doAction(key);
    }

    public void keyTyped(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }
}
