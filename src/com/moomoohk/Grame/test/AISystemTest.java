package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.AI.PlayerSimAI;
import com.moomoohk.Grame.AI.SimpleChaseAI;
import com.moomoohk.Grame.AI.SimpleStrollAI;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;

public class AISystemTest
{
	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		GrameUtils.loadBasicAIs();
		Base b = new Base(20, 20);
		b.setWraparound(true);
		generatePlayers(1, 1, b);
		generatePlayerSims(1, b);
		generateStrollers(5, b);
		generateChasers(b, 5, GrameManager.findEntity(0));
		generateChasers(b, 5, GrameManager.findEntity(1));
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}

	public static void generateStrollers(int amount, Base b)
	{
		for (int i = 0; i < amount; i++)
		{
			Entity ent = new Entity();
			ent.addAI(new SimpleStrollAI(), b.ID);
			ent.setSpeed(5);
			ent.setColor(Color.blue);
			b.addEntity(ent.ID, GrameUtils.randomCoordinates(b));
		}
	}

	public static void generateChasers(Base b, int amount, Entity target)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity(Color.red);
			ent.addAI(new SimpleChaseAI(), b.ID);
			ent.setRange(b.getDiagonal());
			ent.setTarget(target.ID);
			ent.setColor(Color.red);
			ent.setSpeed(5);
			b.addEntity(ent.ID, GrameUtils.randomCoordinates(b));
		}
	}

	public static void generatePlayers(int number, int amount, Base b)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity();
			b.addEntity(ent.ID, GrameUtils.randomCoordinates(b));
			ent.makePlayer(number, true, b.ID);
			ent.setSpeed(2);
			ent.setColor(Color.green);
		}
	}

	public static void generatePlayerSims(int amount, Base b)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity();
			ent.setColor(Color.yellow);
			ent.setOverrideAI(new PlayerSimAI(), b.ID);
			ent.setSpeed(4);
			b.addEntity(ent.ID, GrameUtils.randomCoordinates(b));
		}
	}
}
