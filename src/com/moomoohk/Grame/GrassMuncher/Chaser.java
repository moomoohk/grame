package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;

public class Chaser extends GrameObject
{
	private static final long serialVersionUID = -3075457796713552182L;
	private GrameObject target;

	public Chaser()
	{
		super("Chaser", 4, Color.red, false);
	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int gID)
	{
		if (getPos(gID).distance(target.getPos(gID)) == 1)
			MainScript.lose();
		GrameManager.findGrid(gID).moveGrameObject(ID, getNext(gID));
	}

	@Override
	public void consume(GrameObject go)
	{
	}

	private Coordinates getNext(int gID)
	{
		Dir d = new Dir(getPos(gID), this.target.getPos(gID));
		if (!GrameManager.findGrid(gID).isOccupied(getPos(gID).addDir(d), 1))
			return getPos(gID).addDir(d);
		if (!GrameManager.findGrid(gID).isOccupied(getPos(gID).addDir(d.split()[0]), 1))
			return getPos(gID).addDir(d.split()[0]);
		if (!GrameManager.findGrid(gID).isOccupied(getPos(gID).addDir(d.split()[1]), 1))
			return getPos(gID).addDir(d.split()[1]);
		return getPos(gID);
	}

	public void setTarget(GrameObject go)
	{
		this.target = go;
	}
}
