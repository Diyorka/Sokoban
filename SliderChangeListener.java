import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;

public class SliderChangeListener implements ChangeListener {
    private int lastValue = 0;
    private Viewer viewer;

    public SliderChangeListener(Viewer viewer) {
        this.viewer = viewer;
    }

    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();

        if (!slider.getValueIsAdjusting()) {
            int value = slider.getValue();

            if (value != lastValue) {
                Music currentMusic = viewer.getModel().getCurrentMusic();
                currentMusic.setVolume(value / 100.0f);

                lastValue = value;
            }
        }
    }
}
