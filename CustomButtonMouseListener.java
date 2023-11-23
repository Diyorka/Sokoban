import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CustomButtonMouseListener extends MouseAdapter {

    private CustomButton customButton;

    public CustomButtonMouseListener(CustomButton customButton) {
        this.customButton = customButton;
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (customButton.isEnabled()) {
            customButton.setMouseOver(true);
            customButton.startTimer();
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (customButton.isEnabled()) {
            customButton.setMouseOver(false);
            customButton.startTimer();
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        if (customButton.isEnabled()) {
            customButton.press(me.getPoint());
        }
    }
}
