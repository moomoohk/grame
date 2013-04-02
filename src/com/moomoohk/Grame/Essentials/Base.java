package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.util.ArrayList;

import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.GrameObject;

public class Base
{
	private ArrayList<GrameObjectLayer> layers;
	private ColorLayer floor;
	private Color[] colors;
	private int width, height;
	public int goLayer;
	private boolean wraparound;
	public final int ID;
	private String title;

	public Base(int width, int height)
	{
		this(width, height, Color.white, "");
	}

	public Base(int width, int height, String title)
	{
		this(width, height, Color.white, title);
	}

	public Base(int width, int height, Color floorColor)
	{
		this(width, height, floorColor, "");
	}

	public Base(int width, int height, Color floorColor, String title)
	{
		this.layers = new ArrayList<GrameObjectLayer>();
		this.colors = new Color[width * height];
		this.floor = new ColorLayer(width, height);
		this.floor.setAll(floorColor);
		this.width = width;
		this.height = height;

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				this.colors[x + y * width] = this.floor.getColor(new Coordinates(x, y));
		this.layers.add(new GrameObjectLayer(width, height)); 
		this.goLayer = 0;
		this.setTitle(title);
		this.wraparound = false;
		this.ID = GrameManager.add(this);
	}

	public void tick()
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				// this.colors[x + y * width] = calcColor(new Coordinates(x, y),
				// pos.size() - 1);
				this.colors[x + y * width] = calcColor(new Coordinates(x, y));
			}
	}

	private Color calcColor(Coordinates pos)
	{
		for (int i = this.layers.size() - 1; i >= 0; i--)
			if (this.layers.get(i).getObject(pos) != null)
				return this.layers.get(goLayer).getObject(pos).getColor();
		return floor.getColor(pos);
	}

	/*
	 * private Color calcColor(Coordinates pos, int i) { if (i == 0) return
	 * floor.getColor(pos); GrameObject temp =
	 * this.pos.get(i).getObject(pos); Color front =
	 * this.floor.getColor(pos); if (temp != null) front = temp.getColor();
	 * return front; }
	 */

	public int getRows()
	{
		return this.height;
	}

	public int getColumns()
	{
		return this.width;
	}

	public boolean isInMap(Coordinates pos)
	{
		return (pos.y >= 0) && (pos.y <= this.height - 1) && (pos.x >= 0) && (pos.x <= this.width - 1);
	}

	public Color getColor(Coordinates pos)
	{
		return this.colors[pos.x + pos.y * width];
	}

	public void addGrameObjectLayer(GrameObjectLayer gol, int place)
	{
		if (gol.getHeight() == this.height && gol.getWidth() == this.width)
		{
			this.layers.add(place, gol);
			if (place <= this.goLayer)
				this.goLayer++;
			GrameUtils.print("Added layer "+gol.toString()+" to Base ID:"+ID, MessageLevel.DEBUG);
		}
		else
		{
			GrameUtils.print("Incompatible layer! (Wrong dimensions: is " + gol.toString() + ", should be " + this.width + "x" + this.height + ")", MessageLevel.ERROR);
			return;
		}
	}

	public void setFloorColor(Color c)
	{
		this.floor.setAll(c);
	}

	public void setFloorColor(Coordinates pos, Color c)
	{
		this.floor.setColor(pos, c);
	}

	public void addGrameObject(GrameObject go, Coordinates pos, int layer)
	{
		if(layer>=this.layers.size())
		{
			GrameUtils.print("Layer "+layer+" out of range ("+(this.layers.size()-1)+")!", MessageLevel.ERROR);
			return;
		}
		if (this.layers.get(layer).setObject(pos, go))
			calcColor(pos);
	}

	public void addGrameObject(GrameObject go, Coordinates pos)
	{
		addGrameObject(go, pos, goLayer);
	}

	public boolean containsGrameObject(int goID)
	{
		for (GrameObjectLayer gol : this.layers)
			if (gol.contains(goID))
				return true;
		return false;
	}

	public Coordinates getGrameObjectPos(int eID)
	{
		return this.layers.get(this.goLayer).getObjectPos(eID);
	}

	public GrameObjectLayer getGOLayer()
	{
		return this.layers.get(goLayer);
	}

	public void moveGrameObject(int goID, Coordinates pos)
	{
		for (int i = 0; i < this.layers.size(); i++)
			if (this.layers.get(i).contains(goID) && (this.layers.get(i).getObject(pos) == null || !this.layers.get(i).getObject(pos).isCollidable()))
			{
				Coordinates prev = this.layers.get(i).getObjectPos(goID);
				this.layers.get(i).setObject(prev, null);
				calcColor(prev);
				this.layers.get(i).setObject(pos, GrameManager.findGrameObject(goID));

				GrameManager.findGrameObject(goID).setPos(ID, pos);
				calcColor(pos);
			}
	}

	public boolean isOccupied(Coordinates pos)
	{
		for (int i = 0; i < this.layers.size(); i++)
			if (this.layers.get(i).getObject(pos) != null)
				return true;
		return false;
	}

	public void setWraparound(boolean f)
	{
		this.wraparound = f;
	}

	public boolean getWraparound()
	{
		return this.wraparound;
	}

	public int getDiagonal()
	{
		return Math.max(new Coordinates(0, 0).distance(new Coordinates(this.getColumns() - 1, this.getRows() - 1)), new Coordinates(this.getColumns() - 1, 0).distance(new Coordinates(0, this.getRows() - 1)));
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getTitle()
	{
		return title;
	}
	
	public String toString()
	{
		String st="Base ID:"+ID+"\n";
		st+="Contains: "+this.layers.size()+" Grame Object layers.\n";
		st+="Contains ";
		int count=0;
		for(int i=0; i<this.layers.size(); i++)
			count+=this.layers.get(i).getTotalObjects();
		st+=count+" Grame Objects.";
		return st;
	}
}
