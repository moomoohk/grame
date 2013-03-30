package com.moomoohk.Grame.Essentials;

import java.awt.Color;

import com.moomoohk.Grame.Interfaces.GrameObject;

public class Wall implements GrameObject
{
	private Color color;

	public Wall()
	{
		this(Color.black);
	}

	public Wall(Color color)
	{
		this.color = color;
	}

	public Color getColor()
	{
		return this.color;
	}

	public boolean isCollidable()
	{
		return false;
	}

	public int getID()
	{
		return 1000;
	}
}
