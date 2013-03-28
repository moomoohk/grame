package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.CrashManager;
import com.moomoohk.Grame.Essentials.Dir;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class SimpleChaseAI extends MovementAI
{
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		try
		{
			if (target == null)
			{
				GrameUtils.print("Crucial parameters missing! Returning pos. (" + ent1.getName() + ")", "Simple Chaser", true);
				return pos;
			}
			Dir d = new Dir(pos, target);
			if (!b.isOccupied(pos.addDir(d)))
				return pos.addDir(d);
			if (!b.isOccupied(pos.addDir(d.split()[0])))
				return pos.addDir(d.split()[0]);
			if (!b.isOccupied(pos.addDir(d.split()[1])))
				return pos.addDir(d.split()[1]);
		}
		catch (Exception e)
		{
			CrashManager.showException(e);
		}
		return pos;
	}

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		try
		{
			if (target == null)
			{
				GrameUtils.print("Crucial parameters missing! Returning not valid. (" + ent1.getName() + ")", "Simple Chaser", true);
				return false;
			}
			if (ent1.getPos(b.ID).distance(ent2.getPos(b.ID)) > ent1.getRange())
			{
				return false;
			}

			if (ent1.getPos(b.ID).isSurrounded(b))
			{
				return false;
			}
		}
		catch (Exception e)
		{
			CrashManager.showException(e);
		}
		return true;
	}

	public boolean isOverride()
	{
		return false;
	}

	public String toString()
	{
		return "Simple Chaser";
	}

	public String author()
	{
		return "moomoohk";
	}
}
