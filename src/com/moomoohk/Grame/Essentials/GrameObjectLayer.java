package com.moomoohk.Grame.Essentials;

import com.moomoohk.Grame.Interfaces.GrameObject;

public class GrameObjectLayer
{
	private GrameObject[] objects;
	private int width, height;

	public GrameObjectLayer(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.objects=new GrameObject[width*height];
	}

	public void setObject(Coordinates pos, GrameObject go)
	{
		int place = pos.x + pos.y * this.width;
		if (place < 0 || place >= this.objects.length)
			GrameUtils.print(pos.toString() + " is out of range. Returning.", "Layer Class", false);
		this.objects[place] = go;
	}
	
	public GrameObject getObject(Coordinates pos)
	{
		return this.objects[pos.x+pos.y*width];
	}
	
	public Coordinates getObjectPos(int goID)
	{
		for(int i=0; i<this.objects.length; i++)
			if(this.objects[i].getID()==goID)
				return new Coordinates((i/width)+1, (i%width)-1);
		GrameUtils.print("Couldn't locate GrameObject with ID:"+goID+". Returning null.", "Layer Class", false);
		return null;
	}
	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public boolean contains(int eID)
	{
		for(int i=0; i<this.objects.length; i++)
				if(objects[i].getID()==eID)
					return true;
		return false;
	}
}