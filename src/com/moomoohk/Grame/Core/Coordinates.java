package com.moomoohk.Grame.Core;

import java.io.Serializable;
import java.util.ArrayList;

import com.moomoohk.Grame.Basics.Dir;

/**
 * This class massively simplifies the way coordinates are processed.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Coordinates implements Serializable
{
	private static final long serialVersionUID = 3383675004833512203L;
	protected int x;
	protected int y;

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X value of this set of coordinates.
	 * @param y
	 *            Y value of this set of coordinates.
	 */
	public Coordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor.
	 * <p>
	 * Will make both X and Y values of this set of coordinates 0.
	 */
	public Coordinates()
	{
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Gets the X value of this set of coordinates.
	 * 
	 * @return The X value of this set of coordinates.
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Gets the Y value of this set of coordinates.
	 * 
	 * @return The Y value of this set of coordinates.
	 */
	public int getY()
	{
		return this.y;
	}

	/**
	 * Sets the x value of this set of coordinates.
	 * 
	 * @param x
	 *            The x to set.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets the y value of this set of coordinates.
	 * 
	 * @param y
	 *            The y to set.
	 */
	public void setY(int y)
	{
		this.y = y;
	}

	/**
	 * Will apply a {@link Dir} to this set of coordinates.
	 * 
	 * @param d
	 *            {@link Dir} to apply.
	 * @return A new set of coordinates which are a combination of this set and the {@link Dir}.
	 */
	public Coordinates addDir(Dir d)
	{
		int newX = this.x + d.getX();
		int newY = this.y + d.getY();
		return new Coordinates(newX, newY);
	}

	/**
	 * Calculates and returns the distance between two sets of coordinates.
	 * 
	 * @param c
	 *            Coordinates to measure distance from.
	 * @return The distance between two sets of coordinates.
	 */
	public int distance(Coordinates c)
	{
		return Math.abs((int) Math.sqrt(Math.pow(c.getX() - getX(), 2.0D) + Math.pow(c.getY() - getY(), 2.0D)));
	}

	/**
	 * Will return the X and Y values of this set of coordinates.
	 */
	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}

	/**
	 * Will return an Coordinates[] which contains all the sets of coordinates which are adjacent to this set in a certain {@link Grid} and are also in that same {@link Grid}.
	 * 
	 * @param g
	 *            The {@link Grid} in which to check.
	 * @return An array containing all the sets of coordinates which are adjacent to this set in a certain {@link Grid} and are also in that same {@link Grid}.
	 */
	public Coordinates[] getSurrounding(Grid g)
	{
		ArrayList<Coordinates> sur = new ArrayList<Coordinates>();
		if (g.isInGrid(this.addDir(Dir.UP)) && !g.isOccupied(this.addDir(Dir.UP)))
			sur.add(this.addDir(Dir.UP));
		if (g.isInGrid(this.addDir(Dir.DOWN)) && !g.isOccupied(this.addDir(Dir.DOWN)))
			sur.add(this.addDir(Dir.DOWN));
		if (g.isInGrid(this.addDir(Dir.LEFT)) && !g.isOccupied(this.addDir(Dir.LEFT)))
			sur.add(this.addDir(Dir.LEFT));
		if (g.isInGrid(this.addDir(Dir.RIGHT)) && !g.isOccupied(this.addDir(Dir.RIGHT)))
			sur.add(this.addDir(Dir.RIGHT));
		if (g.isInGrid(this.addDir(new Dir(1, 1))) && !g.isOccupied(this.addDir(new Dir(1, 1))))
			sur.add(this.addDir(new Dir(1, 1)));
		if (g.isInGrid(this.addDir(new Dir(-1, 1))) && !g.isOccupied(this.addDir(new Dir(-1, 1))))
			sur.add(this.addDir(new Dir(-1, 1)));
		if (g.isInGrid(this.addDir(new Dir(1, -1))) && !g.isOccupied(this.addDir(new Dir(1, -1))))
			sur.add(this.addDir(new Dir(1, -1)));
		if (g.isInGrid(this.addDir(new Dir(-1, -1))) && !g.isOccupied(this.addDir(new Dir(-1, -1))))
			sur.add(this.addDir(new Dir(-1, -1)));
		Coordinates[] temp = new Coordinates[sur.size()];
		for (int i = 0; i < sur.size(); i++)
			temp[i] = sur.get(i);
		return temp;
	}

	/**
	 * Will return an Coordinates[] which contains all the sets of coordinates which are adjacent to this set in a certain {@link Grid} but are not necessarily in that same {@link Grid}.
	 * 
	 * @param g
	 *            The {@link Grid} in which to check.
	 * @return An array containing all the sets of coordinates which are adjacent to this set in a certain {@link Grid} but are not necessarily in that same {@link Grid}.
	 */
	public Coordinates[] getAllSurrounding(Grid g)
	{
		return new Coordinates[] { this.addDir(Dir.UP), this.addDir(Dir.LEFT), this.addDir(Dir.RIGHT), this.addDir(Dir.DOWN), this.addDir(new Dir(1, 1)), this.addDir(new Dir(-1, 1)), this.addDir(new Dir(1, -1)), this.addDir(new Dir(-1, -1)) };
	}

	/**
	 * Checks whether all the adjacent sets of coordinates contain a {@link GrameObject} in a certain {@link Grid}.
	 * 
	 * @param g
	 *            {@link Grid} in which to check.
	 * @return True if all the adjacent sets of coordinates contain a {@link GrameObject}, else false.
	 */
	public boolean isSurrounded(Grid g)
	{
		Coordinates[] sur = getSurrounding(g);
		for (Coordinates pos : sur)
			if (!g.isOccupied(pos))
				return false;
		return true;
	}

	public boolean equals(Object o)
	{
		if (!(o instanceof Coordinates))
			return false;
		Coordinates temp = (Coordinates) o;
		return temp.x == this.x && temp.y == this.y;
	}
}
