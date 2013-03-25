
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class SetEntityOverrideAICommand extends Command<Console>
{

	public SetEntityOverrideAICommand(Console handler, String command, String help, int minParams, int maxParams)
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
		if(!arg1[2].equalsIgnoreCase("null"))
		{
			if(GrameManager.ais.get(arg1[2])==null||(GrameManager.ais.get(arg1[2])!=null&&!GrameManager.ais.get(arg1[2]).isOverride()))
			{
				this.message="Valid AIs: ";
				for(String name:GrameManager.ais.keySet())
					if(GrameManager.ais.get(name).isOverride())
						this.message+=name+ " ";
				return;
			}
			GrameManager.findEntity(Integer.parseInt(arg1[0])).setOverrideAI(GrameManager.ais.get(arg1[2]), Integer.parseInt(arg1[1]));
		}
		else
			GrameManager.findEntity(Integer.parseInt(arg1[0])).setOverrideAI(null, Integer.parseInt(arg1[1]));
	}
}

