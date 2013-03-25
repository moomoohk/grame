
package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.Entity;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.RenderManager;

public class AISystemTest
{
	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		GrameUtils.loadBasicAIs();
		Base b=new Base(20, 20);
		//fix inverted issue (x y mixup)
		Entity ent=new Entity();
		b.addEntity(ent.ID, new Coordinates(5, 10));
		ent.makePlayer(true, b.ID); 
		ent.printAI();
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}
}

