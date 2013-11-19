package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Graphics.GridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MainGrameClass;
import com.moomoohk.Grame.Interfaces.MovementAI;
import com.moomoohk.Grame.Interfaces.Render;
import com.moomoohk.Grame.test.EngineState;
import com.moomoohk.Mootilities.FrameDragger.FrameDragger;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

/**
 * The Grame Manager takes care of all the internal Grame operations.
 * <p>
 * Indexes and ticks any {@link GrameObject}s and {@link Base}s that are created automatically.
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
	//	private static ArrayList<GrameObject> grameObjectList;
	//	private static ArrayList<Base> baseList;
	private static boolean initialized = false;
	private static EngineState engineState = null;
	private static HashMap<String, Render> renders = new HashMap<String, Render>();
	private static HashMap<String, MovementAI> ais = new HashMap<String, MovementAI>();
	private static String gameName;
	private static boolean running = false;
	private static boolean paused = false;
	private static boolean debug = false;
	private static boolean disablePrints = false;
	private static boolean spam = false;
	private static InputHandler input;
	private static Thread thread;
	private static int fps = 0;
	private static Render defaultRender = new GridRender();
	private static String savePath = OSUtils.getDynamicStorageLocation() + "Grame/Saves/";
	private static MainMenu mainMenu;

	public static void initialize(MainGrameClass mainClass)
	{
		if (initialized)
			return;
		initialized = true;
		GrameManager.setGameName(mainClass.getGameName());

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
		//		GrameUtils.console.setOutputOverride();
		//GrameUtils.console.setLocation(GrameUtils.console.getLocation().x, Toolkit.getDefaultToolkit().getScreenSize().height);
		mainMenu = new MainMenu(mainClass);
		mainMenu.setVisible(true);
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
	 * This method should never be called by the user as doing that might create issues.
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
				unprocessed -= secondsTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 != 0)
				{
					fps = frames;
					prev += 1000L;
					frames = 0;
				}
				if (engineState != null && !paused)
				{
					tick(input.key);
					RenderManager.tick();
					tickGrameObjects();
					tickBases();
				}
			}

			if (ticked)
			{
				frames++;
			}
			frames++;
		}
		RenderManager.dispose();
		System.gc();
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
		{
			key[27] = false;
			paused = true;
			mainMenu.setVisible(true);
		}
	}

	private static void tickGrameObjects()
	{
		try
		{
			for (int i = 0; i < engineState.getGrameObjects().size(); i++)
			{
				GrameObject go = engineState.getGrameObjects().get(i);
				if (go == null)
					continue;
				if (engineState.getBases() != null)
					for (int bID = 0; bID < engineState.getBases().size(); bID++)
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
		if (engineState.getBases() == null)
			return;
		synchronized (engineState.getBases())
		{
			try
			{
				for (Base b : engineState.getBases())
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
		if (engineState != null)
			for (int i = 0; i < engineState.getBases().size(); i++)
			{
				GrameUtils.print("Disposing of " + engineState.getBases().get(i).ID, MessageLevel.NORMAL);
				engineState.getBases().remove(i);
			}
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
		if (engineState.getGrameObjects().contains(go))
		{
			GrameUtils.print(go.getName() + " already exists in the Grame Object list!", MessageLevel.ERROR);
			return -1;
		}
		engineState.getGrameObjects().add(go);
		GrameUtils.print("Added " + go.getName() + " to the Grame Objects list (ID:" + (engineState.getGrameObjects().size() - 1) + ")", MessageLevel.NORMAL);
		return engineState.getGrameObjects().size() - 1;
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
		if (engineState.getBases().contains(b))
		{
			GrameUtils.print(b.toString() + " already exists in the Grame Object list!", MessageLevel.ERROR);
			return -1;
		}
		engineState.getBases().add(b);
		GrameUtils.print("Added a Base to the Base list (ID:" + (engineState.getBases().size() - 1) + ")", MessageLevel.NORMAL);
		return engineState.getBases().size() - 1;
	}

	/**
	 * Finds and returns a {@link GrameObject} from the Grame Objects list.<br>
	 * If the object is not found, null will be returned.
	 * 
	 * @param id
	 *            The ID of the object to find.
	 * @return The {@link GrameObject} with that ID. If not in the list, null will be returned.
	 */
	public static GrameObject findGrameObject(int id)
	{
		try
		{
			return engineState.getGrameObjects().get(id);
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
	 * 
	 * @param id
	 *            The ID of the {@link Base} to find.
	 * @return The {@link Base} with that ID. If not in the list, null will be returned.
	 */
	public static Base findBase(int id)
	{
		try
		{
			return engineState.getBases().get(id);
		}
		catch (Exception e)
		{
			GrameUtils.print("Base with ID:" + id + " not found! Returning null instead.", MessageLevel.ERROR);
		}
		return null;
	}

	/**
	 * Sets the default {@link Render}.
	 * 
	 * @param render
	 *            {@link Render} to set.
	 */
	public static void setDefaultRender(Render render)
	{
		defaultRender = render;
	}

	/**
	 * Gets the default {@link Render}.
	 * 
	 * @return The default {@link Render}.
	 */
	public static Render getDefaultRender()
	{
		return defaultRender;
	}

	/**
	 * Indexes a {@link Render}.
	 * 
	 * @param render
	 *            {@link Render} to index.
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
	 * 
	 * @param ai
	 *            {@link MovementAI} to index.
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
	 * 
	 * @param f
	 *            True to pause, false to unpause all the {@link GrameObject}.
	 */
	public static void pauseAllGrameObjects(boolean f)
	{
		for (GrameObject go : engineState.getGrameObjects())
			go.pause(f);
	}

	/**
	 * Sets whether debug prints will be visible or not.
	 * 
	 * @param debug
	 *            True to enable debug prints, else false.
	 */
	public static void setDebug(boolean debug)
	{
		GrameManager.debug = debug;
	}

	/**
	 * Returns whether or not debug prints are enabled.
	 * 
	 * @return True if debug prints are enabled, else false.
	 */
	public static boolean isDebug()
	{
		return debug;
	}

	public static void setGameName(String gameName)
	{
		GrameManager.gameName = gameName;
	}

	public static String getGameName()
	{
		return gameName;
	}

	/**
	 * Completely disable prints.
	 * 
	 * @param disablePrints
	 *            True to disable all prints, else false.
	 */
	public static void setDisablePrints(boolean disablePrints)
	{
		GrameManager.disablePrints = disablePrints;
	}

	/**
	 * Returns whether or not prints are completely disabled.
	 * 
	 * @return True if prints are completely disabled, else false.
	 */
	public static boolean isDisablePrints()
	{
		return disablePrints;
	}

	/**
	 * Sets whether spam prints will be visible or not.
	 * 
	 * @param spam
	 *            True to enable spam prints, else false.
	 */
	public static void setSpam(boolean spam)
	{
		GrameManager.spam = spam;
	}

	/**
	 * Returns whether or not spam prints are enabled.
	 * 
	 * @return True if spam prints are enabled, else false.
	 */
	public static boolean isSpam()
	{
		return spam;
	}

	/**
	 * Gets the {@link InputHandler} that is currently being used.
	 * 
	 * @return The {@link InputHandler} that is currently being used.
	 */
	public static InputHandler getInputHandler()
	{
		return input;
	}

	/**
	 * Gets all the loaded {@link MovementAI}s sorted in a HashMap<String, {@link MovementAI}> where the names (spaces replaced with '-') are the keys.
	 * 
	 * @return A HashMap<String, {@link MovementAI}> of all loaded {@link MovementAI}s.
	 */
	public static HashMap<String, MovementAI> getAIs()
	{
		return ais;
	}

	/**
	 * Gets all the loaded {@link Render}s sorted in a HashMap<String, {@link Render}> where the names (spaces replaced with '-') are the keys.
	 * 
	 * @return A HashMap<String, {@link Render}> of all loaded {@link Render}s.
	 */
	public static HashMap<String, Render> getRenders()
	{
		return renders;
	}

	/**
	 * Gets the size of the {@link GrameObject} list.
	 * 
	 * @return The size of the {@link GrameObject} list.
	 */
	public static int getObjectListLength()
	{
		return engineState.getGrameObjects().size();
	}

	/**
	 * Gets the current FPS (Frames Per Second) count.
	 * 
	 * @return The current FPS (Frames Per Second) count.
	 */
	public static int getFPS()
	{
		return fps;
	}

	private static class MainMenu extends JFrame
	{
		private static final long serialVersionUID = -2989260620184596791L;
		private JPanel contentPane;

		private MenuButton btnResume, btnNewGame, btnLoadGame, btnSettings, btnEndGame;

		private static final Rectangle BUTTON1 = new Rectangle(10, 50, 130, 30), BUTTON2 = new Rectangle(20, 90, 110, 30), BUTTON3 = new Rectangle(20, 130, 110, 30), BUTTON4 = new Rectangle(20, 170, 110, 30), BUTTON5 = new Rectangle(20, 210, 110, 30);

		public MainMenu(final MainGrameClass mainClass)
		{
			setUndecorated(true);
			setResizable(false);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(600, 400);
			setLocationRelativeTo(null);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JLabel lblGameName = new JLabel(gameName);
			lblGameName.setFont(new Font("Lucida Grande", Font.BOLD, 22));
			lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
			lblGameName.setBounds(20, 6, 560, 33);
			contentPane.add(lblGameName);

			btnResume = new MenuButton("Resume Game", Color.red, Color.yellow, Color.blue);
			btnResume.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					dispose();
					paused = false;
				}
			});
			contentPane.add(btnResume);

			btnNewGame = new MenuButton("New Game", Color.red, Color.yellow, Color.blue);
			btnNewGame.setBounds(BUTTON1);
			btnNewGame.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					dispose();
					GrameUtils.console.setVisible(true);
					RenderManager.clearAllText();
					engineState = new EngineState();
					mainClass.newGame();
					if (!running)
						start();
					paused = false;
				}
			});
			contentPane.add(btnNewGame);

			btnLoadGame = new MenuButton("Load Game", Color.red, Color.yellow, Color.blue);
			btnLoadGame.setEnabled(false);
			btnLoadGame.setBounds(BUTTON2);
			btnLoadGame.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					System.out.println("Path: " + savePath);
				}
			});
			contentPane.add(btnLoadGame);

			btnSettings = new MenuButton("Settings", Color.red, Color.yellow, Color.blue);
			btnSettings.setBounds(BUTTON3);
			btnSettings.setEnabled(false);
			contentPane.add(btnSettings);

			btnEndGame = new MenuButton("End Game", Color.red, Color.yellow, Color.blue);
			btnEndGame.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					paused = false;
					stop();
					updateButtons();
				}
			});
			contentPane.add(btnEndGame);

			JPanel panel = new JPanel();
			panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			panel.setBounds(150, 50, 430, 290);
			contentPane.add(panel);

			final JLabel lblMadeWithGrame = new JLabel("Made with moomoohk's Grame engine");
			lblMadeWithGrame.addMouseListener(new MouseAdapter()
			{
				public void mouseEntered(MouseEvent me)
				{
					lblMadeWithGrame.setText("Version: " + GrameManager.VERSION_NUMBER);
				}

				public void mouseExited(MouseEvent me)
				{
					lblMadeWithGrame.setText("Made with moomoohk's Grame engine");
				}
			});
			lblMadeWithGrame.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 11));
			lblMadeWithGrame.setForeground(Color.DARK_GRAY);
			lblMadeWithGrame.setBounds(18, 353, 421, 37);
			contentPane.add(lblMadeWithGrame);

			MenuButton btnQuit = new MenuButton("Quit", Color.red, Color.yellow, Color.blue);
			btnQuit.setBounds(450, 350, 130, 40);
			btnQuit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					System.exit(0);
				}
			});
			contentPane.add(btnQuit);

			new FrameDragger().applyTo(this);
		}

		public void updateButtons()
		{
			if (paused)
			{
				btnResume.setBounds(BUTTON1);
				btnNewGame.setBounds(BUTTON2);
				btnLoadGame.setBounds(BUTTON3);
				btnSettings.setBounds(BUTTON4);
				btnEndGame.setBounds(BUTTON5);
			}
			else
			{
				btnResume.setBounds(0, 0, 0, 0);
				btnNewGame.setBounds(BUTTON1);
				btnLoadGame.setBounds(BUTTON2);
				btnSettings.setBounds(BUTTON3);
				btnEndGame.setBounds(0, 0, 0, 0);
			}
		}

		public void setVisible(boolean f)
		{
			updateButtons();
			super.setVisible(f);
		}

		private static class MenuButton extends JButton
		{
			private static final long serialVersionUID = -2192610213120657509L;

			public boolean mouseOn = false, mouseDown = false;
			private double animTime = 0;
			private Color startColor, endColor, clickColor, fill;
			private Timer t = new Timer(10, new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					repaint();
					animTime += 0.03;
				}
			});

			public MenuButton(String text, Color startColor, Color endColor, Color clickColor)
			{
				super(text);
				this.startColor = startColor;
				this.endColor = endColor;
				this.clickColor = clickColor;
				this.fill = this.startColor;
				addMouseListener(new MouseAdapter()
				{

					public void mouseReleased(MouseEvent arg0)
					{
						mouseDown = false;
						repaint();
						t.stop();
					}

					@Override
					public void mousePressed(MouseEvent arg0)
					{
						mouseDown = true;
						repaint();
						animTime = 0;
						t.start();
					}

					@Override
					public void mouseExited(MouseEvent arg0)
					{
						mouseOn = false;
						repaint();
					}

					@Override
					public void mouseEntered(MouseEvent arg0)
					{
						mouseOn = true;
						repaint();
						animTime = 0;
						t.start();
					}
				});
			}

			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (isEnabled())
				{
					if (mouseDown && mouseOn)
						fill = clickColor;
					else
					{
						if (mouseOn)
							fill = new Color((int) (endColor.getRed() * Math.abs(Math.sin(animTime)) + startColor.getRed() * (1 - Math.abs(Math.sin(animTime)))), (int) (endColor.getGreen() * Math.abs(Math.sin(animTime)) + startColor.getGreen() * (1 - Math.abs(Math.sin(animTime)))),
									(int) (endColor.getBlue() * Math.abs(Math.sin(animTime)) + startColor.getBlue() * (1 - Math.abs(Math.sin(animTime)))));
						else
						{
							fill = new Color((int) (fill.getRed() * Math.abs(Math.sin(animTime)) + startColor.getRed() * (1 - Math.abs(Math.sin(animTime)))), (int) (fill.getGreen() * Math.abs(Math.sin(animTime)) + startColor.getGreen() * (1 - Math.abs(Math.sin(animTime)))), (int) (fill.getBlue()
									* Math.abs(Math.sin(animTime)) + startColor.getBlue() * (1 - Math.abs(Math.sin(animTime)))));
							if (fill.equals(startColor))
							{
								t.stop();
								if (mouseDown)
									fill = clickColor;
								else
									fill = startColor;
							}
						}
					}
					g2.setPaint(fill);
				}
				else
				{
					g2.setPaint(Color.LIGHT_GRAY);
				}
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
				g2.setPaint(Color.black);
				if (mouseOn && isEnabled())
					g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 6, 6);
				else
					g2.fillRoundRect(3, 3, getWidth() - 6, getHeight() - 6, 6, 6);
				g2.setPaint(Color.white);
				FontMetrics fm = g2.getFontMetrics();
				g2.drawString(getText(), (getWidth() / 2) - (fm.stringWidth(getText()) / 2), (getHeight() / 2) + 4);
				g2.dispose();
			}
		}
	}
}
