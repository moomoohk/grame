package com.moomoohk.Grame.Basics.AI;

import java.util.Random;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.MovementAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Grid;

/**
 * AI that is supposed to simulate a player.
 * <p>
 * Made for debug and testing purposes.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class PlayerSimAI extends MovementAI
{
	private static final long serialVersionUID = 7309667291702854785L;
	private Dir direction;
	private int step, tries;

	/**
	 * Constructor.
	 */
	public PlayerSimAI()
	{
		super();
		this.step = 0;
		this.tries = 0;
	}

	@Override
	public String author()
	{
		return "moomoohk";
	}

	@Override
	public Coordinates getNext(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2)
	{
		if (this.step == 0)
			this.direction = generateDir();
		Coordinates next = pos.addDir(this.direction);
		if (g.getWraparound())
			next = MovementAI.wraparound(g, pos, this.direction);
		else
			if (!g.isInGrid(next))
			{
				this.step = 0;
				return pos;
			}
		if (g.isOccupied(next))
		{
			this.step = 0;
			this.tries++;
			if (this.tries == 10)
			{
				this.tries = 0;
				return pos;
			}
			return getNext(pos, targetPos, g, ent1, ent2);
		}
		this.tries = 0;
		this.step--;
		return next;
	}

	@Override
	public boolean isValid(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2)
	{
		return true;
	}

	@Override
	public boolean isOverride()
	{
		return true;
	}

	private Dir generateDir()
	{
		this.step = new Random().nextInt(30) + 10;
		return Dir.getAllDirs()[new Random().nextInt(Dir.getAllDirs().length)];
	}

	public String toString()
	{
		return "Player sim";
	}
}
