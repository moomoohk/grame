package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class AddEntityAICommand extends Command<Console>
{

	public AddEntityAICommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(GrameManager.getAIs().size()==0)
		{
			this.message="No AIs loaded!";
			return;
		}
		if(GrameManager.getAIs().get(arg1[2])==null)
		{
			this.message="Valid AIs: ";
			for(String name:GrameManager.getAIs().keySet())
				this.message+=name+ " ";
			return;
		}
		this.message="";
		((Entity)(GrameManager.findGrameObject(Integer.parseInt(arg1[0])))).addAI(GrameManager.getAIs().get(arg1[2]), Integer.parseInt(arg1[1]));
	}
}

