package com.moomoohk.Grame.Core;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;

import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;

/**
 * Manages crashes and exceptions.
 *
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-04-05
 */
public class CrashManager
{
	private static JFrame crashF;
	private static JButton email, copy;
	private static JTextPane crashlogPane;
	private static String user = "Not given";
	private static Thread t = new Thread();
	private static boolean isOpen = false;
	private static boolean reasonProvided = false;
	private static String reason = "";
	private static Exception exception = null;

	/**
	 * Displays an {@link Exception} and any relevant info about it in a window.
	 *
	 * @param e
	 *            The {@link Exception} to show.
	 * @param reason
	 *            Any reasons that might've caused the {@link Exception}.
	 */
	public static void showException(Exception e, String reason)
	{
		if (isOpen)
			return;
		e.printStackTrace();
		isOpen = true;
		GrameUtils.print("Crash detected.", MessageLevel.NORMAL);
		try
		{
			GrameUtils.print("Stopping main thread...", MessageLevel.DEBUG);
			GrameManager.stop();
		}
		catch (Exception ex)
		{
			GrameUtils.print("(Fatal error) //Grame Manager not initialized.", MessageLevel.NORMAL);
		}
		GrameUtils.print("Gathering crash info...", MessageLevel.DEBUG);
		String line = "0";
		String className = "Unknown", method = "Unknown", error = "";
		for (int i = e.getStackTrace().length - 1; i >= 0; i--)
		{
			error += "  at " + e.getStackTrace()[e.getStackTrace().length - 1 - i] + "\n";
			line = "" + e.getStackTrace()[i].getLineNumber();
			className = e.getStackTrace()[i].getClassName().substring(e.getStackTrace()[i].getClassName().indexOf('.') + 1);
			method = e.getStackTrace()[i].getMethodName();
		}
		String OS = "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version") + "\n";
		String title = "- CRASHLOG -\nGame: " + GrameManager.getGameName() + "\n";
		String GrameV = "Version: " + GrameManager.VERSION_NUMBER + "\n";
		String exception = "Exception: " + e.getClass().getSimpleName() + "\n";
		String cause = "Cause: " + e.getLocalizedMessage() + "\n";
		className = "Class: " + className + "\n";
		method = "Method: " + method + "\n";
		line = "Line: " + line + "\n";
		error = "Full error: \n" + error + "\n";
		GrameUtils.print("Figuring out possible causes...", MessageLevel.DEBUG);
		if ((reason != null) && (!reason.equals("")))
		{
			reasonProvided = true;
			CrashManager.reason = reason;
		}
		CrashManager.exception = e;
		String possCauses = "Could be: \n" + getPossibleCause(className.substring(7)) + "\n";
		final String message = title + GrameV + exception + cause + className + method + line + OS + "\n\n" + error + "\n" + possCauses;
		GrameUtils.print("Setting up window GUI...", MessageLevel.DEBUG);
		crashF = new JFrame("CRASH!!!");
		crashF.setLayout(new FlowLayout());
		crashF.setResizable(false);
		crashF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		crashF.setSize(new Dimension(424, 350));
		JPanel changelogPanel = new JPanel(new FlowLayout());
		crashlogPane = new JTextPane()
		{

			private static final long serialVersionUID = 1L;

			@Override
			public boolean getScrollableTracksViewportWidth()
			{
				return getUI().getPreferredSize(this).width <= getParent().getSize().width;
			}
		};
		crashlogPane.setText(message);
		crashlogPane.setEditable(false);
		crashlogPane.setOpaque(false);
		JScrollPane scroller = new JScrollPane(crashlogPane);
		scroller.setPreferredSize(new Dimension(400, 150));
		final JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		quit.registerKeyboardAction(quit.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		quit.registerKeyboardAction(quit.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		copy = new JButton("Copy to clipboard");
		copy.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				GrameUtils.print("Copying...", MessageLevel.DEBUG);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(message), new ClipboardOwner()
				{
					@Override
					public void lostOwnership(Clipboard paramClipboard, Transferable paramTransferable)
					{

					}
				});
				copy.setEnabled(false);
				copy.setText("        Copied!        ");
				email.setEnabled(false);
				crashlogPane.setEnabled(false);
				quit.requestFocus();
			}
		});
		copy.registerKeyboardAction(copy.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		copy.registerKeyboardAction(copy.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		email = new JButton("E-Mail log");
		email.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GrameUtils.print("Passing to emailer...", MessageLevel.DEBUG);
				getAddress(message);
			}
		});
		email.addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent arg0)
			{
				if (email.isEnabled() && crashF.hasFocus())
					email.requestFocus();
			}

			@Override
			public void focusGained(FocusEvent arg0)
			{

			}
		});
		email.registerKeyboardAction(email.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), JComponent.WHEN_FOCUSED);
		email.registerKeyboardAction(email.getActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true)), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
		crashF.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent arg0)
			{
				if (email.isEnabled())
				{
					crashF.getRootPane().setDefaultButton(email);
					email.requestFocus();
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{

			}
		});
		crashF.add(new JLabel("<HTML><center>Oh noes!!!<BR>It seems I've crashed<BR>NOTE: This could be a problem with your code.<BR>Use the info below to find out.<BR>Only email the log if it's not your bug. Thanks.<BR>-------------------------------------------<BR></center></HTML>"), "North");
		changelogPanel.add(scroller, "South");
		crashF.add(changelogPanel, "Center");
		crashF.add(copy, "South");
		email.setEnabled(false);
		crashF.add(email, "South");
		crashF.add(quit, "South");
		crashF.setLocationRelativeTo(null);
		GrameUtils.print("Showing window...", MessageLevel.DEBUG);
		crashF.setVisible(true);
		email.requestFocus();
		crashF.getRootPane().setDefaultButton(email);
		GrameUtils.print("Window is visible.", MessageLevel.DEBUG);
	}

	/**
	 * Displays an {@link Exception} and any relevant info about it in a window.
	 *
	 * @param e
	 *            The {@link Exception} to show.
	 */
	public static void showException(Exception e)
	{
		showException(e, null);
	}

	private static void getAddress(final String message)
	{
		GrameUtils.print("Setting up window GUI...", MessageLevel.DEBUG);
		final JDialog f = new JDialog();
		f.setResizable(false);
		f.setSize(520, 120);
		JPanel p = new JPanel();
		final JTextField field = new JTextField(20);
		final JButton done = new JButton("Done");
		field.addKeyListener(new KeyListener()
		{

			@Override
			public void keyTyped(KeyEvent paramKeyEvent)
			{
			}

			@Override
			public void keyReleased(KeyEvent paramKeyEvent)
			{
				if (isValidEmailAddress(field.getText()) || field.getText().length() == 0)
					done.setEnabled(true);
				else
					done.setEnabled(false);
			}

			@Override
			public void keyPressed(KeyEvent paramKeyEvent)
			{
			}
		});
		JLabel label = new JLabel("<HTML><center>Please enter your e-mail address as I might need it to contact you in the future:</center></HTML>");
		JLabel label2 = new JLabel("<HTML><center>(I will under no circustances give it away. If you don't believe me, leave it blank)</center></HTML>");
		done.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				GrameUtils.print("Emailing...", MessageLevel.DEBUG);
				f.setVisible(false);
				if (field.getText().length() > 0)
					user = field.getText();
				Thread emailThread = new Thread(new CrashManager().new Emailer(message + "\n\nReply to: " + user, "moomoohkscrashbot@gmail.com", "donthackthis", "meshulamhk@gmail.com", true));
				emailThread.start();
				Thread emailThread2 = new Thread(
						new CrashManager().new Emailer(
								"[This is an automated message. If you have no idea what this is about, kindly disregard this email]\nThanks for sending in your crash log.\nI'll try to fix the bug based on the info you provided.\nIf I have further inquiries as to what led up to the crash, I'll let you know.\n\n-Meshulam Silk (moomoohk)",
								"moomoohkscrashbot@gmail.com", "donthackthis", field.getText(), false));
				if (field.getText() != null && !field.getText().equals(""))
					emailThread2.start();
			}
		});
		p.add(label);
		p.add(field);
		p.add(done);
		p.add(label2);
		f.add(p);
		f.setModal(true);
		f.setLocationRelativeTo(null);
		GrameUtils.print("Showing window...", MessageLevel.DEBUG);
		f.setVisible(true);
	}

	private static String getPossibleCause(String className)
	{
		if (reasonProvided)
		{
			Scanner parser = new Scanner(reason);
			parser.useDelimiter(":");
			String newReason = "";
			while (parser.hasNext())
				if (exception.toString().equals(parser.next().trim()))
					newReason += parser.next() + "\n";
			parser.close();
			if (newReason.equals(""))
				return "Unknown";
			return newReason;
		}
		String cause = "";
		if (className.contains("Entity"))
		{
			if (exception.toString().equals("java.lang,NullPointerException"))
			{
				cause += "Entity isn't in the Entity list\n";
				cause += "Base isn't in the Base list\n";
				cause += "Tried to reference null\n";
			}
			if (exception.toString().equals("java.lang.InterruptedException"))
				cause += "Problem with joining thread\n";
			if (exception.toString().equals("java.lang.ArrayIndexOutOfBoundsException"))
			{
				cause += "Entity not in Entity list\n";
				cause += "Base not in Base list\n";
			}
		}
		if (className.contains("//GrameManager"))
			if (exception.toString().equals("java.lang.NullPointerException"))
				cause += "//Grame Manager not initialized\n";
		if (className.contains("Dir"))
			if (exception.toString().equals("java.lang.NullPointerException"))
				cause += "Passed a null set of Coordinates\n";
		if (className.contains("//GrameUtils"))
		{
			if (exception.toString().equals("java.io.IOException"))
			{
				cause += "Invalid URI\n";
				cause += "Couldn't open connection\n";
			}
			if (exception.toString().equals("java.net.MalformedURLException"))
				cause += "Invalid URI\n";
			if (exception.toString().equals("java.io.FileNotFoundException"))
				cause += "Bad file\n";
		}
		if (cause.equals(""))
			cause = "Unknown";
		if (cause.equals("Unknown"))
			GrameUtils.print("Unknown reason for " + exception.getClass().getSimpleName() + " from class " + className, MessageLevel.DEBUG);
		return cause;
	}

	private static boolean isValidEmailAddress(String email)
	{
		boolean result = true;
		try
		{
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
			if (email.contains("@") && ((email.charAt(email.length() - 3) == '.' && email.charAt(email.length() - 1) != '.') || email.charAt(email.length() - 4) == '.' || email.charAt(email.length() - 5) == '.'))
				result = true;
			else
				result = false;
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}

	private class Emailer implements Runnable
	{
		private JDialog progD;
		private JPanel progP;
		private JProgressBar progBar;
		private final String message, from, pass, to;
		private final boolean visible;

		public Emailer(String message, String from, String pass, String to, boolean visible)
		{
			this.message = message;
			this.from = from;
			this.pass = pass;
			this.to = to;
			this.visible = visible;
		}

		@Override
		public void run()
		{

			if (this.visible)
				EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						JButton cancel = new JButton("Cancel");
						cancel.addActionListener(new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent arg0)
							{
								t.interrupt();
							}
						});
						// cancel.setEnabled(false);
						Emailer.this.progD = new JDialog();
						Emailer.this.progD.setTitle("Sending...");
						Emailer.this.progD.setModal(true);
						Emailer.this.progD.setResizable(false);
						Emailer.this.progD.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						Emailer.this.progD.setLayout(new FlowLayout());
						Emailer.this.progP = new JPanel(new FlowLayout());
						Emailer.this.progBar = new JProgressBar();
						Emailer.this.progBar.setIndeterminate(true);
						Emailer.this.progP.add(Emailer.this.progBar, "Center");
						Emailer.this.progP.add(cancel, "South");
						Emailer.this.progBar.setVisible(true);
						Emailer.this.progD.add(Emailer.this.progP, "South");
						Emailer.this.progD.setSize(300, 80);
						Emailer.this.progP.setSize(250, 100);
						Emailer.this.progD.setLocationRelativeTo(null);
						Emailer.this.progD.setVisible(true);
					}
				});
			GrameUtils.print("Emailing to " + this.to + ".", MessageLevel.DEBUG);
			final String host = "smtp.gmail.com";
			Properties props = System.getProperties();
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.user", this.from);
			props.put("mail.smtp.password", this.pass);
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");

			final Session session = Session.getDefaultInstance(props, null);
			final MimeMessage message = new MimeMessage(session);
			try
			{
				message.setFrom(new InternetAddress(this.from));
			}
			catch (AddressException e)
			{
				e.printStackTrace();
			}
			catch (MessagingException e)
			{
				e.printStackTrace();
			}

			final String m = this.message;
			t = new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						InternetAddress toAddress = new InternetAddress(Emailer.this.to);
						message.addRecipient(Message.RecipientType.TO, toAddress);
						message.setSubject("Crash log");
						message.setText(m);
						Transport transport = session.getTransport("smtp");
						transport.connect(host, Emailer.this.from, Emailer.this.pass);
						transport.sendMessage(message, message.getAllRecipients());
						transport.close();
					}
					catch (Exception e)
					{
						GrameUtils.print("Email failed. (" + e.getMessage() + ")", MessageLevel.DEBUG);
						e.printStackTrace();
						Emailer.this.progD.setVisible(false);
						Emailer.this.progD.dispose();
						System.err.println("Failed email");
						JOptionPane.showMessageDialog(new JFrame(), "Something broke while trying to email log.\nI would recommend copying the log and sending it manually to:\nmoomoohkscrashbot@gmail.com\nOr saving it and trying again later.", "Uh Oh", JOptionPane.PLAIN_MESSAGE);
						email.setText("Failed :(");
						email.setEnabled(false);
						copy.setEnabled(true);
						copy.requestFocus();
						crashF.setEnabled(true);
						return;
					}
				}
			});
			if (this.visible)
			{
				EventQueue.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						Emailer.this.progD.setVisible(false);
						Emailer.this.progD.dispose();
					}
				});
				JOptionPane.showMessageDialog(new JFrame(), "Crash log sent successfully!", "Thanks!", JOptionPane.PLAIN_MESSAGE);
				email.setText("    Sent!     ");
				email.setEnabled(false);
				copy.setEnabled(false);
				crashlogPane.setEnabled(false);
				crashF.setEnabled(true);
			}
		}
	}
}
