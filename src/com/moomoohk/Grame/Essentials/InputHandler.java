
package com.moomoohk.Grame.Essentials;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;


public class InputHandler implements KeyEventDispatcher, FocusListener
{
	public boolean[] key=new boolean[68836];
	public static boolean isFocused=false;
	
	public InputHandler()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}
	public void focusGained(FocusEvent e)
	{
		isFocused=true;
	}

	
	public void focusLost(FocusEvent e)
	{
		isFocused=false;
		for(int i=0; i<key.length; i++)
			key[i]=false;
	}


	public boolean dispatchKeyEvent(KeyEvent e)
	{
		/*System.out.println(e.getID());
		System.out.println("Key pressed: "+KeyEvent.KEY_PRESSED);
		System.out.println("Key released: "+KeyEvent.KEY_RELEASED);
		System.out.println("Key typed: "+KeyEvent.KEY_TYPED);*/
		int keyCode=e.getKeyCode();
		switch(e.getID())
		{
		case KeyEvent.KEY_PRESSED:
			if((keyCode>0)&&(keyCode<key.length))
				key[keyCode]=true;
			GrameUtils.print("Key pressed: "+e.getKeyCode(), "Input Handler", true);
			break;
		case KeyEvent.KEY_RELEASED:
			if((keyCode>0)&&(keyCode<key.length))
				key[keyCode]=false;
			GrameUtils.print("Key released: "+e.getKeyCode(), "Input Handler", true);
			break;
		case KeyEvent.KEY_TYPED:
			if((keyCode>0)&&(keyCode<key.length))
				key[keyCode]=true;
			GrameUtils.print("Key pressed: "+e.getKeyCode(), "Input Handler", true);
			break;
		}
		return false;
	}

}

