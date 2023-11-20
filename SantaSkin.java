import java.awt.Image;
import javax.swing.ImageIcon;

public class SantaSkin implements PlayerSkin {
    private String type;
    private Image frontSantaImage;
    private Image backSantaImage;
    private Image leftSantaImage;
    private Image rightSantaImage;
    private Image wallImage;
    private Image boxImage;
    private Image targetImage;
    private Image groundImage;
    private Image coinImage;

    public SantaSkin() {
        type = "Santa Skin";
        frontSantaImage = new ImageIcon("images/front-santa.png").getImage();
        backSantaImage = new ImageIcon("images/back-santa.png").getImage();
        leftSantaImage = new ImageIcon("images/left-side-santa.png").getImage();
        rightSantaImage = new ImageIcon("images/right-side-santa.png").getImage();
        wallImage = new ImageIcon("images/wall.png").getImage();
        boxImage = new ImageIcon("images/present-box.png").getImage();
        targetImage = new ImageIcon("images/target2.png").getImage();
        groundImage = new ImageIcon("images/ground2.png").getImage();
        coinImage = new ImageIcon("images/coin1.png").getImage();
    }

    public String getType() {
        return type;
    }

    public Image getFrontPlayerImage() {
        return frontSantaImage;
    }

    public Image getBackPlayerImage() {
        return backSantaImage;
    }

    public Image getRightPlayerImage() {
        return rightSantaImage;
    }

    public Image getLeftPlayerImage() {
        return leftSantaImage;
    }

    public Image getWallImage() {
        return wallImage;
    }

    public Image getBoxImage() {
        return boxImage;
    }

    public Image getTargetImage() {
        return targetImage;
    }

    public Image getGroundImage() {
        return groundImage;
    }

    public Image getCoinImage() {
        return coinImage;
    }
}
