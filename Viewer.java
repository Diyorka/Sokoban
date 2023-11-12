import javax.swing.JFrame;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private LevelChooser levelChooser;
    private MenuPanel menu;
    private JFrame frame;
    private Image backgroundImage;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        backgroundImage = new ImageIcon("images/background.jpg").getImage();
        levelChooser = new LevelChooser(model);
        menu = new MenuPanel(backgroundImage);
        frame = new JFrame("Sokoban");
        frame.setSize(1200, 800);
        frame.setLocation(200, 15);
        frame.add("Center", menu);
        frame.setVisible(true);
        frame.addKeyListener(controller);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update() {
        canvas.repaint();
    }
}
