package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class ClearEntityAI extends Command
{
	public boolean check(String[] params)
	{
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Grame Object with ID:" + params[0] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		if (!(GrameManager.findGrameObject(Integer.parseInt(params[0])) instanceof Entity))
		{
			this.outputMessage = "Grame Object with ID:" + params[0] + " is not an Entity!";
			this.outputColor = Color.red;
			return false;
		}
		return super.check(params);
	}

	public ClearEntityAI()
	{
		super();
	}

	@Override
	public void execute(String[] params)
	{
		((Entity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).clearAI();
	}

	@Override
	public String getCommand()
	{
		return "clearentityai";
	}

	@Override
	public String getHelpMessage()
	{
		return "Clears the AI list of an Entity";
	}

	@Override
	public String getUsage()
	{
		return "clearentityai <entity ID>";
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
