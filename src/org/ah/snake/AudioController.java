package org.ah.snake;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController {
    public AudioController() {
        // TODO Auto-generated constructor stub
    }

    public static void play(String sound){
        try {
            final File jarFile = new File(AudioController.class.getProtectionDomain().getCodeSource().getLocation().getPath());

            AudioInputStream audioIn;
            if  (jarFile.isFile()) {
                URL url = AudioController.class.getClassLoader().getResource(sound);
                audioIn = AudioSystem.getAudioInputStream(url);
            } else {
                audioIn = AudioSystem.getAudioInputStream(new File("resources/" + sound));

            }
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
