import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;

public class LevelChooser extends JPanel {
    private LevelChooserController levelChooserController;

    public LevelChooser() {
        levelChooserController = new LevelChooserController();
        setLayout(null);

        JLabel title = new JLabel("Level Selection");
        Font font = new Font("Tahoma", Font.BOLD, 48);
        title.setFont(font);
        title.setBounds(370, 50, 450, 55);
        title.setForeground(Color.WHITE);

        add(title);
        add(createImageButton("Level 1", "Level 1", "images/level1.png", 170, 200, 200, 120, true));
        add(createImageButton("Level 2", "Level 2", "images/level1.png", 390, 200, 200, 120, true));
        add(createImageButton("Level 3", "Level 3", "images/level1.png", 610, 200, 200, 120, true));
        add(createImageButton("Level 4", "Level 4", "images/level1.png", 830, 200, 200, 120, true));
        add(createImageButton("Level 5", "Level 5", "images/level1.png", 170, 340, 200, 120, true));
        add(createImageButton("Level 6", "Level 6", "images/level1.png", 390, 340, 200, 120, true));
        add(createImageButton("Level 7", "Level 7", "images/level1.png", 610, 340, 200, 120, true));
        add(createImageButton("Level 8", "Level 8", "images/level1.png", 830, 340, 200, 120, true));
        add(createImageButton("Level 9", "Level 9", "images/level1.png", 170, 480, 200, 120, true));
        add(createImageButton("", "Back", "images/back.png", 100, 50, 70, 70, false));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image backgroundImage = new ImageIcon("images/background.jpg").getImage();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private JButton createImageButton(String levelName, String command, String imagePath,
                                      int x, int y, int w, int h, boolean borderFlag) {
        JButton button = new ImageButton(levelName, imagePath, 36, borderFlag);
        button.setBounds(x, y, w, h);
        button.setActionCommand(command);
        button.addActionListener(levelChooserController);

        return button;
    }


}
