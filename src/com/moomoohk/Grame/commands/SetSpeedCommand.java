package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class SetSpeedCommand extends Command
{

	public SetSpeedCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Grame Object with ID:" + params[0] + " does not exist!";
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
		GrameManager.findGrameObject(Integer.parseInt(params[0])).setSpeed(Integer.parseInt(params[1]));
	}

	@Override
	public String getCommand()
	{
		return "setspeed";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sets the speed of a specified Grame Object in a specified Base";
	}

	@Override
	public String getUsage()
	{
		return "setspeed <go ID> <base ID>";
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
