package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Graphics.CleanGridRender;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class GrameLogo
{
	public static void main(String[] args)
	{
		Grid g = new Grid(6, 6);
		TestGrameObject player = new TestGrameObject("player", 1, Color.gray, false);
		TestGrameObject monster = new TestGrameObject("monster", 1, Color.red, false);
		g.addGrameObject(player, new Coordinates(0, 0));
		g.addGrameObject(new Wall(), new Coordinates(1, 1));
		g.addGrameObject(new Wall(), new Coordinates(1, 2));
		g.addGrameObject(new Wall(), new Coordinates(1, 3));
		g.addGrameObject(new Wall(), new Coordinates(1, 4));
		g.addGrameObject(new Wall(), new Coordinates(2, 1));
		g.addGrameObject(new Wall(), new Coordinates(3, 1));
		g.addGrameObject(new Wall(), new Coordinates(4, 1));
		g.addGrameObject(new Wall(), new Coordinates(2, 4));
		g.addGrameObject(new Wall(), new Coordinates(3, 4));
		g.addGrameObject(new Wall(), new Coordinates(4, 4));
		g.addGrameObject(new Wall(), new Coordinates(4, 3));
		g.addGrameObject(monster, new Coordinates(5, 5));
		RenderManager.render(g.ID, new CleanGridRender());
		RenderManager.setVisible(true);
	}
}
