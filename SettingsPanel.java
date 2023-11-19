import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSlider;
import javax.swing.JOptionPane;

public class SettingsPanel extends JPanel {

    private Viewer viewer;
    private Player player;
    private Image backgroundImage;
    private Font font;
    private JLabel nickname;
    private JLabel coins;
    private SettingsController controller;
    private JButton defaultSkinButton;
    private JButton santaSkinButton;
    private JButton premiumSkinButton;
    private int premiumSkinCost;

    public SettingsPanel(Viewer viewer, Model model) {
        this.viewer = viewer;
        controller = new SettingsController(this, viewer, model);
        player = model.getPlayer();
        backgroundImage = new ImageIcon("images/settings-background.png").getImage();
        font = viewer.getCustomFont(Font.PLAIN, 24f);
        premiumSkinCost = 15;
        init();
    }

    public void updateButtonStates() {
        String type = player.getCurrentSkin().getType();

        if ("Default Skin".equals(type)) {
            enableButtons(false, true, true);

        } else if ("Santa Skin".equals(type)) {
            enableButtons(true, false, true);

        } else if ("Premium Skin".equals(type)) {
            enableButtons(true, true, false);
        }
    }

    public void updatePremiumButtonText() {
        if (!player.isPremiumAvailable()) {
            premiumSkinButton.setText("Buy");
            premiumSkinButton.setActionCommand("Buy_Premium");
        } else {
            premiumSkinButton.setText("Choose");
            premiumSkinButton.setActionCommand("Premium_Skin");
        }
    }

    public void showNotEnoughCoinsMessage() {
        JOptionPane.showMessageDialog(null, "You don't have enough coins", "Purchase is impossible", JOptionPane.INFORMATION_MESSAGE);
    }

    public int getPremiumSkinCost() {
        return premiumSkinCost;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        nickname.setText("Player: " + player.getNickname());
        coins.setText(": " + player.getTotalCoins());
        g.drawImage(backgroundImage, 0, 0, null);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private void init() {
        setLayout(null);

        ImageIcon coinIcon = new ImageIcon("images/coin-image.png");
        JLabel coinImage = createLabelImage(coinIcon, 15, 10, 70, 70);

        Font labelFont = viewer.getCustomFont(Font.PLAIN, 34f);
        coins = createLabel("", 80, 25, 100, 30, labelFont);
        nickname = createLabel("", 170, 25, 200, 30, labelFont);

        JLabel label = createLabel("Settings", 460, 40, 500, 100, viewer.getCustomFont(Font.PLAIN, 72f));
        JLabel skinsLabel = createLabel("Skins:", 80, 210, 100, 30, labelFont);
        showSkinSettings();

        JLabel musicLabel = createLabel("Music:", 80, 480, 100, 30, labelFont);
        showMusicSettings();

        JButton returnButton = createButton("Back", "Back", 40, 665, true);

        add(coinImage);
        add(nickname);
        add(coins);
        add(label);
        add(skinsLabel);
        add(musicLabel);
        add(returnButton);
    }

    private void showSkinSettings() {
        ImageIcon defaultIcon = new ImageIcon("images/default-skin.png");
        ImageIcon santaIcon = new ImageIcon("images/santa-skin.png");
        ImageIcon premiumIcon = new ImageIcon("images/premium-skin.png");

        JLabel defaultSkinImage = createLabelImage(defaultIcon, 310, 200, 100, 130);
        JLabel santaSkinImage = createLabelImage(santaIcon, 518, 165, 100, 150);
        JLabel premiumSkinImage = createLabelImage(premiumIcon, 730, 190, 100, 150);

        JLabel defaultSkinPrice = createLabel("Default", 333, 320, 100, 30, font);
        JLabel santaSkinPrice = createLabel("Free", 550, 320, 100, 30, font);
        JLabel premiumSkinPrice = createLabel(premiumSkinCost + " coins", 745, 320, 110, 30, font);
        premiumSkinPrice.setForeground(new Color(251, 197, 24));

        defaultSkinButton = createButton("Choose", "Default_Skin", 315, 375, false);
        santaSkinButton = createButton("Choose", "Santa_Skin", 520, 375, false);

        if (player.isPremiumAvailable()) {
            premiumSkinButton = createButton("Choose", "Premium_Skin", 730, 375, false);
        } else {
            premiumSkinButton = createButton("Buy", "Buy_Premium", 730, 375, false);
        }
        updateButtonStates();

        add(defaultSkinImage);
        add(santaSkinImage);
        add(premiumSkinImage);
        add(defaultSkinPrice);
        add(santaSkinPrice);
        add(premiumSkinPrice);
        add(defaultSkinButton);
        add(premiumSkinButton);
        add(santaSkinButton);
    }

    private void enableButtons(boolean defaultSkinEnable, boolean santaSkinEnable, boolean premiumSkinEnable) {
        defaultSkinButton.setEnabled(defaultSkinEnable);
        santaSkinButton.setEnabled(santaSkinEnable);
        premiumSkinButton.setEnabled(premiumSkinEnable);
    }

    private void showMusicSettings() {
        JRadioButton defaultMusic = createJRadioButton("Default", "Default_Music", 325, 480, true);
        JRadioButton christmasMusic = createJRadioButton("Christmas music", "Christmas_Music", 495, 480, false);
        JRadioButton noSound = createJRadioButton("Soundless", "No_Sound", 715, 480, false);

        ButtonGroup music = new ButtonGroup();
        music.add(defaultMusic);
        music.add(christmasMusic);

        JLabel volumeLabel = createLabel("Volume:", 420, 550, 100, 20, font);

        JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setBounds(500, 540, 200, 50);
        volumeSlider.setOpaque(false);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(false);
        volumeSlider.setPaintLabels(false);

        SliderChangeListener listener = new SliderChangeListener();
        volumeSlider.addChangeListener(listener);

        add(defaultMusic);
        add(christmasMusic);
        add(noSound);
        add(volumeLabel);
        add(volumeSlider);
    }

    private JLabel createLabel(String name, int x, int y, int width, int height, Font font) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        label.setForeground(Color.WHITE);
        label.setFont(font);
        return label;
    }

    private JLabel createLabelImage(ImageIcon icon, int x, int y, int width, int height) {
        JLabel labelImage = new JLabel(icon);
        labelImage.setBounds(x, y, width, height);
        return labelImage;
    }

    private JButton createButton(String name, String command, int x, int y, boolean isEnabled) {
        JButton button = new JButton(name);
        button.setBounds(x, y, 100, 30);
        button.setFocusable(false);
        button.setFont(viewer.getCustomFont(Font.PLAIN, 20f));
        button.setEnabled(isEnabled);
        button.setActionCommand(command);
        button.addActionListener(controller);
        return button;
    }

    private JRadioButton createJRadioButton(String name, String command, int x, int y, boolean isSelected) {
        JRadioButton radioButton = new JRadioButton(name);
        radioButton.setBounds(x, y, 200, 30);
        radioButton.setSelected(isSelected);
        radioButton.setFont(font);
        radioButton.setForeground(Color.WHITE);
        radioButton.setFocusable(false);
        radioButton.setOpaque(false);
        radioButton.setActionCommand(command);
        radioButton.addActionListener(controller);
        return radioButton;
    }
}
