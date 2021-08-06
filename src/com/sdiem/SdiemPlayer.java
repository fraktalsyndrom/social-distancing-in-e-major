package com.sdiem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SdiemPlayer
{
    private static final int MINIMUM_INTERVAL_SECONDS = 10;
    private static final int MAXIMUM_INTERVAL_SECONDS = 30;

    List<MusicTrack> trackList;

    public SdiemPlayer(List<MusicTrack> trackList)
    {
        this.trackList = trackList;
    }

    public void startPlaying() throws InterruptedException
    {
        for (MusicTrack track : trackList)
        {
            Thread trackThread = new Thread(track);
            trackThread.start();
            Thread.sleep(generateRandomIntervalTime());
        }
    }

    public static void main(String[] args)
    {
        try
        {
            SdiemPlayer player = new SdiemPlayer(createTrackList("tracklist.csv"));
            player.startPlaying();

            Thread.sleep(TimeUnit.MINUTES.toMillis(2));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static List<MusicTrack> createTrackList(String trackListPath) throws IOException
    {
        BufferedReader csvReader = new BufferedReader(new FileReader(trackListPath));
        List<MusicTrack> trackList = new ArrayList<>();
        String row = "";
        while ((row = csvReader.readLine()) != null)
        {
            String[] trackInfo = row.split(",");
            MusicTrack newTrack = new MusicTrack(trackInfo[0], trackInfo[1]);
            trackList.add(newTrack);
        }
        csvReader.close();
        return trackList;
    }

    private static int generateRandomIntervalTime()
    {
        int range = MAXIMUM_INTERVAL_SECONDS - MINIMUM_INTERVAL_SECONDS;
        Random random = new Random();
        int intervalTime = random.nextInt(range) + MINIMUM_INTERVAL_SECONDS;
        return (int) TimeUnit.SECONDS.toMillis(intervalTime);
    }
}
