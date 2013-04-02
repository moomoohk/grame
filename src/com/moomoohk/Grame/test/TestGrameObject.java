
package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Interfaces.GrameObject;

public class TestGrameObject extends GrameObject
{

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
		this.color=GrameUtils.randomColor();
	}
}

