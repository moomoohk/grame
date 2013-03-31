
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class ClearEntityAI extends Command<Console>
{

	public ClearEntityAI(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		((Entity)(GrameManager.findGrameObject(Integer.parseInt(arg1[0])))).clearAI();
	}
}

