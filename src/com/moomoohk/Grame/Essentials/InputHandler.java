
package com.moomoohk.Grame.Essentials;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener
{
	public boolean[] key=new boolean[68836];
	public static int mouseX;
	public static int mouseY;
	public static int mouseDx;
	public static int mouseDy;
	public static int mousePx;
	public static int mousePy;
	public static int mouseButton=0;
	public static boolean dragged=false;
	public static boolean isFocused=false;
	public void mouseDragged(MouseEvent e)
	{
		dragged=true;
		mouseDx=e.getX();
		mouseDy=e.getY();
	}

	public void mouseMoved(MouseEvent e)
	{
		mouseX=e.getX();
		mouseY=e.getY();
	}

	public void mouseClicked(MouseEvent e)
	{
		
	}
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	
	public void mousePressed(MouseEvent e)
	{
		mouseButton=e.getButton();
		mousePx=e.getX();
		mousePy=e.getY();
	}

	
	public void mouseReleased(MouseEvent e)
	{
		dragged=false;
		mouseButton=0;
	}

	
	public void focusGained(FocusEvent e)
	{
		// TODO Auto-generated method stub
		isFocused=true;
	}

	
	public void focusLost(FocusEvent e)
	{
		isFocused=false;
		for(int i=0; i<key.length; i++)
			key[i]=false;
	}

	
	public void keyPressed(KeyEvent e)
	{
		int keyCode=e.getKeyCode();
		if((keyCode>0)&&(keyCode<key.length))
			key[keyCode]=true;
		
	}

	
	public void keyReleased(KeyEvent e)
	{
		int keyCode=e.getKeyCode();
		if((keyCode>0)&&(keyCode<key.length))
			key[keyCode]=false;
	}

	
	public void keyTyped(KeyEvent e)
	{
		
	}

}

