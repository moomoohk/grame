
package com.moomoohk.Grame.test;

import java.awt.Color;

import com.moomoohk.Grame.AI.SimpleChaseAI;
import com.moomoohk.Grame.AI.SimpleStrollAI;
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
		Entity ent=new Entity();
		Entity ent2=new Entity();
		ent2.setColor(Color.green);
		ent.setColor(Color.blue);
		Entity ent3=new Entity();
		Entity ent4=new Entity();
		Entity ent5=new Entity();
		Entity ent6=new Entity();
		Entity ent7=new Entity();
//		b.addGrameObject(new Wall(), new Coordinates(5, 5));
		b.setWraparound(true);
		b.addEntity(ent.ID, new Coordinates(5, 10));
		b.addEntity(ent2.ID, new Coordinates(10, 5));
		b.addEntity(ent3.ID, new Coordinates(19, 19));
		b.addEntity(ent4.ID, new Coordinates( 10, 8));
		b.addEntity(ent5.ID, new Coordinates( 8, 2));
		b.addEntity(ent6.ID, new Coordinates( 3, 12));
		b.addEntity(ent7.ID, new Coordinates( 15, 18));
		ent4.addAI(new SimpleStrollAI(), b.ID);
		ent5.addAI(new SimpleStrollAI(), b.ID);
		ent6.addAI(new SimpleStrollAI(), b.ID);
		ent7.addAI(new SimpleStrollAI(), b.ID);
		ent3.setSpeed(4);
		ent4.setSpeed(4);
		ent5.setSpeed(4);
		ent6.setSpeed(4);
		ent7.setSpeed(4);
		ent2.setTarget(ent2.ID);
		ent2.setSpeed(10);
		ent2.setRange(b.getDiagonal());
		ent2.addAI(new SimpleStrollAI(), b.ID);
		ent.makePlayer(2, true, b.ID); 
		//ent2.makePlayer(1, true, b.ID);
		ent.printAI();
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}
}

