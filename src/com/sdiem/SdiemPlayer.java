package com.sdiem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SdiemPlayer
{
    public static final float VOLUME_RANGE = 0.4f; // 0.0 = all tracks full volume, 1.0 = volume can vary from silent to full
    private static final int MINIMUM_INTERVAL_SECONDS = 10;
    private static final int MAXIMUM_INTERVAL_SECONDS = 30;
    private static final String TRACK_FOLDER_PATH = "media/wav";

    List<MusicTrack> trackList;

    public SdiemPlayer(List<MusicTrack> trackList)
    {
        this.trackList = trackList;
    }

    public static void main(String[] args)
    {
        try
        {
            SdiemPlayer player = new SdiemPlayer(generateTrackListFromFolder(TRACK_FOLDER_PATH));
            player.startPlaying();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startPlaying() throws InterruptedException
    {
        while(true)
        {
            MusicTrack nextTrack = getRandomSilentTrack();
            if (nextTrack != null)
            {
                Thread trackThread = new Thread(nextTrack);
                trackThread.start();
            }
            else
            {
                System.out.println("No tracks currently silent.");
            }
            int sleepTime = generateRandomTimeInterval();
            System.out.println("Waiting " + sleepTime/1000 + " seconds before starting next track...");
            Thread.sleep(sleepTime);
        }
    }

    public MusicTrack getRandomSilentTrack()
    {
        List<MusicTrack> silentTracks = new ArrayList<>();
        for (MusicTrack track : trackList)
        {
            if (!track.isPlaying())
            {
                silentTracks.add(track);
            }
        }

        Random random = new Random();
        if (silentTracks.isEmpty())
            return null;

        int randomIndex = random.nextInt(silentTracks.size());
        return silentTracks.get(randomIndex);
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

    private static List<MusicTrack> generateTrackListFromFolder(String folderPath) throws IOException{
        File folder = new File(folderPath);
        List<MusicTrack> trackList = new ArrayList<>();
        for (File file : folder.listFiles())
        {
            MusicTrack newTrack = new MusicTrack(file.getName(), file.getAbsolutePath());
            System.out.println("Adding track " + newTrack + " to list.");
            trackList.add(newTrack);
        }

        return trackList;
    }

    private static int generateRandomTimeInterval()
    {
        int range = MAXIMUM_INTERVAL_SECONDS - MINIMUM_INTERVAL_SECONDS;
        Random random = new Random();
        int intervalTime = random.nextInt(range) + MINIMUM_INTERVAL_SECONDS;
        return (int) TimeUnit.SECONDS.toMillis(intervalTime);
    }
}
