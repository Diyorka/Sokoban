import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, ActionListener, MouseListener {

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

    public void keyTyped(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }

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
        }
    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        model.doMouseAction(x, y);
        System.out.println("Clicked at x: " + x + ", y: " + y);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
