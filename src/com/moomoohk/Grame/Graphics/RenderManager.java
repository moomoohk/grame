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

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.Render;

public class RenderManager
{
	public static HashMap<Integer, Render> renders;
	public static JFrame mainFrame = new JFrame();
	public static Canvas mainCanvas=null;
	public static int mainBase = -1;
	public static boolean drawCoordinates=false;

	static
	{
		renders = new HashMap<Integer, Render>();
		loadRenders();
		GrameUtils.print("Initialized successfully.", MessageLevel.NORMAL);
	}

	public static void render(int bID)
	{
		render(bID, renders.get(bID));
	}

	public static void render(final int bID, Render render)
	{
		if(bID==-1)
			return;
		setupFrame(bID);
		mainBase=bID;
		mainFrame.setTitle("Rendering Base number "+mainBase);
		if (render == null)
			render = GrameManager.defaultRender;
		renders.put(bID, render);
		BufferStrategy bs = mainCanvas.getBufferStrategy();
		if (bs == null)
		{
			if(mainFrame.isVisible())
				mainCanvas.createBufferStrategy(3);
			return;
		}
		BufferedImage img = new BufferedImage(mainCanvas.getWidth(), mainCanvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		try
		{
			pixels = render.getPixels(pixels, GrameManager.findBase(bID), mainCanvas.getWidth(), mainCanvas.getHeight());
		}
		catch(Exception e)
		{
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, mainCanvas.getWidth(), mainCanvas.getHeight(), null);
		if(drawCoordinates)
		{
			g.setColor(Color.black);
			g.setFont(new Font("LucidaTypewriter", 1, 8));
			int squaresize=mainCanvas.getWidth()/GrameManager.findBase(bID).getColumns();
			for(int x=0; x<GrameManager.findBase(bID).getColumns(); x++)
				for(int y=0; y<GrameManager.findBase(bID).getRows(); y++)
				{
					g.drawString("("+y+", "+x+")", y*squaresize+5, (x+1)*squaresize-(squaresize/2));
				}
		}
		g.dispose();
		bs.show();
	}

	public static Canvas generateCanvas(int bID)
	{
		Canvas c = new Canvas();
		int width = Math.min(30 * GrameManager.findBase(bID).getColumns(), Toolkit.getDefaultToolkit().getScreenSize().width), height = Math.min(30 * GrameManager.findBase(bID).getRows(), Toolkit.getDefaultToolkit().getScreenSize().height-50);
		c.setSize(width, height);
		return c;
	}
	public static void setupFrame(int bID)
	{
		if(bID!=mainBase)
		{
			if(mainCanvas==null)
				mainCanvas=generateCanvas(bID);
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setResizable(false);
			mainFrame.add(mainCanvas);
			mainFrame.pack();
			mainFrame.setLocationRelativeTo(null);
			mainFrame.addFocusListener(new FocusListener()
			{
				
				public void focusLost(FocusEvent paramFocusEvent)
				{
					GrameManager.input.resetKeys();
				}
				
				public void focusGained(FocusEvent paramFocusEvent)
				{
				}
			});
		}
	}

	public synchronized static void tick()
	{
		render(mainBase, renders.get(mainBase));
		mainFrame.repaint();
	}

	public static void setRender(int bID, Render render)
	{
		renders.put(bID, render);
	}

	public static void setMainBase(int bID)
	{
		mainFrame.removeAll();
		setupFrame(bID);
		render(bID, renders.get(bID));
	}

	private static void loadRenders()
	{
		GrameManager.addRender(new GridRender());
		GrameManager.addRender(new PlainGridRender());
	}
	public static void setVisible(boolean f)
	{
		mainFrame.setVisible(f);
	}
	public static boolean isVisible()
	{
		return mainFrame.isVisible();
	}
}
