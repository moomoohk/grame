package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.util.ArrayList;

import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class Base
{
	private ArrayList<GrameObjectLayer> pos;
	private ColorLayer floor;
	private Color[] colors;
	private ArrayList<MovementAI> AIs;
	private int width, height, coinLayer, entLayer;
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
		this.pos = new ArrayList<GrameObjectLayer>();
		this.colors = new Color[width * height];
		this.floor = new ColorLayer(width, height);
		this.floor.setAll(floorColor);
		this.AIs = new ArrayList<MovementAI>();
		this.width = width;
		this.height = height;
		
		for(int x=0; x<width; x++)
			for(int y=0; y<height; y++)
				floor.setColor(new Coordinates(x, y), GrameUtils.randomColor());

		this.pos.add(new GrameObjectLayer(width, height)); // coin layer
		this.coinLayer = 0;
		this.pos.add(new GrameObjectLayer(width, height)); // entity layer
		this.entLayer = 1;
		this.title = title;
		this.ID = GrameManager.add(this);
	}

	public void tick()
	{
		for(int x=0; x<width; x++)
			for(int y=0; y<height; y++)
			{
				
			}
	}
	private Color calcColor(Coordinates pos, int i)
	{
		if(i==0)
			return floor.getColor(pos);
		GrameObject temp=GrameManager.findBase(ID).pos.get(i).getObject(pos);
		Color front=Color.white;
		if(temp!=null)
			front=temp.getColor();
	}
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
		return this.floor.getColor(pos);
		//return this.colors[pos.x + pos.y + width];
	}

	public void addGOLayer(GrameObjectLayer gol, int place)
	{
		if (gol.getHeight() == this.height && gol.getWidth() == this.width)
		{
			GrameManager.findBase(ID).pos.add(place, gol);
			if (place < GrameManager.findBase(ID).entLayer)
			{
				if (place < GrameManager.findBase(ID).coinLayer)
					GrameManager.findBase(ID).entLayer++;
				GrameManager.findBase(ID).coinLayer++;
			}
			GrameUtils.print("Added layer", "Base ID:" + ID, true);
		}
		else
		{
			GrameUtils.print("Incompatible layer! (Wrong dimensions: is " + gol.toString() + ", should be " + this.width + "x" + this.height + ")", "Base ID:" + ID, false);
			return;
		}
	}

	public void setFloorColor(Color c)
	{
		GrameManager.findBase(ID).floor.setAll(c);
	}

	public void setFloorColor(Coordinates pos, Color c)
	{
		GrameManager.findBase(ID).floor.setColor(pos, c);
	}

	public boolean containsEnt(int eID)
	{
		for (GrameObjectLayer gol : GrameManager.findBase(ID).pos)
			if (gol.contains(eID))
				return true;
		return false;
	}

	public Coordinates getEntPos(int eID)
	{
		return GrameManager.findBase(ID).pos.get(GrameManager.findBase(ID).entLayer).getObjectPos(eID);
	}

}
