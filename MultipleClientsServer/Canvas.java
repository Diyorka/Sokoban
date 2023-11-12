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
  private Image imageWall;
  private Image imageBox;
  private Image imageGoal;
  private Image imageError;

  public Canvas(Model model) {
    this.model = model;
    setBackground(Color.BLACK);
    setOpaque(true);

    File fileGamer = new File("images/gamer.png");
    File fileWall = new File("images/wall.png");
    File fileBox = new File("images/box.png");
    File fileGoal = new File("images/goal.png");
    File fileError = new File("images/error.png");

    try {
      imageGamer = ImageIO.read(fileGamer);
      imageWall = ImageIO.read(fileWall);
      imageBox = ImageIO.read(fileBox);
      imageGoal = ImageIO.read(fileGoal);
      imageError = ImageIO.read(fileError);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }

  public void paint(Graphics g) {
    super.paint(g);
    boolean state = model.getState();
    if(state) {
      drawDesktop(g);
    } else {
      drawErrorMessage(g);
    }
  }

  private void drawDesktop(Graphics g) {
    int[][] desktop = model.getDesktop();
    int start = 100;
    int x = start;
    int y = start;
    int width = 50;
    int height = 50;
    int offset = 0;

    for(int i = 0; i < desktop.length; i++) {
      for(int j = 0; j < desktop[i].length; j++) {
        if(desktop[i][j] == 1) {
          g.drawImage(imageGamer, x, y, null);
        } else if(desktop[i][j] == 2) {
          g.drawImage(imageWall, x, y, null);
        } else if(desktop[i][j] == 3) {
          g.drawImage(imageBox, x, y, null);
        } else if(desktop[i][j] == 4) {
          g.drawImage(imageGoal, x, y, null);
        } else {
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
