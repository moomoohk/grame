package com.moomoohk.Grame.Interfaces;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;

public abstract class MovementAI
{
	public MovementAI()
	{
		GrameManager.addAI(this);
	}

	public static Coordinates wraparound(Base b, Coordinates pos, Dir d)
	{
		if (pos.addDir(d).getX() <= -1 && pos.addDir(d).getY() <= -1)
			return new Coordinates(b.getColumns() - 1, b.getRows() - 1);
		if (pos.addDir(d).getX() <= -1 && pos.addDir(d).getY() >= b.getRows())
			return new Coordinates(b.getColumns() - 1, 0);
		if (pos.addDir(d).getX() >= b.getColumns() && pos.addDir(d).getY() <= -1)
			return new Coordinates(0, b.getRows() - 1);
		if (pos.addDir(d).getX() >= b.getColumns() && pos.addDir(d).getY() >= b.getRows())
			return new Coordinates(0, 0);
		if (pos.addDir(d).getX() <= -1)
			return new Coordinates(b.getColumns() - 1, pos.getY());
		if (pos.addDir(d).getY() <= -1)
			return new Coordinates(pos.getX(), b.getRows() - 1);
		if (pos.addDir(d).getX() >= b.getColumns())
			return new Coordinates(0, pos.getY());
		if (pos.addDir(d).getY() >= b.getRows())
			return new Coordinates(pos.getX(), 0);
		return pos.addDir(d);
	}

	public abstract String author();

	public abstract Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isOverride();
}