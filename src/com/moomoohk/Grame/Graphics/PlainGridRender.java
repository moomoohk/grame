
package com.moomoohk.Grame.Graphics;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Interfaces.Render;

public class PlainGridRender implements Render
{
	public int[] getPixels(int[] pixels, Base b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				Coordinates currSquare = new Coordinates((x ) / ((width) /( b.getColumns())), (y ) / (height / b.getRows()));
				if(currSquare.getX()>=b.getColumns())
					currSquare.setX(b.getColumns()-1);
				if( currSquare.getY()>=b.getRows())
					currSquare.setY(b.getRows()-1);
				if (b.isInMap(currSquare))
				try
				{
					pixels[x + y * width] = b.getColor(currSquare).getRGB();
				}
				catch(Exception e)
				{
					System.out.println(x+" "+width);
				}
			}
		return pixels;
	}

	public String getName()
	{
		return "Plain_grid_render";
	}
	
}

