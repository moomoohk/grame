package com.moomoohk.Grame.Essentials;

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
		this.x=x;
	}
	public void setY(int y)
	{
		this.y=y;
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
		return "("+this.x+", "+this.y+")";
	}
}
