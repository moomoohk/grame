package com.moomoohk.Grame.Core.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.Render;
import com.moomoohk.Grame.Core.Graphics.PostProcessing.Label;

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
	private static ArrayList<Label> labels;
	private static JFrame mainFrame;
	private static Canvas mainCanvas;
	private static int mainBase;
	private static boolean drawCoordinates;
	private static int squareSize = 30;
	private static Graphics2D g2;
	private static BufferedImage img;

	public static void initialize()
	{
		renders = new HashMap<Integer, Render>();
		text = new HashMap<Integer, TextLayer>();
		labels = new ArrayList<Label>();
		loadRenders();
		mainFrame = new JFrame();
		mainCanvas = null;
		mainBase = -1;
		drawCoordinates = false;
		GrameUtils.print("Initialized successfully.", MessageLevel.NORMAL);
	}

	/**
	 * Renders a {@link Grid}.
	 * 
	 * @param gID
	 *            The {@link Grid#ID} of the {@link Grid} to render.
	 */
	public static void render(int gID)
	{
		render(gID, renders.get(gID));
	}

	/**
	 * Renders a {@link Grid} using a given {@link Render}.
	 * 
	 * @param gID
	 *            The {@link Grid#ID} of the {@link Grid} to render.
	 * @param render
	 *            The {@link Render} to use.
	 */
	public static void render(final int gID, Render render)
	{
		if (gID < 0)
			return;
		setupFrame(gID);
		if (gID != mainBase || GrameManager.findGrid(gID) == null)
			GrameManager.setMainGrid(gID);
		mainBase = gID;
		mainFrame.setTitle(GrameManager.getGameName());
		if (render == null)
			render = GrameManager.getDefaultRender();
		if (!render.equals(renders.get(gID)))
			GrameManager.setMainRender(render);
		renders.put(gID, render);
		BufferStrategy bs = mainCanvas.getBufferStrategy();
		if (bs == null)
		{
			if (mainFrame.isVisible())
				mainCanvas.createBufferStrategy(3);
			return;
		}
		img = new BufferedImage(mainCanvas.getWidth(), mainCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		try
		{
			pixels = render.getPixels(pixels, GrameManager.findGrid(gID), mainCanvas.getWidth(), mainCanvas.getHeight());
		}
		catch (Exception e)
		{
			return;
		}
		g2 = (Graphics2D) bs.getDrawGraphics().create();

		g2.setFont(new Font("LucidaTypewriter", 1, 8));
		int squaresize = mainCanvas.getWidth() / GrameManager.findGrid(gID).getColumns();
		for (int x = 0; x < GrameManager.findGrid(gID).getColumns(); x++)
			for (int y = 0; y < GrameManager.findGrid(gID).getRows(); y++)
				if (text.get(gID) != null)
					if (text.get(gID).getText(new Coordinates(x, y)) != null && text.get(gID).getText(new Coordinates(x, y)).trim().length() != 0)
					{
						g2.setColor(text.get(gID).getColor(new Coordinates(x, y)));
						g2.drawString(text.get(gID).getText(new Coordinates(x, y)), y * squaresize, (x + 1) * (squaresize) - ((squaresize / 2) - 5));
					}
		if (!GrameManager.paused)
			g2.drawImage(img, 0, 0, mainCanvas.getWidth(), mainCanvas.getHeight(), null);
		else
		{
			applyGaussianBlur();
			setOverlayColor(Color.gray.brighter());
			drawLabel(new Label(mainCanvas.getWidth() / 2, mainCanvas.getHeight() / 2, "Paused", new Font("LucidaTypewriter", Font.BOLD, 40), Color.white, Color.black, 10, 15, 15, 10));
		}

		for (Label l : labels)
			drawLabel(l);

		if (drawCoordinates)
		{
			g2.setColor(Color.blue);
			for (int x = 0; x < GrameManager.findGrid(gID).getColumns(); x++)
				for (int y = 0; y < GrameManager.findGrid(gID).getRows(); y++)
					g2.drawString("(" + y + ", " + x + ")", y * squaresize, (x + 1) * (squaresize) - ((squaresize / 2) - 5));
		}
		//		drawLabel(new Label(500, 500, "Testing really long string", new Font("LucidaTypewriter", Font.BOLD, 20), Color.white, Color.black, 10, 10, 10, 10));
		g2.dispose();
		bs.show();
	}

	public static void applyGaussianBlur()
	{
		g2.drawImage(new GaussianFilter().filter(img), 0, 0, mainCanvas.getWidth(), mainCanvas.getHeight(), null);
	}

	public static void drawLabel(Label l)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setFont(l.getFont());
		FontMetrics fm = g2.getFontMetrics();
		Rectangle2D textBounds = fm.getStringBounds(l.getText(), g2);
		Rectangle fixed = new Rectangle((int) (Math.min(l.getCenterX(), textBounds.getWidth()) - (textBounds.getWidth() / 2)), (int) (l.getCenterY() - (textBounds.getHeight() / 2)), (int) Math.min(textBounds.getWidth(), mainCanvas.getWidth()), (int) textBounds.getHeight());

		Rectangle back = new Rectangle((int) fixed.getX() - l.getPaddingLeft(), (int) fixed.getY() - l.getPaddingTop(), (int) (fixed.getWidth()) + l.getPaddingRight() + l.getPaddingLeft(), (int) (fixed.getHeight()) + l.getPaddingBottom() + l.getPaddingTop());
		g2.setColor(new Color(0, 0, 0, 180));
		g2.fillRoundRect((int) back.getX(), (int) back.getY(), (int) back.getWidth(), (int) back.getHeight(), 10, 10);
		g2.setColor(new Color(l.getTextColor().getRed(), l.getTextColor().getGreen(), l.getTextColor().getBlue(), 180));
		g2.drawString(l.getText(), (int) (fixed.getX()), (int) (fixed.getY() + Math.max(fm.getMaxAscent(), fm.getMaxDescent())));

		g2.setColor(Color.cyan);
		g2.draw(new Ellipse2D.Double(l.getCenterX() - 5, l.getCenterY() - 5, 10, 10));
		g2.setColor(Color.blue);
		g2.draw(back);
		g2.setColor(Color.green);
		g2.draw(textBounds);
		g2.setColor(Color.red);
		g2.draw(fixed);
		g2.fill(new Ellipse2D.Double(fixed.getCenterX() - 2, fixed.getCenterY() - 2, 4, 4));
	}

	public static void setOverlayColor(Color color)
	{
		g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 127));
		g2.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
	}

	/**
	 * Generates a blank {@link Canvas} which is of the required size.
	 * 
	 * @param gID
	 *            {@link Grid#ID} of the {@link Grid} of which this {@link Canvas} is.
	 * @return A {@link Canvas}.
	 */
	public static Canvas generateCanvas(int gID)
	{
		Canvas c = new Canvas();
		int width = Math.min(squareSize * GrameManager.findGrid(gID).getColumns(), Toolkit.getDefaultToolkit().getScreenSize().width), height = Math.min(squareSize * GrameManager.findGrid(gID).getRows(), Toolkit.getDefaultToolkit().getScreenSize().height - 50);
		c.setSize(width, height);
		return c;
	}

	/**
	 * Sets up the main window.
	 * 
	 * @param gID
	 *            {@link Grid#ID} of the {@link Grid} which is being rendered.
	 */
	public static void setupFrame(int gID)
	{
		if (gID != mainBase)
		{
			if (mainCanvas == null)
				mainCanvas = generateCanvas(gID);
			mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			mainFrame.setResizable(false);
			mainFrame.add(mainCanvas);
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.addFocusListener(new FocusListener()
			{

				@Override
				public void focusLost(FocusEvent paramFocusEvent)
				{
					GrameManager.getInputHandler().resetKeys();
					//					GrameManager.pauseAllGrameObjects(true);
				}

				@Override
				public void focusGained(FocusEvent paramFocusEvent)
				{
					//					GrameManager.pauseAllGrameObjects(false);
				}
			});
		}
	}

	/**
	 * Renders the main {@link Grid}.
	 */
	public synchronized static void tick()
	{
		render(mainBase, renders.get(mainBase));
		mainFrame.repaint();
	}

	/**
	 * Attaches a {@link Render} to a {@link Grid} for future reference.
	 * 
	 * @param gID
	 *            {@link Grid#ID} of {@link Grid} to attach the {@link Render} to.
	 * @param render
	 *            The {@link Render} to attach.
	 */
	public static void setRender(int gID, Render render)
	{
		renders.put(gID, render);
	}

	/**
	 * Loads some basic {@link Render}s.
	 */
	private static void loadRenders()
	{
		GrameManager.addRender(new DefaultGridRender());
		GrameManager.addRender(new CleanGridRender());
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
	 * Gets the {@link Grid#ID} of the main {@link Grid}.
	 * 
	 * @return The {@link Grid#ID} of the main {@link Grid}.
	 */
	public static int getMainGrid()
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

	public static void setText(int gID, Coordinates pos, String text, Color color)
	{
		if (!GrameManager.findGrid(gID).isInGrid(pos))
			return;
		if (RenderManager.text.get(gID) == null)
			RenderManager.text.put(gID, new TextLayer(GrameManager.findGrid(gID).getRows(), GrameManager.findGrid(gID).getColumns()));
		RenderManager.text.get(gID).setText(pos, text, color);
	}

	public static void clearAllText()
	{
		for (Integer i : text.keySet())
			clearText(i);
	}

	public static void clearText(int gID)
	{
		text.put(gID, new TextLayer(GrameManager.findGrid(gID).getRows(), GrameManager.findGrid(gID).getColumns()));
	}

	private static class TextLayer
	{
		private final String[][] text;
		private final Color[][] color;

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
		if (mainFrame != null)
		{
			mainFrame.removeAll();
			mainFrame.dispose();
		}
	}

	public static void addLabel(Label l)
	{
		labels.add(l);
	}

	public static void removeLabel(Label l)
	{
		if (labels.contains(l))
			labels.remove(l);
	}
}