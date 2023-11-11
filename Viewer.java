import javax.swing.JFrame;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private LevelChooser levelChooser;
    private JFrame frame;
    private Image backgroundImage;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        levelChooser = new LevelChooser();
        File backgroundFile = new File("images/background.jpg");
        backgroundImage = getImage(backgroundFile);
    }

    public void runApplication() {
        MenuPanel menu = new MenuPanel(backgroundImage);
        frame = new JFrame("Sokoban");
        frame.setSize(1000, 700);
        frame.setLocation(270, 70);
        frame.add("Center", menu);
        frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update() {
        canvas.repaint();
    }

    private Image getImage(File file) {
        Image image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
          System.out.println("Error: " + e);
        }
        return image;
    }
}
