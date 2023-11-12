import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Font;

public class Canvas extends JPanel {
  private Model model;
  private Image imageGamer;
  private Image imageFrontGamer;
  private Image imageBackGamer;
  private Image imageLeftGamer;
  private Image imageRightGamer;
  private Image imageWall;
  private Image imageBox;
  private Image imageGoal;
  private Image imageGround;
  private Image imageError;

  public Canvas(Model model) {
    this.model = model;
    setBackground(Color.BLACK);
    setOpaque(true);

    File filefrontGamer = new File("images/front-player.png");
    File fileBackGamer = new File("images/back-player.png");
    File fileLeftGamer = new File("images/left-side-player.png");
    File fileRightGamer = new File("images/right-side-player.png");
    File fileWall = new File("images/wall.png");
    File fileBox = new File("images/box.png");
    File fileGoal = new File("images/target1.png");
    File fileGround = new File("images/ground1.png");
    File fileError = new File("images/error.png");

    try {
      imageFrontGamer = ImageIO.read(filefrontGamer);
      imageBackGamer = ImageIO.read(fileBackGamer);
      imageLeftGamer = ImageIO.read(fileLeftGamer);
      imageRightGamer = ImageIO.read(fileRightGamer);
      imageWall = ImageIO.read(fileWall);
      imageBox = ImageIO.read(fileBox);
      imageGoal = ImageIO.read(fileGoal);
      imageGround = ImageIO.read(fileGround);
      imageError = ImageIO.read(fileError);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
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
              imageGamer = imageLeftGamer;
              break;
          case "Right":
              imageGamer = imageRightGamer;
              break;
          case "Up":
              imageGamer = imageBackGamer;
              break;
          case "Down":
              imageGamer = imageFrontGamer;
              break;
      }
  }

  private void drawDesktop(Graphics g, int[][] desktop) {
    int start = 100;
    int x = start;
    int y = start;
    int width = 50;
    int height = 50;
    int offset = 0;

    for(int i = 0; i < desktop.length; i++) {
      boolean isFirstWallFound = false;

      for(int j = 0; j < desktop[i].length; j++) {
        if(!isFirstWallFound && desktop[i][j] == 2) {
          isFirstWallFound = true;
        }

        if(isFirstWallFound) {
          if(desktop[i][j] == 0) {
            g.drawImage(imageGround, x, y, null);
          }else if(desktop[i][j] == 1) {
            g.drawImage(imageGamer, x, y, null);
          } else if(desktop[i][j] == 2) {
            g.drawImage(imageWall, x, y, null);
          } else if(desktop[i][j] == 3) {
            g.drawImage(imageBox, x, y, null);
          } else if(desktop[i][j] == 4) {
            g.drawImage(imageGoal, x, y, null);
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
    g.drawImage(imageError, 200, 200, null);
    g.setFont(font);
    g.setColor(Color.RED);
    g.drawString("Initialization Error!", 250, 100);
  }

}
