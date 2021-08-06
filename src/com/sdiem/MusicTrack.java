package com.sdiem;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class MusicTrack
{
	private String name;
	private String path;
	private boolean isPlaying;
	private AudioInputStream inputStream;
	private Clip clip;
	private int duration;

	public MusicTrack(String name, String path)
	{
		try
		{
			this.name = name;
			File file = new File(path).getAbsoluteFile();
			this.inputStream  = AudioSystem.getAudioInputStream(file);

			duration = extractDuration(file);

			clip = AudioSystem.getClip();
			clip.open(inputStream);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private int extractDuration(File file)
	{
		AudioFormat format = inputStream.getFormat();
		long audioFileLength = file.length();
		int frameSize = format.getFrameSize();
		float frameRate = format.getFrameRate();

		return (int)(audioFileLength / (frameSize * frameRate)) + 1; //Rounded up
	}

	public void play()
	{
		System.out.println("Playing track " + name);
		clip.loop(1);
		isPlaying = true;
	}

	public void stop()
	{
		clip.stop();
		isPlaying = false;
	}

	public boolean isPlaying()
	{
		return isPlaying;
	}

	public String toString()
	{
		String output = "MusicTrack, name: " + name + ", duration: " + duration;
		return output;
	}
}
