package com.sdiem;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        List<AudioInputStream> streamList;
        AudioInputStream audioInputStream;
        Clip clip;
        try {
            for (int i = 0; i < 4; i++) {
                audioInputStream = AudioSystem.getAudioInputStream(new File("testaudio.wav").getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(10000);
            }
            Thread.sleep(1000000);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Ladda ljudfil
        // Spela upp den
    }


}
