
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class AddGrameObjectCommand extends Command<Console>
{

	public AddGrameObjectCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		GrameManager.findBase(Integer.parseInt(arg1[1])).addGrameObject(GrameManager.findGrameObject(Integer.parseInt(arg1[0])), new Coordinates(Integer.parseInt(arg1[2]), Integer.parseInt(arg1[3])));
	}
}

