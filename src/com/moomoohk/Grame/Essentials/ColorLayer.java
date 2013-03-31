package com.moomoohk.Grame.Essentials;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;

public class ColorLayer
{
	private Color[] colors;
	private int width, height;

	public ColorLayer(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.colors=new Color[width*height];
	}

	public void setColor(Coordinates pos, Color c)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.colors.length)
			GrameUtils.print(pos.toString() + " is out of range. Returning.", MessageLevel.ERROR);
		this.colors[place] = c;
	}

	public void setAll(Color c)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				this.colors[x + y * width] = c;
	}

	public Color getColor(Coordinates pos)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.colors.length)
			GrameUtils.print(pos.toString() + " is out of range. Returning.", MessageLevel.ERROR);
		return this.colors[place];
	}

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
	public String toString()
	{
		return this.width+"x"+this.height;
	}
}
