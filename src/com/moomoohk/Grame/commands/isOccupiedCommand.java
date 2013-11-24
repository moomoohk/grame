package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
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
			this.outputMessage = "Base with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		if (!GrameManager.findBase(Integer.parseInt(params[0])).isInBase(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2]))))
		{
			this.outputMessage = "Coordinates (" + params[1] + ", " + params[2] + ") are not in Base ID:" + params[0];
			this.outputColor = Color.red;
			return false;
		}
		return super.check(params);
	}

	@Override
	public void execute(String[] params)
	{
		this.outputMessage = "" + GrameManager.findBase(Integer.parseInt(params[0])).isOccupied(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2])));
	}

	@Override
	public String getCommand()
	{
		return "isinbase";
	}

	@Override
	public String getHelpMessage()
	{
		return "Checks whether Coordinates in a Base are occupied by a Grame object";
	}

	@Override
	public String getUsage()
	{
		return "isoccupied <base ID> <x> <y>";
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
