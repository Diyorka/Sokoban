import java.awt.Image;
import javax.swing.ImageIcon;

public class PremiumSkin implements PlayerSkin {

    private String type;
    private Image frontPremiumImage;
    private Image backPremiumImage;
    private Image leftPremiumImage;
    private Image rightPremiumImage;
    private Image wallImage;
    private Image boxImage;
    private Image targetImage;
    private Image groundImage;

    public PremiumSkin() {
        type = "Premium Skin";
        frontPremiumImage = new ImageIcon("images/front-premium.png").getImage();
        backPremiumImage = new ImageIcon("images/back-premium.png").getImage();
        leftPremiumImage = new ImageIcon("images/left-side-premium.png").getImage();
        rightPremiumImage = new ImageIcon("images/right-side-premium.png").getImage();
        wallImage = new ImageIcon("images/wall1.png").getImage();
        boxImage = new ImageIcon("images/box1.png").getImage();
        targetImage = new ImageIcon("images/target.png").getImage();
        groundImage = new ImageIcon("images/ground.png").getImage();
    }

    public String getType() {
        return type;
    }

    public Image getFrontPlayerImage() {
        return frontPremiumImage;
    }

    public Image getBackPlayerImage() {
        return backPremiumImage;
    }

    public Image getRightPlayerImage() {
        return rightPremiumImage;
    }

    public Image getLeftPlayerImage() {
        return leftPremiumImage;
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
}
