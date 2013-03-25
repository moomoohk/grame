
package com.moomoohk.Grame.commands;

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
		this.message="";
		GrameManager.findEntity(Integer.parseInt(arg1[0])).makePlayer(Boolean.parseBoolean(arg1[2]), Integer.parseInt(arg1[1]));
	}
}

