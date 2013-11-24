package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;

public class DrawCoordinatesCommand extends Command
{
	public DrawCoordinatesCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		try
		{
			Boolean.parseBoolean(params[0]);
		}
		catch (Exception e)
		{
			this.outputMessage = "Only true or false are accepted inputs!";
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		RenderManager.drawCoordinates(Boolean.parseBoolean(params[0]));
	}

	@Override
	public String getCommand()
	{
		return "drawcoordinates";
	}

	@Override
	public String getHelpMessage()
	{
		return "Draws the coordinates in each square";
	}

	@Override
	public String getUsage()
	{
		return "drawcoordinates <true/false>";
	}

	@Override
	public int getMaxParams()
	{
		return 1;
	}

	@Override
	public int getMinParams()
	{
		return 1;
	}
}
