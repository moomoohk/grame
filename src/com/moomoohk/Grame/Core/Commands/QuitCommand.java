package com.moomoohk.Grame.Core.Commands;

import com.moomoohk.MooCommands.Command;

public class QuitCommand extends Command
{

	public QuitCommand()
	{
		super();
	}

	@Override
	public void execute(String[] params)
	{
		System.exit(0);
	}

	@Override
	public String getCommand()
	{
		return "quit";
	}

	@Override
	public String getHelpMessage()
	{
		return "Quits the game";
	}

	@Override
	public String getUsage()
	{
		return "quit";
	}

	@Override
	public int getMaxParams()
	{
		return 0;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}
}
