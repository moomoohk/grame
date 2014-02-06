package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.MooCommands.Command;

public class IsOccupiedCommand extends Command
{

	public IsOccupiedCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Grid with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		if (!GrameManager.findGrid(Integer.parseInt(params[0])).isInGrid(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2]))))
		{
			this.outputMessage = "Coordinates (" + params[1] + ", " + params[2] + ") are not in Grid ID:" + params[0];
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		this.outputMessage = "" + GrameManager.findGrid(Integer.parseInt(params[0])).isOccupied(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2])));
	}

	@Override
	public String getCommand()
	{
		return "isoccupied";
	}

	@Override
	public String getHelpMessage()
	{
		return "Checks whether Coordinates in a Grid are occupied by a Grame object";
	}

	@Override
	public String getUsage()
	{
		return "isoccupied <grid ID> <x> <y>";
	}

	@Override
	public int getMaxParams()
	{
		return 3;
	}

	@Override
	public int getMinParams()
	{
		return 3;
	}
}
