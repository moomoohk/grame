
package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Interfaces.GrameObject;

public class TestGrameObject extends GrameObject
{
	private static final long serialVersionUID = -480851231518169067L;
	private int time=0;
	public TestGrameObject(String name, int speed, Color color, boolean paused)
	{
		super(name, speed, color, paused);
	}

	@Override
	public boolean isCollidable()
	{
		return false;
	}

	@Override
	public void tick(int bID)
	{
		//this.color=GrameUtils.randomColor();
		time++;
		//this.color=new Color(Math.abs((int)(245*Math.sin(time/4)))+10, Math.abs((int)(245*Math.sin((time+100)/4)))+10, Math.abs((int)(245*Math.sin((time+200)/4)))+10);
	}

	@Override
	public void consume(GrameObject go)
	{
	}
}

