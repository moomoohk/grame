
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.MooCommands.Command;
import com.moomoohk.MooConsole.Console;

public class RenderBaseCommand extends Command<Console>
{

	public RenderBaseCommand(Console handler, String command, String help, int minParams, int maxParams)
	{
		super(handler, command, help, minParams, maxParams);
	}

	@Override
	public void execute(Console arg0, String[] arg1)
	{
		if(GrameManager.getRenders().size()==0)
		{
			this.message="No renders loaded!";
			return;
		}
		if(GrameManager.getRenders().get(arg1[1])==null)
		{
			this.message="Valid renders: ";
			for(String name:GrameManager.getRenders().keySet())
				this.message+=name+ " ";
			return;
		}
		this.message="";
		RenderManager.render(Integer.parseInt(arg1[0]), GrameManager.getRenders().get(arg1[1]));
	}

}

