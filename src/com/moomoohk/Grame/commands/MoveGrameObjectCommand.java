package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class MoveGrameObjectCommand extends Command
{

	public MoveGrameObjectCommand()
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
		if (params.length == 3 && !GrameManager.findBase(Integer.parseInt(params[1])).isInBase(GrameManager.findGrameObject(Integer.parseInt(params[0])).getPos(Integer.parseInt(params[1])).addDir(new Dir(params[2]))))
		{
			this.outputMessage = "Can't move there!";
			this.outputColor = Color.red;
			return false;
		}
		if (params.length == 4 && !GrameManager.findBase(Integer.parseInt(params[1])).isInBase(new Coordinates(Integer.parseInt(params[2]), Integer.parseInt(params[3]))))
		{
			this.outputMessage = "Can't move there!";
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		if (params.length == 3)
			GrameManager.findGrameObject(Integer.parseInt(params[0])).setPos(Integer.parseInt(params[1]), GrameManager.findGrameObject(Integer.parseInt(params[0])).getPos(Integer.parseInt(params[1])).addDir(new Dir(params[2])));
		if (params.length == 4)
			GrameManager.findGrameObject(Integer.parseInt(params[0])).setPos(Integer.parseInt(params[1]), new Coordinates(Integer.parseInt(params[2]), Integer.parseInt(params[3])));
	}

	@Override
	public String getCommand()
	{
		return "move";
	}

	@Override
	public String getHelpMessage()
	{
		return "Moves a Grame Object.";
	}

	@Override
	public String getUsage()
	{
		return "move <go ID> <base ID> <dir>";
	}

	@Override
	public int getMaxParams()
	{
		return 4;
	}

	@Override
	public int getMinParams()
	{
		return 3;
	}
}
