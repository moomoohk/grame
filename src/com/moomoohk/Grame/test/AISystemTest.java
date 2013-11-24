package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.AI.PlayerSimAI;
import com.moomoohk.Grame.AI.SimpleChaseAI;
import com.moomoohk.Grame.AI.SimpleStrollAI;
import com.moomoohk.Grame.Basics.OldEntity;
import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MainGrameClass;

public class AISystemTest implements MainGrameClass
{
	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		GrameUtils.loadBasicAIs();
		GrameManager.initialize(new AISystemTest());
	}

	public static void generateStrollers(int amount, Base b)
	{
		for (int i = 0; i < amount; i++)
		{
			OldEntity ent = new OldEntity();
			ent.addAI(new SimpleStrollAI(), b.ID);
			ent.setSpeed(5);
			ent.setColor(Color.blue);
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(ent, temp);
		}
	}

	public static void generateChasers(Base b, int amount, OldEntity target)
	{
		for (int i = 1; i <= amount; i++)
		{
			OldEntity ent = new OldEntity(Color.red);
			ent.addAI(new SimpleChaseAI(), b.ID);
			ent.setRange(b.getDiagonal());
			ent.setTarget(target.ID);
			ent.setColor(Color.red);
			ent.setSpeed(2);
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(ent, temp);
		}
	}

	public static void generatePlayers(int number, int amount, Base b)
	{
		for (int i = 1; i <= amount; i++)
		{
			OldEntity ent = new OldEntity();
			ent.makePlayer(number, true, b.ID);
			ent.setSpeed(1);
			ent.setColor(Color.green);
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(ent, temp);
		}
	}

	public static void generatePlayerSims(int amount, Base b)
	{
		for (int i = 1; i <= amount; i++)
		{
			OldEntity ent = new OldEntity();
			ent.setColor(Color.yellow);
			ent.setOverrideAI(new PlayerSimAI(), b.ID);
			ent.setSpeed(1);
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(ent, temp);
		}
	}

	public static void generateWalls(int amount, Base b)
	{
		for (int i = 1; i <= amount; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(new Wall(), temp);
		}
	}

	@Override
	public void newGame()
	{
		GrameManager.setDebug(true);
		GrameManager.setSpam(false);
		Base b = new Base(20, 20);
		b.setWraparound(true);
		generatePlayers(1, 1, b);
		generatePlayerSims(1, b);
		generateStrollers(5, b);
		generateChasers(b, 3, (OldEntity) GrameManager.findGrameObject(1));
		generateChasers(b, 3, (OldEntity) GrameManager.findGrameObject(0));
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
		RenderManager.setText(b.ID, new Coordinates(6, 6), "test", Color.red);
		RenderManager.setText(b.ID, new Coordinates(19, 19), "penis", Color.yellow);
		RenderManager.setText(b.ID, new Coordinates(5, 5), "hohohohoh", Color.green);
	}

	@Override
	public String getGameName()
	{
		return "AI System Test";
	}
}
