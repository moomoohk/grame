
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class isOccupiedCommand extends Command<Console>
{

	public isOccupiedCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[]arg1)
	{
		if(!GrameManager.findBase(Integer.parseInt(arg1[0])).isInBase(new Coordinates(Integer.parseInt(arg1[1]), Integer.parseInt(arg1[1]))))
		{
			this.message="Coordinates "+new Coordinates(Integer.parseInt(arg1[1]), Integer.parseInt(arg1[1])).toString()+" isn't in Base ID:"+arg1[0];
			return;
		}
		boolean isOccupied=GrameManager.findBase(Integer.parseInt(arg1[0])).isOccupied(new Coordinates(Integer.parseInt(arg1[1]), Integer.parseInt(arg1[1])));
		this.message=""+isOccupied;
	}
}

