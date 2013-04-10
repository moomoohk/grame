
package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Interfaces.Render;

public class SpriteRender implements Render
{

	@Override
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
				//imageX=width%x;
				//imageY=height%y;
				if (b.isInBase(currSquare))
				{
					try
					{
						pixels[x + y * width] = b.getColor(currSquare).getRGB();
					}
					catch(Exception e)
					{
						System.out.println(x+" "+width);
					}
				}
			}
		return pixels;
	}

	@Override
	public String getName()
	{
		return "Sprite render";
	}

}

