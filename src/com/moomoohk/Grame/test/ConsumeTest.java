package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Basics.OldEntity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.GrassMuncher.Coin;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MainGrameClass;

public class ConsumeTest implements MainGrameClass
{
	public static class Player extends OldEntity
	{
		private static final long serialVersionUID = -3253634438661661214L;

		public void consume(GrameObject go)
		{
			System.out.println("Consumed");
		}
	}

	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		GrameManager.initialize(new ConsumeTest());
	}

	@Override
	public void newGame()
	{
		Base b = new Base(20, 20);
		Player p = new Player();
		b.addGrameObject(p, new Coordinates(0, 0));
		for (int i = 0; i < b.getRows(); i++)
			for (int j = 0; j < b.getColumns(); j++)
				if (!b.isOccupied(new Coordinates(i, j)))
					b.addGrameObject(new Coin(), new Coordinates(i, j));
		p.makePlayer(1, true, b.ID);
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}

	@Override
	public String getGameName()
	{
		return "Consume Test";
	}
}
