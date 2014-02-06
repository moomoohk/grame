package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Basics.AI.PlayerSimAI;
import com.moomoohk.Grame.Basics.AI.SimpleChaseAI;
import com.moomoohk.Grame.Basics.AI.SimpleStrollAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class AISystemTest implements MainGrameClass
{
	public static void main(String[] args)
	{
		GrameManager.initialize(new AISystemTest());
	}

	public static void generateStrollers(int amount, Grid g)
	{
		for (int i = 0; i < amount; i++)
		{
			Entity ent = new Entity();
			ent.addAI(new SimpleStrollAI(), g.ID);
			ent.setSpeed(5);
			ent.setColor(Color.blue);
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(ent, temp);
		}
	}

	public static void generateChasers(Grid g, int amount, Entity target)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity(Color.red);
			ent.addAI(new SimpleChaseAI(), g.ID);
			ent.setRange(g.getDiagonal());
			ent.setTarget(target.ID);
			ent.setColor(Color.red);
			ent.setSpeed(2);
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(ent, temp);
		}
	}

	public static void generatePlayers(int number, int amount, Grid b)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity();
			ent.makePlayer(number, true, b.ID);
			ent.setSpeed(1);
			ent.setColor(Color.green);
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(ent, temp);
		}
	}

	public static void generatePlayerSims(int amount, Grid g)
	{
		for (int i = 1; i <= amount; i++)
		{
			Entity ent = new Entity();
			ent.setColor(Color.yellow);
			ent.setOverrideAI(new PlayerSimAI(), g.ID);
			ent.setSpeed(1);
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(ent, temp);
		}
	}

	public static void generateWalls(int amount, Grid g)
	{
		for (int i = 1; i <= amount; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(new Wall(), temp);
		}
	}

	@Override
	public void newGame()
	{
		GrameManager.setDebug(true);
		GrameManager.setSpam(false);
		Grid g = new Grid(20, 20);
		g.setWraparound(true);
		generatePlayers(1, 1, g);
		generatePlayerSims(1, g);
		generateStrollers(5, g);
		generateChasers(g, 3, (Entity) GrameManager.findGrameObject(1));
		generateChasers(g, 3, (Entity) GrameManager.findGrameObject(0));
		RenderManager.render(g.ID);
		RenderManager.setVisible(true);
		RenderManager.setText(g.ID, new Coordinates(6, 6), "test", Color.red);
		RenderManager.setText(g.ID, new Coordinates(19, 19), "phallus", Color.yellow);
		RenderManager.setText(g.ID, new Coordinates(5, 5), "hohohohoh", Color.green);
	}

	@Override
	public String getGameName()
	{
		return "AI System Test";
	}
}
