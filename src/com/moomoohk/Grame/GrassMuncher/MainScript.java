package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameObjectLayer;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MainGrameClass;
import com.moomoohk.Grame.test.SpriteRender;

public class MainScript implements MainGrameClass
{
	public static Base b;
	public static Player p;
	public static Chaser c;
	public static int maxCoins = 0;

	public static void main(String[] args)
	{
		GrameManager.initialize(new MainScript());
	}

	public static void win()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				GrameManager.pauseAllGrameObjects(true);
				JOptionPane.showMessageDialog(new JFrame(), "You win!", "", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		});
	}

	public static void lose()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				GrameManager.pauseAllGrameObjects(true);
				JOptionPane.showMessageDialog(new JFrame(), "You lose!\nYour score: " + p.getPoints(), "", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
		});
	}

	@Override
	public void newGame()
	{
		b = new Base(20, 20);
		p = new Player();
		c = new Chaser();
		GrameManager.pauseAllGrameObjects(true);
		c.setTarget(p);
		c.setSpeed(20);
		p.setSpeed(5);
		b.addGrameObjectLayer(new GrameObjectLayer(b.getColumns(), b.getRows()), 1);
		b.addGrameObject(p, new Coordinates(0, 0), 0);
		b.addGrameObject(c, new Coordinates(b.getColumns() - 1, b.getRows() - 1), 1);
		b.setFloorColor(new Color(139, 69, 19));
		for (int i = 1; i <= 339; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp) || temp.equals(new Coordinates(0, 0)) || temp.equals(new Coordinates(b.getColumns() - 1, b.getRows() - 1)))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(new Coin(), temp, 0);
			maxCoins++;
		}
		for (int i = 1; i <= 60; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(b);
			while (b.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(b);
			b.addGrameObject(new Wall(), temp, 0);
			b.addGrameObject(new Wall(), temp, 1);
		}
		SpriteRender.objects.put(p.ID, "player");
		SpriteRender.objects.put(c.ID, "monster");
		RenderManager.render(b.ID, new SpriteRender());
		RenderManager.setVisible(true);
		JOptionPane.showMessageDialog(new JFrame(), "Ready to begin?", "Grass Muncher!", JOptionPane.PLAIN_MESSAGE);
		GrameManager.pauseAllGrameObjects(false);
		//		c.pause(true);
	}

	@Override
	public String getGameName()
	{
		return "Grass Muncher";
	}
}
