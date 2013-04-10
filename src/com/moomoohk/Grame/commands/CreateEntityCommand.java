
package com.moomoohk.Grame.commands;

import java.awt.Color;
import java.util.HashMap;

import com.moomoohk.Grame.Basics.DefaultRandomGen;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class CreateEntityCommand extends Command<Console>
{
	public CreateEntityCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		this.message="";
		String type=new DefaultRandomGen().typeGen(), name=new DefaultRandomGen().nameGen();
		int level=0;
		Color color=GrameUtils.randomColor();
		HashMap<String, String> flags=Command.parseFlags(arg1);
		if(flags.get("t")!=null)
			type=flags.get("t");
		if(flags.get("n")!=null)
			name=flags.get("n");
		if(flags.get("l")!=null)
			level=Integer.parseInt(flags.get("l"));
		new Entity(type, name, level, color);
	}
	public boolean check(Console handler, String[] params)
	{
		boolean f=true;
		for(String arg:params)
			if(!arg.contains(":"))
			{
				this.message="Incorrect syntax!";
				f=false;
				break;
			}
		return super.check(handler, params)&&f;
	}
}

