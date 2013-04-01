package com.moomoohk.Grame.Essentials;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Graphics.GridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;
import com.moomoohk.Grame.Interfaces.Render;

public class GrameManager implements Runnable
{
	private static ArrayList<GrameObject> grameObjectList;
	private static ArrayList<Base> baseList;
	private static HashMap<String, Render> renders = new HashMap<String, Render>();
	private static HashMap<String, MovementAI> ais = new HashMap<String, MovementAI>();
	private static boolean running = false;
	private static boolean debug = false;
	private static boolean disablePrints = false;
	private static boolean initialized = false;
	private static boolean spam=false;
	private static String gameName = "Grame";
	private static InputHandler input;
	private static Thread thread;
	private static int fps = 0;
	private static int time = 1;
	public static Dir dir1 = null, dir2 = null;
	private static Render defaultRender = new GridRender();
	public static final String VERSION_NUMBER = "3.0";
	private static boolean showConsole;

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
				GrameUtils.print("Exiting...", MessageLevel.NORMAL);
			}
		}));
		System.setOut(new GrameManager().new OutputOverride(System.out));
		System.setErr(new GrameManager().new OutputOverride(System.err));
		GrameUtils.console.setVisible(true);
		grameObjectList=new ArrayList<GrameObject>();
		baseList = new ArrayList<Base>();
		input = new InputHandler();
		showConsole=false;
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
		GrameUtils.print("Grame "+VERSION_NUMBER+" is initialized.", MessageLevel.NORMAL);
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
					fps=frames;
					prev += 1000L;
					frames = 0;
				}
				RenderManager.tick();
				//tickEnts();
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
			for (int i=0; i<grameObjectList.size(); i++)
			{
				GrameObject go=grameObjectList.get(i);
				if (go == null )
					continue;
				if(baseList!=null)
					for(int bID=0; bID<baseList.size(); bID++)
						if (findBase(bID).containsGrameObject(go.ID)&&go.getSpeed()!=0&&time % go.getSpeed() == 0&& !go.isPaused())
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

	public static int add(GrameObject go)
	{
		grameObjectList.add(go);
		GrameUtils.print("Added " + go.getName() + " to the Grame Objects list (ID:" + (grameObjectList.size() - 1) + ")", MessageLevel.NORMAL);
		return grameObjectList.size() - 1;
	}

	public static int add(Base b)
	{
		try
		{
			baseList.add(b);
			GrameUtils.print("Added a Base to the Base list (ID:" + (baseList.size() - 1) + ")", MessageLevel.NORMAL);
			return baseList.size() - 1;
		}
		catch (Exception e)
		{
			CrashManager.showException(e);
		}
		return -1;
	}

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

	public static void setGameName(String name)
	{
		gameName = name;
	}
	
	public static String getGameName()
	{
		return gameName;
	}

	public static void setDefaultRender(Render render)
	{
		defaultRender = render;
	}
	
	public static Render getDefaultRender()
	{
		return defaultRender;
	}

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

	public static void pauseAllGrameObjects(boolean f)
	{
		for (GrameObject go : grameObjectList)
			go.pause(f);
	}

	public static void setDebug(boolean debug)
	{
		GrameManager.debug = debug;
	}

	public static boolean isDebug()
	{
		return debug;
	}

	public static void setDisablePrints(boolean disablePrints)
	{
		GrameManager.disablePrints = disablePrints;
	}

	public static boolean isDisablePrints()
	{
		return disablePrints;
	}

	public static boolean isInitialized()
	{
		return initialized;
	}

	public static void setSpam(boolean spam)
	{
		GrameManager.spam = spam;
	}

	public static boolean isSpam()
	{
		return spam;
	}

	public static int getFps()
	{
		return fps;
	}

	public static void showConsole(boolean showConsole)
	{
		GrameManager.showConsole = showConsole;
	}
	
	public static boolean showingConsole()
	{
		return showConsole;
	}
	
	public static InputHandler getInputHandler()
	{
		return input;
	}
	
	public static HashMap<String, MovementAI> getAIs()
	{
		return ais;
	}
	
	public static HashMap<String, Render> getRenders()
	{
		return renders;
	}
	
	public static int getObjectListLength()
	{
		return grameObjectList.size();
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
				GrameUtils.print("[From Console (" + Thread.currentThread().getStackTrace()[9].getFileName().subSequence(0, Thread.currentThread().getStackTrace()[9].getFileName().indexOf(".java")) + ":" + Thread.currentThread().getStackTrace()[9].getLineNumber() + ")] " + text, MessageLevel.NORMAL);
		}

		@Override
		public void write(byte[] buf, int off, int len)
		{
			super.write(buf, off, len);
			String text = new String(buf, off, len).trim();
			if (!text.equals("") && !text.equals("\n"))
				GrameUtils.print("[From Console (" + Thread.currentThread().getStackTrace()[9].getFileName().subSequence(0, Thread.currentThread().getStackTrace()[9].getFileName().indexOf(".java")) + ":" + Thread.currentThread().getStackTrace()[9].getLineNumber() + ")] " + text, MessageLevel.NORMAL);
		}

		@Override
		public void write(int b)
		{
			throw new UnsupportedOperationException("Write(int) is not supported by OutputOverride.");
		}
	}
}
