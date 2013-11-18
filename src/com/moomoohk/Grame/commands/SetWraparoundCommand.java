package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class SetWraparoundCommand extends Command<Console>
{
	public SetWraparoundCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		Base b = GrameManager.findBase(Integer.parseInt(arg1[0]));
		b.setWraparound(Boolean.parseBoolean(arg1[1]));
	}
}
