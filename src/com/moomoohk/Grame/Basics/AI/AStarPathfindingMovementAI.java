package com.moomoohk.Grame.Basics.AI;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.MovementAI;
import com.moomoohk.Grame.Basics.Schematic;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObjectLayer;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.Grid;
import com.moomoohk.Grame.Core.MainGrameClass;
import com.moomoohk.Grame.Core.Graphics.CleanGridRender;
import com.moomoohk.Grame.Core.Graphics.RenderManager;

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
	public Coordinates getNext(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2)
	{
		if (ent1.getPos(g.ID).distance(ent2.getPos(g.ID)) == 1)
			return pos;
		if (cachedTargetPos != null)
			if (cachedTargetPos.equals(targetPos))
			{
				Node last = getLast(cachedNodePath.get(cachedNodePath.size() - 1));
				if (cachedNodePath.remove(last))
					return last.getPos();
			}
			else
			{
				if (showVisualization)
					g.setFloorColor(Color.white);
				if (showCosts)
					RenderManager.clearText(g.ID);
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
			if (current.getPos().distance(ent2.getPos(g.ID)) == 1)
				break;
			for (Coordinates sur : current.getPos().getAllSurrounding(g))
			{
				if (!g.isInGrid(sur) || g.isOccupied(sur) || contains(closed, sur))
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
		if (showCosts || showVisualization)
			for (int i = 0; i < closed.size(); i++)
			{
				if (showVisualization)
					g.setFloorColor(closed.get(i).getPos(), new Color((255 - ((255 - visualizationColor.getRed()) / closed.size() * i)), (255 - ((255 - visualizationColor.getGreen()) / closed.size() * i)), (255 - ((255 - visualizationColor.getBlue()) / closed.size() * i))));
				if (showCosts)
					RenderManager.setText(g.ID, new Coordinates(closed.get(i).getPos().getY(), closed.get(i).getPos().getX()), /*"F" + (int)(calcG(current, closed.get(i).getPos()) + calcH(closed.get(i).getPos(), targetPos))*/
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
	public boolean isValid(Coordinates pos, Coordinates targetPos, Grid g, Entity ent1, Entity ent2)
	{
		//		boolean[][] checked = new boolean[b.getRows()][b.getColumns()];
		//		for (int i = 0; i < checked.length; i++)
		//			for (int j = 0; j < checked[0].length; j++)
		//				checked[i][j] = false;
		//		return floodCheck(checked, pos, targetPos, b);
		return true;
	}

	public boolean floodCheck(boolean[][] checked, Coordinates pos, Coordinates targetPos, Grid g)
	{
		g.setFloorColor(pos, GrameUtils.randomColor());
		if (pos.equals(targetPos))
			return true;
		checked[pos.getY()][pos.getX()] = true;
		for (Coordinates sur : pos.getSurrounding(g))
			if (!checked[sur.getY()][sur.getX()])
				return floodCheck(checked, sur, targetPos, g);
		return true;
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

	private static class Node implements Serializable
	{
		private static final long serialVersionUID = -6786064237047397741L;
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
		GrameManager.initialize(new AStarPathfindingMovementAI());
	}

	@Override
	public void newGame()
	{
		Grid g = new Grid(20, 20);
		g.setWraparound(true);
		Entity player = new Entity("Player", Color.gray);
		Entity monster = new Entity("Monster", Color.red);
		g.addGrameObjectLayer(new GrameObjectLayer(g.getColumns(), g.getRows()), 1);
		player.makePlayer(1, true, g.ID);
		player.setSpeed(1);
		monster.setTarget(player.ID);
		AStarPathfindingMovementAI aStar = new AStarPathfindingMovementAI();
		monster.addAI(aStar, g.ID);
		monster.setSpeed(5);
		g.addGrameObject(player, new Coordinates(10, 10));
		g.addGrameObject(monster, new Coordinates(18, 10), 1);
		for (int i = 1; i <= 10; i++)
			new Schematic().load(g, GrameUtils.randomCoordinates(g));
		//		Schematic s = new Schematic(1);
		//		System.out.println(s.toString());
		//		s.load(b, new Coordinates(10, 10));
		RenderManager.render(g.ID, new CleanGridRender());
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
