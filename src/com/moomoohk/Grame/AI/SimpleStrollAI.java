package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class SimpleStrollAI extends MovementAI
{
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		Coordinates[] sur = pos.getSurrounding(b);
		Coordinates next = sur[(int) (Math.random() * sur.length)];
		try
		{
			if ((int) (Math.random() * 5.0D) > 2)
			{
				if (!b.isInMap(next) || b.isOccupied(next))
					for (int i = 0; i < sur.length && (!b.isInMap(next) || b.isOccupied(next)); i++)
						next = sur[(int) (Math.random() * sur.length)];
			}
			else
				next = pos;
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			next = pos;
		}
		if (b.isOccupied(next))
			next = pos;
		return next;
	}

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
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
