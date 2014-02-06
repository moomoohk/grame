package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.RenderManager;
import com.moomoohk.Grame.GrassMuncher.Coin;

public class ConsumeTest implements MainGrameClass
{
	public static class Player extends Entity
	{
		private static final long serialVersionUID = -3253634438661661214L;

		public void consume(GrameObject go)
		{
			System.out.println("Consumed");
		}
	}

	public static void main(String[] args)
	{
		GrameManager.initialize(new ConsumeTest());
	}

	@Override
	public void newGame()
	{
		Grid g = new Grid(20, 20);
		Player p = new Player();
		g.addGrameObject(p, new Coordinates(0, 0));
		for (int i = 0; i < g.getRows(); i++)
			for (int j = 0; j < g.getColumns(); j++)
				if (!g.isOccupied(new Coordinates(i, j)))
					g.addGrameObject(new Coin(), new Coordinates(i, j));
		p.makePlayer(1, true, g.ID);
		RenderManager.render(g.ID);
		RenderManager.setVisible(true);
	}

	@Override
	public String getGameName()
	{
		return "Consume Test";
	}
}
