package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Basics.OldEntity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;

public class PrintEntityAICommand extends Command
{

	public PrintEntityAICommand()
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
		if (!(GrameManager.findGrameObject(Integer.parseInt(params[0])) instanceof OldEntity))
		{
			this.outputMessage = "Grame Object with ID:" + params[0] + " is not an Entity!";
			this.outputColor = Color.red;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		((OldEntity) (GrameManager.findGrameObject(Integer.parseInt(params[0])))).printAI();
	}

	@Override
	public String getCommand()
	{
		return "printentityai";
	}

	@Override
	public String getHelpMessage()
	{
		return "Prints the AI list for a given Entity";
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

	@Override
	public String getUsage()
	{
		return "printentityai <entity ID>";
	}
}
