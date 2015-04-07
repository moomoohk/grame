package com.moomoohk.Grame.Core.Graphics;

import java.awt.Color;
import java.io.Serializable;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.Render;

/**
 * Render {@link Grid}s in a grid with grouting lines.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-04-05
 */
public class DefaultGridRender implements Render, Serializable
{
	private static final long serialVersionUID = 2057716349989082233L;

	public int[] getPixels(int[] pixels, Grid b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				int squareW = (width) / (b.getColumns()), squareH = (height / b.getRows());
				if (squareH == 0)
				{
					pixels[x + y * width] = Color.black.getRGB();
					continue;
				}
				int pixelX = (x) / (squareW), pixelY = y / squareH;
				Coordinates currSquare = new Coordinates(pixelX, pixelY);
				if (b.isInGrid(currSquare))
					pixels[x + y * width] = b.getColor(currSquare).getRGB();
				if (x % (width / b.getColumns()) == 0 || (x + 1) % (width / b.getColumns()) == 0 || y % (height / b.getRows()) == 0 || (y + 1) % (height / b.getRows()) == 0)
					pixels[x + y * width] = Color.black.getRGB();
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
		return "Default_grid_render";
	}

}
