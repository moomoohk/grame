package com.moomoohk.Grame.Core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.moomoohk.Grame.Basics.AI.AStarPathfindingMovementAI;
import com.moomoohk.Grame.Basics.AI.PlayerMovementAI;
import com.moomoohk.Grame.Basics.AI.PlayerSimAI;
import com.moomoohk.Grame.Basics.AI.SimpleChaseAI;
import com.moomoohk.Grame.Basics.AI.SimpleStrollAI;
import com.moomoohk.Grame.Core.Commands.AddEntityAICommand;
import com.moomoohk.Grame.Core.Commands.AddGrameObjectCommand;
import com.moomoohk.Grame.Core.Commands.ClearEntityAI;
import com.moomoohk.Grame.Core.Commands.CreateEntityCommand;
import com.moomoohk.Grame.Core.Commands.DrawCoordinatesCommand;
import com.moomoohk.Grame.Core.Commands.IsOccupiedCommand;
import com.moomoohk.Grame.Core.Commands.MakePlayerCommand;
import com.moomoohk.Grame.Core.Commands.MoveGrameObjectCommand;
import com.moomoohk.Grame.Core.Commands.PrintEntityAICommand;
import com.moomoohk.Grame.Core.Commands.QuitCommand;
import com.moomoohk.Grame.Core.Commands.RenderGridCommand;
import com.moomoohk.Grame.Core.Commands.SetEntityOverrideAICommand;
import com.moomoohk.Grame.Core.Commands.SetSpeedCommand;
import com.moomoohk.Grame.Core.Commands.SetSpriteCommand;
import com.moomoohk.Grame.Core.Commands.SetVisibleCommand;
import com.moomoohk.Grame.Core.Commands.SetWraparoundCommand;
import com.moomoohk.Grame.Core.Commands.ShowDialogCommand;
import com.moomoohk.MooCommands.CommandsManager;
import com.moomoohk.MooConsole.Console;
import com.moomoohk.MooConsole.HelpCommand;

/**
 * The Grame Utils class provides useful utilities.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class GrameUtils
{
	/**
	 * Folder where {@link GrameObject}s can be saved.
	 */
	public static File saveFolder = new File("GrameObjects");
	/**
	 * Folder where sound files can be saved.
	 */
	public static File soundsFolder = new File("Sounds");
	/**
	 * {@link Console} for debug prints and command execution.
	 */
	public static Console console;
	private static boolean isDialog = false;
	private static ArrayList<String> wordQueue = new ArrayList<String>();

	static
	{
		try
		{
			console = new Console();
			console.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		catch (Error e)
		{
			System.out.println("You don't appear to have MooConsole installed!");
			System.out.println("Get it at: https://github.com/moomoohk/MooConsole/raw/master/Build/MooConsole.jar");
			System.exit(0);
		}
		loadBasicCommands();
		loadBasicAIs();
	}

	/**
	 * Writes a {@link String} out to a {@link File}.
	 * 
	 * @param f
	 *            {@link File} to write to.
	 * @param s
	 *            {@link String} to write.
	 * @return True if the process was completed successfully, else false.
	 */
	public static boolean write(File f, String s)
	{
		try
		{
			if (!f.exists())
				f.createNewFile();
			FileWriter stream = new FileWriter(f);
			BufferedWriter out = new BufferedWriter(stream);
			out.write(s);
			out.flush();
			out.close();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Reads a {@link File} and returns a {@link String} array where each place is a segment of text separated by a delimiter.
	 * 
	 * @param st
	 *            Path to file.
	 * @param delimiter
	 *            Delimiter to use.
	 * @return A {@link String} array.
	 */
	public static String[] readFile(String st, String delimiter)
	{
		try
		{
			File f = new File(st);
			Scanner scanner = new Scanner(new FileReader(f));
			int count = 0;
			while (scanner.hasNextLine())
			{
				scanner.nextLine();
				count++;
			}
			String[] contents = new String[count];
			scanner = new Scanner(new FileReader(f));
			scanner.useDelimiter(delimiter);
			for (int i = 0; i < count; i++)
			{
				String val = isolate(scanner.nextLine(), scanner.delimiter().toString());
				contents[i] = val;
			}
			return contents;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static String isolate(String line, String delimiter)
	{
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(delimiter);
		if (scanner.hasNext())
		{
			scanner.next();
			String value = scanner.next();
			return value.trim();
		}

		System.out.println("Empty or invalid line. Unable to process.");

		return "oops";
	}

	/**
	 * Creates and returns a random color.
	 * 
	 * @return A random color.
	 */
	public static Color randomColor()
	{
		Random r = new Random();
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}

	/**
	 * Will generate a random set of {@link Coordinates} which exist in the given {@link Grid}.
	 * 
	 * @param b
	 *            The {@link Grid} in which to get {@link Coordinates}.
	 * @return A random set of {@link Coordinates} which exist in the given {@link Grid}.
	 */
	public static Coordinates randomCoordinates(Grid b)
	{
		return new Coordinates(new Random().nextInt(b.getColumns()), new Random().nextInt(b.getRows()));
	}

	/**
	 * Deletes the Entity save folder.
	 */
	public static void deleteEntFolder()
	{
		System.err.println("Deleting entity folder...");
		File f = saveFolder;
		if (f.isDirectory())
		{
			boolean success = delete(f);
			if (success)
				System.err.println("Entity folder deleted successfully");
			else
				System.err.println("Error in deleting entity folder");
		}
		else
		{
			System.err.println("Entity folder not found");
		}
	}

	/**
	 * Deletes a {@link File} and all its children files.
	 * 
	 * @param f
	 *            {@link File} to delete.
	 * @return True if the operation was successful, else false.
	 */
	private static boolean delete(File f)
	{
		if (f.isDirectory())
		{
			String[] subs = f.list();
			for (int i = 0; i < subs.length; i++)
			{
				System.err.println("-Deleting " + subs[i]);
				boolean success = delete(new File(f, subs[i]));
				if (!success)
					return false;
			}
		}
		return f.delete();
	}

	/**
	 * Creates a dialog with buttons.
	 * 
	 * @param paneText
	 *            Text to show in the dialog.
	 * @param options
	 *            Names of buttons.
	 * @param title
	 *            Title of dialog.
	 * @param events
	 *            What happens when a button gets pressed.
	 */
	public static void makeDialog(String paneText, Object[] options, String title, Runnable[] events)
	{
		if (!isDialog)
		{
			isDialog = true;
			JOptionPane pane = new JOptionPane(paneText);
			pane.setOptions(options);
			JDialog dialog = pane.createDialog(new JFrame(), title);
			dialog.setSize(400, 150);
			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
			dialog.setLocation((screensize.width - dialog.getWidth()) / 2, (screensize.height - dialog.getHeight()) / 2);
			dialog.setVisible(true);
			Object obj = pane.getValue();
			int result = -1;
			for (int k = 0; k < options.length; k++)
				if (options[k].equals(obj))
					result = k;
			dialog.dispose();
			isDialog = false;
			if (result == -1)
				System.exit(0);
			events[result].run();
		}
	}

	/**
	 * Plays a sound.
	 * 
	 * @param file
	 *            The path to the sound file.
	 */
	public static void playSound(String file)
	{
		File soundFile = null;
		AudioInputStream audioStream = null;

		SourceDataLine sourceLine = null;
		String strFilename = soundsFolder + "/" + file;
		try
		{
			soundFile = new File(strFilename);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		try
		{
			audioStream = AudioSystem.getAudioInputStream(soundFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		AudioFormat audioFormat = audioStream.getFormat();
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
		try
		{
			sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open(audioFormat);
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		sourceLine.start();

		int nBytesRead = 0;
		byte[] abData = new byte[128000];
		while (nBytesRead != -1)
		{
			try
			{
				nBytesRead = audioStream.read(abData, 0, abData.length);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			if (nBytesRead < 0)
			{
				continue;
			}
		}

		sourceLine.drain();
		sourceLine.close();
	}

	/**
	 * Capitalizes a word.
	 * 
	 * @param word
	 *            Word to capitalize.
	 * @return The capitalized word.
	 */
	public static String capitalize(String word)
	{
		if (word.length() > 0)
			return word.substring(0, 1).toUpperCase() + word.substring(1, word.length()).toLowerCase();
		return "";
	}

	/**
	 * Prints messages out into the console.
	 * 
	 * @param st
	 *            The message to print.
	 * @param level
	 *            The {@link MessageLevel} of the message.
	 */
	public static synchronized void print(String st, MessageLevel level)
	{
		if (!GrameManager.isDisablePrints() && ((level == MessageLevel.DEBUG || level == MessageLevel.DEBUG_ERROR && GrameManager.isDebug()) || (level == MessageLevel.SPAM && GrameManager.isSpam()) || (level == MessageLevel.NORMAL) || (level == MessageLevel.ERROR)))
		{
			StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
			wordQueue.add("[" + caller.getFileName().subSequence(0, caller.getFileName().indexOf(".java")) + ":" + caller.getLineNumber() + "] " + st);
			Color printColor = Color.white;
			if (level == MessageLevel.ERROR || level == MessageLevel.DEBUG_ERROR)
				printColor = Color.red;
			else
				if (level == MessageLevel.DEBUG)
					printColor = Color.gray.brighter();
			console.addText(wordQueue.get(0) + "\n", printColor);
			wordQueue.remove(0);
		}
	}

	/**
	 * Will create a clickable JLabel which opens a URL.
	 * 
	 * @param text
	 *            JLabel text.
	 * @param URL
	 *            The URL to open on click.
	 * @param toolTip
	 *            The tooltip of the JLabel.
	 * @return A clickable JLabel.
	 */
	public static JLabel linkify(final String text, String URL, String toolTip)
	{
		URI temp = null;
		try
		{
			temp = new URI(URL);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		final URI uri = temp;
		final JLabel link = new JLabel();
		link.setText("<HTML><FONT color=\"#000099\">" + text + "</FONT></HTML>");
		if (!toolTip.equals(""))
			link.setToolTipText(toolTip);
		link.setCursor(new Cursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent arg0)
			{
				link.setText("<HTML><FONT color=\"#000099\">" + text + "</FONT></HTML>");
			}

			public void mouseEntered(MouseEvent arg0)
			{
				link.setText("<HTML><FONT color=\"#000099\"><U>" + text + "</U></FONT></HTML>");
			}

			public void mouseClicked(MouseEvent arg0)
			{
				if (Desktop.isDesktopSupported())
				{
					try
					{
						Desktop.getDesktop().browse(uri);
					}
					catch (Exception e)
					{
						CrashManager.showException(e);
					}
				}
				else
				{
					JOptionPane pane = new JOptionPane("Could not open link.");
					JDialog dialog = pane.createDialog(new JFrame(), "");
					dialog.setVisible(true);
				}
			}
		});
		return link;
	}

	private static void loadBasicCommands()
	{
		int prevLength = CommandsManager.getAllCommands().size();
		new HelpCommand();
		new SetVisibleCommand();
		new MoveGrameObjectCommand();
		new RenderGridCommand();
		new QuitCommand();
		new CreateEntityCommand();
		new AddGrameObjectCommand();
		new AddEntityAICommand();
		new MakePlayerCommand();
		new SetEntityOverrideAICommand();
		new ClearEntityAI();
		new PrintEntityAICommand();
		new IsOccupiedCommand();
		new DrawCoordinatesCommand();
		new SetSpeedCommand();
		new SetSpriteCommand();
		new SetWraparoundCommand();
		new ShowDialogCommand();
		print("Loaded " + (CommandsManager.getAllCommands().size() - prevLength) + " commands.", MessageLevel.DEBUG);
	}

	private static void loadBasicAIs()
	{
		GrameManager.addAI(new SimpleStrollAI());
		GrameManager.addAI(new PlayerMovementAI(1));
		GrameManager.addAI(new SimpleChaseAI());
		GrameManager.addAI(new PlayerSimAI());
		GrameManager.addAI(new AStarPathfindingMovementAI());
	}

	/**
	 * Prints out the current stack trace.
	 */
	public static void dumpStackTrace()
	{
		for (StackTraceElement ste : Thread.currentThread().getStackTrace())
			System.out.println(ste);
	}

	/**
	 * Used to denote print types.
	 * 
	 * @author Meshulam Silk <moomoohk@ymail.com>
	 * @version 1.0
	 * @since 2013-04-05
	 */
	public enum MessageLevel
	{
		/**
		 * Error messages.
		 */
		ERROR,
		/**
		 * Normal messages.
		 */
		NORMAL,
		/**
		 * Normal debug messages.
		 */
		DEBUG,
		/**
		 * Debug error messages.
		 */
		DEBUG_ERROR,
		/**
		 * Spam messages.
		 * <p>
		 * These are messages that might get sent repeatedly thus spamming the console.
		 */
		SPAM
	}
}
