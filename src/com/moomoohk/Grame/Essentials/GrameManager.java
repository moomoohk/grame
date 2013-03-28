package com.moomoohk.Grame.Essentials;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.Graphics.GridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MovementAI;
import com.moomoohk.Grame.Interfaces.Render;

public class GrameManager implements Runnable
{
	public static ArrayList<Entity> entList;
	public static ArrayList<Base> baseList;
	public static ArrayList<Render> renders = new ArrayList<Render>();
	public static HashMap<String, MovementAI> ais = new HashMap<String, MovementAI>();
	public static boolean running = false;
	public static boolean debug = false;
	public static boolean disablePrints = false;
	public static boolean initialized = false;
	public static String gameName = "Grame";
	public static InputHandler input;
	public static Thread thread;
	public static int fps = 0;
	public static int time = 1;
	public static Dir dir1 = null, dir2 = null;
	public static Render defaultRender = new GridRender();
	public static HashMap<Integer, ArrayList<Integer>> render;
	public static final String VERSION_NUMBER = "2.1";
	public static boolean showConsole = false;
	public static int playerSpeed = 4;

	static
	{
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
		{
			public void uncaughtException(Thread t, Throwable e)
			{
				GrameUtils.console.addText("Unhandled exception!\n");
				e.printStackTrace();
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			public void run()
			{
				disposeAll();
				GrameUtils.print("Exiting...", "Grame Manager", false);
			}
		}));
		System.setOut(new GrameManager().new OutputOverride(System.out));
		System.setErr(new GrameManager().new OutputOverride(System.err));
		GrameUtils.console.setVisible(true);
		entList = new ArrayList<Entity>();
		baseList = new ArrayList<Base>();
		input = new InputHandler();
		render = new HashMap<Integer, ArrayList<Integer>>();
		new RenderManager();
		GrameUtils.console.setAlwaysOnTop(true);
		start();
	}

	public static void start()
	{
		if (running)
			return;
		running = true;
		thread = new Thread(new GrameManager(), "Game");
		thread.start();
		GrameUtils.print("Grame is initialized.", "Grame Manager", false);
	}

	public static synchronized void stop()
	{
		if (!running)
			return;
		running = false;
		try
		{
			thread.join();
		}
		catch (Exception e)
		{
			System.out.println("Something broke");
		}
	}

	public void run()
	{
		int frames = 0;
		double unprocessed = 0.0D;
		long prev = System.nanoTime();
		double secondsTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while (running)
		{
			long curr = System.nanoTime();
			long delta = curr - prev;
			prev = curr;
			unprocessed += delta / 1000000000.0;
			while (unprocessed > secondsTick)
			{
				tick(input.key);
				unprocessed -= secondsTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 != 0)
				{
					fps = frames;
					prev += 1000L;
					frames = 0;
				}
				RenderManager.tick();
				tickEnts();
				tickBases();
			}

			if (ticked)
			{
				frames++;
			}
			frames++;
		}
	}

	public void tick(boolean[] key)
	{
		time += 1;
		dir1 = null;
		dir2 = null;
		if (key[83])
			dir1 = Dir.DOWN;
		if (key[65])
			dir1 = Dir.LEFT;
		if (key[68])
			dir1 = Dir.RIGHT;
		if (key[87])
			dir1 = Dir.UP;
		if (key[83] && key[65])
			dir1 = new Dir(-1, 1);
		if (key[83] && key[68])
			dir1 = new Dir(1, 1);
		if (key[87] && key[65])
			dir1 = new Dir(-1, -1);
		if (key[87] && key[68])
			dir1 = new Dir(1, -1);

		if (key[40])
			dir2 = Dir.DOWN;
		if (key[37])
			dir2 = Dir.LEFT;
		if (key[39])
			dir2 = Dir.RIGHT;
		if (key[38])
			dir2 = Dir.UP;
		if (key[40] && key[37])
			dir2 = new Dir(-1, 1);
		if (key[40]&&key[39])
			dir2 = new Dir(1, 1);
		if (key[38]&&key[37])
			dir2 = new Dir(-1, -1);
		if (key[38]&&key[39])
			dir2 = new Dir(1, -1);
		if (key[27])
			System.exit(0);
	}

	private static void tickEnts()
	{
		synchronized (entList)
		{
			try
			{
				for (Entity ent : entList)
				{
					if (ent == null || render == null || (render != null && render.get(ent.ID) == null))
						continue;
					for (int bID : render.get(ent.ID))
					{
						if (ent.isPlayer(bID))
						{
							if (time % playerSpeed == 0)
								ent.tick(bID);
						}
						else
							if (time % ent.getSpeed() == 0 && !ent.isPaused() && render.containsKey(ent.ID))
								ent.tick(bID);
					}
				}
			}
			catch (Exception e)
			{
				CrashManager.showException(e);
			}
		}
	}

	private static void tickBases()
	{
		if (baseList == null)
			return;
		synchronized (baseList)
		{
			try
			{
				for (Base b : baseList)
				{
					if (b == null)
						continue;
					b.tick();
				}
			}
			catch (Exception e)
			{
				CrashManager.showException(e);
			}
		}
	}

	public static void disposeAll()
	{
		GrameUtils.print("Disposing of all the Bases in the Base list...", "Grame Manager", false);
		for (int i = 0; i < baseList.size(); i++)
		{
			GrameUtils.print("Disposing of " + baseList.get(i).ID, "Grame Manager", true);
			baseList.remove(i);
		}
		baseList = null;
		GrameUtils.print("Done disposing of Bases.", "Grame Manager", false);
	}

	public static int add(Entity ent)
	{
		entList.add(ent);
		GrameUtils.print("Added " + ent.getName() + " to the Entity list (ID:" + (entList.size() - 1) + ")", "Grame Manager", false);
		render.put(ent.ID, new ArrayList<Integer>());
		GrameUtils.print("Made " + ent.getName() + " a render list", "Grame Manager", false);
		return entList.size() - 1;
	}

	public static int add(Base b)
	{
		try
		{
			baseList.add(b);
			GrameUtils.print("Added a Base to the Base list (ID:" + (baseList.size() - 1) + ")", "Grame Manager", false);
			return baseList.size() - 1;
		}
		catch (Exception e)
		{
			CrashManager.showException(e);
		}
		return -1;
	}

	public static Entity findEntity(int id)
	{
		try
		{
			return entList.get(id);
		}
		catch (Exception e)
		{
			// GrameUtils.print("Entity with ID:" + id +
			// " not found! Returning null instead.", "Grame Manager", false);
		}
		return null;
	}

	public static Base findBase(int id)
	{
		try
		{
			return baseList.get(id);
		}
		catch (Exception e)
		{
			GrameUtils.print("Base with ID:" + id + " not found! Returning null instead.", "Grame Manager", false);
		}
		return null;
	}

	public static void setGameName(String name)
	{
		gameName = name;
	}

	public static void setDefaultRender(Render render)
	{
		defaultRender = render;
	}

	public static void addRender(Render render)
	{
		if (render == null)
			return;
		for (Render temp : renders)
			if (temp.getName().equals(render.getName()))
				return;
		renders.add(render);
		GrameUtils.print("Added " + render.getName() + " to the render list.", "Grame Manager", false);
	}

	public static void addAI(MovementAI ai)
	{
		if (ai == null)
			return;
		String name = ai.toString().toLowerCase().trim().replace(' ', '_');
		for (String temp : ais.keySet())
			if (ais.get(temp).toString().equals(ai.toString()))
				return;
		ais.put(name, ai);
		GrameUtils.print("Added " + name + " to the AI list.", "Grame Manager", false);
	}

	private class OutputOverride extends PrintStream
	{
		public OutputOverride(OutputStream str)
		{
			super(str);
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			super.write(b);
			String text = new String(b).trim();
			if (!text.equals("") && !text.equals("\n"))
				GrameUtils.console.addText("[From Console (" + Thread.currentThread().getStackTrace()[9].getFileName().subSequence(0, Thread.currentThread().getStackTrace()[9].getFileName().indexOf(".java")) + ":" + Thread.currentThread().getStackTrace()[9].getLineNumber() + ")] " + text + "\n");
		}

		@Override
		public void write(byte[] buf, int off, int len)
		{
			super.write(buf, off, len);
			String text = new String(buf, off, len).trim();
			if (!text.equals("") && !text.equals("\n"))
				GrameUtils.console.addText("[From Console (" + Thread.currentThread().getStackTrace()[9].getFileName().subSequence(0, Thread.currentThread().getStackTrace()[9].getFileName().indexOf(".java")) + ":" + Thread.currentThread().getStackTrace()[9].getLineNumber() + ")] " + text + "\n");
		}

		@Override
		public void write(int b)
		{
			throw new UnsupportedOperationException("Write(int) is not supported by OutputOverride.");
		}
	}
}
