import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

public class SliderChangeListener implements ChangeListener {

    private int lastValue = 0;

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        if (!slider.getValueIsAdjusting()) {
            int value = slider.getValue();
            
            if (value != lastValue) {
                System.out.println(value);
                lastValue = value;
            }
        }
    }
}
