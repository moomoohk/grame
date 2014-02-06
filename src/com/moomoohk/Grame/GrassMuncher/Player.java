package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.Grid;

public class Player extends GrameObject
{
	private static final long serialVersionUID = 1615372944054959042L;
	private int points;

	public Player()
	{
		super("Player", 2, Color.DARK_GRAY, false);
		this.points = -1;
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int gID)
	{
		setPos(gID, getNext(gID));
		if (this.points + 1 == MainScript.maxCoins)
			MainScript.win();
		if (getPos(gID).distance(MainScript.c.getPos(gID)) == 1)
			MainScript.lose();
	}

	@Override
	public void consume(GrameObject go)
	{
		if (!(go instanceof Coin))
			return;
		Coin coin = (Coin) go;
		points += coin.getWorth();
	}

	private Coordinates getNext(int gID)
	{
		Dir d = GrameManager.dir1;
		if (d == null)
			return getPos(gID);
		Grid b = GrameManager.findGrid(gID);
		if (!b.isInGrid(getPos(gID).addDir(d)))
			return getPos(gID);
		if (b.isOccupied(getPos(gID).addDir(d)))
		{
			if (!b.isOccupied(getPos(gID).addDir(d.split()[0])))
				return getPos(gID).addDir(d.split()[0]);
			if (!b.isOccupied(getPos(gID).addDir(d.split()[1])))
				return getPos(gID).addDir(d.split()[1]);
		}
		return getPos(gID).addDir(d);
	}

	public int getPoints()
	{
		return this.points + 1;
	}
}
