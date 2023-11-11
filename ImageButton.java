import javax.swing.JButton;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Color;

public class ImageButton extends JButton {

    private Image backgroundImage;
    private String buttonText;
    private int fontSize;

    public ImageButton(String buttonText, String imagePath, int fontSize, boolean borderFlag) {
        this.buttonText = buttonText;
        this.backgroundImage = new ImageIcon(imagePath).getImage();
        this.fontSize = fontSize;
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));
        setBorderPainted(borderFlag);
        setContentAreaFilled(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        if(!buttonText.equals("")) {
          g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        }

        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        Font newFont = new Font("Tahoma", Font.BOLD, fontSize);
        g2d.setFont(newFont);

        FontMetrics metrics = g2d.getFontMetrics(newFont);

        int x = (getWidth() - metrics.stringWidth(buttonText)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g2d.setColor(Color.BLACK);
        g2d.drawString(buttonText, x, y);

        g2d.dispose();
    }

}
