package com.moomoohk.Grame.Basics;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;

/**
 * This class is a collection of {@link Wall}s which is arranged in a certain pattern.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Schematic
{
	private Color[][] map;
	private int height;
	private int width;
	private int type;

	/**
	 * Constructor.
	 */
	public Schematic()
	{
		this((int) (Math.random() * 13));
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            1 of 13 premade Schematics.
	 */
	public Schematic(int type)
	{
		this.type = type;
		this.map = new Color[5][5];
		this.height = this.map.length;
		this.width = this.map[0].length;
		for (int i = 0; i < this.map.length; i++)
			for (int j = 0; j < this.map.length; j++)
				this.map[i][j] = null;
		switch (this.type)
		{
		case 0:
			def(2, 0);
			def(2, 1);
			def(1, 2);
			def(2, 2);
			def(3, 2);
			def(2, 3);
			def(2, 4);
			break;
		case 1:
			def(0, 2);
			def(1, 2);
			def(2, 1);
			def(2, 2);
			def(2, 3);
			def(3, 2);
			def(4, 2);
			break;
		case 2:
			def(0, 0);
			def(1, 0);
			def(2, 0);
			def(0, 1);
			def(0, 2);
			break;
		case 3:
			def(0, 0);
			def(0, 1);
			def(0, 2);
			def(0, 3);
			def(1, 0);
			def(2, 0);
			def(3, 0);
			break;
		case 4:
			def(0, 0);
			def(1, 0);
			def(2, 0);
			def(4, 0);
			def(0, 2);
			def(2, 2);
			def(3, 2);
			def(4, 2);
			def(0, 4);
			def(1, 4);
			def(2, 4);
			def(4, 4);
			break;
		case 5:
			def(0, 0);
			def(0, 1);
			def(0, 2);
			def(0, 4);
			def(2, 0);
			def(2, 2);
			def(2, 3);
			def(2, 4);
			def(4, 0);
			def(4, 1);
			def(4, 2);
			def(4, 4);
			break;
		case 6:
			def(0, 0);
			def(1, 0);
			def(2, 0);
			def(3, 0);
			def(4, 0);
			def(2, 1);
			def(2, 2);
			def(2, 3);
			def(0, 4);
			def(1, 4);
			def(2, 4);
			def(3, 4);
			def(4, 4);
			break;
		case 7:
			def(0, 0);
			def(0, 1);
			def(0, 2);
			def(0, 3);
			def(0, 4);
			def(1, 2);
			def(2, 2);
			def(3, 2);
			def(4, 0);
			def(4, 1);
			def(4, 2);
			def(4, 3);
			def(4, 4);
			break;
		case 8:
			def(0, 0);
			def(1, 0);
			def(3, 0);
			def(4, 0);
			def(1, 1);
			def(2, 1);
			def(3, 1);
			def(1, 3);
			def(2, 3);
			def(3, 3);
			def(0, 4);
			def(1, 4);
			def(3, 4);
			def(4, 4);
			break;
		case 9:
			def(0, 0);
			def(0, 1);
			def(0, 3);
			def(0, 4);
			def(1, 1);
			def(1, 2);
			def(1, 3);
			def(3, 1);
			def(3, 2);
			def(3, 3);
			def(4, 0);
			def(4, 1);
			def(4, 3);
			def(4, 4);
			break;
		case 10:
			def(1, 0);
			def(4, 0);
			def(0, 1);
			def(1, 1);
			def(3, 1);
			def(1, 3);
			def(3, 3);
			def(4, 3);
			def(0, 4);
			def(3, 4);
			break;
		case 11:
			def(0, 0);
			def(3, 0);
			def(1, 1);
			def(3, 1);
			def(4, 1);
			def(0, 3);
			def(1, 3);
			def(3, 3);
			def(1, 4);
			def(4, 4);
			break;
		case 12:
			def(0, 0);
			def(1, 0);
			def(3, 0);
			def(4, 0);
			def(0, 1);
			def(4, 1);
			def(2, 2);
			def(0, 3);
			def(4, 3);
			def(0, 4);
			def(1, 4);
			def(3, 4);
			def(4, 4);
			break;
		case 13:
			def(0, 0);
			def(1, 0);
			trig(2, 0);
			def(3, 0);
			def(4, 0);
			def(0, 1);
			def(4, 1);
			trig(0, 2);
			def(0, 3);
			def(4, 3);
			def(0, 4);
			def(1, 4);
			trig(2, 4);
			def(3, 4);
			def(4, 4);
			trig(4, 2);
			break;
		case 14:
			def(1, 1);
			def(2, 1);
			def(3, 1);
			def(1, 2);
			def(3, 2);
			def(1, 3);
			def(2, 3);
			def(3, 3);
			break;
		case 15:
			for (int i = 0; i < this.map.length; i++)
				for (int j = 0; j < this.map.length; j++)
					def(i, j);
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            Width of Schematic.
	 * @param height
	 *            Height of Schematic.
	 */
	public Schematic(int width, int height)
	{
		this.type = 0;
		this.map = new Color[height][width];
		this.height = this.map.length;
		this.width = this.map[0].length;
		for (int i = 0; i < this.map.length; i++)
			for (int j = 0; j < this.map.length; j++)
				this.map[i][j] = Color.WHITE;
	}

	private void def(int row, int col)
	{
		this.map[row][col] = Color.BLACK;
	}

	private void trig(int row, int col)
	{
		this.map[row][col] = Color.cyan;
	}

	/**
	 * Gets the color at a certain {@link Coordinates}.
	 * 
	 * @param c
	 *            {@link Coordinates} from which to get color.
	 * @return The color at the {@link Coordinates}.
	 */
	public Color getColor(Coordinates c)
	{
		return this.map[c.getY()][c.getX()];
	}

	/**
	 * Shows this Schematic in a {@link Base}.
	 * <p>
	 * For debug purposes.
	 */
	public void show()
	{
		Base b = new Base(5, 5, "Schematic " + type);
		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				b.setFloorColor(new Coordinates(j, i), this.map[i][j]);
	}

	/**
	 * Gets the width of this Schematic.
	 * 
	 * @return The width of this Schematic.
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Gets the height of this Schematic.
	 * 
	 * @return The height of this Schematic.
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * Checks if the place at certain {@link Coordinates} are solid or not.
	 * 
	 * @param c
	 *            {@link Coordinates} to check.
	 * @return True if solid, else false.
	 */
	public boolean isSolid(Coordinates c)
	{
		return (isInSchem(c)) && (this.map[c.getY()][c.getX()] == Color.black);
	}

	private boolean isInSchem(Coordinates c)
	{
		return (c.getY() >= 0) && (c.getX() >= 0) && (c.getX() < this.width) && (c.getY() < this.height);
	}

	/**
	 * Rotates Schematics 90 degrees in a given {@link Dir} a given amount of times.
	 * 
	 * @param d
	 *            {@link Dir} to rotate in.
	 * @param amount
	 *            Amount of times to rotate.
	 * @param s
	 *            Schematic to rotate.
	 * @return The rotated Schematic.
	 */
	public static Schematic rotator(Dir d, int amount, Schematic s)
	{
		amount %= 4;
		if (s.width != s.height)
		{
			GrameUtils.print("Sorry, I can't rotate non-square schematics just yet. Returning your schematic.", MessageLevel.ERROR);
			return s;
		}
		if (amount == 0)
			return s;
		GrameUtils.print("Rotating " + d.toString().toLowerCase() + amount + " times.", MessageLevel.DEBUG);
		if (!d.equals(Dir.LEFT))
			if (d.equals(Boolean.valueOf(!d.equals(Dir.RIGHT))))
			{
				GrameUtils.print("Invalid direction! Use only Dir.LEFT or Dir.RIGHT. Returning your schematic.", MessageLevel.ERROR);
				return s;
			}
		if (amount < 0)
		{
			GrameUtils.print("Invalid amount! Use only positive numbers. Returning your schematic.", MessageLevel.ERROR);
			return s;
		}
		Schematic sn = new Schematic(s.getWidth(), s.getHeight());
		for (int i = 0; i < sn.width; i++)
			for (int j = 0; j < sn.height; j++)
			{
				Coordinates c = new Coordinates(0, 0);
				if (d.equals(Dir.LEFT))
					c = new Coordinates(j, sn.width - i - 1);
				else
					c = new Coordinates(i, sn.width - j - 1);
				sn.setColor(c, s.getColor(new Coordinates(i, j)));
			}
		return rotator(d, amount - 1, sn);
	}

	/**
	 * Sets the color at certain {@link Coordinates}.
	 * 
	 * @param pos
	 *            {@link Coordinates} to set.
	 * @param c
	 *            Color to use.
	 */
	public void setColor(Coordinates pos, Color c)
	{
		this.map[pos.getY()][pos.getX()] = c;
	}

	/**
	 * Loads the Schematic into a given {@link Base} at given {@link Coordinates}.
	 * 
	 * @param b
	 *            {@link Base} in which to load this Schematic.
	 * @param loc
	 *            {@link Coordinates} at which to load this Schematic.
	 */
	public void load(Base b, Coordinates loc)
	{
		int sx = 0;
		int sy = 0;
		int mapx = loc.getX();
		int mapy = loc.getY();
		for (int i = loc.getY(); i < loc.getY() + this.height; i++)
		{
			mapy = i;
			for (int j = loc.getX(); j < loc.getX() + this.width; j++)
			{
				mapx = j;
				if (this.map[sy][sx] != null && b.isInBase(new Coordinates(mapx, mapy)) && !b.isOccupied(new Coordinates(mapx, mapy)))
					b.addGrameObject(new Wall(getColor(new Coordinates(sx, sy))), new Coordinates(mapx, mapy));
				sx++;
			}
			sx = 0;
			sy++;
		}
	}

	public String toString()
	{
		String st = "";
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				st += "[";
				if (this.map[i][j] != null)
					st += "x";
				else
					st += " ";
				st += "]";
			}
			st += "\n";
		}
		return st;
	}

}
