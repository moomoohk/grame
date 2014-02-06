package com.moomoohk.Grame.Core.Commands;

import java.util.ArrayList;

import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.test.Dialog;
import com.moomoohk.MooCommands.Command;

public class ShowDialogCommand extends Command
{

	@Override
	public String getCommand()
	{
		return "dialog";
	}

	@Override
	public String getHelpMessage()
	{
		return "DEBUG: Prints some dialog on a specified Grame Object";
	}

	@Override
	public String getUsage()
	{
		return "dialog <go ID>";
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
	protected void execute(String[] params)
	{
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("My name is Inigo Montoya");
		lines.add("You killed my father");
		lines.add("Prepare to die");
		new Dialog(lines, 5000, GrameManager.findGrameObject(Integer.parseInt(params[0]))).start();
	}
}
