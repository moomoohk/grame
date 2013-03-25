
package com.moomoohk.Grame.commands;

import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.Render;
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
		if(GrameManager.renders.size()==0)
		{
			this.message="No renders loaded!";
			return;
		}
		this.message="";
		Render temp=null;
		if(arg1[1].equalsIgnoreCase("null"))
		{
			RenderManager.render(Integer.parseInt(arg1[0]), temp);
			return;
		}
		for(Render render:GrameManager.renders)
			if(arg1[1].equalsIgnoreCase(render.getName()))
				temp=render;
		if(temp!=null)
			RenderManager.render(Integer.parseInt(arg1[0]), temp);
		else
		{
			this.message="Available renders:";
			for(int i=0; i<GrameManager.renders.size(); i++)
				this.message+=" "+GrameManager.renders.get(i).getName()+",";
		}
	}

}

