import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.File;
import java.awt.Component;


public class Viewer {

    private Controller controller;
    private Canvas canvas;
    private Canvas myCanvas;
    private Canvas enemyCanvas;
    private JSplitPane splitPane;
    private SettingsPanel settings;
    private JFrame frame;
    private CardLayout cardLayout;
    private Model model;
    private EnemyModel enemyModel;

    public Viewer() {
        model = new Model(this);
        enemyModel = new EnemyModel(this);
        controller = new Controller(this, model);
        canvas = new Canvas(model, controller);
        canvas.addKeyListener(controller);

        myCanvas = new Canvas(model, controller);
        myCanvas.addKeyListener(controller);

        enemyCanvas = new Canvas(enemyModel, null);
        LevelChooser levelChooser = new LevelChooser(this, model);
        settings = new SettingsPanel(this, model);
        MenuPanel menu = new MenuPanel(this, model, enemyModel);

        cardLayout = new CardLayout();

        // for two players
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myCanvas, enemyCanvas);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);


        frame = new JFrame("Sokoban");
        frame.setSize(1200, 720);
        frame.setLocation(150, 5);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(cardLayout);

        frame.add(menu, "menu");
        frame.add(levelChooser, "levelChooser");
        frame.add(settings, "settings");
        frame.add(canvas, "canvas");
        frame.add(splitPane, "splitPane");

        frame.setResizable(true);
        frame.setVisible(true);
    }

    public Viewer getViewer() {
        return this;
    }

    public void update() {
        canvas.repaint();
    }
    public void updateEnemyCanvas() {
        enemyCanvas.repaint();
    }

    public void updateMyCanvas() {
        myCanvas.repaint();
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

    public void showCanvas(int gamersCount) {
        System.out.println("in show canvas gamersCount = " + gamersCount);
        if(gamersCount == 1) {
            showCanvas();
            return;
        }
        showTwoCanvas();

    }

    private boolean hasFrameCanvas() {
        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            if (component == canvas) {
                return true;
            }
        }
        return false;
    }

    private void showTwoCanvas() {
        update();
        updateEnemyCanvas();
        cardLayout.show(frame.getContentPane(), "splitPane");
        myCanvas.requestFocusInWindow();

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
