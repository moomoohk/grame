package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.CleanGridRender;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class GrameObjectsTest implements MainGrameClass
{
	public static void main(String[] args)
	{
		MenuConfiguration menuConfig = new MenuConfiguration();
		menuConfig.menuButtonStartColor = Color.red;
		menuConfig.menuButtonEndColor = Color.blue;
		menuConfig.menuButtonClickColor = Color.yellow;
		GrameManager.initialize(new GrameObjectsTest(), menuConfig);
	}

	@Override
	public void newGame()
	{
		Grid g = new Grid(20, 20);
		Entity ent = new Entity();
		g.addGrameObject(ent, new Coordinates(10, 10));
		g.setWraparound(true);
		ent.makePlayer(1, true, g.ID);
		ent.setSpeed(1);
		RenderManager.render(g.ID, new CleanGridRender());
		RenderManager.setVisible(true);
	}

	@Override
	public String getGameName()
	{
		return "Test";
	}
}
