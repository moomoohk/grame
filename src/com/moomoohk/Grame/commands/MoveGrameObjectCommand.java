
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class MoveGrameObjectCommand extends Command<Console>
{

	public MoveGrameObjectCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(arg1.length==3)
			if(GrameManager.findBase(Integer.parseInt(arg1[1])).isInMap(GrameManager.findGrameObject(Integer.parseInt(arg1[0])).getPos(Integer.parseInt(arg1[1])).addDir(new Dir(arg1[2]))))
				GrameManager.findGrameObject(Integer.parseInt(arg1[0])).setPos(Integer.parseInt(arg1[1]), GrameManager.findGrameObject(Integer.parseInt(arg1[0])).getPos(Integer.parseInt(arg1[1])).addDir(new Dir(arg1[2])));
			else
				this.message="Can't move there!";
		if(arg1.length==4)
			if(GrameManager.findBase(Integer.parseInt(arg1[1])).isInMap(new Coordinates(Integer.parseInt(arg1[2]), Integer.parseInt(arg1[3]))))
				GrameManager.findGrameObject(Integer.parseInt(arg1[0])).setPos(Integer.parseInt(arg1[1]), new Coordinates(Integer.parseInt(arg1[2]), Integer.parseInt(arg1[3])));
			else
				this.message="Can't move there!";
	}

}

