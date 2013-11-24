package com.moomoohk.Grame.commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.moomoohk.Grame.test.SpriteRender;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooCommands.CommandsManager;

public class SetSpriteCommand extends Command
{

	public SetSpriteCommand()
	{
		super();
	}

	public boolean check(String[] params)
	{
		if (!SpriteRender.sprites.containsKey(params[0]))
		{
			this.outputMessage = "Available sprites:";
			for (String sprite : SpriteRender.sprites.keySet())
				this.outputMessage += " " + sprite;
			return false;
		}
		return true;
	}

	@Override
	public void execute(String[] params)
	{
		String path = CommandsManager.stringParams(params, 1);
		try
		{
			SpriteRender.sprites.put(params[0], ImageIO.read(new File(path)));
		}
		catch (IOException e)
		{
			this.outputMessage = "Invalid path!";
			this.outputColor = Color.red;
		}
	}

	@Override
	public String getCommand()
	{
		return "setsprite";
	}

	@Override
	public String getHelpMessage()
	{
		return "Sets a certain sprite (NOT SUPPORTED/IN DEVELOPMENT)";
	}

	@Override
	public String getUsage()
	{
		return "setsprite <sprite name> <path to sprite>";
	}

	@Override
	public int getMaxParams()
	{
		return -1;
	}

	@Override
	public int getMinParams()
	{
		return 2;
	}
}
