
package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Interfaces.GrameObject;

public class Player extends GrameObject
{
	private static final long serialVersionUID = 1615372944054959042L;
	private int points;
	public Player()
	{
		super("Player", 2, Color.DARK_GRAY, false);
		this.points=-1;
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int bID)
	{
		setPos(bID, getNext(bID));
		if(this.points+1==MainScript.maxCoins)
			MainScript.win();
		if(getPos(bID).distance(MainScript.c.getPos(bID))==1)
			MainScript.lose();
	}

	@Override
	public void consume(GrameObject go)
	{
		if(!(go instanceof Coin))
			return;
		Coin coin=(Coin)go;
		points+=coin.getWorth();
	}

	private Coordinates getNext(int bID)
	{
		Dir d=GrameManager.dir1;
		if (d == null)
			return getPos(bID);
		Base b=GrameManager.findBase(bID);
		if(!b.isInBase(getPos(bID).addDir(d)))
			return getPos(bID);
		if(b.isOccupied(getPos(bID).addDir(d)))
		{
			if(!b.isOccupied(getPos(bID).addDir(d.split()[0])))
				return getPos(bID).addDir(d.split()[0]);
			if(!b.isOccupied(getPos(bID).addDir(d.split()[1])))
				return getPos(bID).addDir(d.split()[1]);	
		}
		return getPos(bID).addDir(d);
	}

	public int getPoints()
	{
		return this.points+1;
	}
}

