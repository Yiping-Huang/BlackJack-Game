package ui.abstractclasses;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public abstract class InstantSoundEffect {

    private String source;

    public void setSource(String source) {
        this.source = source;
    }

    public void playMusic() {
        try {
            File soundFile = new File(source);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
