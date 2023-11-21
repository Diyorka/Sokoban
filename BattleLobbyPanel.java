import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;

public class BattleLobbyPanel extends JPanel{
    private Viewer viewer;
    private Model model;
    private BattleLobbyController controller;
    private Player player;
    private Image backgroundImage;
    private JLabel nicknameLabel;
    private JLabel playerLevelStats;
    private JLabel playerCoinStats;
    private String text;
    private JLabel playerCard;

    public BattleLobbyPanel(Viewer viewer, Model model) {
        this.viewer = viewer;
        this.model = model;
        controller = new BattleLobbyController(this, viewer, model);
        player = model.getPlayer();
        backgroundImage = new ImageIcon("images/background.jpg").getImage();
        init();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        nicknameLabel.setText(player.getNickname());
        playerCoinStats.setText("Coins earned: " + String.valueOf(calculateTotalCoins()));
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private void init() {
        setLayout(null);
        JLabel label = new JLabel("Sokoban");
        label.setBounds(420, 100, 500, 100);
        label.setForeground(Color.WHITE);

        Font labelFont = viewer.getCustomFont(Font.PLAIN, 120f);
        label.setFont(labelFont);

        playerCard = new JLabel();
        playerCard.setBounds(220, 225, 750, 400);
        playerCard.setForeground(Color.WHITE);
        playerCard.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        JLabel extraTopBorder = new JLabel();
        extraTopBorder.setBounds(225, 220, 740, 5);
        extraTopBorder.setBackground(Color.WHITE);
        extraTopBorder.setOpaque(true);

        JLabel extraBotBorder = new JLabel();
        extraBotBorder.setBounds(225, 625, 740, 5);
        extraBotBorder.setBackground(Color.WHITE);
        extraBotBorder.setOpaque(true);

        JLabel extraLeftBorder = new JLabel();
        extraLeftBorder.setBounds(215, 230, 5, 390);
        extraLeftBorder.setBackground(Color.WHITE);
        extraLeftBorder.setOpaque(true);

        JLabel extraRightBorder = new JLabel();
        extraRightBorder.setBounds(970, 230, 5, 390);
        extraRightBorder.setBackground(Color.WHITE);
        extraRightBorder.setOpaque(true);

        JLabel playerIcon = createLabel("", 50, 100, 200, 250, null);
        playerIcon.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        Font headerFont = viewer.getCustomFont(Font.PLAIN, 60f);

        nicknameLabel = createLabel("", 275, 100, 400, 50, headerFont);
        JLabel awaitingPlayer = createLabel("Waiting other player", 150, 25, 500, 50, headerFont);

        Font statsFont = viewer.getCustomFont(Font.PLAIN, 45f);
        playerCoinStats = createLabel("", 275, 200, 400, 50, statsFont);

        playerLevelStats = createLabel("", 275, 150, 400, 50, statsFont);
        playerLevelStats.setText("Levels passed: " + "-");

        playerCard.add(playerIcon);
        playerCard.add(awaitingPlayer);
        playerCard.add(nicknameLabel);
        playerCard.add(playerCoinStats);
        playerCard.add(playerLevelStats);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(500, 655, 200, 40);
        cancelButton.setFocusable(false);
        cancelButton.setFont(viewer.getCustomFont(Font.PLAIN, 24f));
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(controller);

        add(label);
        add(playerCard);
        add(extraTopBorder);
        add(extraBotBorder);
        add(extraLeftBorder);
        add(extraRightBorder);
        add(cancelButton);
    }

    private int calculateTotalCoins() {
        int totalCoins = player.getTotalCoins();
        if (player.isPremiumAvailable()) {
            totalCoins += 15;
        }
        return totalCoins;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private JLabel createLabel(String name, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        return label;
    }
}
