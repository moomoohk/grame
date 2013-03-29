package com.moomoohk.Grame.AI;

import java.util.Random;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Dir;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class PlayerSimAI extends MovementAI
{
	private Dir direction;
	private int step;

	public PlayerSimAI()
	{
		super();
		this.step = 0;
	}

	@Override
	public String author()
	{
		return "moomoohk";
	}

	@Override
	public Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2)
	{
		if (this.step == 0)
			this.direction = generateDir();
		Coordinates next = pos.addDir(this.direction);
		if (b.getWraparound())
			next = MovementAI.wraparound(b, pos, this.direction);
		else
			if (!b.isInMap(next))
			{
				this.step = 0;
				return pos;
			}
		if(b.isOccupied(next))
		{
			this.step=0;
			return getNext(pos, targetPos, b, ent1, ent2);
		}
		this.step--;
		return next;
	}

	@Override
	public boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2)
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
