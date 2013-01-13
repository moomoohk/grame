package com.moomoohk.Grame.Graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Interfaces.Render;

public class RenderTest
{
	public static JFrame f;
	public static Canvas c;
	public static void main(String[] args)
	{
		new GrameManager();
		/*Base b = new Base(20, 20, 30, "This is a normal grame base", false);
		b.setWraparound(true);
		b.loadSchem(5);
		Entity ent = new Entity(b);
		ent.makePlayer(true);
		b.render(ent);*/
		f = new JFrame("This is my new base");
		c = new Canvas();
		int width=230, height=222;
		c.setSize(width, height);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(c);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		c.addKeyListener(GrameManager.input);
		c.addMouseListener(new MouseListener()
		{

			public void mouseReleased(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e)
			{
				System.out.println(MouseInfo.getPointerInfo().getLocation().x-f.getX()+" "+(MouseInfo.getPointerInfo().getLocation().y-f.getY()));

			}
		});
		c.requestFocus();
		Base b=new Base(20, 20);
		System.out.println(f.getContentPane().getWidth());
		for (;;)
		{
			draw(b, width, height, new RandomRender());
			width = f.getContentPane().getWidth();
			height = f.getContentPane().getHeight();
		}
	}

	public static void draw(Base b, int width, int height, Render render)
	{
		BufferStrategy bs = c.getBufferStrategy();
		if (bs == null)
		{
			c.createBufferStrategy(3);
			return;
		}
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		// pixels = plainGridify(pixels, width, height, b);
		pixels = render.getPixels(pixels, b, width, height);
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		bs.show();
	}

	public static int[] random(int[] pixels)
	{
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = GrameUtils.randomColor().getRGB();
		return pixels;
	}

	public static int[] plainGridify(int[] pixels, Base b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				/*Coordinates currSquare = new Coordinates((x - 4) / (width / b.getColumns()), (y - 4) / (height / b.getRows()));
				if (b.isInMap(currSquare))
					pixels[x + y * width] = b.getColor(currSquare).getRGB();*/
			}
		return pixels;
	}

	public static int[] Gridify(int[] pixels, Base b, int width, int height)
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				if (x % (width / b.getColumns()) == 0 || (x + 1) % (width / b.getColumns()) == 0 || y % (height / b.getRows()) == 0 || (y + 1) % (height / b.getRows()) == 0)
				{
					pixels[x + y * width] = Color.black.getRGB();
				}
				else
				{
					int pixelX = (x) / (width / b.getColumns()), pixelY = (y) / (height / b.getRows());
					Coordinates currSquare = new Coordinates(pixelX, pixelY);
					if (b.isInMap(currSquare))
					{
						//pixels[x + y * width] = b.getColor(currSquare).getRGB();
					}
				}
			}
		return pixels;
	}

}
