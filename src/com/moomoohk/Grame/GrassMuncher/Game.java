package com.moomoohk.Grame.GrassMuncher;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObjectLayer;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.CleanGridRender;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

public class Game implements MainGrameClass
{
	public static Grid g;
	public static Player p;
	public static Chaser c;
	public static int maxCoins = 0;

	public static void main(String[] args)
	{
		GrameManager.initialize(new Game());
	}

	// Until I implement endgame state detection into the engine this will have to do
	public static void win()
	{
		// Without invokeLater this code will run on the game thread (instead of the Swing EDT) which causes graphical sync issues
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GrameManager.pauseAllGrameObjects(true);
				JOptionPane.showMessageDialog(new JFrame(), "You win!", "", JOptionPane.PLAIN_MESSAGE);
				GrameManager.reset();
			}
		});
	}

	public static void lose()
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				GrameManager.pauseAllGrameObjects(true);
				JOptionPane.showMessageDialog(new JFrame(), "You lose!\nYour score: " + p.getPoints(), "", JOptionPane.PLAIN_MESSAGE);
				GrameManager.reset();
			}
		});
	}

	@Override
	public void newGame()
	{
		g = new Grid(20, 20);
		p = new Player();
		c = new Chaser();
		GrameManager.pauseAllGrameObjects(true);
		c.setTarget(p);
		c.setSpeed(20);
		p.setSpeed(5);
		g.addGrameObjectLayer(new GrameObjectLayer(g.getColumns(), g.getRows()), 1);
		g.addGrameObject(p, new Coordinates(0, 0), 0);
		g.addGrameObject(c, new Coordinates(g.getColumns() - 1, g.getRows() - 1), 1);
		g.setFloorColor(new Color(139, 69, 19));
		for (int i = 1; i <= 339; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp) || temp.equals(new Coordinates(0, 0)) || temp.equals(new Coordinates(g.getColumns() - 1, g.getRows() - 1)))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(new Coin(), temp, 0);
			maxCoins++;
		}
		for (int i = 1; i <= 60; i++)
		{
			Coordinates temp = GrameUtils.randomCoordinates(g);
			while (g.isOccupied(temp))
				temp = GrameUtils.randomCoordinates(g);
			g.addGrameObject(new Wall(), temp, 0);
			g.addGrameObject(new Wall(), temp, 1);
		}
		//		SpriteRender.objects.put(p.ID, "player");
		//		SpriteRender.objects.put(c.ID, "monster");
		//		RenderManager.render(g.ID, new SpriteRender());
		RenderManager.render(g.ID, new CleanGridRender());
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
