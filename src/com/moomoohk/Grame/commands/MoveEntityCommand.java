
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Dir;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class MoveEntityCommand extends Command<Console>
{

	public MoveEntityCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		int bID=Integer.parseInt(arg1[1]), eID=Integer.parseInt(arg1[0]);
		Dir d=new Dir(arg1[2]);
		Coordinates pos=GrameManager.findEntity(eID).getPos(bID), newPos=pos.addDir(d);
		if(arg1.length==3)
			if(GrameManager.findBase(bID).isInMap(newPos))
				GrameManager.findEntity(eID).setPos(bID, pos.addDir(d));
			else
				this.message="Can't move there!";
		if(arg1.length==4)
			if(GrameManager.findBase(bID).isInMap(new Coordinates(Integer.parseInt(arg1[2]), Integer.parseInt(arg1[3]))))
				GrameManager.findEntity(Integer.parseInt(arg1[0])).setPos(Integer.parseInt(arg1[1]), new Coordinates(Integer.parseInt(arg1[2]), Integer.parseInt(arg1[3])));
			else
				this.message="Can't move there!";
	}

}

