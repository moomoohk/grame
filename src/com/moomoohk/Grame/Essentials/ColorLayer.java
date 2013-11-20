package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.io.Serializable;

import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;

/**
 * ColorLayers provide an easy way to implement a color matrix.
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class ColorLayer implements Serializable
{
	private static final long serialVersionUID = 4166643485256534497L;
	private Color[] colors;
	private int width, height;
	
	/**
	 * Constructor
	 * @param width Width of ColorLayer
	 * @param height Height of ColorLayer
	 */
	public ColorLayer(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.colors=new Color[width*height];
	}

	/**
	 * Sets the color of a place in this layer.
	 * @param pos {@link Coordinates} of place to set color.
	 * @param c Color to set.
	 */
	public void setColor(Coordinates pos, Color c)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.colors.length)
			GrameUtils.print(pos.toString() + " is out of range. Returning.", MessageLevel.ERROR);
		this.colors[place] = c;
	}

	/**
	 * Sets the color of all the places in this layer.
	 * @param c Color to set.
	 */
	public void setAll(Color c)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				this.colors[x + y * width] = c;
	}

	/**
	 * Gets the color of a place in this layer.
	 * @param pos {@link Coordinates} of place.
	 * @return The color of the place.
	 */
	public Color getColor(Coordinates pos)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.colors.length)
			GrameUtils.print(pos.toString() + " is out of range. Returning.", MessageLevel.ERROR);
		return this.colors[place];
	}

	/**
	 * Returns the width of this layer.
	 * @return The width of this layer.
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Returns the height of this layer.
	 * @return The height of this layer.
	 */
	public int getHeight()
	{
		return this.height;
	}
	
	/**
	 * Prints the dimensions of this layer.
	 */
	public String toString()
	{
		return this.width+"x"+this.height;
	}
}
