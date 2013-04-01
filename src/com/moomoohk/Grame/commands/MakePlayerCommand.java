
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class MakePlayerCommand extends Command<Console>
{
	public MakePlayerCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(arg1[0].equalsIgnoreCase("all"))
		{
			for(int i=0; i<GrameManager.getObjectListLength(); i++)
				if(GrameManager.findGrameObject(i) instanceof Entity)
					((Entity)(GrameManager.findGrameObject(i))).makePlayer(Integer.parseInt(arg1[1]), Boolean.parseBoolean(arg1[3]), Integer.parseInt(arg1[2]));
		}
		else
			if(GrameManager.findGrameObject(Integer.parseInt(arg1[0])) instanceof Entity)
				((Entity)(GrameManager.findGrameObject(Integer.parseInt(arg1[0])))).makePlayer(Integer.parseInt(arg1[1]), Boolean.parseBoolean(arg1[3]), Integer.parseInt(arg1[2]));
	}
}

