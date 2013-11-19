package com.moomoohk.Grame.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;

import javax.swing.JFrame;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.Render;

/**
 * Manages all the rendering operations.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class RenderManager
{
	private static HashMap<Integer, Render> renders;
	private static HashMap<Integer, TextLayer> text;
	private static JFrame mainFrame = new JFrame();
	private static Canvas mainCanvas = null;
	private static int mainBase = -1;
	private static boolean drawCoordinates = false;
	private static int squareSize = 30;

	static
	{
		renders = new HashMap<Integer, Render>();
		text = new HashMap<Integer, TextLayer>();
		loadRenders();
		GrameUtils.print("Initialized successfully.", MessageLevel.NORMAL);
	}

	/**
	 * Renders a {@link Base}.
	 * 
	 * @param bID
	 *            The {@link Base#ID} of the {@link Base} to render.
	 */
	public static void render(int bID)
	{
		render(bID, renders.get(bID));
	}

	/**
	 * Renders a {@link Base} using a given {@link Render}.
	 * 
	 * @param bID
	 *            The {@link Base#ID} of the {@link Base} to render.
	 * @param render
	 *            The {@link Render} to use.
	 */
	public static void render(final int bID, Render render)
	{
		if (bID == -1)
			return;
		setupFrame(bID);
		mainBase = bID;
		//		mainFrame.setTitle("Rendering Base number " + mainBase);
		mainFrame.setTitle(GrameManager.getGameName());
		if (render == null)
			render = GrameManager.getDefaultRender();
		renders.put(bID, render);
		BufferStrategy bs = mainCanvas.getBufferStrategy();
		if (bs == null)
		{
			if (mainFrame.isVisible())
				mainCanvas.createBufferStrategy(3);
			return;
		}
		BufferedImage img = new BufferedImage(mainCanvas.getWidth(), mainCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		try
		{
			pixels = render.getPixels(pixels, GrameManager.findBase(bID), mainCanvas.getWidth(), mainCanvas.getHeight());
		}
		catch (Exception e)
		{
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, mainCanvas.getWidth(), mainCanvas.getHeight(), null);

		g.setFont(new Font("LucidaTypewriter", 1, 8));
		int squaresize = mainCanvas.getWidth() / GrameManager.findBase(bID).getColumns(); //TODO: Fix nullpointer
		for (int x = 0; x < GrameManager.findBase(bID).getColumns(); x++)
			for (int y = 0; y < GrameManager.findBase(bID).getRows(); y++)
				if (text.get(bID) != null)
				{
					if (text.get(bID).getText(new Coordinates(x, y)) != null && text.get(bID).getText(new Coordinates(x, y)).trim().length() != 0)
					{
						g.setColor(text.get(bID).getColor(new Coordinates(x, y)));
						g.drawString(text.get(bID).getText(new Coordinates(x, y)), y * squaresize, (x + 1) * (squaresize) - ((squaresize / 2) - 5));
					}
				}

		if (drawCoordinates)
		{
			//			squaresize = mainCanvas.getWidth() / GrameManager.findBase(bID).getColumns();
			g.setColor(Color.blue);
			for (int x = 0; x < GrameManager.findBase(bID).getColumns(); x++)
				for (int y = 0; y < GrameManager.findBase(bID).getRows(); y++)
					g.drawString("(" + y + ", " + x + ")", y * squaresize, (x + 1) * (squaresize) - ((squaresize / 2) - 5));
		}
		g.dispose();
		bs.show();
	}

	/**
	 * Generates a blank {@link Canvas} which is of the required size.
	 * 
	 * @param bID
	 *            {@link Base#ID} of the {@link Base} of which this {@link Canvas} is.
	 * @return A {@link Canvas}.
	 */
	public static Canvas generateCanvas(int bID)
	{
		Canvas c = new Canvas();
		int width = Math.min(squareSize * GrameManager.findBase(bID).getColumns(), Toolkit.getDefaultToolkit().getScreenSize().width), height = Math.min(squareSize * GrameManager.findBase(bID).getRows(), Toolkit.getDefaultToolkit().getScreenSize().height - 50);
		c.setSize(width, height);
		return c;
	}

	/**
	 * Sets up the main window.
	 * 
	 * @param bID
	 *            {@link Base#ID} of the {@link Base} which is being rendered.
	 */
	public static void setupFrame(int bID)
	{
		if (bID != mainBase)
		{
			if (mainCanvas == null)
				mainCanvas = generateCanvas(bID);
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			mainFrame.setResizable(false);
			mainFrame.add(mainCanvas);
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.addFocusListener(new FocusListener()
			{

				public void focusLost(FocusEvent paramFocusEvent)
				{
					GrameManager.getInputHandler().resetKeys();
					//					GrameManager.pauseAllGrameObjects(true);
				}

				public void focusGained(FocusEvent paramFocusEvent)
				{
					//					GrameManager.pauseAllGrameObjects(false);
				}
			});
		}
	}

	/**
	 * Renders the main {@link Base}.
	 */
	public synchronized static void tick()
	{
		render(mainBase, renders.get(mainBase));
		mainFrame.repaint();
	}

	/**
	 * Attaches a {@link Render} to a {@link Base} for future reference.
	 * 
	 * @param bID
	 *            {@link Base#ID} of {@link Base} to attach the {@link Render} to.
	 * @param render
	 *            The {@link Render} to attach.
	 */
	public static void setRender(int bID, Render render)
	{
		renders.put(bID, render);
	}

	/**
	 * Loads some basic {@link Render}s.
	 */
	private static void loadRenders()
	{
		GrameManager.addRender(new GridRender());
		GrameManager.addRender(new PlainGridRender());
	}

	/**
	 * Sets the visibility of the main window.
	 * 
	 * @param f
	 *            True to make the main window visible, else false.
	 */
	public static void setVisible(boolean f)
	{
		mainFrame.setVisible(f);
	}

	/**
	 * Checks whether the main window is visible or not.
	 * 
	 * @return True if the main window is visible, else false.
	 */
	public static boolean isVisible()
	{
		return mainFrame.isVisible();
	}

	/**
	 * Gets the main window.
	 * 
	 * @return The main window.
	 */
	public static JFrame getMainFrame()
	{
		return mainFrame;
	}

	/**
	 * Gets the main {@link Canvas}.
	 * 
	 * @return The main {@link Canvas}.
	 */
	public static Canvas getMainCanvas()
	{
		return mainCanvas;
	}

	/**
	 * Gets the {@link Base#ID} of the main {@link Base}.
	 * 
	 * @return The {@link Base#ID} of the main {@link Base}.
	 */
	public static int getMainBase()
	{
		return mainBase;
	}

	/**
	 * Gets the {@link Render} {@link HashMap}.
	 * 
	 * @return The {@link Render} {@link HashMap}.
	 */
	public static HashMap<Integer, Render> getRenders()
	{
		return renders;
	}

	/**
	 * Sets whether or not to draw coordinates.
	 * <p>
	 * This method was created for debug and testing purposes.
	 * 
	 * @param f
	 *            True to draw coordinates, else false.
	 */
	public static void drawCoordinates(boolean f)
	{
		drawCoordinates = f;
	}

	public static void setText(int bID, Coordinates pos, String text, Color color)
	{
		if (!GrameManager.findBase(bID).isInBase(pos))
			return;
		if (RenderManager.text.get(bID) == null)
			RenderManager.text.put(bID, new TextLayer(GrameManager.findBase(bID).getRows(), GrameManager.findBase(bID).getColumns()));
		RenderManager.text.get(bID).setText(pos, text, color);
	}

	public static void clearAllText()
	{
		for (Integer i : text.keySet())
			clearText(i);
	}

	public static void clearText(int bID)
	{
		text.put(bID, new TextLayer(GrameManager.findBase(bID).getRows(), GrameManager.findBase(bID).getColumns()));
	}

	private static class TextLayer
	{
		private String[][] text;
		private Color[][] color;

		public TextLayer(int row, int col)
		{
			this.text = new String[row][col];
			this.color = new Color[row][col];
		}

		public void setText(Coordinates pos, String text, Color color)
		{
			this.text[pos.getY()][pos.getX()] = text;
			this.color[pos.getY()][pos.getX()] = color;
		}

		public String getText(Coordinates pos)
		{
			return this.text[pos.getY()][pos.getX()];
		}

		public Color getColor(Coordinates pos)
		{
			return this.color[pos.getY()][pos.getX()];
		}
	}

	public static void dispose()
	{
		mainFrame.removeAll();
		mainFrame.dispose();
	}
}
