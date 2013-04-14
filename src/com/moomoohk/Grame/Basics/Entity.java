package com.moomoohk.Grame.Basics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.AI.PlayerMovementAI;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.EntityGenerator;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

/**
 * Entities are ready to use {@link GrameObject}s which have a lot of configurable aspects.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Entity extends GrameObject
{
	private String type;
	private int level;
	private int range;
	private int points;
	private HashMap<Integer, Boolean> player;
	private int targetID;
	protected EntityGenerator randomGen;
	private HashMap<Integer, MovementAI> activeAI;
	private HashMap<Integer, MovementAI> overrideAI;
	private HashMap<Integer, ArrayList<MovementAI>> AI;

	/**
	 * Constructor.
	 */
	public Entity()
	{
		this(new DefaultRandomGen().nameGen(), new DefaultRandomGen().typeGen(), 1, GrameUtils.randomColor());
	}

	/**
	 * Constructor.
	 * 
	 * @param c
	 *            Entity color.
	 */
	public Entity(Color c)
	{
		this(new DefaultRandomGen().nameGen(), new DefaultRandomGen().typeGen(), 1, c);
	}

	/**
	 * Constructor.
	 * 
	 * @param entGen
	 *            {@link EntityGenerator} to use.
	 */
	public Entity(EntityGenerator entGen)
	{
		this(entGen.nameGen(), entGen.typeGen(), 1, GrameUtils.randomColor());
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Entity name.
	 * @param type
	 *            Entity type.
	 * @param level
	 *            Starting level.
	 * @param color
	 *            Entity color.
	 */
	public Entity(String name, String type, int level, Color color)
	{
		super(name, 5, color, false);
		this.setType(type);
		this.setLevel(level);
		this.player = new HashMap<Integer, Boolean>();
		this.targetID = -1;
		this.range = 5;
		this.setPoints(0);
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.activeAI = new HashMap<Integer, MovementAI>();
		this.overrideAI = new HashMap<Integer, MovementAI>();
	}

	public void tick(int bID)
	{
		determineAI(bID);
		Coordinates c = getPos(bID);
		Coordinates target = null;
		if (this.targetID != -1)
			target = GrameManager.findGrameObject(this.targetID).getPos(bID);
		if (this.activeAI.size() != 0 && this.activeAI.get(bID) != null)
			c = this.activeAI.get(bID).getNext(getPos(bID), target, GrameManager.findBase(bID), this, (Entity) GrameManager.findGrameObject(targetID));
		GrameManager.findBase(bID).moveGrameObject(ID, c);
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

	/**
	 * Prints the {@link MovementAI} list of this Entity.
	 */
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

	/**
	 * Adds a {@link MovementAI} to this Entity's AI list.
	 * 
	 * @param AI
	 *            {@link MovementAI} to add.
	 * @param bID
	 *            The {@link Base#ID} of the {@link Base} in which to apply the {@link MovementAI}.
	 */
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

	/**
	 * Clears this Entity's AI list.
	 */
	public void clearAI()
	{
		this.AI = new HashMap<Integer, ArrayList<MovementAI>>();
		this.activeAI = null;
		this.overrideAI = null;
	}

	/**
	 * Sets the override for this Entity.
	 * 
	 * @param mai
	 *            {@link MovementAI} which is an override AI.
	 * @param bID
	 *            The {@link Base#ID} of the {@link Base} in which to apply the {@link MovementAI}.
	 */
	public void setOverrideAI(MovementAI mai, int bID)
	{
		if (mai.isOverride())
			this.overrideAI.put(bID, mai);
		else
			GrameUtils.print(mai + " is not an override AI!", MessageLevel.ERROR);
	}

	/**
	 * Checks whether this Entity has reached its target.
	 * 
	 * @param bID
	 *            {@link Base#ID} of the {@link Base} in which to check.
	 * @return True if this Entity reached its target, else false.
	 */
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

	/**
	 * Checks whether or not this Entity is a "player" (controllable by the keyboard).
	 * @param bID The {@link Base#ID} of the {@link Base} in which to check.
	 * @return True if this Entity is a "player", else false.
	 * @see Entity#makePlayer(int, boolean, int)
	 */
	public boolean isPlayer(int bID)
	{
		if (this.player.get(bID) == null)
			return false;
		return this.player.get(bID);
	}

	/**
	 * Turns this Entity into a "player".
	 * @param player 1 for wasd control, 2 for arrow keys control.
	 * @param f True to turn this Entity into a "player", else false.
	 * @param bID The {@link Base#ID} of the {@link Base} in which to turn this Entity into a "player".
	 * @see Entity#isPlayer(int)
	 */
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

	/**
	 * Sets the target of this Entity.
	 * @param eID The {@link GrameObject#ID} of the {@link GrameObject}/Entity to set as target. 
	 */
	public void setTarget(int eID)
	{
		if (GrameManager.findGrameObject(eID) != null)
			this.targetID = eID;
		else
			GrameUtils.print("Cannot set that Entity (ID:" + eID + ") as target for Entity with ID:" + ID + " (Entity not found)", MessageLevel.ERROR);
	}
	
	/**
	 * Returns this Entity's target.
	 * @return This Entity's target.
	 */
	public Entity getTarget()
	{
		return (Entity)(GrameManager.findGrameObject(this.targetID));
	}

	/**
	 * Gets the range of this Entity.
	 * @return The range of this Entity.
	 */
	public int getRange()
	{
		return this.range;
	}

	/**
	 * Sets the range of this Entity.
	 * @param range The range to set.
	 */
	public void setRange(int range)
	{
		this.range = range;
	}

	public boolean isCollidable()
	{
		return true;
	}

	/**
	 * Sets the type of this Entity.
	 * @param type The type to set.
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Gets the type of this Entity.
	 * @return The type of this Entity.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the level of this Entity.
	 * @param level The level to set.
	 */
	public void setLevel(int level)
	{
		this.level = level;
	}

	/**
	 * Gets the level of this Entity.
	 * @return The level of this Entity.
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * Sets the points of this Entity.
	 * @param points The points to set.
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	/**
	 * Gets the points of this Entity.
	 * @return The points of this Entity.
	 */
	public int getPoints()
	{
		return points;
	}

	@Override
	public void consume(GrameObject go)
	{

	}
}
