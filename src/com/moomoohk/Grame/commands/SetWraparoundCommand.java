package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class SetWraparoundCommand extends Command
{
	public SetWraparoundCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		try
		{
			Boolean.parseBoolean(params[1]);
		}
		catch (Exception e)
		{
			this.outputMessage = "Only true or false are accepted inputs!";
			this.outputColor = Color.red;
			return false;
		}
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Base with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		return super.check(params);
	}

	@Override
	public void execute(String[] params)
	{
		GrameManager.findBase(Integer.parseInt(params[0])).setWraparound(Boolean.parseBoolean(params[1]));
	}

	@Override
	public String getCommand()
	{
		return "setwraparound";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sets whether the specified Base supports wraparound";
	}

	@Override
	public String getUsage()
	{
		return "setwraparound <base ID> <true/false>";
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
