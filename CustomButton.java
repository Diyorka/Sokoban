import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.io.IOException;
import java.io.ObjectOutputStream;

@SuppressWarnings("serial")
public class CustomButton extends JButton {

    private Color firstColor;
    private Color secondColor;
    private Timer timer;
    private Timer timerPressed;
    private float alpha;
    private boolean mouseOver;
    private boolean pressed;
    private Point pressedLocation;
    private float pressedSize;
    private float alphaPressed;

    public CustomButton(String text, Color firstColor, Color secondColor, Color textColor) {
        super(text);
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        setForeground(textColor);
        alpha = 0.3f;
        alphaPressed = 0.5f;
        init();
    }

    public void increaseTransparency() {
        if (alpha < 0.6f) {
            alpha += 0.05f;
            repaint();
        } else {
            alpha = 0.6f;
            timer.stop();
            repaint();
        }
    }

    public void decreaseTransparency() {
        if (alpha > 0.3f) {
            alpha -= 0.05f;
            repaint();
        } else {
            alpha = 0.3f;
            timer.stop();
            repaint();
        }
    }

    public void animatePressing() {
        pressedSize += 1;
        if (alphaPressed <= 0) {
            pressed = false;
            timerPressed.stop();
        } else {
            repaint();
        }
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public void startTimer() {
        timer.start();
    }

    public void press(Point point) {
        pressedSize = 0;
        alphaPressed = 0.5f;
        pressed = true;
        pressedLocation = point;
        timerPressed.setDelay(0);
        timerPressed.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient;
        if (isEnabled()) {
            gradient = new GradientPaint(0, 0, firstColor, width, 0, secondColor);
        } else {
            gradient = new GradientPaint(0, 0, new Color(43, 48, 64), width, 0, new Color(29, 113, 184));
        }
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, width, height, height / 2, height / 2);
        createStyle(g2);
        if (pressed) {
            paintPressed(g2);
        }
        g2.dispose();
        g.drawImage(img, 0, 0, null);
        super.paintComponent(g);
    }

    private void init() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));

        CustomButtonMouseListener listener = new CustomButtonMouseListener(this);
        addMouseListener(listener);

        ButtonTimerListener timerListener = new ButtonTimerListener(this);
        timer = new Timer(40, timerListener);

        ButtonTimerPressedListener timerPressedListener = new ButtonTimerPressedListener(this);
        timerPressed = new Timer(0, timerPressedListener);
    }

    private void createStyle(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        int width = getWidth();
        int height = getHeight();
        GradientPaint gradient = new GradientPaint(0, 0, Color.WHITE, 0, height, new Color(255, 255, 255, 60));
        g2.setPaint(gradient);
        Path2D.Float f = new Path2D.Float();
        f.moveTo(0, 0);
        int controll = height + height / 2;
        f.curveTo(0, 0, width / 2, controll, width, 0);
        g2.fill(f);
    }

    private void paintPressed(Graphics2D g2) {
        if (pressedLocation.x - (pressedSize / 2) < 0 && pressedLocation.x + (pressedSize / 2) > getWidth()) {
            timerPressed.setDelay(5);
            alphaPressed -= 0.05f;
            if (alphaPressed < 0) {
                alphaPressed = 0;
            }
        }
        g2.setColor(Color.WHITE);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alphaPressed));
        float x = pressedLocation.x - (pressedSize / 2);
        float y = pressedLocation.y - (pressedSize / 2);
        g2.fillOval((int) x, (int) y, (int) pressedSize, (int) pressedSize);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        throw new IOException("This class is NOT serializable.");
    }
}
