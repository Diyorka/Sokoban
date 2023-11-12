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

    public MenuPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        menuController = new MenuController();
        init();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private void init() {
        setLayout(null);
        JLabel label = new JLabel("Sokoban");
        label.setBounds(410, 180, 400, 100);
        label.setForeground(Color.WHITE);

        // Font labelFont = new Font("Showcard Gothic", Font.PLAIN, 72);
        Font labelFont = getCustomFont(Font.ITALIC, 100f);
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

    private Font getCustomFont(int style, float size) {
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/PixelFont.ttf")).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        return customFont;
    }

    private JButton createButton(String name, String command, int x, int y) {
        JButton button = new JButton(name);
        Font font = getCustomFont(Font.PLAIN, 20f);
        button.setBounds(x, y, 200, 40);
        button.setFocusable(false);
        button.setFont(font);
        button.setActionCommand(command);
        return button;
    }
}
