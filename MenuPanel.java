import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class MenuPanel extends JPanel {

    private Viewer viewer;
    private MenuController menuController;
    private JTextField nickname;

    public MenuPanel(Viewer viewer, Model model, EnemyModel enemyModel) {
        this.viewer = viewer;
        menuController = new MenuController(this, viewer, model, enemyModel);
        init();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("images/background.jpg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public String getNicknameText() {
        return nickname.getText();
    }

    private void init() {
        setLayout(null);
        JLabel label = new JLabel("Sokoban");
        label.setBounds(420, 100, 500, 100);
        label.setForeground(Color.WHITE);

        Font labelFont = viewer.getCustomFont(Font.PLAIN, 120f);
        label.setFont(labelFont);

        nickname = new JTextField("Stive");
        nickname.setFont(viewer.getCustomFont(Font.PLAIN, 28f));
        nickname.setBounds(500, 245, 150, 40);
        nickname.setForeground(Color.WHITE);
        nickname.setHorizontalAlignment(JTextField.CENTER);
        nickname.setOpaque(false);

        JButton setNameButton = createButton("Ok", "Set name", 720, 245);
        setNameButton.setFont(viewer.getCustomFont(Font.PLAIN, 18f));
        setNameButton.setBounds(650, 245, 50, 40);
        setNameButton.addActionListener(menuController);

        JButton playButton = createButton("Play", "Play", 500, 315);
        playButton.addActionListener(menuController);

        JButton playWithEnemyButton = createButton("Play With Enemy", "PlayWithEnemy", 500, 385);
        playWithEnemyButton.addActionListener(menuController);

        JButton levelsButton = createButton("Choose level", "Level", 500, 455);
        levelsButton.addActionListener(menuController);

        JButton settingsButton = createButton("Settings", "Settings", 500, 525);
        settingsButton.addActionListener(menuController);

        JButton exitButton = createButton("Exit", "Exit", 500, 585);
        exitButton.addActionListener(menuController);

        add(label);
        add(nickname);
        add(setNameButton);
        add(playButton);
        add(playWithEnemyButton);
        add(levelsButton);
        add(settingsButton);
        add(exitButton);

    }

    private JButton createButton(String name, String command, int x, int y) {
        JButton button = new JButton(name);
        button.setBounds(x, y, 200, 40);
        button.setFocusable(false);
        button.setFont(viewer.getCustomFont(Font.PLAIN, 24f));
        button.setActionCommand(command);
        return button;
    }
}
