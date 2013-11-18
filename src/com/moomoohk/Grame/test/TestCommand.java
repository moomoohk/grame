
package com.moomoohk.Grame.test;

import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class TestCommand extends Command<Console>
{
	public TestCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}
	
	@Override
	public void execute(Console arg0, String[] arg1)
	{
		System.out.println("Hello world!");
	}
}




