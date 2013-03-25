
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class setVisibleCommand extends Command<Console>
{

	public setVisibleCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(arg1.length==0)
			RenderManager.setVisible(!RenderManager.isVisible());
		else
			RenderManager.setVisible(Boolean.parseBoolean(arg1[0]));
	}
}

