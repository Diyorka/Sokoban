import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Canvas extends JPanel {

  private Model model;
  private Image gamerImage;
  private Image frontGamerImage;
  private Image backGamerImage;
  private Image leftGamerImage;
  private Image rightGamerImage;
  private Image wallImage;
  private Image boxImage;
  private Image goalImage;
  private Image groundImage;
  private Image coinImage;
  private Image errorImage;
  private Controller controller;
  private Image backgroundImage;
  private JLabel coinsLabel;
  private JLabel stepsLabel;

  public Canvas(Model model, Controller controller) {
      this.model = model;
      this.controller = controller;
      backgroundImage = new ImageIcon("images/background.jpg").getImage();
      setLayout(null);
      setOpaque(true);

      frontGamerImage = new ImageIcon("images/front-player.png").getImage();
      backGamerImage = new ImageIcon("images/back-player.png").getImage();
      leftGamerImage = new ImageIcon("images/left-side-player.png").getImage();
      rightGamerImage = new ImageIcon("images/right-side-player.png").getImage();
      wallImage = new ImageIcon("images/wall.png").getImage();
      boxImage = new ImageIcon("images/box.png").getImage();
      goalImage = new ImageIcon("images/target1.png").getImage();
      groundImage = new ImageIcon("images/ground1.png").getImage();
      coinImage = new ImageIcon("images/coin.png").getImage();
      errorImage = new ImageIcon("images/error.png").getImage();

      JLabel coinsImageLabel = new JLabel();
      Image coins = new ImageIcon("images/coins.png").getImage();
      Image scaledCoins = coins.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
      ImageIcon coinsIcon = new ImageIcon(scaledCoins);
      coinsImageLabel.setIcon(coinsIcon);
      coinsImageLabel.setBounds(1090, 30, 80, 80);
      add(coinsImageLabel);

      JLabel stepsImageLabel = new JLabel();
      Image steps = new ImageIcon("images/steps.png").getImage();
      Image scaledSteps = steps.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
      ImageIcon stepsIcon = new ImageIcon(scaledSteps);
      stepsImageLabel.setIcon(stepsIcon);
      stepsImageLabel.setBounds(30, 30, 80, 80);
      add(stepsImageLabel);

      File fontFile = new File("fonts/PixelFont.otf");

      coinsLabel = new JLabel("0");
      coinsLabel.setFont(getCustomFont(fontFile, Font.PLAIN, 80f));
      coinsLabel.setForeground(Color.WHITE);
      coinsLabel.setBounds(980, 20, 100, 100);
      coinsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
      add(coinsLabel);

      stepsLabel = new JLabel("0");
      stepsLabel.setFont(getCustomFont(fontFile, Font.PLAIN, 80f));
      stepsLabel.setForeground(Color.WHITE);
      stepsLabel.setBounds(120, 20, 100, 100);
      add(stepsLabel);

      JButton exitGameButton = new JButton("Exit to menu");
      exitGameButton.setBounds(40, 700, 150, 40);
      Font customFont = getCustomFont(fontFile, Font.PLAIN, 22);
      exitGameButton.setFont(customFont);
      exitGameButton.setForeground(Color.BLACK);
      exitGameButton.setBackground(new Color(59, 89, 182));
      exitGameButton.setActionCommand("Exit to menu");
      exitGameButton.addActionListener(controller);
      add(exitGameButton);

      JButton nextLevelButton = new JButton("Next level");
      nextLevelButton.setBounds(1010, 700, 150, 40);
      nextLevelButton.setFont(customFont);
      nextLevelButton.setForeground(Color.BLACK);
      nextLevelButton.setBackground(new Color(59, 89, 182));
      nextLevelButton.setActionCommand("Next level");
      nextLevelButton.addActionListener(controller);
      add(nextLevelButton);
  }

  public void paintComponent(Graphics g) {
      super.paintComponent(g);

      g.drawImage(backgroundImage, 0, 0, null);
      String collectedCoins = String.valueOf(model.getCollectedCoins());
      String totalMoves = String.valueOf(model.getTotalMoves());

      coinsLabel.setText(collectedCoins);
      stepsLabel.setText(totalMoves);

      int[][] desktop = model.getDesktop();
      if(desktop != null) {
          rotateGamer();
          drawDesktop(g, desktop);
      } else {
          drawErrorMessage(g);
      }
  }

  private void rotateGamer() {
      String move = model.getMove();
      switch (move) {
          case "Left":
              gamerImage = leftGamerImage;
              break;
          case "Right":
              gamerImage = rightGamerImage;
              break;
          case "Up":
              gamerImage = backGamerImage;
              break;
          case "Down":
              gamerImage = frontGamerImage;
              break;
      }
  }

  private void drawDesktop(Graphics g, int[][] desktop) {
    int start = 350;
    int x = start;
    int y = 150;
    int width = 50;
    int height = 50;
    int offset = 0;

    for (int i = 0; i < desktop.length; i++) {
      boolean isFirstWallFound = false;

      for (int j = 0; j < desktop[i].length; j++) {
        if (!isFirstWallFound && desktop[i][j] == 2) {
          isFirstWallFound = true;
        }

        if (isFirstWallFound) {
          if (desktop[i][j] == 0) {
            g.drawImage(groundImage, x, y, null);
          } else if (desktop[i][j] == 1) {
            g.drawImage(gamerImage, x, y, null);
          } else if (desktop[i][j] == 2) {
            g.drawImage(wallImage, x, y, null);
          } else if (desktop[i][j] == 3) {
            g.drawImage(boxImage, x, y, null);
          } else if (desktop[i][j] == 4) {
            g.drawImage(goalImage, x, y, null);
          } else if (desktop[i][j] == 5) {
            g.drawImage(coinImage, x, y, null);
          }
        }
        x = x + width + offset;
      }

      x = start;
      y = y + height + offset;
    }

  }

  private void drawErrorMessage(Graphics g) {
    Font font = new Font("Impact", Font.BOLD, 50);
    g.drawImage(errorImage, 200, 200, null);
    g.setFont(font);
    g.setColor(Color.RED);
    g.drawString("Initialization Error!", 250, 100);
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

}
