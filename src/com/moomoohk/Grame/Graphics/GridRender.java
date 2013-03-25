package com.moomoohk.Grame.Graphics;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Interfaces.Render;

public class GridRender implements Render
{
	public int[] getPixels(int[] pixels, Base b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				int squareW = (width) / (b.getColumns()), squareH = (height / b.getRows());
				if(squareH==0)
				{
					pixels[x+y*width]=Color.black.getRGB();
					continue;
				}
				int pixelX = (x) / (squareW), pixelY = y / squareH;
				Coordinates currSquare = new Coordinates(pixelX, pixelY);
				if (b.isInMap(currSquare))
				{
					pixels[x + y * width] = b.getColor(currSquare).getRGB();
				}
				if (x % (width / b.getColumns()) == 0 || (x + 1) % (width / b.getColumns()) == 0 || y % (height / b.getRows()) == 0 || (y + 1) % (height / b.getRows()) == 0)
				{
					pixels[x + y * width] = Color.black.getRGB();
				}
				/*else
				{
					int squareW = (width) / (b.getColumns()), squareH = (height / b.getRows());
					int pixelX = (x) / (squareW), pixelY = y / squareH;
					Coordinates currSquare = new Coordinates(pixelX, pixelY);
					if (b.isInMap(currSquare))
					{
						// pixels[x + y * width] =
						// b.getColor(currSquare).getRGB();
						pixels[x + y * width] = Color.red.getRGB();
					}
				}*/
			}
		return pixels;
	}

	public String getName()
	{
		return "Grid_render";
	}

}
