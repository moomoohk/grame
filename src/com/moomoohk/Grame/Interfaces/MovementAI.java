package com.moomoohk.Grame.Interfaces;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;

/**
 * Class to calculate the AIs of {@link GrameObject}s.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public abstract class MovementAI
{
	/**
	 * Constructor.
	 */
	public MovementAI()
	{
		GrameManager.addAI(this);
	}

	/**
	 * Wraps {@link Coordinates} around a "wraparound" {@link Base}.
	 * 
	 * @param b
	 *            The "wraparound" {@link Base}.
	 * @param pos
	 *            The {@link Coordinates} to wrap.
	 * @param d
	 *            The {@link Dir} to apply.
	 * @return The wrapped {@link Coordinates}.
	 * @see Base#getWraparound()
	 */
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

	/**
	 * Slides {@link Coordinates} on obstacles in a {@link Base}.
	 * 
	 * @param b
	 *            The {@link Base}.
	 * @param pos
	 *            The {@link Coordinates} to slide.
	 * @param d
	 *            The {@link Dir} to apply.
	 * @return The slided {@link Coordinates}.
	 */
	public static Coordinates slide(Base b, Coordinates pos, Dir d, int layer)
	{
		if (!d.isDiag())
			return pos.addDir(d);
		if (b.isInBase(pos.addDir(d)) && !b.isOccupied(pos.addDir(d), layer))
			return pos.addDir(d);
		if (!b.isInBase(pos.addDir(d)))
			if (!b.getWraparound())
			{
				if (b.isInBase(pos.addDir(d.split()[0])))
					return pos.addDir(d.split()[0]);
				if (b.isInBase(pos.addDir(d.split()[1])))
					return pos.addDir(d.split()[1]);
			}
			else
				return wraparound(b, pos, d);
		if (!b.isOccupied(pos.addDir(d.split()[0]), layer))
			return pos.addDir(d.split()[0]);
		if (!b.isOccupied(pos.addDir(d.split()[1]), layer))
			return pos.addDir(d.split()[1]);
		return pos.addDir(d);
	}

	/**
	 * The author of this AI.
	 * 
	 * @return The author of this AI.
	 */
	public abstract String author();

	/**
	 * The AI calculation method.
	 * 
	 * @param pos
	 *            The current {@link Coordinates} of the {@link GrameObject}.
	 * @param targetPos
	 *            The {@link Coordinates} of the {@link GrameObject}'s target.
	 * @param b
	 *            The {@link Base}.
	 * @param ent1
	 *            The {@link GrameObject}.
	 * @param ent2
	 *            The {@link GrameObject}'s target.
	 * @return The next {@link Coordinates} to which the {@link GrameObject} should move.
	 */
	public abstract Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	/**
	 * Checks whether this AI is valid.
	 * 
	 * @param pos
	 *            The current {@link Coordinates} of the {@link GrameObject}.
	 * @param targetPos
	 *            The {@link Coordinates} of the {@link GrameObject}'s target.
	 * @param b
	 *            The {@link Base}.
	 * @param ent1
	 *            The {@link GrameObject}.
	 * @param ent2
	 *            The {@link GrameObject}'s target.
	 * @return True if this AI is valid, else false.
	 */
	public abstract boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2);

	/**
	 * Checks whether this AI is an override AI.
	 * 
	 * @return True if this AI is an override AI, else false.
	 */
	public abstract boolean isOverride();
}