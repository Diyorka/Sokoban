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
                System.out.println(value);
                float maxVolume = 6.0206f;
                float scaledVolume = Math.min(maxVolume, Math.max(0, maxVolume * (sliderValue / 100.0f)));

                Music currentMusic = viewer.getModel().getCurrentMusic();
                currentMusic.setVolume(scaledVolume);

                lastValue = value;
            }
        }
    }
}
