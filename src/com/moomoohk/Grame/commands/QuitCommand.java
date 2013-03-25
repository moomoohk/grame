
package com.moomoohk.Grame.commands;

import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class QuitCommand extends Command<Console>
{

	public QuitCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		System.exit(0);	
	}
}

