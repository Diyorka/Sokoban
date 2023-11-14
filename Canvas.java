import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private Viewer viewer;

  public Canvas(Viewer viewer, Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
    setBackground(Color.BLACK);
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

    JButton exitGameButton = new JButton("Exit to menu");
    exitGameButton.setBounds(10, 10, 100, 30);
    exitGameButton.addActionListener(controller);
    add(exitGameButton);
  }

  public void paint(Graphics g) {
    super.paint(g);

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

}
