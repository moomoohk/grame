
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class SetMainBaseCommand extends Command<Console>
{

	public SetMainBaseCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		RenderManager.setMainBase(Integer.parseInt(arg1[0]));
	}
}

