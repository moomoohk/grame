package com.moomoohk.Grame.Basics;

import java.io.Serializable;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.Grid;

/**
 * Class to calculate the AIs of {@link GrameObject}s.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public abstract class MovementAI implements Serializable
{
	private static final long serialVersionUID = 1621624976123203611L;

	/**
	 * Constructor.
	 */
	public MovementAI()
	{
		GrameManager.addAI(this);
	}

	/**
	 * Wraps {@link Coordinates} around a "wraparound" {@link Grid}.
	 * 
	 * @param g
	 *            The "wraparound" {@link Grid}.
	 * @param pos
	 *            The {@link Coordinates} to wrap.
	 * @param d
	 *            The {@link Dir} to apply.
	 * @return The wrapped {@link Coordinates}.
	 * @see Grid#getWraparound()
	 */
	public static Coordinates wraparound(Grid g, Coordinates pos, Dir d)
	{
		if (pos.addDir(d).getX() <= -1 && pos.addDir(d).getY() <= -1)
			return new Coordinates(g.getColumns() - 1, g.getRows() - 1);
		if (pos.addDir(d).getX() <= -1 && pos.addDir(d).getY() >= g.getRows())
			return new Coordinates(g.getColumns() - 1, 0);
		if (pos.addDir(d).getX() >= g.getColumns() && pos.addDir(d).getY() <= -1)
			return new Coordinates(0, g.getRows() - 1);
		if (pos.addDir(d).getX() >= g.getColumns() && pos.addDir(d).getY() >= g.getRows())
			return new Coordinates(0, 0);
		if (pos.addDir(d).getX() <= -1)
			return new Coordinates(g.getColumns() - 1, pos.getY());
		if (pos.addDir(d).getY() <= -1)
			return new Coordinates(pos.getX(), g.getRows() - 1);
		if (pos.addDir(d).getX() >= g.getColumns())
			return new Coordinates(0, pos.getY());
		if (pos.addDir(d).getY() >= g.getRows())
			return new Coordinates(pos.getX(), 0);
		return pos.addDir(d);
	}

	/**
	 * Slides {@link Coordinates} on obstacles in a {@link Grid}.
	 * 
	 * @param g
	 *            The {@link Grid}.
	 * @param pos
	 *            The {@link Coordinates} to slide.
	 * @param d
	 *            The {@link Dir} to apply.
	 * @return The slided {@link Coordinates}.
	 */
	public static Coordinates slide(Grid g, Coordinates pos, Dir d, int layer)
	{
		if (!d.isDiag())
			return pos.addDir(d);
		if (g.isInGrid(pos.addDir(d)) && !g.isOccupied(pos.addDir(d), layer))
			return pos.addDir(d);
		if (!g.isInGrid(pos.addDir(d)))
			if (!g.getWraparound())
			{
				if (g.isInGrid(pos.addDir(d.split()[0])))
					return pos.addDir(d.split()[0]);
				if (g.isInGrid(pos.addDir(d.split()[1])))
					return pos.addDir(d.split()[1]);
			}
			else
				return wraparound(g, pos, d);
		if (!g.isOccupied(pos.addDir(d.split()[0]), layer))
			return pos.addDir(d.split()[0]);
		if (!g.isOccupied(pos.addDir(d.split()[1]), layer))
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
	 * @param g
	 *            The {@link Grid}.
	 * @param ent1
	 *            The {@link GrameObject}.
	 * @param ent2
	 *            The {@link GrameObject}'s target.
	 * @return The next {@link Coordinates} to which the {@link GrameObject} should move.
	 */
	public abstract Coordinates getNext(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2);

	/**
	 * Checks whether this AI is valid.
	 * 
	 * @param pos
	 *            The current {@link Coordinates} of the {@link GrameObject}.
	 * @param targetPos
	 *            The {@link Coordinates} of the {@link GrameObject}'s target.
	 * @param g
	 *            The {@link Grid}.
	 * @param ent1
	 *            The {@link GrameObject}.
	 * @param ent2
	 *            The {@link GrameObject}'s target.
	 * @return True if this AI is valid, else false.
	 */
	public abstract boolean isValid(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2);

	/**
	 * Checks whether this AI is an override AI.
	 * 
	 * @return True if this AI is an override AI, else false.
	 */
	public abstract boolean isOverride();
}