package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class PlayerMovementAI extends MovementAI
{
	private int player=0;
	public PlayerMovementAI(int player)
	{
		super();
		this.player=player;
	}
	
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		Dir d=player==1?GrameManager.dir1:player==2?GrameManager.dir2:null;
		if (d == null)
			return pos;
		if(!b.isInMap(MovementAI.slide(b, pos, d)))
			if(b.getWraparound())
				return MovementAI.wraparound(b, MovementAI.slide(b, pos, d), d);
			else
				return pos;
		else
			return MovementAI.slide(b, pos, d);
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
