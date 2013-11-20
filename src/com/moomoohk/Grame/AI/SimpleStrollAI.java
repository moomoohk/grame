package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Interfaces.MovementAI;

/**
 * This AI is designed to simulate neutral random movement.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class SimpleStrollAI extends MovementAI
{
	private static final long serialVersionUID = 5810610984037692653L;

	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		Coordinates[] sur = pos.getAllSurrounding(b);
		Coordinates next = sur[(int) (Math.random() * sur.length)];
		try
		{

			if ((int) (Math.random() * 15)>13)
				if (b.getWraparound())
				{
					if (!b.isInBase(next))
						next=MovementAI.wraparound(b, next, new Dir(pos, next));
					if (b.isOccupied(next))
						return pos;
					else
						return next;
				}
				else
				{
					if (b.isOccupied(next))
						for (int i = 0; i < sur.length || (!b.isOccupied(next) && b.isInBase(next)); i++)
							next = sur[i];
					if(!b.isInBase(next))
						next=pos;
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

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		if(pos==null)
			return false;
		return !pos.isSurrounded(b);
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
