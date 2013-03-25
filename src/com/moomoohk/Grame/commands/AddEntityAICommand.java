package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.GrameManager;
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
		if(GrameManager.ais.size()==0)
		{
			this.message="No AIs loaded!";
			return;
		}
		if(GrameManager.ais.get(arg1[2])==null)
		{
			this.message="Valid AIs: ";
			for(String name:GrameManager.ais.keySet())
				this.message+=name+ " ";
			return;
		}
		this.message="";
		GrameManager.findEntity(Integer.parseInt(arg1[0])).addAI(GrameManager.ais.get(arg1[2]), Integer.parseInt(arg1[1]));
	}
}

