import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.File;
import java.io.ObjectOutputStream;
import java.util.HashMap;

@SuppressWarnings("serial")
public class LevelChooser extends JPanel {
    private LevelChooserController levelChooserController;
    private Model model;
    private JLabel[] collectedCoinsLabels;

    public LevelChooser(Viewer viewer, Model model) {
        levelChooserController = new LevelChooserController(viewer, model);
        setLayout(null);
        this.model = model;

        Font font = getCustomFont(Font.PLAIN, 80);
        JLabel title = createLabel("Level Selection", 370, 100, 470, 60, font, Color.WHITE);

        collectedCoinsLabels = new JLabel[9];

        int x = 220;
        int y = 375;
        font = getCustomFont(Font.PLAIN, 48);

        for (int i = 0; i < collectedCoinsLabels.length; i++) {
            collectedCoinsLabels[i] = createLabel("0/2", x, y, 65, 50, font, Color.BLACK);
            collectedCoinsLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            add(collectedCoinsLabels[i]);
            if (i == 4) {
                y = 625;
                x = 220;
            } else {
                x += 160;
            }
        }

        initCoins();

        JButton backButton = createButton("Menu", "Back", 70, 70);
        backButton.addActionListener(levelChooserController);

        add(title);
        add(backButton);
        add(createRoundedButton("1", "Level 1", 215, 220, 130, 150));
        add(createRoundedButton("2", "Level 2", 375, 220, 130, 150));
        add(createRoundedButton("3", "Level 3", 535, 220, 130, 150));
        add(createRoundedButton("4", "Level 4", 695, 220, 130, 150));
        add(createRoundedButton("5", "Level 5", 855, 220, 130, 150));
        add(createRoundedButton("6", "Level 6", 215, 470, 130, 150));
        add(createRoundedButton("7", "Level 7", 375, 470, 130, 150));
        add(createRoundedButton("8", "Level 8", 535, 470, 130, 150));
        add(createRoundedButton("9", "Level 9", 695, 470, 130, 150));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = new ImageIcon("images/levels-background.png").getImage();
        g.drawImage(backgroundImage, 0, 0, null);

        Image coin = new ImageIcon("images/collected-coin.png").getImage();
        g.drawImage(coin, 290, 380, 50, 50, null);
        g.drawImage(coin, 450, 380, 50, 50, null);
        g.drawImage(coin, 610, 380, 50, 50, null);
        g.drawImage(coin, 770, 380, 50, 50, null);
        g.drawImage(coin, 930, 380, 50, 50, null);
        g.drawImage(coin, 290, 630, 50, 50, null);
        g.drawImage(coin, 450, 630, 50, 50, null);
        g.drawImage(coin, 610, 630, 50, 50, null);
        g.drawImage(coin, 770, 630, 50, 50, null);
    }

    public void initCoins() {
        HashMap<Integer, Integer> coinsPerLevels = model.getPlayer().getCoinsOnLevels();

        for (int i = 0; i < collectedCoinsLabels.length; i++) {
            int coins = coinsPerLevels.getOrDefault(i + 1, 0);
            collectedCoinsLabels[i].setText(coins + "/2");
        }
    }

    public void updateCoins(int level, int coins) {
        if (level > 0 && level < 10) {
            collectedCoinsLabels[level - 1].setText(coins + "/2");
        }
    }

    private RoundedButton createRoundedButton(String text, String command, int x, int y, int w, int h) {
        RoundedButton button = new RoundedButton(text);
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

    private JLabel createLabel(String text, int x, int y, int w, int h, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBounds(x, y, w, h);
        label.setForeground(color);

        return label;
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

    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new IOException("This class is NOT serializable.");
    }   

}
