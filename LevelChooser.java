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
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.File;

public class LevelChooser extends JPanel {
    private LevelChooserController levelChooserController;

    public LevelChooser(Viewer viewer, Model model) {
        levelChooserController = new LevelChooserController(viewer, model);
        setLayout(null);

        JLabel title = new JLabel("Level Selection");
        Font font = getCustomFont(Font.PLAIN, 80);
        title.setFont(font);
        title.setBounds(370, 50, 470, 60);
        title.setForeground(Color.WHITE);

        JButton backButton = createButton("Menu", "Back", 100, 70);
        backButton.addActionListener(levelChooserController);

        add(title);
        add(backButton);
        add(createImageButton("Level 1", "Level 1", "images/level1.png", 170, 200, 200, 120, true));
        add(createImageButton("Level 2", "Level 2", "images/level2.png", 390, 200, 200, 120, true));
        add(createImageButton("Level 3", "Level 3", "images/level3.png", 610, 200, 200, 120, true));
        add(createImageButton("Level 4", "Level 4", "images/level4.png", 830, 200, 200, 120, true));
        add(createImageButton("Level 5", "Level 5", "images/level5.png", 170, 340, 200, 120, true));
        add(createImageButton("Level 6", "Level 6", "images/level6.png", 390, 340, 200, 120, true));
        add(createImageButton("Level 7", "Level 7", "images/level7.png", 610, 340, 200, 120, true));
        add(createImageButton("Level 8", "Level 8", "images/level8.png", 830, 340, 200, 120, true));
        add(createImageButton("Level 9", "Level 9", "images/level9.png", 170, 480, 200, 120, true));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("images/background.jpg").getImage();
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private JButton createImageButton(String levelName, String command, String imagePath,
                                      int x, int y, int w, int h, boolean borderFlag) {
        JButton button = new ImageButton(levelName, imagePath, 48, borderFlag);
        button.setBounds(x, y, w, h);
        button.setActionCommand(command);
        button.addActionListener(levelChooserController);

        return button;
    }

    private JButton createButton(String name, String command, int x, int y) {
        JButton button = new JButton(name);
        Font font = getCustomFont(Font.PLAIN, 24f);
        button.setBounds(x, y, 200, 40);
        button.setFocusable(false);
        button.setFont(font);
        button.setActionCommand(command);
        return button;
    }

    private Font getCustomFont(int style, float size) {
        Font customFont = null;
        File fontFile = new File("fonts/PixelFont.otf");

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(style, size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        return customFont;
    }

}
