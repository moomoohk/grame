package com.moomoohk.Grame.test;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class MyFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class MyDispatcher implements KeyEventDispatcher
	{
		public boolean dispatchKeyEvent(KeyEvent e)
		{
			if (e.getID() == KeyEvent.KEY_PRESSED)
			{
				System.out.println("tester");
			}
			else
				if (e.getID() == KeyEvent.KEY_RELEASED)
				{
					System.out.println("2test2");
				}
				else
					if (e.getID() == KeyEvent.KEY_TYPED)
					{
						System.out.println("3test3");
					}
			return false;
		}
	}

	public MyFrame()
	{
		add(new JTextField());
		System.out.println("test");
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());
	}

	public static void main(String[] args)
	{
		MyFrame f = new MyFrame();
		f.pack();
		f.setVisible(true);
	}
}
