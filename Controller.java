import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
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

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();
        if (isSoundOn) {
            model.stopMusic();
            isSoundOn = false;
            setIconAndResize(label, "images/sound-off.png", 80, 80);
        } else {
            isSoundOn = true;
            model.playCurrentMusic();
            setIconAndResize(label, "images/sound-on.png", 80, 80);
        }
    }

    private void setIconAndResize(JLabel label, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        label.setIcon(scaledIcon);
        label.setSize(width, height);
    }







    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
