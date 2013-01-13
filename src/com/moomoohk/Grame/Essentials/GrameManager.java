package com.moomoohk.Grame.Essentials;

import java.util.ArrayList;
import java.util.HashMap;

public class GrameManager implements Runnable
{
	public static ArrayList<Entity> entList;
	public static ArrayList<Base> baseList;
	public static boolean running = false;
	public static boolean debug = false;
	public static boolean disablePrints = false;
	public static boolean initialized = false;
	public static String gameName = "Grame";
	public static InputHandler input;
	public static Thread thread;
	public static int fps = 0;
	public static int time = 1;
	public static Dir dir = null;
	public static HashMap<Integer, ArrayList<Integer>> render;
	public static final String VERSION_NUMBER = "2.0";

	public GrameManager()
	{
		if (initialized)
		{
			GrameUtils.print("I am already initialized!", "Grame Manager", false);
			return;
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			public void run()
			{
				GrameUtils.print("Exiting...", "Grame Manager", false);
			}
		}));
		entList = new ArrayList<Entity>();
		baseList = new ArrayList<Base>();
		input = new InputHandler();
		render = new HashMap<Integer, ArrayList<Integer>>();
		start();
	}

	public void start()
	{
		if (running)
			return;
		GrameUtils.print("Initialized successfully.", "Grame Manager", false);
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
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
		double secondsTick = 0.01666666666666667D;
		int tickCount = 0;
		boolean ticked = false;
		while (running)
		{
			long curr = System.nanoTime();
			long delta = curr - prev;
			prev = curr;
			unprocessed += delta / 1000000000.0D;
			while (unprocessed > secondsTick)
			{
				tick(input.key);
				unprocessed -= secondsTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 != 0)
					continue;
				fps = frames;
				prev += 1000L;
				frames = 0;

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

		dir = null;
		if ((key[83]) || (key[40]))
			dir = Dir.DOWN;
		if ((key[65]) || (key[37]))
			dir = Dir.LEFT;
		if ((key[68]) || (key[39]))
			dir = Dir.RIGHT;
		if ((key[87]) || (key[38]))
			dir = Dir.UP;
		if (((key[83]) || (key[40])) && ((key[65]) || (key[37])))

			dir = new Dir(-1, 1);
		if (((key[83]) || (key[40])) && ((key[68]) || (key[39])))
			dir = new Dir(1, 1);
		if (((key[87]) || (key[38])) && ((key[65]) || (key[37])))
			dir = new Dir(-1, -1);
		if (((key[87]) || (key[38])) && ((key[68]) || (key[39])))
			dir = new Dir(1, -1);

		if (key[27])
			System.exit(0);
		// tickEnts();
	}

	private static void tickEnts()
	{
		try
		{
			for (Entity ent : entList)
			{
				int playerspeed = 5;
				if (ent == null)
					continue;
				if (((ent.isPlayer()) && (time % playerspeed == 0)) || ((time % ent.getSpeed() == 0L) && (!ent.isPaused()) && render.containsKey(ent)))
				{
					for (int bID : render.get(ent))
						ent.tick(bID);
				}
			}
		}
		catch (Exception e)
		{
			CrashManager.showException(e);
		}
	}

	private static void tickBases()
	{

	}

	public static void disposeAll()
	{
		GrameUtils.print("Disposing of all the Bases in the Base list...", "Grame Manager", false);
		for (int i = 0; i < baseList.size(); i++)
		{
			// baseList.get(i).close();
			baseList.remove(i);
			GrameUtils.print("Disposing of " + baseList.get(i).ID, "Grame Manager", true);
		}
		baseList = null;
		GrameUtils.print("Done disposing of Bases.", "Grame Manager", false);
	}

	public static int add(Entity ent)
	{
		entList.add(ent);
		return entList.size();
	}

	public static int add(Base b)
	{
		baseList.add(b);
		return baseList.size();
	}

	public static Entity findEntity(int id)
	{
		try
		{
			return entList.get(id);
		}
		catch (Exception e)
		{
			GrameUtils.print("Entity with ID:" + id + " not found! Returning null instead.", "Grame Manager", false);
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
}
