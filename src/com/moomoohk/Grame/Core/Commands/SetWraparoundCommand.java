package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.MooCommands.Command;

public class SetWraparoundCommand extends Command
{
	public SetWraparoundCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		if (!params[1].equalsIgnoreCase("true") && !params[1].equalsIgnoreCase("false"))
		{
			this.outputMessage = "Only true or false are accepted inputs!";
			this.outputColor = Color.red;
			return false;
		}
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Grid with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		GrameManager.findGrid(Integer.parseInt(params[0])).setWraparound(Boolean.parseBoolean(params[1]));
	}

	@Override
	public String getCommand()
	{
		return "setwraparound";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sets whether the specified Grid supports wraparound";
	}

	@Override
	public String getUsage()
	{
		return "setwraparound <grid ID> <true/false>";
	}

	@Override
	public int getMaxParams()
	{
		return 2;
	}

	@Override
	public int getMinParams()
	{
		return 2;
	}
}
