package com.moomoohk.Grame.Core;

import java.io.Serializable;

import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;

/**
 * GrameObjectLayers provide an easy way to store {@link GrameObject}s.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 3.0
 * @since 2013-04-05
 */
public class GrameObjectLayer implements Serializable
{
	private static final long serialVersionUID = 4254735732818296783L;
	private GrameObject[] objects;
	private int width, height;
	
	/**
	 * Constructor.
	 * @param width The width of the GrameObjectLayer.
	 * @param height The height of the GrameObjectLayer.
 	 */
	public GrameObjectLayer(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.objects = new GrameObject[width * height];
	}

	/**
	 * Sets the {@link GrameObject} at a certain set of {@link Coordinates}.
	 * @param pos Place to set the object.
	 * @param go Object to set.
	 * @return True if the operation was successful, else false.
	 */
	public boolean setObject(Coordinates pos, GrameObject go)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.objects.length)
		{
			GrameUtils.print(pos.toString() + " is out of range. Returning.", MessageLevel.ERROR);
			return false;
		}
		this.objects[place] = go;
		return true;
	}

	/**
	 * Returns the {@link GrameObject} at certain {@link Coordinates}.
	 * @param pos {@link Coordinates} from which to get the object.
	 * @return The {@link GrameObject} at the {@link Coordinates}. If the {@link Coordinates} are out of bounds null will be returned.
	 */
	public GrameObject getObject(Coordinates pos)
	{
		try
		{
			return this.objects[pos.x + pos.y * width];
		}
		catch (Exception e)
		{
			return null;
		}
	}

	/**
	 * Returns the {@link Coordinates} of a specified {@link GrameObject}.
	 * @param goID The {@link GrameObject#ID} of the {@link GrameObject} to find.
	 * @return The {@link Coordinates} of the object in this layer. If the object isn't present in this layer null will be returned.
	 */
	public Coordinates getObjectPos(int goID)
	{
		for (int i = 0; i < this.objects.length; i++)
			if (this.objects[i] != null && this.objects[i].ID == goID)
				return new Coordinates((i % width), (i / width));
		GrameUtils.print("Couldn't locate GrameObject with ID:" + goID + ". Returning null.", MessageLevel.ERROR);
		return null;
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
	 * Checks whether this layer contains a {@link GrameObject}.
	 * @param goID The {@link GrameObject#ID} of the object to check.
	 * @return True if this layer contains the object, else false.
	 */
	public boolean contains(int goID)
	{
		for (int i = 0; i < this.objects.length; i++)
			if (objects[i] != null && objects[i].ID == goID)
				return true;
		return false;
	}

	/**
	 * Calculates the total number of {@link GrameObject}s this layer contains.
	 * @return The total number of {@link GrameObject}s this layer contains.
	 */
	public int getTotalObjects()
	{
		int count = 0;
		for (int i = 0; i < this.objects.length; i++)
			if (this.objects[i] != null)
				count++;
		return count;
	}

	/**
	 * Prints the dimensions of this layer.
	 */
	public String toString()
	{
		return "(" + this.width + ", " + this.height + ")";
	}
}