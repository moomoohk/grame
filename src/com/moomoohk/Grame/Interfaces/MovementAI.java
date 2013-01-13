package com.moomoohk.Grame.Interfaces;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Entity;

public abstract interface MovementAI
{
	public abstract String author();

	public abstract Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	public abstract boolean isOverride();
}