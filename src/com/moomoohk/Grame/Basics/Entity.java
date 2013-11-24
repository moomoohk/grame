package com.moomoohk.Grame.Basics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.AI.PlayerMovementAI;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.GrassMuncher.Coin;
import com.moomoohk.Grame.Interfaces.EntityGenerator;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

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
	public void tick(int bID)
	{
		determineAI(bID);
		Coordinates c = getPos(bID);
		Coordinates target = null;
		if (this.targetID != -1)
			target = GrameManager.findGrameObject(this.targetID).getPos(bID);
		if (this.activeAI.size() != 0 && this.activeAI.get(bID) != null)
			c = this.activeAI.get(bID).getNext(getPos(bID), target, GrameManager.findBase(bID), this, (Entity) GrameManager.findGrameObject(targetID));
		setPos(bID, c);
	}

	private void determineAI(int bID)
	{
		if (!GrameManager.findBase(bID).containsGrameObject(ID) || (this.AI.get(bID) == null && this.overrideAI.get(bID) == null) || (this.AI.get(bID) != null && this.AI.get(bID).size() == 0 && this.overrideAI.get(bID) == null))
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
				if (!this.AI.get(bID).get(i).isValid(getPos(bID), target, GrameManager.findBase(bID), this, (Entity) GrameManager.findGrameObject(targetID)))
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

	@Override
	public void consume(GrameObject go)
	{
		if (go instanceof Coin)
			this.points += ((Coin) go).getWorth();
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
