package com.moomoohk.Grame.test;

import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.Wall;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class AStar extends MovementAI
{
	private static final double NORMAL_COST = 1, DIAGONAL_COST = 0.5;

	@Override
	public String author()
	{
		return "moomoohk";
	}

	@Override
	public Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2)
	{
		Node start = new Node(pos, null, 0, 0);
		return null;
	}

	@Override
	public boolean isValid(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2)
	{
		return !pos.isSurrounded(b);
	}

	@Override
	public boolean isOverride()
	{
		return false;
	}

	private static class Node
	{
		private Coordinates pos;
		private Node parent;
		private double gCost, hCost, fCost;

		public Node(Coordinates pos, Node parent, double gCost, double hCost)
		{
			this.pos = pos;
			this.parent = parent;
			this.gCost = gCost;
			this.hCost = hCost;
			this.fCost = hCost + gCost;
		}

		public Coordinates getPos()
		{
			return this.pos;
		}

		public Node getParent()
		{
			return this.parent;
		}

		public double getGCost()
		{
			return this.gCost;
		}

		public double getHCost()
		{
			return this.hCost;
		}

		public double getFCost()
		{
			return this.fCost;
		}
	}

	public static void main(String[] args)
	{
		Base b = new Base(7, 5);
		b.addGrameObject(new Wall(), new Coordinates(3, 1));
		b.addGrameObject(new Wall(), new Coordinates(3, 2));
		b.addGrameObject(new Wall(), new Coordinates(3, 3));
		RenderManager.render(b.ID);
		RenderManager.setVisible(true);
	}
}
