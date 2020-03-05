
package Proze.ProjektProze;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;


/**
 * Klasa zapewniająca muzykę i jej kontrolę w grze.
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 20:30
 */

public class Music  {

    float  vol=0.0f;
    public Clip clip;
    public long cliptime;
    /**
     * Sprawdzanie czy plik z muzyką istnieje jeśli tak ustawiamy jego głośność i zapętlamy go w nieskończoność.
     * @param musicLocation Nazwa pliku zawierajacego muzykę z rozszerzeniem .wav
     */

    void CustomSoundBackground(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()) {
                AudioInputStream AudioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(AudioInput);
                FloatControl volume =
                        (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(vol); //
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }


            else {
                System.out.println("Error");
            }
            ;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Zatrzymanie aktualnie grającego dźwięku.
     */
    public void stopSound() {
        clip.stop();
        //clip.flush();
        clip.close();
    }

    /**
     * Zapauzowanie aktualnie grającego dźwięku i zapisanie jego aktulanego czasu w celu
     * odpauzowania w tym samym momencie utworu.
     */

    public long pauseSound(){
        long cliptime = clip.getMicrosecondPosition();
        clip.stop();
return cliptime;
        // clip.setMicrosecondPosition(cliptime);
        // clip.start();
    }
    /**
     * Odpauzowanie gry w momencie w którym została wczesniej zapauzowana
     */
    public void playSound(long cliptime){

        FloatControl volume =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(vol);
        clip.setMicrosecondPosition(cliptime);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);







    }

}