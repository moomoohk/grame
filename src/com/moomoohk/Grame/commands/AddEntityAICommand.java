package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class AddEntityAICommand extends Command
{

	public AddEntityAICommand()
	{
		super();
	}

	protected boolean check(String[] params)
	{
		if (GrameManager.getAIs().size() == 0)
		{
			this.outputMessage = "No AIs loaded!";
			this.outputColor = Color.red;
			return false;
		}
		if (GrameManager.getAIs().get(params[2]) == null)
		{
			this.outputMessage = "Valid AIs: ";
			for (String name : GrameManager.getAIs().keySet())
				this.outputMessage += name + " ";
			return false;
		}
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

	@Override
	public void execute(String[] params)
	{
		((Entity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).addAI(GrameManager.getAIs().get(params[2]), Integer.parseInt(params[1]));
	}

	@Override
	public String getCommand()
	{
		return "addentityai";
	}

	@Override
	public String getHelpMessage()
	{
		return "Adds a MovememntAI to an Entity's AI list";
	}

	@Override
	public String getUsage()
	{
		return "addentityai <entity ID> <base ID> <movementai name>";
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
