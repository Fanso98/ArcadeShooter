package Proze.ProjektProze;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa zapewniająca efekty dzwiekowe przy trafienia/nacisnieciach w odpowiednich miejscach.
 * @author KH AW
 * @version 1.0.0  12 czerwca 2019 21:30
 */



public class  Sounds
{
    Map<String, Clip> sounds = new HashMap<>();
    /**
     * Konstruktor klasy Sounds ładuje używane później pliki z rozszerzeniem wav
     */

    public Sounds()
    {
        loadResources("hit.wav");
        loadResources("Button.wav");
        loadResources("usp1.wav");
    }

    /**
     * Sprawdzanie czy plik istnieje i jeśli istnieje jest ładowany
     * @param soundName Nazwa pliku zawierajacego dźwięk z rozszerzeniem .wav
     */
    public void loadResources(String soundName)
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            sounds.put(soundName, clip);
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }

    }
    /**
     * Jednorazowe wykonanie pliku muzycznego
     * @param soundName Nazwa pliku zawierajacego dźwięk z rozszerzeniem .wav
     */
    public void playSound(String soundName)
    {
        Clip c = sounds.get(soundName);
        c.setFramePosition(0);
        c.start();
    }

}