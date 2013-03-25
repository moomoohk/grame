package com.moomoohk.Grame.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToggleButton;

import com.moomoohk.Grame.AI.StrollAI;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Dir;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.GrameFrame;
import com.moomoohk.Grame.Graphics.PlainGridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.Render;
import com.moomoohk.Grame.commands.HelpCommand;
import com.moomoohk.Grame.commands.MoveEntityCommand;
import com.moomoohk.Grame.commands.QuitCommand;
import com.moomoohk.Grame.commands.RenderBaseCommand;
import com.moomoohk.Grame.commands.SetMainBaseCommand;
import com.moomoohk.Grame.commands.setVisibleCommand;
import com.moomoohk.MooCommands.Command;

public class RenderTest
{
	public static JFrame f;
	public static Canvas c;
	public static void main(String[] args)
	{
		new GrameManager();
		GrameManager.debug=true;
		/*Base b = new Base(20, 20, 30, "This is a normal grame base", false);
		b.setWraparound(true);
		b.loadSchem(5);
		Entity ent = new Entity(b);
		ent.makePlayer(true);
		b.render(ent);*/
		Base b=new Base(20, 20);
		Entity ent=new Entity();
		Entity ent2=new Entity();
		Entity ent3=new Entity();
		//ent.makePlayer(true);
		//ent.makePlayer(true, b.ID);
		b.addEntity(ent.ID, new Coordinates(3, 3));
		b.addEntity(ent2.ID, new Coordinates(5, 5));
		b.addEntity(ent3.ID, new Coordinates(10, 5));
		ent.addAI(new StrollAI(), b.ID);
		//b2.addGrameObject(ent, new Coordinates(10, 10));
		System.out.println(b.getEntPos(ent.ID));
		RenderManager.render(b.ID, new PlainGridRender());
		//RenderManager.render(b2.ID);
		System.out.println("Base ID: "+b.ID);
		System.out.println("Ent ID: "+ent.ID);
		//makeControlWindow(b, ent);
		//for (;;)
		{
			/*draw(b, width, height, new PlainGridRender());
			width = f.getContentPane().getWidth();
			height = f.getContentPane().getHeight();
			b.setFloorColor(new Coordinates(0, 0), Color.black);*/
			
		}
		ArrayList<Command<?>> commands=new ArrayList<Command<?>>();
		commands.add(new setVisibleCommand(GrameUtils.console, "setvisible", "Toggles the visibility of a Base. Usage: setvisible [true/false]", 0, 1));
		commands.add(new MoveEntityCommand(GrameUtils.console, "move", "Moves an Entity. Usage: move <ent ID> <base ID> <dir>", 3, 4));
		commands.add(new HelpCommand(GrameUtils.console, "help", "Will print the help of a command. Leave blank for all the commands. Usage: help [command name]", 0, 1));
		commands.add(new RenderBaseCommand(GrameUtils.console, "render", "Will render a base using a render in the Render list. Usage: render <base ID> <render name>", 2, 2));
		commands.add(new QuitCommand(GrameUtils.console, "quit", "Quits the program. Usage: quit", 0, 0));
		commands.add(new SetMainBaseCommand(GrameUtils.console, "setmainbase", "Sets the main Base to display. Usage: setmainbase <base ID>", 1, 1));
		GrameUtils.console.loadCommands(commands);
	}
	public static void makeControlWindow(final Base b, final Entity ent)
	{
		GrameFrame frame=new GrameFrame(100, 200);
		JToggleButton visible=new JToggleButton("Toggle Visible");
		visible.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				RenderManager.mainFrame.setVisible(!RenderManager.mainFrame.isVisible());
			}
		});
		visible.setBounds(7, 10, 115, 30);
		JButton up=new JButton("↑"), down=new JButton("↓"), right=new JButton("→"), left=new JButton("←");
		up.setBounds(50, 40, 20, 30);
		down.setBounds(50, 100, 20, 30);
		left.setBounds(10, 75, 30, 20);
		right.setBounds(80, 75, 30, 20);
		up.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				int x=ent.getPos(b.ID).getX(), y=ent.getPos(b.ID).getY();
				System.out.println(x+" "+(y-1));
				ent.setPos(b.ID, ent.getPos(b.ID).addDir(Dir.UP));
			}
		});
		down.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				int x=ent.getPos(b.ID).getX(), y=ent.getPos(b.ID).getY();
				System.out.println(x+" "+(y-1));
				ent.setPos(b.ID, ent.getPos(b.ID).addDir(Dir.DOWN));
			}
		});
		left.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				int x=ent.getPos(b.ID).getX(), y=ent.getPos(b.ID).getY();
				System.out.println(x+" "+(y-1));
				ent.setPos(b.ID, ent.getPos(b.ID).addDir(Dir.LEFT));
			}
		});
		right.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent paramActionEvent)
			{
				int x=ent.getPos(b.ID).getX(), y=ent.getPos(b.ID).getY();
				System.out.println(x+" "+(y-1));
				ent.setPos(b.ID, ent.getPos(b.ID).addDir(Dir.RIGHT));
			}
		});
	//	String[] renderList={"Grid", "Plain grid"};
	//	JComboBox renders=new JComboBox(renderList);
		frame.add(visible);
		frame.add(up);
		frame.add(down);
		frame.add(left);
		frame.add(right);
		frame.setResizable(false);
		frame.setVisible(true);
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
				Coordinates currSquare = new Coordinates((x - 4) / (width / b.getColumns()), (y - 4) / (height / b.getRows()));
				if (b.isInMap(currSquare))
					pixels[x + y * width] = b.getColor(currSquare).getRGB();
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
