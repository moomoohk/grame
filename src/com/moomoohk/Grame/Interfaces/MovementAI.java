package com.moomoohk.Grame.Interfaces;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;

public abstract class MovementAI
{
	public MovementAI()
	{
		GrameManager.addAI(this);
	}

	public abstract String author();

	public abstract Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isOverride();
}