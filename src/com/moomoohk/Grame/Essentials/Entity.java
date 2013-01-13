package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.util.ArrayList;

import sun.net.www.content.text.Generic;

import com.moomoohk.Grame.AI.PlayerMovementAI;
import com.moomoohk.Grame.Interfaces.EntityGenerator;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class Entity implements GrameObject
{
	private String type;
	private String name;
	private int level;
	private int range;
	private int points;
	public final int ID;
	private Color color;
	private boolean player;
	private boolean isRendered;
	private boolean showRange;
	private boolean paused;
	private int targetID;
	private int speed;
	protected EntityGenerator randomGen;
	private ArrayList<MovementAI> AI;
	public ArrayList<Object> mods;
	private MovementAI activeAI;
	private MovementAI overrideAI;
	private Coordinates next;

	public Entity()
	{
		this("", "", 1, GrameUtils.randomColor(), false, new DefaultRandomGen());
	}

	public Entity(EntityGenerator entGen)
	{
		this("", "", 1, GrameUtils.randomColor(), false, entGen);
	}

	public Entity(String type, String name, int level, Color color)
	{
		this(type, name, level, color, false, new DefaultRandomGen());
	}

	public Entity(String type, String name, int level, Color color, boolean player, EntityGenerator entGen)
	{
		if (entGen != null)
		{
			name = entGen.nameGen();
			type = entGen.typeGen();
		}
		this.type = type;
		this.name = name;
		this.level = level;
		this.color = color;
		this.player = player;
		this.targetID = -1;
		this.speed = 100;
		this.range = 5;
		this.isRendered = false;

		this.points = 0;
		this.showRange = false;
		this.paused = false;
		this.AI = new ArrayList<MovementAI>();
		this.mods = new ArrayList<Object>();
		this.activeAI = null;
		this.overrideAI = null;
		this.next = null;
		ID = GrameManager.add(this);
	}

	public void tick(int bID)
	{
		determineAI(bID);
		/*
		 * if (this.activeAI != null) { if (this.activeAI.equals(new
		 * SimpleChaseAI())) this.color = Color.red; if
		 * (this.activeAI.equals(new StrollAI())) this.color = Color.green; }
		 */
		Coordinates c = getPos(bID);
		Coordinates target = null;
		if (GrameManager.findEntity(targetID) != null)
			target = GrameManager.findEntity(targetID).getPos(bID);
		if (GrameManager.findEntity(ID).activeAI != null)
			c = GrameManager.findEntity(ID).activeAI.getNext(getPos(bID), target, GrameManager.findBase(bID), this, GrameManager.findEntity(targetID));
		/*
		 * if (this.showRange) { this.b.drawCircle(this.range, this.pos,
		 * this.color); if (!this.b.isOccupied(c)) {
		 * this.b.drawCircle(this.range, this.pos, null); } }
		 */
		//GrameManager.findBase(bID).render(this, c);
	}

	private void determineAI(int bID)
	{
		if ((GrameManager.findEntity(ID).AI.size() == 0) && (GrameManager.findEntity(ID).overrideAI == null))
		{
			GrameManager.findEntity(ID).activeAI = null;
			return;
		}
		if (GrameManager.findEntity(ID).overrideAI == null)
		{
			MovementAI temp = null;
			boolean done = false;
			for (int i = 0; i < GrameManager.findEntity(ID).AI.size(); i++)
			{
				if (done)
					continue;
				Coordinates target = null;
				if (targetID != -1)
					target = GrameManager.findEntity(targetID).getPos(bID);
				if (!GrameManager.findEntity(ID).AI.get(i).isValid(getPos(bID), target, GrameManager.findBase(bID), GrameManager.findEntity(ID), GrameManager.findEntity(targetID)))
					continue;
				done = true;
				temp = (MovementAI) GrameManager.findEntity(ID).AI.get(i);
			}

			GrameManager.findEntity(ID).activeAI = temp;
		}
		else
		{
			GrameManager.findEntity(ID).activeAI = GrameManager.findEntity(ID).overrideAI;
		}
	}

	public void printAI()
	{
		String st = "null";
		if (GrameManager.findEntity(ID).activeAI != null)
			st = GrameManager.findEntity(ID).activeAI + " (" + GrameManager.findEntity(ID).activeAI.author() + ")";
		GrameUtils.print("Active: " + st, getName(), false);
		st = "null";
		if (GrameManager.findEntity(ID).overrideAI != null)
			st = GrameManager.findEntity(ID).overrideAI + " (" + GrameManager.findEntity(ID).overrideAI.author() + ")";
		GrameUtils.print("Override: " + st, getName(), false);
		if (GrameManager.findEntity(ID).AI.size() == 0)
			GrameUtils.print("My AI list is empty!", getName(), false);
		else
			for (int i = 0; i < GrameManager.findEntity(ID).AI.size(); i++)
			{
				st = "null";
				if (GrameManager.findEntity(ID).AI.get(i) != null)
					st = GrameManager.findEntity(ID).AI.get(i) + " (" + ((MovementAI) GrameManager.findEntity(ID).AI.get(i)).author() + ")";
				GrameUtils.print(i + 1 + ") " + st, getName(), false);
			}
	}

	public void addAI(MovementAI AI)
	{
		if (!AI.isOverride())
			GrameManager.findEntity(ID).AI.add(AI);
		else
			GrameUtils.print(AI + " is an override AI, it doens't belong in my AI list!", getName(), false);
	}

	public void clearAI()
	{
		GrameManager.findEntity(ID).AI = new ArrayList<MovementAI>();
		GrameManager.findEntity(ID).activeAI = null;
		GrameManager.findEntity(ID).overrideAI = null;
	}

	public void setOverrideAI(MovementAI mai)
	{
		if (mai.isOverride())
			GrameManager.findEntity(ID).overrideAI = mai;
		else
			GrameUtils.print(mai + " is not an override AI!", getName(), false);
	}

	public boolean reachedTarget(int bID)
	{
		return (targetID != -1) && (GrameManager.findEntity(ID).getPos(bID).distance(GrameManager.findEntity(targetID).getPos(bID)) == 1);
	}

	public Coordinates getPos(int bID)
	{
		if (GrameManager.findBase(bID).containsEnt(ID))
			return GrameManager.findBase(bID).getEntPos(ID);
		GrameUtils.print("Base with ID:" + bID + " does not contain Entity with ID:" + ID + ". Returning null.", "Entity class", false);
		return null;
	}

	/*
	 * public void move(Coordinates c) { if (this.rendered) {
	 * this.b.render(this, c); } }
	 */

	public void setName(String name)
	{
		GrameManager.findEntity(ID).name = name;
	}

	public String getName()
	{
		return GrameManager.findEntity(ID).name;
	}

	public void setColor(Color c)
	{
		GrameManager.findEntity(ID).color = c;
	}

	public Color getColor()
	{
		return GrameManager.findEntity(ID).color;
	}

	public boolean isPlayer()
	{
		return GrameManager.findEntity(ID).player;
	}

	public void makePlayer(boolean f)
	{
		GrameManager.findEntity(ID).player = f;
		if (f)
		{
			//GrameManager.findEntity(ID).b.addPlayer();
			GrameManager.findEntity(ID).overrideAI = new PlayerMovementAI();
		}
		else 
			if (GrameManager.findEntity(ID).player)
			{
				GrameManager.findEntity(ID).player = false;
				GrameManager.findEntity(ID).overrideAI = null;
			}
			else
			{
				GrameUtils.print("Not a player!", getName(), false);
			}
	}

	public void setTarget(int eID)
	{
		if (GrameManager.findEntity(eID) != null)
			GrameManager.findEntity(ID).targetID = eID;
		else
			GrameUtils.print("Cannot set that Entity (ID:" + eID + ") as target for Entity with ID:" + ID + " (Entity not found)", "Entity Class", false);
	}

	public int getRange()
	{
		return GrameManager.findEntity(ID).range;
	}

	public void setRange(int range)
	{
		GrameManager.findEntity(ID).range = range;
		/*if (range == 0)
		{
			Coordinates ul = new Coordinates(0, 0);
			Coordinates ur = new Coordinates(GrameManager.findEntity(ID).b.getColumns() - 1, 0);
			Coordinates dl = new Coordinates(0, GrameManager.findEntity(ID).b.getRows() - 1);
			Coordinates dr = new Coordinates(GrameManager.findEntity(ID).b.getColumns() - 1, GrameManager.findEntity(ID).b.getRows() - 1);
			GrameManager.findEntity(ID).range = Math.max(ul.distance(dr), ur.distance(dl));
		}*/
	}

	public void setSpeed(int speed)
	{
		GrameManager.findEntity(ID).speed = speed;
	}

	public long getSpeed()
	{
		return GrameManager.findEntity(ID).speed;
	}

	public boolean isCollidable()
	{
		return true;
	}

	public void pause(boolean f)
	{
		GrameManager.findEntity(ID).paused = f;
	}

	public boolean isPaused()
	{
		return GrameManager.findEntity(ID).paused;
	}

	public int getID()
	{
		return ID;
	}
}
