package com.moomoohk.Grame.test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.Render;

public class NewSpriteRender implements Render
{
	public static String floorPath;
	
	@Override
	public int[] getPixels(int[] pixels, Grid g, int width, int height)
	{
		for (int xSquare = 0; xSquare < g.getColumns(); xSquare++)
			for (int ySquare = 0; ySquare < g.getRows(); ySquare++)
			{
				try
				{
					drawSprite(xSquare, ySquare, width, height, pixels, g, sprites.get("grass"));
					for (int layer = 0; layer < g.getLayerCount(); layer++)
						if (g.getGrameObject(new Coordinates(xSquare, ySquare), layer) != null && objects.containsKey(g.getGrameObject(new Coordinates(xSquare, ySquare), layer).ID))
							drawSprite(xSquare, ySquare, width, height, pixels, g, sprites.get(objects.get(g.getGrameObject(new Coordinates(xSquare, ySquare), layer).ID)));
						else
							if (g.getGrameObject(new Coordinates(xSquare, ySquare), layer) != null)
								drawSquare(xSquare, ySquare, width, height, pixels, g, g.getGrameObject(new Coordinates(xSquare, ySquare), layer).getColor());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		return pixels;
	}

	private void drawSprite(int xSquare, int ySquare, int width, int height, int[] pixels, Grid g, BufferedImage sprite)
	{
		for (int y = 0; y < sprite.getHeight(); y++)
		{
			int yPix = y + (ySquare * sprite.getHeight());
			if ((yPix < 0) || (yPix >= height))
				continue;
			for (int x = 0; x < sprite.getHeight(); x++)
			{
				int xPix = x + (xSquare * sprite.getWidth());
				if ((xPix < 0) || (xPix >= width))
					continue;
				int alpha = sprite.getRGB(x, y);
				if ((alpha >> 24) != 0x00)
					pixels[xPix + yPix * width] = alpha;
			}
		}
	}

	private void drawSquare(int xSquare, int ySquare, int width, int height, int[] pixels, Grid g, Color c)
	{
		for (int y = 0; y < 30; y++)
		{
			int yPix = y + (ySquare * 30);
			if ((yPix < 0) || (yPix >= height))
				continue;
			for (int x = 0; x < 30; x++)
			{
				int xPix = x + (xSquare * 30);
				if ((xPix < 0) || (xPix >= width))
					continue;
				int alpha = c.getRGB();
				if ((alpha >> 24) != 0x00)
					pixels[xPix + yPix * width] = alpha;
			}
		}
	}

	@Override
	public String getName()
	{
		return "Sprite Render";
	}

}
