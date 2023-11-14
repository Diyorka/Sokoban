import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music implements AutoCloseable {
    private boolean released;
    private AudioInputStream stream;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean playing;

    public Music(File file) {
        try {
            stream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new SoundController());
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            System.err.println(exc);
            released = false;
            close();
        }
    }

    public boolean isReleased() {
        return released;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void play(boolean breakOld) {
        if (released) {
            if (breakOld) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
    }

    public void play() {
        play(true);
    }

    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null)
        clip.close();

        if (stream != null)
        try {
            stream.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    // public void setVolume(float x) {
    //     if (x < 0) x = 0;
    //     if (x > 1) x = 1;
    //     float min = volumeControl.getMinimum();
    //     float max = volumeControl.getMaximum();
    //     volumeControl.setValue((max - min) * x + min);
    // }
    //
    // public float getVolume() {
    //     float v = volumeControl.getValue();
    //     float min = volumeControl.getMinimum();
    //     float max = volumeControl.getMaximum();
    //     return (v - min) / (max - min);
    // }

    // public void join() {
    //     if (!released) return;
    //     synchronized (clip) {
    //         try {
    //             while (playing)
    //             clip.wait();
    //         } catch (InterruptedException ie) {
    //             System.err.println(ie);
    //         }
    //     }
    // }

    private class SoundController implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized (clip) {
                    clip.notify();
                }
            }
        }
    }
}
