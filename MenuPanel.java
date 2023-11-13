import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.File;

public class MenuPanel extends JPanel {

    private Image backgroundImage;
    private MenuController menuController;
    private File fontFile;

    public MenuPanel(Viewer viewer, Model model) {
        backgroundImage = viewer.getBackgroundImage();
        menuController = new MenuController(viewer, model);
        fontFile = new File("fonts/PixelFont.otf");
        init();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private void init() {
        setLayout(null);
        JLabel label = new JLabel("Sokoban");
        label.setBounds(420, 160, 500, 100);
        label.setForeground(Color.WHITE);

        Font labelFont = getCustomFont(fontFile, Font.PLAIN, 120f);
        label.setFont(labelFont);

        JButton playButton = createButton("Play", "Play", 500, 315);
        playButton.addActionListener(menuController);

        JButton levelsButton = createButton("Choose level", "Level", 500, 385);
        levelsButton.addActionListener(menuController);

        JButton settingsButton = createButton("Settings", "Settings", 500, 455);
        settingsButton.addActionListener(menuController);

        JButton exitButton = createButton("Exit", "Exit", 500, 525);
        exitButton.addActionListener(menuController);

        add(label);
        add(playButton);
        add(levelsButton);
        add(settingsButton);
        add(exitButton);
    }

    private Font getCustomFont(File file, int style, float size) {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, file).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        return customFont;
    }

    private JButton createButton(String name, String command, int x, int y) {
        JButton button = new JButton(name);
        Font font = getCustomFont(fontFile, Font.PLAIN, 24f);
        button.setBounds(x, y, 200, 40);
        button.setFocusable(false);
        button.setFont(font);
        button.setActionCommand(command);
        return button;
    }
}
