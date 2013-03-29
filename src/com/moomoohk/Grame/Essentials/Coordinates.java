package com.moomoohk.Grame.Essentials;

import java.util.ArrayList;
import java.util.Random;

public class Coordinates
{
	protected int x;
	protected int y;

	public Coordinates(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Coordinates()
	{
		this.x = 0;
		this.y = 0;
	}

	public Coordinates(Base b)
	{
		this.x = new Random(b.getColumns()).nextInt();
		this.y = new Random(b.getRows()).nextInt();
	}

	public int getX()
	{
		return this.x;
	}

	public int getY()
	{
		return this.y;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Coordinates addDir(Dir d)
	{
		int newX = this.x + d.getX();
		int newY = this.y + d.getY();
		return new Coordinates(newX, newY);
	}

	public int distance(Coordinates c)
	{
		return Math.abs((int) Math.sqrt(Math.pow(c.getX() - getX(), 2.0D) + Math.pow(c.getY() - getY(), 2.0D)));
	}

	public String toString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}

	public Coordinates[] getSurrounding(Base b)
	{
		ArrayList<Coordinates> sur = new ArrayList<Coordinates>();
		if (b.isInMap(this.addDir(Dir.UP)) && !b.isOccupied(this.addDir(Dir.UP)))
			sur.add(this.addDir(Dir.UP));
		if (b.isInMap(this.addDir(Dir.DOWN)) && !b.isOccupied(this.addDir(Dir.DOWN)))
			sur.add(this.addDir(Dir.DOWN));
		if (b.isInMap(this.addDir(Dir.LEFT)) && !b.isOccupied(this.addDir(Dir.LEFT)))
			sur.add(this.addDir(Dir.LEFT));
		if (b.isInMap(this.addDir(Dir.RIGHT)) && !b.isOccupied(this.addDir(Dir.RIGHT)))
			sur.add(this.addDir(Dir.RIGHT));
		if (b.isInMap(this.addDir(new Dir(1, 1))) && !b.isOccupied(this.addDir(new Dir(1, 1))))
			sur.add(this.addDir(new Dir(1, 1)));
		if (b.isInMap(this.addDir(new Dir(-1, 1))) && !b.isOccupied(this.addDir(new Dir(-1, 1))))
			sur.add(this.addDir(new Dir(-1, 1)));
		if (b.isInMap(this.addDir(new Dir(1, -1))) && !b.isOccupied(this.addDir(new Dir(1, -1))))
			sur.add(this.addDir(new Dir(1, -1)));
		if (b.isInMap(this.addDir(new Dir(-1, -1))) && !b.isOccupied(this.addDir(new Dir(-1, -1))))
			sur.add(this.addDir(new Dir(-1, -1)));
		Coordinates[] temp = new Coordinates[sur.size()];
		for (int i = 0; i < sur.size(); i++)
			temp[i] = sur.get(i);
		return temp;
	}
	public Coordinates[] getAllSurrounding(Base b)
	{
		return new Coordinates[]{this.addDir(Dir.UP),this.addDir( Dir.LEFT), this.addDir(Dir.RIGHT), this.addDir(Dir.DOWN), this.addDir(new Dir(1, 1)), this.addDir(new Dir(-1, 1)), this.addDir(new Dir(1, -1)), this.addDir(new Dir(-1, -1))};
	}

	public boolean isSurrounded(Base b)
	{
		Coordinates[] sur = getSurrounding(b);
		for (Coordinates pos : sur)
			if (!b.isOccupied(pos))
				return false;
		return true;
	}
}
