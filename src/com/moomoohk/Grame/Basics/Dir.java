package com.moomoohk.Grame.Basics;

import com.moomoohk.Grame.Essentials.Coordinates;

/**
 * Represents directions.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Dir extends Coordinates
{
	private static final long serialVersionUID = -8957814328221236455L;
	/**
	 * Up Dir.
	 */
	public static final Dir UP = new Dir("up");
	/**
	 * Down Dir.
	 */
	public static final Dir DOWN = new Dir("down");
	/**
	 * Left Dir.
	 */
	public static final Dir LEFT = new Dir("left");
	/**
	 * Right Dir.
	 */
	public static final Dir RIGHT = new Dir("right");

	/**
	 * Constructor.
	 * 
	 * @param dir
	 *            Direction as a {@link String} ("up", "down", "left", "right").
	 */
	public Dir(String dir)
	{

		this.x = 0;
		this.y = 0;
		if (dir.equalsIgnoreCase("up"))
			this.y = -1;
		if (dir.equalsIgnoreCase("down"))
			this.y = 1;
		if (dir.equalsIgnoreCase("left"))
			this.x = -1;
		if (dir.equalsIgnoreCase("right"))
			this.x = 1;
	}

	/**
	 * Constructor.
	 * <p>
	 * Will create a Dir which connects between two {@link Coordinates}.
	 * 
	 * @param a
	 *            Point A.
	 * @param b
	 *            Point B.
	 */
	public Dir(Coordinates a, Coordinates b)
	{
		this.x = 0;
		this.y = 0;
		int xa = a.getX();
		int ya = a.getY();
		int xb = b.getX();
		int yb = b.getY();
		if ((xa > xb) && (ya == yb))
			this.x = -1;
		if ((xa < xb) && (ya == yb))
			this.x = 1;
		if ((xa == xb) && (ya < yb))
			this.y = 1;
		if ((xa == xb) && (ya > yb))
			this.y = -1;
		if ((xa > xb) && (ya < yb))
		{
			this.x = -1;
			this.y = 1;
		}
		if ((xa < xb) && (ya > yb))
		{
			this.x = 1;
			this.y = -1;
		}
		if ((xa < xb) && (ya < yb))
		{
			this.x = 1;
			this.y = 1;
		}
		if ((xa > xb) && (ya > yb))
		{
			this.x = -1;
			this.y = -1;
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param x
	 *            X value for this Dir.
	 * @param y
	 *            Y value for this Dir.
	 */
	public Dir(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public String toString()
	{
		String st = "";
		if (this.y == 1)
			st = st + "Down ";
		if (this.y == -1)
			st = st + "Up ";
		if (this.x == 1)
			st = st + "Right ";
		if (this.x == -1)
			st = st + "Left ";
		return st;
	}

	/**
	 * Parses a keyboard key to a Dir.
	 * 
	 * @param key
	 *            Key code of key to parse.
	 * @return A Dir representing the key. If the parse fails, null will be returned.
	 */
	public static Dir parseKey(int key)
	{
		switch (key)
		{
		case 38:
			return UP;
		case 40:
			return DOWN;
		case 39:
			return RIGHT;
		case 37:
			return LEFT;
		}
		return null;
	}

	/**
	 * Checks whether this Dir is diagonal.
	 * 
	 * @return True if this Dir is diagonal, else false.
	 */
	public boolean isDiag()
	{
		return toString().length() > 5;
	}

	/**
	 * Splits diagonal Dirs into two non-diagonal Dirs.
	 * <p>
	 * If this Dir is not diagonal the array that is returned will contain this Dir.
	 * 
	 * @return A Dir[] containing two non-diagonal Dirs.
	 */
	public Dir[] split()
	{
		Dir[] a = new Dir[2];
		if ((this.x == 0) || (this.y == 0))
		{
			a[0] = this;
			a[1] = this;
		}
		else
		{
			a[0] = new Dir(0, this.y);
			a[1] = new Dir(this.x, 0);
		}
		return a;
	}

	/**
	 * Checks if this Dir is equals to another Dir.
	 * 
	 * @param d
	 *            The Dir to compare to.
	 * @return True if the Dirs are equal, else false.
	 */
	public boolean equals(Dir d)
	{
		if (this.x == d.x && this.y == d.y)
			return true;
		return false;
	}

	/**
	 * Return a Dir array containing all 8 possible directions.
	 * 
	 * @return A Dir array containing all 8 possible directions.
	 */
	public static Dir[] getAllDirs()
	{
		return new Dir[] { UP, DOWN, LEFT, RIGHT, new Dir(1, 1), new Dir(1, -1), new Dir(-1, -1), new Dir(-1, 1) };
	}
}
