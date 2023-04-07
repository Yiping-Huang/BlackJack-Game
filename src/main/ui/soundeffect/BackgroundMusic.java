package ui.soundeffect;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class BackgroundMusic {
    private Clip clip;

    public void playMusic() {
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("./sound/BackgroundMusic.wav")));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        clip.stop();
    }
}
