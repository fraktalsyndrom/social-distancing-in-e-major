package com.sdiem;

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

	public MusicTrack(String name, String path)
	{
		try
		{
			this.name = name;
			File file = new File(path).getAbsoluteFile();
			this.inputStream  = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public void start()
	{
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
