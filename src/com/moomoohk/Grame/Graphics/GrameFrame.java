
package com.moomoohk.Grame.Graphics;

import javax.swing.JFrame;

public class GrameFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrameFrame(int width, int height)
	{
		super();
		setBounds(width, height, width, height);
		//setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
	}
}

