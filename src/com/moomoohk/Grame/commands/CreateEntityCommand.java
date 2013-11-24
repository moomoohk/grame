package com.moomoohk.Grame.commands;

import java.awt.Color;
import java.util.HashMap;

import com.moomoohk.Grame.Basics.DefaultRandomGen;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.OldEntity;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooCommands.CommandsManager;

public class CreateEntityCommand extends Command
{
	public CreateEntityCommand()
	{
		super();
	}

	@Override
	public void execute(String[] arg1)
	{
		String type = new DefaultRandomGen().typeGen(), name = new DefaultRandomGen().nameGen();
		int level = 0;
		Color color = GrameUtils.randomColor();
		HashMap<String, String> flags = CommandsManager.parseFlags(arg1);
		if (flags.get("t") != null)
			type = flags.get("t");
		if (flags.get("n") != null)
			name = flags.get("n");
		if (flags.get("l") != null)
			level = Integer.parseInt(flags.get("l"));
		new Entity(name, color);
	}

	public boolean check(String[] params)
	{
		for (String arg : params)
			if (!arg.contains(":"))
			{
				this.outputMessage = "Incorrect syntax!";
				this.outputColor = Color.red;
				return false;
			}
		return true;
	}

	@Override
	public String getCommand()
	{
		return "createentity";
	}

	@Override
	public String getHelpMessage()
	{
		return "Creates a new Entity";
	}

	@Override
	public String getUsage()
	{
		return "createentity [n:name] [t:type] [l:level] [c:color]";
	}

	@Override
	public int getMaxParams()
	{
		return 4;
	}

	@Override
	public int getMinParams()
	{
		return 0;
	}
}
