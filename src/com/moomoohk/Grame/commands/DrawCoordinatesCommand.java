
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class DrawCoordinatesCommand extends Command<Console>
{
	public DrawCoordinatesCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		RenderManager.drawCoordinates(Boolean.parseBoolean(arg1[0]));
	}
}

