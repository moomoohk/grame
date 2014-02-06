package com.moomoohk.Grame.Core.Graphics;

import java.io.Serializable;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.Render;

/**
 * Renders {@link Grid}s in a grid without grouting lines.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class CleanGridRender implements Render, Serializable
{
	private static final long serialVersionUID = 1269305794830678142L;

	public int[] getPixels(int[] pixels, Grid b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				Coordinates currSquare = new Coordinates((x) / ((width) / (b.getColumns())), (y) / (height / b.getRows()));
				if (currSquare.getX() >= b.getColumns())
					currSquare.setX(b.getColumns() - 1);
				if (currSquare.getY() >= b.getRows())
					currSquare.setY(b.getRows() - 1);
				if (b.isInGrid(currSquare))
					try
					{
						pixels[x + y * width] = b.getColor(currSquare).getRGB();
					}
					catch (Exception e)
					{
						System.out.println(x + " " + width);
					}
			}
		return pixels;
	}

	public String getName()
	{
		return "Clean_grid_render";
	}
}
