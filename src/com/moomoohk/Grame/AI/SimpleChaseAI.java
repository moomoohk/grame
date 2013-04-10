package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameUtils;
import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.MovementAI;

/**
 * This AI is designed to chase objects.
 * <p>
 * The algorithm will try to follow a straight line between point A and point B with no regard for obstacles.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class SimpleChaseAI extends MovementAI
{
	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		if (target == null)
		{
			GrameUtils.print("Crucial parameters missing! Returning pos. (" + ent1.getName() + ")", MessageLevel.ERROR);
			return pos;
		}
		if (!b.getWraparound())
		{
			Dir d = new Dir(pos, target);
			if (!b.isOccupied(pos.addDir(d)))
				return pos.addDir(d);
			return MovementAI.slide(b, pos, d);
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
				if (!wrapped && !b.isInBase(temp.addDir(dir)))
				{
					temp = MovementAI.wraparound(b, MovementAI.slide(b, temp, dir), dir);
					wrapped = true;
				}
				else
					if (!wrapped)
					{
						Dir dir2 = new Dir(temp, target);
						Dir wall = closestWall(temp, b);
						if (wall.getX() != 0)
							dir2.setX(wall.getX());
						if (wall.getY() != 0)
							dir2.setY(wall.getY());
						temp = MovementAI.slide(b, temp, dir2);
					}
					else
						temp = MovementAI.slide(b, temp, new Dir(temp, target));
				distanceThroughWall++;
			}
			if (normalDistance <= distanceThroughWall)
				return MovementAI.slide(b, pos, new Dir(pos, target));
			else
			{
				Dir dir2 = new Dir(pos, target);
				Dir wall = closestWall(pos, b);
				if (wall.getX() != 0)
					dir2.setX(wall.getX());
				if (wall.getY() != 0)
					dir2.setY(wall.getY());
				return MovementAI.wraparound(b, MovementAI.slide(b, pos, dir2), dir2);
			}
		}
	}

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		if (pos == null)
			return false;
		if (target == null)
		{
			GrameUtils.print("Crucial parameters missing! Returning not valid. (" + ent1.getName() + ")", MessageLevel.ERROR);
			return false;
		}
		if (ent1.getPos(b.ID).distance(ent2.getPos(b.ID)) > ent1.getRange())
		{
			return false;
		}

		if (ent1.getPos(b.ID).isSurrounded(b))
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

	public Dir closestWall(Coordinates pos, Base b)
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
