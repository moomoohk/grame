package com.moomoohk.Grame.Essentials;

public class Dir extends Coordinates
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Dir UP = new Dir("up");
	public static final Dir DOWN = new Dir("down");
	public static final Dir LEFT = new Dir("left");
	public static final Dir RIGHT = new Dir("right");

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

	public boolean isDiag()
	{
		return toString().length() > 5;
	}

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
	public boolean equals(Dir d)
	{
		if(this.x==d.x&&this.y==d.y)
			return true;
		return false;
	}
	public static Dir[] getAllDirs()
	{
		return new Dir[]{UP, DOWN, LEFT, RIGHT, new Dir(1, 1), new Dir(1, -1), new Dir(-1, -1), new Dir(-1, 1)};
	}
}
