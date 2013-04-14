
package com.moomoohk.Grame.commands;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.moomoohk.Grame.test.SpriteRender;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class SetSpriteCommand extends Command<Console>
{

	public SetSpriteCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(!SpriteRender.sprites.containsKey(arg1[0]))
		{
			this.message="Available sprites:";
			for(String sprite:SpriteRender.sprites.keySet())
				this.message+=" "+sprite;
			return;
		}
		String path=Command.stringParams(arg1, 1);
		try
		{
			SpriteRender.sprites.put(arg1[0], ImageIO.read(new File(path)));
		}
		catch (IOException e2)
		{
			this.message="Invalid path!";
		}
	}
}

