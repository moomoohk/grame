
package com.moomoohk.Grame.Interfaces;

import com.moomoohk.Grame.Essentials.Base;

/**
 * Objects to render {@link Base}s.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public interface Render
{
	/**
	 * Method to calculate the pixels.
	 * @param pixels An int array of pixels.
	 * @param b The {@link Base} to render.
	 * @param width The width of the main canvas.
	 * @param height The height of the main canvas.
	 * @return The pixel array, edited with the new render pixels.
	 */
	public int[] getPixels(int[] pixels, Base b, int width, int height);
	/**
	 * The name of this render.
	 * @return The name of this render.
	 */
	public String getName();
}

