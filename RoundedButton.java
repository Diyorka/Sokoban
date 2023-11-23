import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class RoundedButton extends JButton {
    private String buttonText;

    public RoundedButton(String buttonText) {
        this.buttonText = buttonText;

        setBackground(new Color(200, 130, 90));
        setFocusable(false);

        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);

        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getModel().isArmed()) {
            g.setColor(Color.gray);
        } else {
            g.setColor(getBackground());
        }

        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 40, 40);

        Font newFont = getCustomFont(Font.PLAIN, 72);
        g.setFont(newFont);

        FontMetrics metrics = g.getFontMetrics(newFont);

        int x = (getWidth() - metrics.stringWidth(buttonText)) / 2;
        int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setColor(Color.WHITE);
        g.drawString(buttonText, x, y);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.white);
        Graphics2D g2d = (Graphics2D) g;

        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(8.0f));

        g2d.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 40, 40);

        g2d.setStroke(oldStroke);
    }

    private Font getCustomFont(int style, float size) {
        Font customFont = null;
        File fontFile = new File("fonts/PixelFont.otf");

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            customFont = customFont.deriveFont(style, size);
        } catch (IOException | FontFormatException e) {
            System.out.println(e);
        }
        return customFont;
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new IOException("This class is NOT serializable.");
    }
}