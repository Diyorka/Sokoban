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
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Viewer {
    private Controller controller;
    private Canvas canvas;
    private CanvasForTwoPlayers myCanvas;
    private CanvasForTwoPlayers enemyCanvas;
    private JSplitPane splitPane;
    private SettingsPanel settings;
    private LevelChooser levelChooser;
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

        myCanvas = new CanvasForTwoPlayers(model, controller, "myCanvas");
        myCanvas.addKeyListener(controller);

        enemyCanvas = new CanvasForTwoPlayers(enemyModel, null, "enemyCanvas");
        levelChooser = new LevelChooser(this, model);
        settings = new SettingsPanel(this, model);
        MenuPanel menu = new MenuPanel(this, model, enemyModel);

        cardLayout = new CardLayout();

        // for two players
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, myCanvas, enemyCanvas);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);

        frame = new JFrame("Sokoban");
        frame.setSize(1200, 800);
        frame.setLocation(200, 15);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(cardLayout);

        frame.add(menu, "menu");
        frame.add(levelChooser, "levelChooser");
        frame.add(settings, "settings");
        frame.add(canvas, "canvas");
        frame.add(splitPane, "splitPane");

        ImageIcon gameIcon = new ImageIcon("images/game-icon.png");
        frame.setIconImage(gameIcon.getImage());

        frame.setResizable(false);
        frame.setVisible(true);
    }

    public Viewer getViewer() {
        return this;
    }

    public Model getModel() {
        return model;
    }

    public LevelChooser getLevelChooser() {
        return levelChooser;
    }

    public EnemyModel getEnemyModel() {
        return enemyModel;
    }

    public CanvasForTwoPlayers getEnemyCanvas() {
        return enemyCanvas;
    }

    public CanvasForTwoPlayers getMyCanvas() {
        return myCanvas;
    }

    public void disableMyCanvas() {
        myCanvas.removeKeyListener(controller);
    }

    public void enableMyCanvas() {
        myCanvas.requestFocusInWindow();
        myCanvas.addKeyListener(controller);
    }

    public void update() {
        canvas.repaint();
    }

    public void updateSkin() {
        canvas.setSkin();
        canvas.repaint();
        settings.updateButtonStates();
    }

    public void updateEnemySkin() {
        enemyCanvas.setSkin();
        enemyCanvas.repaint();
    }

    public void updateMySkin() {
        myCanvas.setSkin();
        myCanvas.repaint();
    }

    public void updateEnemyCanvas() {
        enemyCanvas.repaint();
    }

    public void updateMyCanvas() {
        myCanvas.setSkin();
        myCanvas.repaint();
    }

    public void updateSettings(Player player) {
        settings.setPlayer(player);
        settings.repaint();
    }

    public void updateButtonText() {
        settings.updatePremiumButtonText();
    }

    public void showMenu() {
        cardLayout.show(frame.getContentPane(), "menu");
    }

    public void showCanvas() {
        update();
        cardLayout.show(frame.getContentPane(), "canvas");
        canvas.requestFocusInWindow();
    }

    public void showCanvas(String gameType) {
        System.out.println("in show canvas gameType" + gameType);
        if(gameType.equals("alone")) {
            showCanvas();
            return;
        }
        showTwoCanvas();

    }

    public String showSoloEndLevelDialog() {
        Object[] options = {"Go to levels", "Next level"};
        int totalMoves = model.getTotalMoves();
        int levelNumber = model.getCurrentLevelNumber();

        if (levelNumber == 9) {
            options[1] = "Back to menu";
        }

        int userChoise = javax.swing.JOptionPane.showOptionDialog(
                null, "You completed level " + levelNumber +
                "!\nTotal moves: " + totalMoves, "Congratulations!",
                javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.INFORMATION_MESSAGE,
                null, options, options[1]);

        if (userChoise == javax.swing.JOptionPane.NO_OPTION) {
            return (String) options[1];
        } else if (userChoise == javax.swing.JOptionPane.YES_OPTION) {
            showLevelChooser();
        } else {
            showMenu();
        }

        return "Not a play";
    }

    public String showOnlineEndLevelDialog() {
        String[] options = {"Wait results (30 sec)", "Give up"};
        int totalMoves = model.getTotalMoves();
        Player player = model.getPlayer();
        int userChoise = javax.swing.JOptionPane.showOptionDialog(
                null, player.getNickname() + " passed game ! Congratulations", "Total moves: " + totalMoves,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
        );

        if (userChoise == 0) {
            System.out.println("Wait option selected");
            return options[0];
        } else {
            System.out.println("Give up option selected");
            return options[1];
        }
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
    public void showEnemyGiveUpDialog() {
        int totalMoves = model.getTotalMoves();
        String[] options = {"Exit to Menu"};
        int result = JOptionPane.showOptionDialog(
                null, "Your opponent resigned, you won ! Your total moves " + totalMoves, "Congratulations !",
                JOptionPane.DEFAULT_OPTION,  JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]
        );

        if (result == 0) {
            showMenu();
        } else {
            showMenu();
        }
    }

    public void ResultsOnlineGameDialog(String absoluteWinner) {
        int myTotalMoves = model.getTotalMoves();
        int enemyTotalMoves = enemyModel.getTotalMoves();

        String winner = "It's a tie!";

        if(absoluteWinner == null) {
            winner = (myTotalMoves < enemyTotalMoves) ? "You won !" : "You lose";
        } else {
            winner = absoluteWinner.equals("me") ? "You won !" : "You lose";
        }


       String message = String.format("Your total moves: %d\nEnemy total moves: %d\n%s",
               myTotalMoves, enemyTotalMoves, winner);

      JOptionPane.showMessageDialog(null, message, "Game Results", JOptionPane.INFORMATION_MESSAGE);

    }

    private void showTwoCanvas() {
        updateMyCanvas();
        updateEnemyCanvas();
        updateMySkin();
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

    public void showErrorDialog(String errorMessage) {
        String[] options = {"Exit to Menu"};
        int result = JOptionPane.showOptionDialog(
                null, errorMessage, "Error",
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                null, options, options[0]
        );

        if (result == 0) {
            showMenu();
        } else {
            showMenu();
        }
    }
}
