import java.awt.Image;
import javax.swing.ImageIcon;

public class DefaultSkin implements PlayerSkin {
    private String type;
    private Image frontDefaultImage;
    private Image backDefalutImage;
    private Image leftDefaultImage;
    private Image rightDefaultImage;
    private Image wallImage;
    private Image boxImage;
    private Image targetImage;
    private Image groundImage;
    private Image coinImage;

    public DefaultSkin() {
        type = "Default Skin";
        frontDefaultImage = new ImageIcon("images/front-player.png").getImage();
        backDefalutImage = new ImageIcon("images/back-player.png").getImage();
        leftDefaultImage = new ImageIcon("images/left-side-player.png").getImage();
        rightDefaultImage = new ImageIcon("images/right-side-player.png").getImage();
        wallImage = new ImageIcon("images/wall.png").getImage();
        boxImage = new ImageIcon("images/box.png").getImage();
        targetImage = new ImageIcon("images/target1.png").getImage();
        groundImage = new ImageIcon("images/ground1.png").getImage();
        coinImage = new ImageIcon("images/coin.png").getImage();
    }

    public String getType() {
        return type;
    }

    public Image getFrontPlayerImage() {
        return frontDefaultImage;
    }

    public Image getBackPlayerImage() {
        return backDefalutImage;
    }

    public Image getRightPlayerImage() {
        return rightDefaultImage;
    }

    public Image getLeftPlayerImage() {
        return leftDefaultImage;
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
