package com.moomoohk.Grame.Basics.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.MovementAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Grid;

/**
 * This AI is designed to simulate neutral random movement.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-04-05
 */
public class SimpleStrollAI extends MovementAI
{
	private static final long serialVersionUID = 5810610984037692653L;

	public Coordinates getNext(Coordinates pos, Coordinates target, Grid g, Entity ent1, Entity ent2)
	{
		Coordinates[] sur = pos.getAllSurrounding(g);
		Coordinates next = sur[(int) (Math.random() * sur.length)];
		try
		{

			if ((int) (Math.random() * 15) > 13)
				if (g.getWraparound())
				{
					if (!g.isInGrid(next))
						next = MovementAI.wraparound(g, next, new Dir(pos, next));
					if (g.isOccupied(next))
						return pos;
					else
						return next;
				}
				else
				{
					if (g.isOccupied(next))
						for (int i = 0; i < sur.length || (!g.isOccupied(next) && g.isInGrid(next)); i++)
							next = sur[i];
					if (!g.isInGrid(next))
						next = pos;
				}
			else
				next = pos;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			next = pos;
		}
		return next;
	}

	public boolean isValid(Coordinates pos, Coordinates target, Grid g, Entity ent1, Entity ent2)
	{
		if (pos == null)
			return false;
		return !pos.isSurrounded(g);
	}

	public boolean isOverride()
	{
		return false;
	}

	public String toString()
	{
		return "Simple Stroller";
	}

	public String author()
	{
		return "moomoohk";
	}
}
