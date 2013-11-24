package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
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
		if (GrameManager.findBase(Integer.parseInt(params[1])) == null)
		{
			this.outputMessage = "Base with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		if (!GrameManager.findBase(Integer.parseInt(params[1])).isInBase(new Coordinates(Integer.parseInt(params[1]), Integer.parseInt(params[2]))))
		{
			this.outputMessage = "Coordinates (" + params[1] + ", " + params[2] + ") are not in Base with ID:" + params[1];
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		GrameManager.findBase(Integer.parseInt(params[1])).addGrameObject(GrameManager.findGrameObject(Integer.parseInt(params[0])), new Coordinates(Integer.parseInt(params[2]), Integer.parseInt(params[3])));
	}

	@Override
	public String getCommand()
	{
		return "addobject";
	}

	@Override
	public String getHelpMessage()
	{
		return "Adds a Grame Object to a Base";
	}

	@Override
	public String getUsage()
	{
		return "addobject <go ID> <base ID> <x> <y>";
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
