package com.sdiem;

public class Main
{
    public static void main(String[] args)
    {
        MusicTrack testTrack = new MusicTrack("testName", "testaudio.wav");
        testTrack.start();
        try
        {
            Thread.sleep(1000000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }


}
