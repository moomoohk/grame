
package com.moomoohk.Grame.Graphics;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Interfaces.Render;

public class RandomRender implements Render
{

	public int[] getPixels(int[] pixels, Base b, int width, int height)
	{
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = GrameUtils.randomColor().getRGB();
		return pixels;
	}

}

