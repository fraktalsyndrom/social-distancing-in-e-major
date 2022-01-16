package com.sdiem;

import javax.sound.sampled.*;
import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MusicTrack implements Runnable
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

			System.out.println("Created " + this);
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

	public boolean isPlaying()
	{
		return isPlaying;
	}

	public String toString()
	{
		String output = "MusicTrack, name: " + name + ", duration: " + duration + "s" + ", playing: " + isPlaying;
		return output;
	}

	@Override
	public void run()
	{
		try
		{
			setVolume(getRandomVolume());
			System.out.println("Playing track " + name);
			isPlaying = true;
			clip.loop(1);

			Thread.sleep(TimeUnit.SECONDS.toMillis(duration));

			clip.stop();
			isPlaying = false;
			System.out.println("Stopping track " + name);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void setVolume(float volume)
	{
		FloatControl gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(20f * (float)Math.log10(volume));
	}

	private static float getRandomVolume()
	{
		Random random = new Random();

		float volumeRange = SdiemPlayer.VOLUME_RANGE;
		float volumeModifier = random.nextFloat() * volumeRange;
		float randomVolume = 1 - volumeModifier;

		System.out.println("Random volume: " + randomVolume);

		return randomVolume;
	}
}
