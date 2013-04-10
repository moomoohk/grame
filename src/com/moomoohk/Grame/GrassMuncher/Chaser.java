
package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Interfaces.GrameObject;

public class Chaser extends GrameObject
{
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
	public void tick(int bID)
	{
		if(getPos(bID).distance(target.getPos(bID))==1)
			MainScript.lose();
		GrameManager.findBase(bID).moveGrameObject(ID, getNext(bID));
	}

	@Override
	public void consume(GrameObject go)
	{
	} 
	private Coordinates getNext(int bID)
	{
		Dir d = new Dir(getPos(bID), this.target.getPos(bID));
		if (!GrameManager.findBase(bID).isOccupied(getPos(bID).addDir(d), 1))
			return getPos(bID).addDir(d);
		if(!GrameManager.findBase(bID).isOccupied(getPos(bID).addDir(d.split()[0]), 1))
			return getPos(bID).addDir(d.split()[0]);
		if(!GrameManager.findBase(bID).isOccupied(getPos(bID).addDir(d.split()[1]), 1))
			return getPos(bID).addDir(d.split()[1]);
		return getPos(bID);
	}
	public void setTarget(GrameObject go)
	{
		this.target=go;
	}
}

