import javax.swing.JFrame;
import java.awt.Image;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private LevelChooser levelChooser;
    private MenuPanel menu;
    private JFrame frame;
    private Image backgroundImage;
    private CardLayout cardLayout;

    public Viewer() {
        controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        canvas.addKeyListener(controller);
        levelChooser = new LevelChooser(this, model);

        backgroundImage = new ImageIcon("images/background.jpg").getImage();
        menu = new MenuPanel(this, model);

        cardLayout = new CardLayout();

        frame = new JFrame("Sokoban");
        frame.setSize(1200, 700);
        frame.setLocation(100, 15);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(cardLayout);

        frame.add(menu, "menu");
        frame.add(levelChooser, "levelChooser");
        frame.add(canvas, "canvas");

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void update() {
        canvas.repaint();
    }

    public void showMenu() {
      cardLayout.show(frame.getContentPane(), "menu");
    }

    public void showCanvas() {
        update();
        cardLayout.show(frame.getContentPane(), "canvas");
        canvas.requestFocusInWindow();
    }

    public void showLevelChooser() {
        cardLayout.show(frame.getContentPane(), "levelChooser");
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }
}
