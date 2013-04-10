
package com.moomoohk.Grame.Graphics;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Interfaces.Render;

/**
 * Randomizes a bunch of pixels.
 * <p>
 * This {@link Render} was made for debug and testing purposes.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class RandomRender implements Render
{
	public int[] getPixels(int[] pixels, Base b, int width, int height)
	{
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = GrameUtils.randomColor().getRGB();
		return pixels;
	}

	public String getName()
	{
		return "Random_render";
	}

}

