package com.moomoohk.Grame.Core;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;

/**
 * Handles keyboard input.
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class InputHandler implements KeyEventDispatcher
{
	/**
	 * Boolean array representing all the key combinations.
	 */
	public boolean[] key = new boolean[68836];

	/**
	 * Constructor.
	 */
	public InputHandler()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		GrameUtils.print("Input Handler added", MessageLevel.DEBUG);
	}

	/**
	 * Resets the key array.
	 */
	public void resetKeys()
	{
		for (int i = 0; i < key.length; i++)
			key[i] = false;
	}

	public boolean dispatchKeyEvent(KeyEvent e)
	{
		if (GrameUtils.console.hasFocus()||GrameUtils.console.isFocusOwner())
			return false;
		int keyCode = e.getKeyCode();
		switch (e.getID())
		{
		case KeyEvent.KEY_PRESSED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = true;
			GrameUtils.print("Key pressed: " + e.getKeyCode(), MessageLevel.SPAM);
			break;
		case KeyEvent.KEY_RELEASED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = false;
			GrameUtils.print("Key released: " + e.getKeyCode(), MessageLevel.SPAM);
			break;
		case KeyEvent.KEY_TYPED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = true;
			GrameUtils.print("Key typed: " + e.getKeyCode(), MessageLevel.SPAM);
			break;
		}
		return false;
	}
}
