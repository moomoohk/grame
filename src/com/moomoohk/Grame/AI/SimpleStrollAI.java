package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Dir;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class SimpleStrollAI extends MovementAI
{
	public static long currtime=System.currentTimeMillis();
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		if(ent1.ID==3)
		{
			long temp=System.currentTimeMillis();
			//System.out.println(temp-currtime);
			currtime=temp;
		}
		Coordinates[] sur = pos.getAllSurrounding(b);
		Coordinates next = sur[(int) (Math.random() * sur.length)];
		try
		{

			if ((int) (Math.random() * 15)>13)
				if (b.getWraparound())
				{
					if (!b.isInMap(next))
						next=MovementAI.wraparound(b, next, new Dir(pos, next));
					if (b.isOccupied(next))
						return pos;
					else
						return next;
				}
				else
				{
					if (b.isOccupied(next))
						for (int i = 0; i < sur.length || (!b.isOccupied(next) && b.isInMap(next)); i++)
							next = sur[i];
					if(!b.isInMap(next))
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
