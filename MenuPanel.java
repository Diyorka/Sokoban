import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;

public class MenuPanel extends JPanel {

    private Image backgroundImage;
    private MenuController menuController;

    public MenuPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
        menuController = new MenuController();
        init();
    }

    private void init() {
        setLayout(null);
        JLabel label = new JLabel("Sokoban");
        label.setBounds(330, 120, 400, 100);
        label.setForeground(Color.WHITE);

        Font labelFont = new Font("Showcard Gothic", Font.PLAIN, 72);
        label.setFont(labelFont);

        JButton playButton = createButton("Play", "Play", 390, 250);
        playButton.addActionListener(menuController);

        JButton levelsButton = createButton("Choose level", "Level", 390, 310);
        levelsButton.addActionListener(menuController);

        JButton settingsButton = createButton("Settings", "Settings", 390, 370);
        settingsButton.addActionListener(menuController);

        JButton exitButton = createButton("Exit", "Exit", 390, 430);
        exitButton.addActionListener(menuController);

        add(label);
        add(playButton);
        add(levelsButton);
        add(settingsButton);
        add(exitButton);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private JButton createButton(String name, String command, int x, int y) {
        JButton button = new JButton(name);
        Font font = new Font("Showcard Gothic", Font.PLAIN, 20);
        button.setBounds(x, y, 200, 40);
        button.setFocusable(false);
        button.setFont(font);
        button.setActionCommand(command);
        return button;
    }
}
