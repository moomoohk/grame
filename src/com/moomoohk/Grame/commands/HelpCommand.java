package com.moomoohk.Grame.commands;

import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class HelpCommand extends Command<Console>
{

	public HelpCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console handler, String[] params)
	{
		if (params.length == 0)
		{
			this.message = getAllHelp(false);
			return;
		}
		if (Command.getCommand(params[0]) == null)
		{
			this.message = "Command not found!";
			return;
		}
		this.message = Command.getCommand(params[0]).getCommand() + ": " + Command.getCommand(params[0]).getHelp();

	}

}
