
package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.AI.SimpleChaseAI;
import com.moomoohk.Grame.AI.SimpleStrollAI;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;

public class TestScript
{
	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		Base b=new Base(20, 20);
		TestGrameObject test=new TestGrameObject("Test", 1, GrameUtils.randomColor(), false);
		Entity ent=new Entity();
		Entity ent2=new Entity();
		ent2.setTarget(ent.ID);
		ent2.setSpeed(7);
		ent2.addAI(new SimpleChaseAI(), b.ID);
		ent2.addAI(new SimpleStrollAI(), b.ID);
		ent.makePlayer(1, true, b.ID);
		b.addGrameObject(test, GrameUtils.randomCoordinates(b));
		b.addGrameObject(ent2, GrameUtils.randomCoordinates(b));
		b.addGrameObject(ent, GrameUtils.randomCoordinates(b));
		b.setFloorColor(new Coordinates(5, 5), Color.blue);
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}
}

