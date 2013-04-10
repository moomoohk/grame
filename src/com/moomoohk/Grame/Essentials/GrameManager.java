package com.moomoohk.Grame.Essentials;

import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Graphics.GridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;
import com.moomoohk.Grame.Interfaces.Render;

/**
 * The Grame Manager takes care of all the internal Grame operations.
 * <p>
 * Indexes and ticks any {@link GrameObject}s and {@link Base}s that are created
 * automatically.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class GrameManager implements Runnable
{
	/**
	 * The Grame version number.
	 */
	public static final String VERSION_NUMBER = "3.1";
	/**
	 * The WASD keys parsed to a {@link Dir}.
	 */
	public static Dir dir1 = null;
	/**
	 * The arrow keys parsed to a {@link Dir}.
	 */
	public static Dir dir2 = null;
	/**
	 * Current game time.
	 */
	public static int time = 1;
	private static ArrayList<GrameObject> grameObjectList;
	private static ArrayList<Base> baseList;
	private static HashMap<String, Render> renders = new HashMap<String, Render>();
	private static HashMap<String, MovementAI> ais = new HashMap<String, MovementAI>();
	private static boolean running = false;
	private static boolean debug = false;
	private static boolean disablePrints = false;
	private static boolean spam = false;
	private static String gameName = "Grame";
	private static InputHandler input;
	private static Thread thread;
	private static int fps = 0;
	private static Render defaultRender = new GridRender();

	static
	{
		input = new InputHandler();
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
				GrameUtils.print("Exiting...", MessageLevel.NORMAL);
				stop();
				disposeAll();
			}
		}));
		grameObjectList = new ArrayList<GrameObject>();
		baseList = new ArrayList<Base>();
		GrameUtils.console.setOutputOverride();
		//GrameUtils.console.setLocation(GrameUtils.console.getLocation().x, Toolkit.getDefaultToolkit().getScreenSize().height);
		GrameUtils.console.setVisible(true);
		start();
	}

	private static void start()
	{
		if (running)
			return;
		running = true;
		thread = new Thread(new GrameManager(), "Game");
		thread.start();
		GrameUtils.print("Grame " + VERSION_NUMBER + " is initialized.", MessageLevel.NORMAL);
	}

	public static synchronized void stop()
	{
		if (!running)
			return;
		running = false;
	}

	/**
	 * Starts the Grame Manager clock.
	 * <p>
	 * This method should never be called by the user as doing that might create
	 * issues.
	 */
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
				tickGrameObjects();
				tickBases();
			}

			if (ticked)
			{
				frames++;
			}
			frames++;
		}
	}

	private void tick(boolean[] key)
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
		if (key[40] && key[39])
			dir2 = new Dir(1, 1);
		if (key[38] && key[37])
			dir2 = new Dir(-1, -1);
		if (key[38] && key[39])
			dir2 = new Dir(1, -1);
		if (key[27])
			System.exit(0);
	}

	private static void tickGrameObjects()
	{
		try
		{
			for (int i = 0; i < grameObjectList.size(); i++)
			{
				GrameObject go = grameObjectList.get(i);
				if (go == null)
					continue;
				if (baseList != null)
					for (int bID = 0; bID < baseList.size(); bID++)
						if (findBase(bID).containsGrameObject(go.ID) && go.getSpeed() != 0 && time % go.getSpeed() == 0 && !go.isPaused())
							go.tick(bID);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
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

	/**
	 * Disposes of all the {@link GrameObject}s and {@link Base}s.
	 */
	public static void disposeAll()
	{
		GrameUtils.print("Disposing of all the Bases in the Base list...", MessageLevel.NORMAL);
		for (int i = 0; i < baseList.size(); i++)
		{
			GrameUtils.print("Disposing of " + baseList.get(i).ID, MessageLevel.NORMAL);
			baseList.remove(i);
		}
		baseList = null;
		GrameUtils.print("Done disposing of Bases.", MessageLevel.NORMAL);
	}

	/**
	 * Adds a {@link GrameObject} to the Grame Object list.
	 * <p>
	 * The user should never have to call this method.
	 * 
	 * @param go
	 *            {@link GrameObject} to add.
	 * @return The ID number for the object.
	 */
	public static int add(GrameObject go)
	{
		if (grameObjectList.contains(go))
		{
			GrameUtils.print(go.getName() + " already exists in the Grame Object list!", MessageLevel.ERROR);
			return -1;
		}
		grameObjectList.add(go);
		GrameUtils.print("Added " + go.getName() + " to the Grame Objects list (ID:" + (grameObjectList.size() - 1) + ")", MessageLevel.NORMAL);
		return grameObjectList.size() - 1;
	}

	/**
	 * Adds a {@link Base} to the Base list.
	 * <p>
	 * The user should never have to call this method.
	 * 
	 * @param b
	 *            {@link Base} to add.
	 * @return The ID number for the Base.
	 */
	public static int add(Base b)
	{
		if (baseList.contains(b))
		{
			GrameUtils.print(b.toString() + " already exists in the Grame Object list!", MessageLevel.ERROR);
			return -1;
		}
		baseList.add(b);
		GrameUtils.print("Added a Base to the Base list (ID:" + (baseList.size() - 1) + ")", MessageLevel.NORMAL);
		return baseList.size() - 1;
	}

	/**
	 * Finds and returns a {@link GrameObject} from the Grame Objects list.<br>
	 * If the object is not found, null will be returned.
	 * @param id The ID of the object to find.
	 * @return The {@link GrameObject} with that ID. If not in the list, null will be returned.
	 */
	public static GrameObject findGrameObject(int id)
	{
		try
		{
			return grameObjectList.get(id);
		}
		catch (Exception e)
		{
			// GrameUtils.print("Entity with ID:" + id +
			// " not found! Returning null instead.", "Grame Manager", false);
		}
		return null;
	}

	/**
	 * Finds and returns a {@link Base} from the Base list.<br>
	 * If the {@link Base} is not found, null will be returned.
	 * @param id The ID of the {@link Base} to find.
	 * @return The {@link Base} with that ID. If not in the list, null will be returned.
	 */
	public static Base findBase(int id)
	{
		try
		{
			return baseList.get(id);
		}
		catch (Exception e)
		{
			GrameUtils.print("Base with ID:" + id + " not found! Returning null instead.", MessageLevel.ERROR);
		}
		return null;
	}

	/**
	 * Sets the game name.
	 * @param name Game name to set.
	 */
	public static void setGameName(String name)
	{
		gameName = name;
	}

	/**
	 * Gets the game name.
	 * @return The game name.
	 */
	public static String getGameName()
	{
		return gameName;
	}
	
	/**
	 * Sets the default {@link Render}.
	 * @param render {@link Render} to set.
	 */
	public static void setDefaultRender(Render render)
	{
		defaultRender = render;
	}
	
	/**
	 * Gets the default {@link Render}.
	 * @return The default {@link Render}.
	 */
	public static Render getDefaultRender()
	{
		return defaultRender;
	}

	/**
	 * Indexes a {@link Render}.
	 * @param render {@link Render} to index.
	 */
	public static void addRender(Render render)
	{
		if (render == null)
			return;
		String name = render.getName().toLowerCase().trim().replace(' ', '_');
		for (String temp : renders.keySet())
			if (renders.get(temp).getName().equals(render.getName()))
				return;
		renders.put(name, render);
		GrameUtils.print("Added " + name + " to the render list.", MessageLevel.DEBUG);
	}

	/**
	 * Indexes a {@link MovementAI}.
	 * @param ai {@link MovementAI} to index.
	 */
	public static void addAI(MovementAI ai)
	{
		if (ai == null)
			return;
		String name = ai.toString().toLowerCase().trim().replace(' ', '_');
		for (String temp : ais.keySet())
			if (ais.get(temp).toString().equals(ai.toString()))
				return;
		ais.put(name, ai);
		GrameUtils.print("Added " + name + " to the AI list.", MessageLevel.DEBUG);
	}

	/**
	 * Pauses or unpauses all the {@link GrameObject}s.
	 * @param f True to pause, false to unpause all the {@link GrameObject}.
	 */
	public static void pauseAllGrameObjects(boolean f)
	{
		for (GrameObject go : grameObjectList)
			go.pause(f);
	}

	/**
	 * Sets whether debug prints will be visible or not.
	 * @param debug True to enable debug prints, else false.
	 */
	public static void setDebug(boolean debug)
	{
		GrameManager.debug = debug;
	}

	/**
	 * Returns whether or not debug prints are enabled.
	 * @return True if debug prints are enabled, else false.
	 */
	public static boolean isDebug()
	{
		return debug;
	}

	/**
	 * Completely disable prints.
	 * @param disablePrints True to disable all prints, else false.
	 */
	public static void setDisablePrints(boolean disablePrints)
	{
		GrameManager.disablePrints = disablePrints;
	}

	/**
	 * Returns whether or not prints are completely disabled.
	 * @return True if prints are completely disabled, else false.
	 */
	public static boolean isDisablePrints()
	{
		return disablePrints;
	}

	/**
	 * Sets whether spam prints will be visible or not. 
	 * @param spam True to enable spam prints, else false.
	 */
	public static void setSpam(boolean spam)
	{
		GrameManager.spam = spam;
	}

	/**
	 * Returns whether or not spam prints are enabled.
	 * @return True if spam prints are enabled, else false.
	 */
	public static boolean isSpam()
	{
		return spam;
	}

	/**
	 * Gets the {@link InputHandler} that is currently being used.
	 * @return The {@link InputHandler} that is currently being used.
	 */
	public static InputHandler getInputHandler()
	{
		return input;
	}

	/**
	 * Gets all the loaded {@link MovementAI}s sorted in a HashMap<String, {@link MovementAI}> where the names (spaces replaced with '-') are the keys. 
	 * @return A HashMap<String, {@link MovementAI}> of all loaded {@link MovementAI}s.
	 */
	public static HashMap<String, MovementAI> getAIs()
	{
		return ais;
	}

	/**
	 * Gets all the loaded {@link Render}s sorted in a HashMap<String, {@link Render}> where the names (spaces replaced with '-') are the keys. 
	 * @return A HashMap<String, {@link Render}> of all loaded {@link Render}s.
	 */
	public static HashMap<String, Render> getRenders()
	{
		return renders;
	}

	/**
	 * Gets the size of the {@link GrameObject} list.
	 * @return The size of the {@link GrameObject} list.
	 */
	public static int getObjectListLength()
	{
		return grameObjectList.size();
	}
	
	/**
	 * Gets the current FPS (Frames Per Second) count.
	 * @return The current FPS (Frames Per Second) count.
	 */
	public static int getFPS()
	{
		return fps;
	}
}
