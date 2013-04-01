package com.moomoohk.Grame.Basics;

import java.awt.Color;

import com.moomoohk.Grame.Interfaces.GrameObject;

public class Wall extends GrameObject
{
	public Wall()
	{
		this(Color.black);
	}

	public Wall(Color color)
	{
		super("Wall", 1, color, false);
	}

	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int bID)
	{
	}
}
