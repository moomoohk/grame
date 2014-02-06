package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.MooCommands.Command;

public class MakePlayerCommand extends Command
{
	public MakePlayerCommand()
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
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		if (params[0].equalsIgnoreCase("all"))
		{
			for (int i = 0; i < GrameManager.getObjectListLength(); i++)
				if (GrameManager.findGrameObject(i) instanceof Entity)
					((Entity) (GrameManager.findGrameObject(i))).makePlayer(Integer.parseInt(params[1]), Boolean.parseBoolean(params[3]), Integer.parseInt(params[2]));
		}
		else
			if (GrameManager.findGrameObject(Integer.parseInt(params[0])) instanceof Entity)
				((Entity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).makePlayer(Integer.parseInt(params[1]), Boolean.parseBoolean(params[3]), Integer.parseInt(params[2]));
	}

	@Override
	public String getCommand()
	{
		return "makeplayer";
	}

	@Override
	public String getHelpMessage()
	{
		return "Turns an Entity into a controllable \"player\"";
	}

	@Override
	public String getUsage()
	{
		return "makeplayer <entity ID> <player 1/2> <grid ID> <true/false>";
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
