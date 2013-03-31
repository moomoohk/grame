package com.moomoohk.Grame.Essentials;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Graphics.RenderManager;

public class InputHandler implements KeyEventDispatcher
{
	public boolean[] key = new boolean[68836];

	public InputHandler()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	public void resetKeys()
	{
		for (int i = 0; i < key.length; i++)
			key[i] = false;
	}

	public boolean dispatchKeyEvent(KeyEvent e)
	{
		if (!RenderManager.mainFrame.isFocusOwner() && !RenderManager.mainFrame.hasFocus())
			return false;
		int keyCode = e.getKeyCode();
		switch (e.getID())
		{
		case KeyEvent.KEY_PRESSED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = true;
			GrameUtils.print("Key pressed: " + e.getKeyCode(), MessageLevel.DEBUG);
			break;
		case KeyEvent.KEY_RELEASED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = false;
			GrameUtils.print("Key released: " + e.getKeyCode(), MessageLevel.DEBUG);
			break;
		case KeyEvent.KEY_TYPED:
			if ((keyCode > 0) && (keyCode < key.length))
				key[keyCode] = true;
			GrameUtils.print("Key pressed: " + e.getKeyCode(), MessageLevel.DEBUG);
			break;
		}
		return false;
	}

}
