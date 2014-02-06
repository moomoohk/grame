package com.moomoohk.Grame.Core.Commands;

import java.awt.Color;

import com.moomoohk.Grame.Core.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;

public class SetVisibleCommand extends Command
{

	public SetVisibleCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		if (params.length > 0)
			try
			{
				Boolean.parseBoolean(params[0]);
			}
			catch (Exception e)
			{
				this.outputMessage = "Only true or false are accepted inputs!";
				this.outputColor = Color.red;
				return false;
			}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		if (params.length == 0)
			RenderManager.setVisible(!RenderManager.isVisible());
		else
			RenderManager.setVisible(Boolean.parseBoolean(params[0]));
	}

	@Override
	public String getCommand()
	{
		return "setvisible";
	}

	@Override
	public String getHelpMessage()
	{
		return "Toggles the visibility of the main window";
	}

	@Override
	public String getUsage()
	{
		return "setvisible [true/false]";
	}

	@Override
	public int getMaxParams()
	{
		return 1;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}
}
