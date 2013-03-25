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
		System.out.println("hi");
		if (GrameManager.dir == null) 
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
