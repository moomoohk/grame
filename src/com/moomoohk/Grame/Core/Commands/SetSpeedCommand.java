package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Core.GrameManager;
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
		if (GrameManager.findGrid(Integer.parseInt(params[1])) == null)
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
		return "Sets the speed of a specified Grame Object in a specified Grid";
	}

	@Override
	public String getUsage()
	{
		return "setspeed <go ID> <grid ID>";
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
