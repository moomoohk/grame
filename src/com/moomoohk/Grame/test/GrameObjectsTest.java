package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;

public class GrameObjectsTest
{
	public static void main(String[] args)
	{
		Base b=new Base(20, 20);
		GrameUtils.loadBasicCommands();
		GrameUtils.loadBasicAIs();
		Entity ent=new Entity();
		b.addGrameObject(ent, new Coordinates(10, 10));
		ent.makePlayer(1, true, b.ID);
		ent.setSpeed(1);
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}
}
