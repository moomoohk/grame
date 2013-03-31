package com.moomoohk.Grame.Interfaces;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;

public abstract class GrameObject
{
	public final int ID;
	protected String name;
	protected int speed;
	protected Color color;
	protected boolean paused;

	public GrameObject(String name, int speed, Color color, boolean paused)
	{
		this.name=name;
		this.speed=speed;
		this.color=color;
		this.paused=paused;
		ID = GrameManager.add(this);
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSpeed()
	{
		return this.speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public Color getColor()
	{
		return this.color;
	}

	public void setColor(Color c)
	{
		this.color = c;
	}

	public void pause(boolean pause)
	{
		this.paused = pause;
	}

	public boolean isPaused()
	{
		return paused;
	}
	
	public Coordinates getPos(int bID)
	{
		if (GrameManager.findBase(bID).containsGrameObject(ID))
			return GrameManager.findBase(bID).getGrameObjectPos(ID);
		GrameUtils.dumpStackTrace();
		GrameUtils.print("Base with ID:" + bID + " does not contain Entity with ID:" + ID + ". Returning null.", MessageLevel.ERROR);
		return null;
	}

	public void setPos(int bID, Coordinates pos)
	{
		if (GrameManager.findBase(bID).containsGrameObject(ID))
		{
			GrameManager.findBase(bID).moveGrameObject(ID, pos);
			return;
		}
	}
	
	public abstract boolean isCollidable();

	public abstract void tick(int bID);
}
