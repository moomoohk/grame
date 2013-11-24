package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Graphics.PlainGridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MainGrameClass;

public class GrameObjectsTest implements MainGrameClass
{
	public static void main(String[] args)
	{
		MenuConfiguration menuConfig = new MenuConfiguration();
		menuConfig.menuButtonStartColor = Color.red;
		menuConfig.menuButtonEndColor = Color.blue;
		menuConfig.menuButtonClickColor = Color.yellow;
		GrameManager.initialize(new GrameObjectsTest(), menuConfig);
	}

	@Override
	public void newGame()
	{
		Base b = new Base(20, 20);
		Entity ent = new Entity();
		b.addGrameObject(ent, new Coordinates(10, 10));
		b.setWraparound(true);
		ent.makePlayer(1, true, b.ID);
		ent.setSpeed(1);
		RenderManager.render(b.ID, new PlainGridRender());
		RenderManager.setVisible(true);
	}

	@Override
	public String getGameName()
	{
		return "Test";
	}
}
