package com.moomoohk.Grame.Essentials;

import java.util.ArrayList;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Interfaces.GrameObject;

/**
 * This class massively simplifies the way coordinates are processed.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Coordinates
{
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
	 * @param x The x to set.
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * Sets the y value of this set of coordinates.
	 * @param y The y to set.
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
	 * @return A new set of coordinates which are a combination of this set and
	 *         the {@link Dir}.
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
	 * Will return an Coordinates[] which contains all the sets of coordinates
	 * which are adjacent to this set in a certain {@link Base} and are also in
	 * that same {@link Base}.
	 * 
	 * @param b
	 *            The {@link Base} in which to check.
	 * @return An array containing all the sets of coordinates which are
	 *         adjacent to this set in a certain {@link Base} and are also in
	 *         that same {@link Base}.
	 */
	public Coordinates[] getSurrounding(Base b)
	{
		ArrayList<Coordinates> sur = new ArrayList<Coordinates>();
		if (b.isInBase(this.addDir(Dir.UP)) && !b.isOccupied(this.addDir(Dir.UP)))
			sur.add(this.addDir(Dir.UP));
		if (b.isInBase(this.addDir(Dir.DOWN)) && !b.isOccupied(this.addDir(Dir.DOWN)))
			sur.add(this.addDir(Dir.DOWN));
		if (b.isInBase(this.addDir(Dir.LEFT)) && !b.isOccupied(this.addDir(Dir.LEFT)))
			sur.add(this.addDir(Dir.LEFT));
		if (b.isInBase(this.addDir(Dir.RIGHT)) && !b.isOccupied(this.addDir(Dir.RIGHT)))
			sur.add(this.addDir(Dir.RIGHT));
		if (b.isInBase(this.addDir(new Dir(1, 1))) && !b.isOccupied(this.addDir(new Dir(1, 1))))
			sur.add(this.addDir(new Dir(1, 1)));
		if (b.isInBase(this.addDir(new Dir(-1, 1))) && !b.isOccupied(this.addDir(new Dir(-1, 1))))
			sur.add(this.addDir(new Dir(-1, 1)));
		if (b.isInBase(this.addDir(new Dir(1, -1))) && !b.isOccupied(this.addDir(new Dir(1, -1))))
			sur.add(this.addDir(new Dir(1, -1)));
		if (b.isInBase(this.addDir(new Dir(-1, -1))) && !b.isOccupied(this.addDir(new Dir(-1, -1))))
			sur.add(this.addDir(new Dir(-1, -1)));
		Coordinates[] temp = new Coordinates[sur.size()];
		for (int i = 0; i < sur.size(); i++)
			temp[i] = sur.get(i);
		return temp;
	}

	/**
	 * Will return an Coordinates[] which contains all the sets of coordinates
	 * which are adjacent to this set in a certain {@link Base} but are not
	 * necessarily in that same {@link Base}.
	 * 
	 * @param b
	 *            The {@link Base} in which to check.
	 * @return An array containing all the sets of coordinates which are
	 *         adjacent to this set in a certain {@link Base} but are not
	 *         necessarily in that same {@link Base}.
	 */
	public Coordinates[] getAllSurrounding(Base b)
	{
		return new Coordinates[]
		                       { this.addDir(Dir.UP), this.addDir(Dir.LEFT), this.addDir(Dir.RIGHT), this.addDir(Dir.DOWN), this.addDir(new Dir(1, 1)), this.addDir(new Dir(-1, 1)), this.addDir(new Dir(1, -1)), this.addDir(new Dir(-1, -1)) };
	}

	/**
	 * Checks whether all the adjacent sets of coordinates contain a
	 * {@link GrameObject} in a certain {@link Base}.
	 * 
	 * @param b
	 *            {@link Base} in which to check.
	 * @return True if all the adjacent sets of coordinates contain a
	 *         {@link GrameObject}, else false.
	 */
	public boolean isSurrounded(Base b)
	{
		Coordinates[] sur = getSurrounding(b);
		for (Coordinates pos : sur)
			if (!b.isOccupied(pos))
				return false;
		return true;
	}
}
