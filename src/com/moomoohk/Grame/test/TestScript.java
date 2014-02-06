package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.AI.SimpleChaseAI;
import com.moomoohk.Grame.Basics.AI.SimpleStrollAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class TestScript implements MainGrameClass
{
	public static void main(String[] args)
	{
		GrameManager.initialize(new TestScript());
	}

	@Override
	public void newGame()
	{
		Grid g = new Grid(20, 20);
		g.setWraparound(true);
		Entity ent = new Entity(Color.blue);
		ent.setSpeed(1);
		Entity ent2 = new Entity();
		ent2.setTarget(ent.ID);
		ent2.setSpeed(7);
		ent2.setRange(g.getDiagonal());
		ent2.addAI(new SimpleChaseAI(), g.ID);
		ent2.addAI(new SimpleStrollAI(), g.ID);
		ent.makePlayer(1, true, g.ID);
		GrameObject go = new GrameObject("test", 1, Color.black, false)
		{
			private static final long serialVersionUID = -7904811686747660223L;

			@Override
			public void tick(int gID)
			{
				this.color = GrameUtils.randomColor();
			}

			@Override
			public boolean isCollidable()
			{
				return false;
			}

			@Override
			public void consume(GrameObject go)
			{
			}
		};
		SpriteRender.objects.put(ent.ID, "player");
		SpriteRender.objects.put(ent2.ID, "monster");
		g.addGrameObject(ent2, GrameUtils.randomCoordinates(g));
		g.addGrameObject(ent, new Coordinates(10, 10));
		g.addGrameObject(go, GrameUtils.randomCoordinates(g));
		RenderManager.render(g.ID, new SpriteRender());
		RenderManager.setVisible(true);
	}

	@Override
	public String getGameName()
	{
		return "test";
	}
}
