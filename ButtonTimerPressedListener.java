import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTimerPressedListener implements ActionListener {
    private CustomButton customButton;

    public ButtonTimerPressedListener(CustomButton customButton) {
        this.customButton = customButton;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        customButton.animatePressing();
    }
}
