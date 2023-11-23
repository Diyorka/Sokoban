import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTimerListener implements ActionListener {
    private CustomButton customButton;

    public ButtonTimerListener(CustomButton customButton) {
        this.customButton = customButton;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (customButton.isMouseOver()) {
            customButton.increaseTransparency();
        } else {
            customButton.decreaseTransparency();
        }
    }
}
