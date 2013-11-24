package com.moomoohk.Grame.AI;

import java.awt.Color;
import java.util.ArrayList;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.OldEntity;
import com.moomoohk.Grame.Basics.Schematic;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Essentials.GrameObjectLayer;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Graphics.PlainGridRender;
import com.moomoohk.Grame.Graphics.RenderManager;
import com.moomoohk.Grame.Interfaces.MainGrameClass;
import com.moomoohk.Grame.Interfaces.MovementAI;

public class AStarPathfindingMovementAI extends MovementAI implements MainGrameClass
{
	private static final long serialVersionUID = -5135305842198542597L;

	private static final double NORMAL_COST = 1, DIAGONAL_COST = Math.sqrt(2);

	private Coordinates cachedTargetPos = null;

	private ArrayList<Node> cachedNodePath = null;

	public boolean showCosts = false, showVisualization = false;
	public Color costColor = Color.blue, visualizationColor = Color.green;

	@Override
	public String author()
	{
		return "moomoohk";
	}

	@Override
	public Coordinates getNext(Coordinates pos, Coordinates targetPos, Base b, Entity ent1, Entity ent2)
	{
		if (ent1.getPos(b.ID).distance(ent2.getPos(b.ID)) == 1)
			return pos;
		if (cachedTargetPos != null)
			if (cachedTargetPos.equals(targetPos))
			{
				Node last = getLast(cachedNodePath.get(cachedNodePath.size() - 1));
				if (cachedNodePath.remove(last)) //TODO: Fix this list
					return last.getPos();
			}
			else
			{
				if (showVisualization)
					b.setFloorColor(Color.white);
				if (showCosts)
					RenderManager.clearText(b.ID);
				cachedTargetPos = targetPos;
			}
		else
			cachedTargetPos = targetPos;

		Node start = new Node(pos, null, 0, 0);
		ArrayList<Node> open = new ArrayList<Node>();
		open.add(start);

		ArrayList<Node> closed = new ArrayList<Node>();
		Node current = findLowestFCost(open);
		do
		{
			if (!current.getPos().equals(pos))
				closed.add(current);
			open.remove(current);
			if (current.getPos().distance(ent2.getPos(b.ID)) == 1)
				break;
			for (Coordinates sur : current.getPos().getAllSurrounding(b))
			{
				if (!b.isInBase(sur) || b.isOccupied(sur) || contains(closed, sur))
				{
					open.remove(getNodeFromList(open, sur));
					continue;
				}
				if (!contains(open, sur))
					open.add(new Node(sur, current, calcG(current, sur), calcH(current.getPos(), targetPos)));
				if (calcG(current, sur) < current.getGCost())
				{
					getNodeFromList(open, sur).setParent(current);
					getNodeFromList(open, sur).setGCost(calcG(current, sur));
					getNodeFromList(open, sur).reCalcFCost();
				}
			}
			current = findLowestFCost(open);
		}
		while (current.getPos() != targetPos);

		Node node = getLast(closed.get(closed.size() - 1));
		//		visualizationColor = new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
		if (showCosts || showVisualization)
			for (int i = 0; i < closed.size(); i++)
			{
				if (showVisualization)
					b.setFloorColor(closed.get(i).getPos(), new Color((255 - ((255 - visualizationColor.getRed()) / closed.size() * i)), (255 - ((255 - visualizationColor.getGreen()) / closed.size() * i)), (255 - ((255 - visualizationColor.getBlue()) / closed.size() * i))));
				if (showCosts)
					RenderManager.setText(b.ID, new Coordinates(closed.get(i).getPos().getY(), closed.get(i).getPos().getX()), /*"F" + (int)(calcG(current, closed.get(i).getPos()) + calcH(closed.get(i).getPos(), targetPos))*/
					"G" + (int) calcG(current, closed.get(i).getPos()) + "H" + (int) calcH(closed.get(i).getPos(), targetPos), costColor);
			}

		cachedNodePath = closed;

		return node.getPos();
	}

	private Node getLast(Node start)
	{
		Node temp = null;
		while (start.getParent() != null)
		{
			temp = start.getParent();
			if (temp.getParent() == null)
				break;
			start = start.getParent();
		}
		return start;
	}

	private double calcG(Node end, Coordinates next)
	{
		return end.getGCost() + (new Dir(end.getPos(), next).isDiag() ? DIAGONAL_COST : NORMAL_COST) - 1;
	}

	private double calcH(Coordinates start, Coordinates end)
	{
		return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
		//		return Math.sqrt(Math.pow(Math.abs(start.getX() - end.getX()), 2) + Math.pow(Math.abs(start.getY() - start.getY()), 2));
	}

	private boolean contains(ArrayList<Node> list, Coordinates pos)
	{
		for (Node node : list)
			if (pos.getX() == node.getPos().getX() && pos.getY() == node.getPos().getY())
				return true;
		return false;
	}

	private Node getNodeFromList(ArrayList<Node> list, Coordinates pos)
	{
		for (Node node : list)
			if (pos.getX() == node.getPos().getX() && pos.getY() == node.getPos().getY())
				return node;
		return null;
	}

	private Node findLowestFCost(ArrayList<Node> open)
	{
		double min = -1;
		Node lowestFCost = null;
		for (Node node : open)
			if (node.getFCost() >= min)
				min = node.getFCost();
		for (Node node : open)
			if (node.getFCost() <= min)
			{
				min = node.getFCost();
				lowestFCost = node;
			}
		return lowestFCost;
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

	public String toString()
	{
		return "A* Pathfinding AI";
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

		public void setParent(Node parent)
		{
			this.parent = parent;
		}

		public Node getParent()
		{
			return this.parent;
		}

		public void setGCost(double gCost)
		{
			this.gCost = gCost;
		}

		public double getGCost()
		{
			return this.gCost;
		}

		public void reCalcFCost()
		{
			this.fCost = this.gCost + this.hCost;
		}

		public double getFCost()
		{
			return this.fCost;
		}

		public String toString()
		{
			return "G:" + this.gCost + " H:" + this.hCost + " F:" + this.fCost + " pos:" + this.pos;
		}
	}

	public static void main(String[] args)
	{
		GrameUtils.loadBasicCommands();
		GrameManager.initialize(new AStarPathfindingMovementAI());
	}

	@Override
	public void newGame()
	{
		Base b = new Base(20, 20);
		b.setWraparound(true);
		OldEntity player = new OldEntity("Player", Color.gray);
		OldEntity monster = new OldEntity("Monster", Color.red);
		b.addGrameObjectLayer(new GrameObjectLayer(b.getColumns(), b.getRows()), 1);
		player.makePlayer(1, true, b.ID);
		player.setSpeed(1);
		monster.setTarget(player.ID);
		AStarPathfindingMovementAI aStar = new AStarPathfindingMovementAI();
		monster.addAI(aStar, b.ID);
		monster.setSpeed(5);
		b.addGrameObject(player, new Coordinates(0, 0));
		b.addGrameObject(monster, new Coordinates(18, 10), 1);
		for (int i = 1; i <= 10; i++)
			new Schematic().load(b, GrameUtils.randomCoordinates(b));
		RenderManager.render(b.ID, new PlainGridRender());
		RenderManager.setVisible(true);
		aStar.showVisualization = true;
		//		for (int i = 0; i < b.getColumns(); i++)
		//			for (int j = 0; j < b.getRows(); j++)
		//				if (!b.isOccupied(new Coordinates(i, j)) && !b.isOccupied(new Coordinates(i, j), 1))
		//					b.addGrameObject(new Coin(), new Coordinates(i, j));
	}

	@Override
	public String getGameName()
	{
		return "A* Pathfinding Test";
	}
}
