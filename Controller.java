import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JButton;

public class Controller implements KeyListener, ActionListener, MouseListener {

    private Model model;
    private Viewer viewer;
    private boolean isSoundOn = true;

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
            case "Choose Level":
                viewer.showLevelChooser();
                break;
            case "Sound Off":
                JButton button = (JButton) e.getSource();
                if (isSoundOn) {
                    model.stopMusic();
                    isSoundOn = false;
                    setIconAndResize(button, "images/sound-off.png", 80, 80);
                } else {
                    isSoundOn = true;
                    model.playCurrentMusic();
                    setIconAndResize(button, "images/sound-on.png", 80, 80);
                }
                break;
            case "MoveBack":
                model.moveBack();
                viewer.showCanvas();
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

    public void mouseClicked(MouseEvent e) {
        int x = e.getPoint().x;
        int y = e.getPoint().y;
        model.doMouseAction(x, y);
    }

     private void setIconAndResize(JButton button, String imagePath, int width, int height) {
         ImageIcon icon = new ImageIcon(imagePath);
         Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
         ImageIcon scaledIcon = new ImageIcon(scaledImage);
         button.setIcon(scaledIcon);
         button.setSize(width, height);
     }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
