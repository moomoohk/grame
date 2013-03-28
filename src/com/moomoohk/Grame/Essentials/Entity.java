package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

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
	private HashMap<Integer, Boolean> player;
	private boolean showRange;
	private boolean paused;
	private int targetID;
	private int speed;
	protected EntityGenerator randomGen;
	public ArrayList<Object> mods;
	private HashMap<Integer, MovementAI> activeAI;
	private HashMap<Integer, MovementAI> overrideAI;
	private HashMap<Integer, ArrayList<MovementAI>> AI;

	public Entity()
	{
		this(new DefaultRandomGen().nameGen(), new DefaultRandomGen().typeGen(), 1, GrameUtils.randomColor());
	}
	public Entity(Color c)
	{
		this(new DefaultRandomGen().nameGen(), new DefaultRandomGen().typeGen(), 1, c);
	}

	public Entity(EntityGenerator entGen)
	{
		this(entGen.nameGen(), entGen.typeGen(), 1, GrameUtils.randomColor());
	}

	public Entity(String name, String type, int level, Color color)
	{
		this.setType(type);
		this.name = name;
		this.setLevel(level);
		this.color = color;
		this.player = new HashMap<Integer, Boolean>();
		this.targetID = -1;
		this.speed = 100;
		this.range = 5;
		this.setPoints(0);
		this.showRange = false;
		this.paused = false;
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.mods = new ArrayList<Object>();
		this.activeAI = new HashMap<Integer, MovementAI>();
		this.overrideAI = new HashMap<Integer, MovementAI>();
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
		if (GrameManager.findEntity(ID).activeAI.size() != 0 && GrameManager.findEntity(ID).activeAI.get(bID) != null)
			c = GrameManager.findEntity(ID).activeAI.get(bID).getNext(getPos(bID), target, GrameManager.findBase(bID), this, GrameManager.findEntity(targetID));
		/*
		 * if (this.showRange) { this.b.drawCircle(this.range, this.pos,
		 * this.color); if (!this.b.isOccupied(c)) {
		 * this.b.drawCircle(this.range, this.pos, null); } }
		 */
		GrameManager.findBase(bID).moveEnt(ID, c);
	}

	private void determineAI(int bID)
	{
		if ((GrameManager.findEntity(ID).AI.get(bID) == null && GrameManager.findEntity(ID).overrideAI.get(bID) == null) || (GrameManager.findEntity(ID).AI.get(bID) != null && GrameManager.findEntity(ID).AI.get(bID).size() == 0 && GrameManager.findEntity(ID).overrideAI.get(bID) == null))
		{
			GrameManager.findEntity(ID).activeAI.remove(bID);
			return;
		}
		if (GrameManager.findEntity(ID).overrideAI.size() == 0 || GrameManager.findEntity(ID).overrideAI.get(bID) == null)
		{
			MovementAI temp = null;
			for (int i = 0; i < GrameManager.findEntity(ID).AI.get(bID).size(); i++)
			{
				Coordinates target = null;
				if (targetID != -1)
					target = GrameManager.findEntity(targetID).getPos(bID);
				if (!GrameManager.findEntity(ID).AI.get(bID).get(i).isValid(getPos(bID), target, GrameManager.findBase(bID), GrameManager.findEntity(ID), GrameManager.findEntity(targetID)))
					continue;
				temp = (MovementAI) GrameManager.findEntity(ID).AI.get(bID).get(i);
				break;
			}
			GrameManager.findEntity(ID).activeAI.put(bID, temp);
		}
		else
		{
			GrameManager.findEntity(ID).activeAI.put(bID, GrameManager.findEntity(ID).overrideAI.get(bID));
		}
	}

	public void printAI()
	{
		GrameUtils.print("Override AIs:", "Entity", false);
		if (overrideAI.size() != 0)
			for (int bID : overrideAI.keySet())
				GrameUtils.print(bID + ": " + overrideAI.get(bID) + " (" + overrideAI.get(bID).author() + ")", "Entity", false);
		else
			GrameUtils.print("[Empty]", "Entity", false);
		GrameUtils.print("Active AIs:", "Entity", false);
		if (activeAI.size() != 0)
			for (int bID : activeAI.keySet())
				GrameUtils.print(bID + ": " + activeAI.get(bID) + " (" + activeAI.get(bID).author() + ")", "Entity", false);
		else
			GrameUtils.print("[Empty]", "Entity", false);
		for (int bID : AI.keySet())
		{
			GrameUtils.print("For base ID:" + bID, getName(), false);
			String st = "null";
			if (GrameManager.findEntity(ID).AI.size() == 0)
				GrameUtils.print("My AI list is empty!", getName(), false);
			else
				for (int i = 0; i < GrameManager.findEntity(ID).AI.size(); i++)
				{
					st = "null";
					if (GrameManager.findEntity(ID).AI.get(i) != null)
						st = GrameManager.findEntity(ID).AI.get(i) + " (" + ((MovementAI) GrameManager.findEntity(ID).AI.get(bID).get(i)).author() + ")";
					GrameUtils.print(i + 1 + ") " + st, getName(), false);
				}
		}
	}

	public void addAI(MovementAI AI, int bID)
	{
		if (!AI.isOverride())
		{
			if (GrameManager.findEntity(ID).AI.get(bID) == null)
				GrameManager.findEntity(ID).AI.put(bID, new ArrayList<MovementAI>());
			GrameManager.findEntity(ID).AI.get(bID).add(AI);
		}
		else
			GrameUtils.print(AI + " is an override AI, it doens't belong in my AI list!", getName(), false);
	}

	public void clearAI()
	{
		GrameManager.findEntity(ID).AI = new HashMap<Integer, ArrayList<MovementAI>>();
		GrameManager.findEntity(ID).activeAI = null;
		GrameManager.findEntity(ID).overrideAI = null;
	}

	public void setOverrideAI(MovementAI mai, int bID)
	{
		if (mai.isOverride())
			GrameManager.findEntity(ID).overrideAI.put(bID, mai);
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

	public void setPos(int bID, Coordinates pos)
	{
		if (GrameManager.findBase(bID).containsEnt(ID))
		{
			GrameManager.findBase(bID).moveEnt(ID, pos);
			return;
		}
	}

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

	public boolean isPlayer(int bID)
	{
		if (GrameManager.findEntity(ID).player.get(bID) == null)
			return false;
		return GrameManager.findEntity(ID).player.get(bID);
	}

	public void makePlayer(int player, boolean f, int bID)
	{
		if (f)
		{
			GrameManager.findEntity(ID).player.put(bID, true);
			GrameManager.findEntity(ID).overrideAI.put(bID, new PlayerMovementAI(player));
		}
		else
			if (GrameManager.findEntity(ID).player.get(bID))
			{
				GrameManager.findEntity(ID).player.put(bID, false);
				GrameManager.findEntity(ID).overrideAI.remove(bID);
			}
			else
				GrameUtils.print("Not a player!", getName(), false);
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
	public void setType(String type)
	{
		this.type = type;
	}
	public String getType()
	{
		return type;
	}
	public void setLevel(int level)
	{
		this.level = level;
	}
	public int getLevel()
	{
		return level;
	}
	public void setPoints(int points)
	{
		this.points = points;
	}
	public int getPoints()
	{
		return points;
	}
}
