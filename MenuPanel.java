import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
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
        label.setBounds(445, 120, 500, 100);
        label.setForeground(Color.WHITE);

        Font labelFont = viewer.getCustomFont(Font.PLAIN, 100f);
        label.setFont(labelFont);

        nickname = new JTextField("Stive");
        nickname.setFont(viewer.getCustomFont(Font.PLAIN, 28f));
        nickname.setBounds(500, 245, 140, 40);
        nickname.setForeground(Color.WHITE);
        nickname.setHorizontalAlignment(JTextField.CENTER);
        nickname.setOpaque(false);

        JButton setNameButton = viewer.createDarkButton("Ok", "Set name", 720, 245, 200, 40, true, menuController);
        setNameButton.setFont(viewer.getCustomFont(Font.PLAIN, 18f));
        setNameButton.setBounds(640, 245, 60, 40);

        JButton playButton = viewer.createLightButton("Play", "Play", 500, 315, 200, 40, true, menuController);
        JButton playWithEnemyButton = viewer.createLightButton("Play With Enemy", "PlayWithEnemy", 500, 385, 200, 40, true, menuController);
        JButton levelsButton = viewer.createLightButton("Choose level", "Level", 500, 455, 200, 40, true, menuController);
        JButton settingsButton = viewer.createLightButton("Settings", "Settings", 500, 525, 200, 40, true, menuController);
        JButton exitButton = viewer.createLightButton("Exit", "Exit", 500, 595, 200, 40, true, menuController);

        add(label);
        add(nickname);
        add(setNameButton);
        add(playButton);
        add(playWithEnemyButton);
        add(levelsButton);
        add(settingsButton);
        add(exitButton);

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new IOException("This class is NOT serializable.");
    }
}
