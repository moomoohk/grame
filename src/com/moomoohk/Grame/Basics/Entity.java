package com.moomoohk.Grame.Basics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.Basics.AI.PlayerMovementAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;
import com.moomoohk.Grame.GrassMuncher.Coin;

public class Entity extends GrameObject
{
	private HashMap<Integer, Boolean> player;
	private HashMap<Integer, MovementAI> activeAI;
	private HashMap<Integer, MovementAI> overrideAI;
	private HashMap<Integer, ArrayList<MovementAI>> AI;
	private int points;
	private int range;
	private int targetID;

	public Entity()
	{
		this(new DefaultRandomGen());
	}

	public Entity(String name)
	{
		this(name, GrameUtils.randomColor(), 5);
	}

	public Entity(String name, int speed)
	{
		this(name, GrameUtils.randomColor(), speed);
	}

	public Entity(Color c)
	{
		this(new DefaultRandomGen().nameGen(), c, 5);
	}

	public Entity(Color c, int speed)
	{
		this(new DefaultRandomGen().nameGen(), c, speed);
	}

	public Entity(String name, Color c)
	{
		this(name, c, 5);
	}

	public Entity(EntityGenerator entGen)
	{
		this(entGen.nameGen(), GrameUtils.randomColor(), 5);
	}

	public Entity(EntityGenerator entGen, int speed)
	{
		this(entGen.nameGen(), GrameUtils.randomColor(), speed);
	}

	public Entity(String name, Color c, int speed)
	{
		super(name, speed, c, false);
		this.player = new HashMap<Integer, Boolean>();
		this.targetID = -1;
		this.range = 5;
		this.points = 0;
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.activeAI = new HashMap<Integer, MovementAI>();
		this.overrideAI = new HashMap<Integer, MovementAI>();
	}

	private static final long serialVersionUID = -4997365624920436027L;

	@Override
	public boolean isCollidable()
	{
		return true;
	}

	@Override
	public void tick(int gID)
	{
		determineAI(gID);
		Coordinates c = getPos(gID);
		Coordinates target = null;
		if (this.targetID != -1)
			target = GrameManager.findGrameObject(this.targetID).getPos(gID);
		if (this.activeAI.size() != 0 && this.activeAI.get(gID) != null)
			c = this.activeAI.get(gID).getNext(getPos(gID), target, GrameManager.findGrid(gID), this, (Entity) GrameManager.findGrameObject(targetID));
		setPos(gID, c);
	}

	private void determineAI(int gID)
	{
		if (!GrameManager.findGrid(gID).containsGrameObject(ID) || (this.AI.get(gID) == null && this.overrideAI.get(gID) == null) || (this.AI.get(gID) != null && this.AI.get(gID).size() == 0 && this.overrideAI.get(gID) == null))
		{
			this.activeAI.remove(gID);
			return;
		}
		if (this.overrideAI.size() == 0 || this.overrideAI.get(gID) == null)
		{
			MovementAI temp = null;
			for (int i = 0; i < this.AI.get(gID).size(); i++)
			{
				Coordinates target = null;
				if (targetID != -1)
					target = GrameManager.findGrameObject(targetID).getPos(gID);
				if (!this.AI.get(gID).get(i).isValid(getPos(gID), target, GrameManager.findGrid(gID), this, (Entity) GrameManager.findGrameObject(targetID)))
					continue;
				temp = (MovementAI) this.AI.get(gID).get(i);
				break;
			}
			this.activeAI.put(gID, temp);
		}
		else
		{
			this.activeAI.put(gID, this.overrideAI.get(gID));
		}
	}

	public void addAI(MovementAI AI, int gID)
	{
		if (!AI.isOverride())
		{
			if (this.AI.get(gID) == null)
				this.AI.put(gID, new ArrayList<MovementAI>());
			this.AI.get(gID).add(AI);
		}
		else
			GrameUtils.print(AI + " is an override AI, it doens't belong in my AI list!", MessageLevel.ERROR);
	}

	public void printAI()
	{
		GrameUtils.print("Override AIs:", MessageLevel.NORMAL);
		if (overrideAI.size() != 0)
			for (int gID : overrideAI.keySet())
				GrameUtils.print(gID + ": " + overrideAI.get(gID) + " (" + overrideAI.get(gID).author() + ")", MessageLevel.NORMAL);
		else
			GrameUtils.print("[Empty]", MessageLevel.NORMAL);
		GrameUtils.print("Active AIs:", MessageLevel.NORMAL);
		if (activeAI.size() != 0)
			for (int gID : activeAI.keySet())
				GrameUtils.print(gID + ": " + activeAI.get(gID) + " (" + activeAI.get(gID).author() + ")", MessageLevel.NORMAL);
		else
			GrameUtils.print("[Empty]", MessageLevel.NORMAL);
		for (int gID : AI.keySet())
		{
			GrameUtils.print("For grid ID:" + gID, MessageLevel.NORMAL);
			String st = "null";
			if (this.AI.size() == 0)
				GrameUtils.print("My AI list is empty!", MessageLevel.ERROR);
			else
				for (int i = 0; i < this.AI.size(); i++)
				{
					st = "null";
					if (this.AI.get(i) != null)
						st = this.AI.get(i) + " (" + ((MovementAI) this.AI.get(gID).get(i)).author() + ")";
					GrameUtils.print(i + 1 + ") " + st, MessageLevel.NORMAL);
				}
		}
	}

	public void clearAI()
	{
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.activeAI = null;
		this.overrideAI = null;
	}

	public void setOverrideAI(MovementAI mai, int gID)
	{
		if (mai.isOverride())
			this.overrideAI.put(gID, mai);
		else
			GrameUtils.print(mai + " is not an override AI!", MessageLevel.ERROR);
	}

	@Override
	public void consume(GrameObject go)
	{
		if (go instanceof Coin)
			this.points += ((Coin) go).getWorth();
	}

	public boolean isPlayer(int gID)
	{
		if (this.player.get(gID) == null)
			return false;
		return this.player.get(gID);
	}

	public void makePlayer(int player, boolean f, int gID)
	{
		if (f)
		{
			this.player.put(gID, true);
			this.overrideAI.put(gID, new PlayerMovementAI(player));
		}
		else
			if (this.player.get(gID))
			{
				this.player.put(gID, false);
				this.overrideAI.remove(gID);
			}
			else
				GrameUtils.print("Not a player!", MessageLevel.ERROR);
	}

	public int getRange()
	{
		return this.range;
	}

	public void setRange(int range)
	{
		this.range = range;
	}

	public void setTarget(int eID)
	{
		if (GrameManager.findGrameObject(eID) != null)
			this.targetID = eID;
		else
			GrameUtils.print("Cannot set that Entity (ID:" + eID + ") as target for Entity with ID:" + ID + " (Entity not found)", MessageLevel.ERROR);
	}

	public Entity getTarget()
	{
		return (Entity) (GrameManager.findGrameObject(this.targetID));
	}
}
