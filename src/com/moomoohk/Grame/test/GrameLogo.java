
package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Graphics.PlainGridRender;
import com.moomoohk.Grame.Graphics.RenderManager;

public class GrameLogo
{
	public static void main(String[] args)
	{
		Base b=new Base(6, 6);
		TestGrameObject player=new TestGrameObject("player", 1, Color.gray, false);
		TestGrameObject monster=new TestGrameObject("monster", 1, Color.red, false);
		b.addGrameObject(player, new Coordinates(0, 0));
		b.addGrameObject(new Wall(), new Coordinates(1, 1));
		b.addGrameObject(new Wall(), new Coordinates(1, 2));
		b.addGrameObject(new Wall(), new Coordinates(1, 3));
		b.addGrameObject(new Wall(), new Coordinates(1, 4));
		b.addGrameObject(new Wall(), new Coordinates(2, 1));
		b.addGrameObject(new Wall(), new Coordinates(3, 1));
		b.addGrameObject(new Wall(), new Coordinates(4, 1));
		b.addGrameObject(new Wall(), new Coordinates(2, 4));
		b.addGrameObject(new Wall(), new Coordinates(3, 4));
		b.addGrameObject(new Wall(), new Coordinates(4, 4));
		b.addGrameObject(new Wall(), new Coordinates(4, 3));
		b.addGrameObject(monster, new Coordinates(5, 5));
		RenderManager.render(b.ID, new PlainGridRender());
		RenderManager.setVisible(true);
	}
}

