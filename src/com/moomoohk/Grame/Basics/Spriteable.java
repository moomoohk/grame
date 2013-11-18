package com.moomoohk.Grame.Basics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.moomoohk.Grame.Interfaces.GrameObject;

public abstract class Spriteable extends GrameObject
{
	private String path;
	private BufferedImage sprite;

	public Spriteable(String name, int speed, String path, boolean paused)
	{
		super(name, speed, null, paused);
		setPath(path);
	}

	public BufferedImage getSprite()
	{
		return this.sprite;
	}
	
	public void setPath(String path)
	{
		this.path=path;
		cacheSprite();
	}
	
	public String getPath()
	{
		return this.path;
	}

	private void cacheSprite()
	{
		try
		{
			this.sprite = ImageIO.read(getClass().getResource(this.path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
