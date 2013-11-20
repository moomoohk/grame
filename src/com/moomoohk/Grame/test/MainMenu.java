package com.moomoohk.Grame.test;

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
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.MainMenuPanels.LoadGamePanel;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MainGrameClass;
import com.moomoohk.Mootilities.FrameDragger.FrameDragger;
import com.moomoohk.Mootilities.OSUtils.OSUtils;

public class MainMenu extends JFrame
{
	private static final long serialVersionUID = -2989260620184596791L;
	private static JPanel contentPane;
	private static MenuButton btnResume, btnNewGame, btnSaveGame, btnLoadGame, btnSettings, btnEndGame;
	private static JLabel lblMadeWithGrame;
	public static JScrollPane sidePanel;
	public static MouseListener helpTextListener = (new MouseAdapter()
	{
		public void mouseEntered(MouseEvent me)
		{
			lblMadeWithGrame.setText(((MenuButton) me.getSource()).getHelpText());
		}

		public void mouseExited(MouseEvent me)
		{
			lblMadeWithGrame.setText("");
		}

		public void mouseReleased(MouseEvent me)
		{
			if (((MenuButton) me.getSource()).isEnabled())
				lblMadeWithGrame.setText("");
		}
	});
	private static String savePath = OSUtils.getDynamicStorageLocation() + "Grame/Saves/Test/";
	private static boolean paused = false;
	private static final Rectangle BUTTON1 = new Rectangle(10, 50, 130, 30), BUTTON2 = new Rectangle(20, 90, 110, 30), BUTTON3 = new Rectangle(20, 130, 110, 30), BUTTON4 = new Rectangle(20, 170, 110, 30), BUTTON5 = new Rectangle(20, 210, 110, 30);
	private static JPanel loadGamePanel = new LoadGamePanel(savePath);

	public static void main(String[] args)
	{
		new MainMenu(null).setVisible(true);
		paused = true;
		new MainMenu(null).setVisible(true);
	}

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

		JLabel lblGameName = new JLabel("Game Name");
		lblGameName.setFont(new Font("Lucida Grande", Font.BOLD, 22));
		lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
		lblGameName.setBounds(20, 6, 560, 33);
		contentPane.add(lblGameName);

		btnResume = new MenuButton("Resume Game", Color.red, Color.yellow, Color.blue, "Unpause the game");
		btnResume.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
				paused = false;
			}
		});
		contentPane.add(btnResume);

		btnNewGame = new MenuButton("New Game", Color.red, Color.yellow, Color.blue, "Start a new game");
		btnNewGame.setBounds(BUTTON1);
		btnNewGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				dispose();
				GrameUtils.console.setVisible(true);
				RenderManager.clearAllText();
//				engineState = new EngineState();
				mainClass.newGame();
//				if (!running)
//					start();
				paused = false;
			}
		});
		contentPane.add(btnNewGame);

		btnSaveGame = new MenuButton("Save Game", Color.red, Color.yellow, Color.blue, "Save your game to file");
		btnSaveGame.setEnabled(false);
		contentPane.add(btnSaveGame);

		btnLoadGame = new MenuButton("Load Game", Color.red, Color.yellow, Color.blue, "Load a saved game");
		btnLoadGame.setBounds(BUTTON2);
		btnLoadGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("Path: " + savePath);
				sidePanel.setViewportView(loadGamePanel);
			}
		});
		contentPane.add(btnLoadGame);

		btnSettings = new MenuButton("Settings", Color.red, Color.yellow, Color.blue, "Show the settings");
		btnSettings.setBounds(BUTTON3);
		btnSettings.setEnabled(false);
		contentPane.add(btnSettings);

		btnEndGame = new MenuButton("End Game", Color.red, Color.yellow, Color.blue, "Abandon the current game");
		btnEndGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				paused = false;
//				stop();
				updateButtons();
			}
		});
		contentPane.add(btnEndGame);

		sidePanel = new JScrollPane();
		sidePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sidePanel.setBounds(150, 50, 430, 290);
		contentPane.add(sidePanel);

		lblMadeWithGrame = new JLabel();
		lblMadeWithGrame.addMouseListener(new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				lblMadeWithGrame.setText("Made with moomoohk's Grame (v" + GrameManager.VERSION_NUMBER + ")");
			}

			public void mouseExited(MouseEvent me)
			{
				lblMadeWithGrame.setText("");
			}
		});
		lblMadeWithGrame.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 11));
		lblMadeWithGrame.setForeground(Color.DARK_GRAY);
		lblMadeWithGrame.setBounds(18, 353, 421, 37);
		contentPane.add(lblMadeWithGrame);

		MenuButton btnQuit = new MenuButton("Quit", Color.red, Color.yellow, Color.blue, "Quit the game");
		btnQuit.setBounds(450, 350, 130, 40);
		btnQuit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				System.exit(0);
			}
		});
		contentPane.add(btnQuit);

		btnResume.addMouseListener(helpTextListener);
		btnNewGame.addMouseListener(helpTextListener);
		btnSaveGame.addMouseListener(helpTextListener);
		btnLoadGame.addMouseListener(helpTextListener);
		btnSettings.addMouseListener(helpTextListener);
		btnEndGame.addMouseListener(helpTextListener);
		btnQuit.addMouseListener(helpTextListener);

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
			btnSaveGame.setBounds(BUTTON2);
			btnLoadGame.setBounds(BUTTON3);
			btnSettings.setBounds(BUTTON4);
			btnEndGame.setBounds(0, 0, 0, 0);
		}
	}

	public void setVisible(boolean f)
	{
		updateButtons();
		super.setVisible(f);
	}

	public static class MenuButton extends JButton
	{
		private static final long serialVersionUID = -2192610213120657509L;

		public boolean mouseOn = false, mouseDown = false;
		private double animTime = 0;
		private Color startColor, endColor, clickColor, fill;
		private String helpText;
		private Timer t = new Timer(10, new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				repaint();
				animTime += 0.03;
			}
		});

		public MenuButton(String text, Color startColor, Color endColor, Color clickColor, String helpText)
		{
			super(text);
			this.startColor = startColor;
			this.endColor = endColor;
			this.clickColor = clickColor;
			this.fill = this.startColor;
			this.helpText = helpText;
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

		public String getHelpText()
		{
			return this.helpText;
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
				g2.setPaint(fill);
			}
			else
				g2.setPaint(Color.LIGHT_GRAY);
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