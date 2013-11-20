package com.moomoohk.Grame.Essentials.MainMenuPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.moomoohk.Grame.test.EngineState;
import com.moomoohk.Grame.test.MainMenu;
import com.moomoohk.Grame.test.MainMenu.MenuButton;

public class LoadGamePanel extends JPanel
{
	private static final long serialVersionUID = -2270645491547851288L;

	private String savePath;
	private ArrayList<EngineState> saves;
	private JLabel noSaves = new JLabel("No saves found");

	public LoadGamePanel(String savePath)
	{
		this.savePath = savePath;
		this.saves = new ArrayList<EngineState>();
		this.noSaves.setHorizontalAlignment(SwingConstants.CENTER);
		this.noSaves.setFont(new Font("Lucida Grande", Font.BOLD | Font.ITALIC, 15));
		this.noSaves.setForeground(Color.lightGray);
		loadInfo();

		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);

		JLabel lblSelectSaveFile = new JLabel("Select save file to load:");
		lblSelectSaveFile.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		springLayout.putConstraint(SpringLayout.NORTH, lblSelectSaveFile, 20, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblSelectSaveFile, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, lblSelectSaveFile, -20, SpringLayout.EAST, this);
		add(lblSelectSaveFile);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, lblSelectSaveFile);
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 20, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -100, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -20, SpringLayout.EAST, this);
		add(scrollPane);

		if (this.saves.size() == 0)
			scrollPane.setViewportView(noSaves);

		MenuButton btnLoad = new MenuButton("Load", Color.red, Color.yellow, Color.blue, "Load the selected save");
		springLayout.putConstraint(SpringLayout.SOUTH, btnLoad, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnLoad, 0, SpringLayout.EAST, lblSelectSaveFile);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -20, SpringLayout.NORTH, btnLoad);
		btnLoad.setPreferredSize(new Dimension(100, 30));
		add(btnLoad);

		MenuButton btnCancel = new MenuButton("Cancel", Color.red, Color.yellow, Color.blue, "Cancels this operation");
		springLayout.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, lblSelectSaveFile);
		springLayout.putConstraint(SpringLayout.SOUTH, btnCancel, 0, SpringLayout.SOUTH, btnLoad);
		btnCancel.setPreferredSize(new Dimension(100, 30));
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				MainMenu.sidePanel.setViewportView(null);
			}
		});
		add(btnCancel);

		btnLoad.addMouseListener(MainMenu.helpTextListener);
		btnCancel.addMouseListener(MainMenu.helpTextListener);
	}

	public void loadInfo()
	{
		System.out.println("Loading save info...");
		File f = new File(savePath);
		if (!f.exists())
			return;
		for (File child : f.listFiles())
			System.out.println(child);
		System.out.println("Done");
	}
}
