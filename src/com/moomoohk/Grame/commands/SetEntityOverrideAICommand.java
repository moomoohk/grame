package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class SetEntityOverrideAICommand extends Command
{

	public SetEntityOverrideAICommand()
	{
		super();
	}

	protected boolean check(String[] params)
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
		if (GrameManager.findGrameObject(Integer.parseInt(params[0])) == null)
		{
			this.outputMessage = "Base with ID:" + params[1] + " does not exist!";
			this.outputColor = Color.red;
			return false;
		}
		if (GrameManager.getAIs().size() == 0)
		{
			this.outputMessage = "No AIs loaded!";
			this.outputColor = Color.red;
			return false;
		}
		if (GrameManager.getAIs().get(params[2]) == null || (GrameManager.getAIs().get(params[2]) != null && !GrameManager.getAIs().get(params[2]).isOverride()))
		{
			this.outputMessage = "Valid AIs: ";
			for (String name : GrameManager.getAIs().keySet())
				if (GrameManager.getAIs().get(name).isOverride())
					this.outputMessage += name + " ";
			return false;
		}
		return super.check(params);
	}

	@Override
	public void execute(String[] params)
	{
		if (!params[2].equalsIgnoreCase("null"))
			((Entity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).setOverrideAI(GrameManager.getAIs().get(params[2]), Integer.parseInt(params[1]));
		else
			((Entity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).setOverrideAI(null, Integer.parseInt(params[1]));
	}

	@Override
	public String getCommand()
	{
		return "setentityoverrideai";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sets the override AI for a given Entity";
	}

	@Override
	public String getUsage()
	{
		return "setentityoverrideai <entity ID> <base ID> <override movementai name>";
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
