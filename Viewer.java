import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.File;

public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private SettingsPanel settings;
    private JFrame frame;
    private CardLayout cardLayout;
    private Model model;

    public Viewer() {
        model = new Model(this);
        controller = new Controller(this, model);
        canvas = new Canvas(model, controller);
        canvas.addKeyListener(controller);
        LevelChooser levelChooser = new LevelChooser(this, model);
        settings = new SettingsPanel(this, model);
        MenuPanel menu = new MenuPanel(this, model);

        cardLayout = new CardLayout();

        frame = new JFrame("Sokoban");
        frame.setSize(1200, 800);
        frame.setLocation(200, 15);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(cardLayout);

        frame.add(menu, "menu");
        frame.add(levelChooser, "levelChooser");
        frame.add(canvas, "canvas");
        frame.add(settings, "settings");

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Viewer getViewer() {
        return this;
    }

    public void update() {
        canvas.repaint();
    }

    public void updateSettings(Player player) {
        settings.setPlayer(player);
        settings.repaint();
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

    public void showSettings() {
        cardLayout.show(frame.getContentPane(), "settings");
    }

    public Font getCustomFont(int style, float size) {
        Font customFont = null;
        File file = new File("fonts/PixelFont.otf");
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        return customFont;
    }
}
