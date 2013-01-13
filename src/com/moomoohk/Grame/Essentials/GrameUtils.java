package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class GrameUtils
{
	public static final String VERSION_NUMBER = "2.0";
	public static File saveFolder = new File("Entities");
	public static File soundsFolder = new File("Sounds");
	private static boolean isDialog = false;
	public static ArrayList<String> wordQueue = new ArrayList<String>();

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

	public static Color randomColor()
	{
		Random r = new Random();
		return new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
	}

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

	public static String capitalize(String word)
	{
		if (word.length() > 0)
			return word.substring(0, 1).toUpperCase() + word.substring(1, word.length()).toLowerCase();
		return "";
	}

	public static synchronized void print(String st, String sender, boolean debug)
	{
		if ((!GrameManager.disablePrints) && (((debug) && (GrameManager.debug)) || (!debug)))
		{
			wordQueue.add("[" + sender + "]: " + st);
			System.out.println((String) wordQueue.get(0));
			wordQueue.remove(0);
			/*if(GrameManager.debug)
				for (StackTraceElement ste : Thread.currentThread().getStackTrace())
					System.out.println(ste);*/
		}
	}

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
		link.addMouseListener(new MouseListener()
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

			public void mousePressed(MouseEvent e)
			{
			}

			public void mouseReleased(MouseEvent e)
			{
			}
		});
		return link;
	}

}
