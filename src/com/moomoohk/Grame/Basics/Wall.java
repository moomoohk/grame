package com.moomoohk.Grame.Basics;

import java.awt.Color;

import com.moomoohk.Grame.Core.GrameObject;

/**
 * Walls are ready to use {@link GrameObject}s which are supposed to represent ordinary walls.
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-04-05
 */
public class Wall extends GrameObject
{
	private static final long serialVersionUID = 3012316648510197720L;

	/**
	 * Constructor.
	 */
	public Wall()
	{
		this(Color.black);
	}

	/**
	 * Constructor.
	 * @param color Color of this Wall.
	 */
	public Wall(Color color)
	{
		super("Wall", 1, color, false);
	}

	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int gID)
	{
	}

	@Override
	public void consume(GrameObject go)
	{
	}
}
