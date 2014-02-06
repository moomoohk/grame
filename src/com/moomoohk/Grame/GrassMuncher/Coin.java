package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import com.moomoohk.Grame.Core.GrameObject;

public class Coin extends GrameObject
{
	private static final long serialVersionUID = 3955843569288863271L;
	private int worth;

	public Coin()
	{
		super("Coin", 0, Color.green, false);
		this.worth = 1;
	}

	public Coin(int worth)
	{
		super("Coin", 0, Color.green, false);
		this.worth = worth;
	}

	@Override
	public boolean isCollidable()
	{
		return false;
	}

	@Override
	public void tick(int gID)
	{

	}

	public int getWorth()
	{
		return this.worth;
	}

	@Override
	public void consume(GrameObject go)
	{
	}
}
