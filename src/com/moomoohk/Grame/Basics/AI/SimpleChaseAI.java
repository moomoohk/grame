package com.moomoohk.Grame.Basics.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Basics.MovementAI;
import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameUtils;
import com.moomoohk.Grame.Core.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Core.Grid;

/**
 * This AI is designed to chase objects.
 * <p>
 * The algorithm will try to follow a straight line between point A and point B with no regard for obstacles.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @version 1.0
 * @since 2013-04-05
 */
public class SimpleChaseAI extends MovementAI
{
	private static final long serialVersionUID = 4361422461574615758L;

	public Coordinates getNext(Coordinates pos, Coordinates target, Grid g, Entity ent1, Entity ent2)
	{
		if (target == null)
		{
			GrameUtils.print("Crucial parameters missing! Returning pos. (" + ent1.getName() + ")", MessageLevel.ERROR);
			return pos;
		}
		int layer = -1;
		for (int i = 0; i < g.getGrameObjectLayers().size(); i++)
			if (g.getGrameObjectLayers().get(i).contains(ent1.ID))
				layer = i;
		if (!g.getWraparound())
		{
			Dir d = new Dir(pos, target);
			if (!g.isOccupied(pos.addDir(d)))
				return pos.addDir(d);
			return MovementAI.slide(g, pos, d, layer);
		}
		else
		{
			int normalDistance = pos.distance(target);
			Coordinates temp = pos;
			int distanceThroughWall = 0;
			boolean wrapped = false;
			while (temp.distance(target) > 0)
			{
				Dir dir = new Dir(target, temp);
				if (!wrapped && !g.isInGrid(temp.addDir(dir)))
				{
					temp = MovementAI.wraparound(g, MovementAI.slide(g, temp, dir, layer), dir);
					wrapped = true;
				}
				else
					if (!wrapped)
					{
						Dir dir2 = new Dir(temp, target);
						Dir wall = closestWall(temp, g);
						if (wall.getX() != 0)
							dir2.setX(wall.getX());
						if (wall.getY() != 0)
							dir2.setY(wall.getY());
						temp = MovementAI.slide(g, temp, dir2, layer);
					}
					else
						temp = MovementAI.slide(g, temp, new Dir(temp, target), layer);
				distanceThroughWall++;
			}
			if (normalDistance <= distanceThroughWall)
				return MovementAI.slide(g, pos, new Dir(pos, target), layer);
			else
			{
				Dir dir2 = new Dir(pos, target);
				Dir wall = closestWall(pos, g);
				if (wall.getX() != 0)
					dir2.setX(wall.getX());
				if (wall.getY() != 0)
					dir2.setY(wall.getY());
				return MovementAI.wraparound(g, /*MovementAI.slide(b, pos, dir2)*/pos, dir2);
			}
		}
	}

	public boolean isValid(Coordinates pos, Coordinates target, Grid g, Entity ent1, Entity ent2)
	{
		if (pos == null)
			return false;
		if (target == null)
		{
			GrameUtils.print("Crucial parameters missing! Returning not valid. (" + ent1.getName() + ")", MessageLevel.ERROR);
			return false;
		}
		if (ent1.getPos(g.ID).distance(ent2.getPos(g.ID)) > ent1.getRange())
		{
			return false;
		}

		if (ent1.getPos(g.ID).isSurrounded(g))
		{
			return false;
		}
		return true;
	}

	public boolean isOverride()
	{
		return false;
	}

	public String toString()
	{
		return "Simple Chaser";
	}

	public String author()
	{
		return "moomoohk";
	}

	public Dir closestWall(Coordinates pos, Grid b)
	{
		int up = pos.getY(), down = b.getRows() - pos.getY() - 1, left = pos.getX(), right = b.getColumns() - pos.getX() - 1;
		int min = Math.min(up, Math.min(down, Math.min(left, right)));
		if (min == up)
			return Dir.UP;
		if (min == down)
			return Dir.DOWN;
		if (min == left)
			return Dir.LEFT;
		return Dir.RIGHT;
	}
}
