package com.moomoohk.Grame.Core.GUI;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.Timer;

import com.moomoohk.Grame.test.MenuConfiguration;

public class MainMenuButton extends JButton
{
	private static final long serialVersionUID = -2192610213120657509L;

	public boolean mouseOn = false, mouseDown = false;
	private double animTime = 0;
	private MenuConfiguration menuConfiguration;
	private Color startColor, endColor, clickColor, fill;
	private String helpText;
	private Timer t = new Timer(10, new ActionListener()
	{
		public void actionPerformed(ActionEvent arg0)
		{
			repaint();
			animTime += 0.03;
		}
	});

	public MainMenuButton(String text)
	{
		this(text, Color.black, Color.white, Color.gray, "Default", new MenuConfiguration());
	}

	public MainMenuButton(String text, String helpText)
	{
		this(text, Color.black, Color.white, Color.gray, helpText, new MenuConfiguration());
	}

	public MainMenuButton(String text, MenuConfiguration menuConfiguration)
	{
		this("Default", Color.black, Color.white, Color.gray, "Default", menuConfiguration);
	}

	public MainMenuButton(String text, Color startColor, Color endColor, Color clickColor, String helpText, MenuConfiguration menuConfiguration)
	{
		super(text);
		this.startColor = startColor;
		this.endColor = endColor;
		this.clickColor = clickColor;
		this.fill = this.startColor;
		this.helpText = helpText;
		this.menuConfiguration = menuConfiguration;
		addMouseListener(new MouseAdapter()
		{
			public void mouseReleased(MouseEvent arg0)
			{
				mouseDown = false;
				repaint();
				t.stop();
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
				mouseDown = true;
				repaint();
				animTime = 0;
				t.start();
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
				mouseOn = false;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				mouseOn = true;
				repaint();
				animTime = 0;
				t.start();
			}
		});
	}

	public void setStartColor(Color c)
	{
		this.startColor = c;
	}

	public void setEndColor(Color c)
	{
		this.endColor = c;
	}

	public void setClickColor(Color c)
	{
		this.clickColor = c;
	}

	public void setHelpText(String helpText)
	{
		this.helpText = helpText;
	}

	public String getHelpText()
	{
		return this.helpText;
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (isEnabled())
		{
			if (mouseDown && mouseOn)
				fill = clickColor;
			else
				if (mouseOn)
					fill = new Color((int) (endColor.getRed() * Math.abs(Math.sin(animTime)) + startColor.getRed() * (1 - Math.abs(Math.sin(animTime)))), (int) (endColor.getGreen() * Math.abs(Math.sin(animTime)) + startColor.getGreen() * (1 - Math.abs(Math.sin(animTime)))),
							(int) (endColor.getBlue() * Math.abs(Math.sin(animTime)) + startColor.getBlue() * (1 - Math.abs(Math.sin(animTime)))));
				else
				{
					fill = new Color((int) (fill.getRed() * Math.abs(Math.sin(animTime)) + startColor.getRed() * (1 - Math.abs(Math.sin(animTime)))), (int) (fill.getGreen() * Math.abs(Math.sin(animTime)) + startColor.getGreen() * (1 - Math.abs(Math.sin(animTime)))), (int) (fill.getBlue()
							* Math.abs(Math.sin(animTime)) + startColor.getBlue() * (1 - Math.abs(Math.sin(animTime)))));
					if (fill.equals(startColor))
					{
						t.stop();
						if (mouseDown)
							fill = clickColor;
						else
							fill = startColor;
					}
				}
			g2.setPaint(fill);
		}
		else
			g2.setPaint(menuConfiguration.disabledButtonColor);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
		g2.setPaint(Color.black);
		if (mouseOn && isEnabled())
			g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 6, 6);
		else
			g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 6, 6);
		g2.setPaint(Color.white);
		FontMetrics fm = g2.getFontMetrics();
		g2.drawString(getText(), (getWidth() / 2) - (fm.stringWidth(getText()) / 2), (getHeight() / 2) + 4);
		g2.dispose();
	}
}
