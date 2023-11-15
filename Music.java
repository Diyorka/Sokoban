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

    private AudioInputStream inputStream;
    private Clip clip;
    private FloatControl volumeControl;
    private boolean playing;
    private boolean initialization;

    public Music(File file) {
        try {
            inputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            initialization = true;
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            System.err.println(exc);
            initialization = false;
            close();
        }
    }

    public void play() {
        if (initialization) {
            if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
                return;
            }
            clip.stop();
            clip.setFramePosition(0);
            clip.start();
            playing = true;
        }
    }

    public void playLoop() {
        if (initialization) {
            while (true) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
                if (!isPlaying()) {
                    clip.setFramePosition(0);
                    clip.start();
                    playing = true;
                }
            }
        }
    }

    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null)
        clip.close();

        if (inputStream != null)
        try {
            inputStream.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    public boolean isInitialization() {
        return initialization;
    }

    public boolean isPlaying() {
        return playing;
    }

    public Clip getClip(){
        return clip;
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
    //     if (!initialization) return;
    //     synchronized (clip) {
    //         try {
    //             while (playing)
    //             clip.wait();
    //         } catch (InterruptedException ie) {
    //             System.err.println(ie);
    //         }
    //     }
    // }

    // private class SoundController implements LineListener {
    //     public void update(LineEvent ev) {
    //         if (ev.getType() == LineEvent.Type.STOP) {
    //             playing = false;
    //             synchronized (clip) {
    //                 clip.notify();
    //             }
    //         }
    //     }
    // }
}
