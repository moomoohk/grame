package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class PlayerMovementAI extends MovementAI
{
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		if (GrameManager.dir == null)
			return pos;
		if(!b.isInMap(pos.addDir(GrameManager.dir)))
			if(b.getWraparound())
			{
				Coordinates temp=pos;
				if (pos.getX() == -1)
					temp= new Coordinates(b.getColumns() - 1, pos.getY());
				if (pos.getY() == -1)
					temp= new Coordinates(pos.getX(), b.getRows() - 1);
				if (pos.getX() == b.getColumns())
					temp= new Coordinates(0, pos.getY());
				if (pos.getY() == b.getRows())
					temp= new Coordinates(pos.getX(), 0);
				return temp;
			}
			else
				return pos;
		return pos.addDir(GrameManager.dir);
	}

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		return ent1.isPlayer(b.ID);
	}

	public boolean isOverride()
	{
		return true;
	}

	public String toString()
	{
		return "Player Movement";
	}

	public String author()
	{
		return "moomoohk";
	}
}
