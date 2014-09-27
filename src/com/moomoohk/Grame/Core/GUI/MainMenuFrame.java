package com.moomoohk.Grame.Core.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import com.moomoohk.Grame.test.MenuConfiguration;

public class MainMenuFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblHelpText;
	private MenuConfiguration menuConfiguration;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainMenuFrame frame = new MainMenuFrame(new MenuConfiguration());
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenuFrame(MenuConfiguration menuConfiguration)
	{
		this.menuConfiguration = menuConfiguration;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 385));
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);

		lblHelpText = new JLabel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblHelpText, -26, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblHelpText, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblHelpText, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblHelpText, -10, SpringLayout.EAST, contentPane);
		contentPane.add(lblHelpText);

		JPanel panel = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.NORTH, lblHelpText);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 204, SpringLayout.WEST, contentPane);
		contentPane.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 10, SpringLayout.EAST, panel);
		panel.setLayout(null);

		MainMenuButton btnButton = new MainMenuButton("Button 1", "This is button 1");
		btnButton.setBounds(0, 0, 194, 40);
		panel.add(btnButton);

		MainMenuButton btnButton_1 = new MainMenuButton("Button 2", "This is button 2");
		btnButton_1.setBounds(10, 50, 174, 30);
		panel.add(btnButton_1);

		MainMenuButton btnButton_2 = new MainMenuButton("Button 3", "This is button 3");
		btnButton_2.setBounds(10, 90, 174, 30);
		panel.add(btnButton_2);

		MainMenuButton btnButton_3 = new MainMenuButton("Button 4", "This is button 4");
		btnButton_3.setBounds(10, 130, 174, 30);
		panel.add(btnButton_3);

		MainMenuButton btnButton_4 = new MainMenuButton("Button 5", "This is button 5");
		btnButton_4.setBounds(10, 172, 178, 30);
		panel.add(btnButton_4);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.NORTH, lblHelpText);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);

		MouseListener helpTextListener = new MouseAdapter()
		{
			public void mouseEntered(MouseEvent me)
			{
				lblHelpText.setText(((MainMenuButton) me.getSource()).getHelpText());
			}

			public void mouseExited(MouseEvent me)
			{
				lblHelpText.setText("");
			}
		};

		btnButton.addMouseListener(helpTextListener);
		btnButton_1.addMouseListener(helpTextListener);
		btnButton_2.addMouseListener(helpTextListener);
		btnButton_3.addMouseListener(helpTextListener);
		btnButton_4.addMouseListener(helpTextListener);
	}
}
