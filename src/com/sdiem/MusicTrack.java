package com.sdiem;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class MusicTrack
{
	private String name;
	private String path;
	private boolean isPlaying;
	private AudioInputStream inputStream;
	private Clip clip;
	private float duration;

	public MusicTrack(String name, String path)
	{
		try
		{
			this.name = name;
			File file = new File(path).getAbsoluteFile();
			duration = extractDuration(file);
			this.inputStream  = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private float extractDuration(File file)
	{
		AudioFormat format = inputStream.getFormat();
		long audioFileLength = file.length();
		int frameSize = format.getFrameSize();
		float frameRate = format.getFrameRate();

		return (audioFileLength / (frameSize * frameRate));
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
}
