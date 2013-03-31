package com.moomoohk.Grame.Basics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.AI.PlayerMovementAI;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.DefaultRandomGen;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.EntityGenerator;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class Entity extends GrameObject
{
	private String type;
	private int level;
	private int range;
	private int points;
	private HashMap<Integer, Boolean> player;
	private boolean showRange;
	private int targetID;
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
		super(name, 5, color, false);
		this.setType(type);
		this.setLevel(level);
		this.player = new HashMap<Integer, Boolean>();
		this.targetID = -1;
		this.range = 5;
		this.setPoints(0);
		this.showRange = false;
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.mods = new ArrayList<Object>();
		this.activeAI = new HashMap<Integer, MovementAI>();
		this.overrideAI = new HashMap<Integer, MovementAI>();
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
		if ((Entity) GrameManager.findGrameObject(targetID) != null)
			target = ((Entity) (GrameManager.findGrameObject(targetID))).getPos(bID);
		if (this.activeAI.size() != 0 && this.activeAI.get(bID) != null)
			c = this.activeAI.get(bID).getNext(getPos(bID), target, GrameManager.findBase(bID), this, (Entity)GrameManager.findGrameObject(targetID));
		/*
		 * if (this.showRange) { this.b.drawCircle(this.range, this.pos,
		 * this.color); if (!this.b.isOccupied(c)) {
		 * this.b.drawCircle(this.range, this.pos, null); } }
		 */
		GrameManager.findBase(bID).moveGrameObject(ID, c);
	}

	private void determineAI(int bID)
	{
		if (!GrameManager.findBase(bID).containsGrameObject(ID)||(this.AI.get(bID) == null && this.overrideAI.get(bID) == null) || (this.AI.get(bID) != null && this.AI.get(bID).size() == 0 && this.overrideAI.get(bID) == null))
		{
			this.activeAI.remove(bID);
			return;
		}
		if (this.overrideAI.size() == 0 || this.overrideAI.get(bID) == null)
		{
			MovementAI temp = null;
			for (int i = 0; i < this.AI.get(bID).size(); i++)
			{
				Coordinates target = null;
				if (targetID != -1)
					target = GrameManager.findGrameObject(targetID).getPos(bID);
				if (!this.AI.get(bID).get(i).isValid(getPos(bID), target, GrameManager.findBase(bID), this, (Entity)GrameManager.findGrameObject(targetID)))
					continue;
				temp = (MovementAI) this.AI.get(bID).get(i);
				break;
			}
			this.activeAI.put(bID, temp);
		}
		else
		{
			this.activeAI.put(bID, this.overrideAI.get(bID));
		}
	}

	public void printAI()
	{
		GrameUtils.print("Override AIs:", MessageLevel.NORMAL);
		if (overrideAI.size() != 0)
			for (int bID : overrideAI.keySet())
				GrameUtils.print(bID + ": " + overrideAI.get(bID) + " (" + overrideAI.get(bID).author() + ")", MessageLevel.NORMAL);
		else
			GrameUtils.print("[Empty]", MessageLevel.NORMAL);
		GrameUtils.print("Active AIs:", MessageLevel.NORMAL);
		if (activeAI.size() != 0)
			for (int bID : activeAI.keySet())
				GrameUtils.print(bID + ": " + activeAI.get(bID) + " (" + activeAI.get(bID).author() + ")", MessageLevel.NORMAL);
		else
			GrameUtils.print("[Empty]", MessageLevel.NORMAL);
		for (int bID : AI.keySet())
		{
			GrameUtils.print("For base ID:" + bID, MessageLevel.NORMAL);
			String st = "null";
			if (this.AI.size() == 0)
				GrameUtils.print("My AI list is empty!", MessageLevel.ERROR);
			else
				for (int i = 0; i < this.AI.size(); i++)
				{
					st = "null";
					if (this.AI.get(i) != null)
						st = this.AI.get(i) + " (" + ((MovementAI) this.AI.get(bID).get(i)).author() + ")";
					GrameUtils.print(i + 1 + ") " + st, MessageLevel.NORMAL);
				}
		}
	}

	public void addAI(MovementAI AI, int bID)
	{
		if (!AI.isOverride())
		{
			if (this.AI.get(bID) == null)
				this.AI.put(bID, new ArrayList<MovementAI>());
			this.AI.get(bID).add(AI);
		}
		else
			GrameUtils.print(AI + " is an override AI, it doens't belong in my AI list!", MessageLevel.ERROR);
	}

	public void clearAI()
	{
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.activeAI = null;
		this.overrideAI = null;
	}

	public void setOverrideAI(MovementAI mai, int bID)
	{
		if (mai.isOverride())
			this.overrideAI.put(bID, mai);
		else
			GrameUtils.print(mai + " is not an override AI!", MessageLevel.ERROR);
	}

	public boolean reachedTarget(int bID)
	{
		return (targetID != -1) && (this.getPos(bID).distance(GrameManager.findGrameObject(targetID).getPos(bID)) == 1);
	}

	public Coordinates getPos(int bID)
	{
		if (GrameManager.findBase(bID).containsGrameObject(ID))
			return GrameManager.findBase(bID).getGrameObjectPos(ID);
		GrameUtils.print("Base with ID:" + bID + " does not contain Entity with ID:" + ID + ". Returning null.", MessageLevel.ERROR);
		return null;
	}

	public void setPos(int bID, Coordinates pos)
	{
		if (GrameManager.findBase(bID).containsGrameObject(ID))
		{
			GrameManager.findBase(bID).moveGrameObject(ID, pos);
			return;
		}
	}

	public boolean isPlayer(int bID)
	{
		if (this.player.get(bID) == null)
			return false;
		return this.player.get(bID);
	}

	public void makePlayer(int player, boolean f, int bID)
	{
		if (f)
		{
			this.player.put(bID, true);
			this.overrideAI.put(bID, new PlayerMovementAI(player));
		}
		else
			if (this.player.get(bID))
			{
				this.player.put(bID, false);
				this.overrideAI.remove(bID);
			}
			else
				GrameUtils.print("Not a player!", MessageLevel.ERROR);
	}

	public void setTarget(int eID)
	{
		if (GrameManager.findGrameObject(eID) != null)
			this.targetID = eID;
		else
			GrameUtils.print("Cannot set that Entity (ID:" + eID + ") as target for Entity with ID:" + ID + " (Entity not found)", MessageLevel.ERROR);
	}

	public int getRange()
	{
		return this.range;
	}

	public void setRange(int range)
	{
		this.range = range;
	}

	public boolean isCollidable()
	{
		return true;
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
