package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.MooCommands.Command;

public class AddGrameObjectCommand extends Command
{

	public AddGrameObjectCommand()
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
		if (!GrameManager.findGrid(Integer.parseInt(params[1])).isInGrid(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2]))))
		{
			this.outputMessage = "Coordinates (" + params[1] + ", " + params[2] + ") are not in Grid with ID:" + params[1];
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		GrameManager.findGrid(Integer.parseInt(params[1])).addGrameObject(GrameManager.findGrameObject(Integer.parseInt(params[0])), new Coordinates(Integer.parseInt(params[2]), Integer.parseInt(params[3])));
	}

	@Override
	public String getCommand()
	{
		return "addobject";
	}

	@Override
	public String getHelpMessage()
	{
		return "Adds a Grame Object to a Grid";
	}

	@Override
	public String getUsage()
	{
		return "addobject <go ID> <grid ID> <x> <y>";
	}

	@Override
	public int getMaxParams()
	{
		return 4;
	}

	@Override
	public int getMinParams()
	{
		return 4;
	}
}
