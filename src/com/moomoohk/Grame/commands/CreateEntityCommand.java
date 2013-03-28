
package com.moomoohk.Grame.commands;

import java.awt.Color;

import com.moomoohk.Grame.Essentials.DefaultRandomGen;
import com.moomoohk.Grame.Essentials.Entity;
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
		for(String arg:arg1)
		{
			switch(arg.charAt(0))
			{
			case 't':
				type=arg.substring(arg.indexOf(":"));
				break;
			case 'n':
				name=arg.substring(arg.indexOf(":"));
				break;
			case 'l':
				level=Integer.parseInt(arg.substring(arg.indexOf(":")));
			}
		}
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

