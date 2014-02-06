package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class SaveTest implements MainGrameClass
{
	public static void main(String[] args)
	{
		GrameManager.initialize(new SaveTest());
	}

	public void newGame()
	{
		Grid g = new Grid(20, 20);
		Entity e = new Entity();
		e.makePlayer(1, true, g.ID);
		g.addGrameObject(e, GrameUtils.randomCoordinates(g));
		RenderManager.render(g.ID);
		RenderManager.setVisible(true);
	}

	public String getGameName()
	{
		return "Save Test";
	}
}
