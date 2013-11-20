package com.moomoohk.Grame.AI;

import com.moomoohk.Grame.Basics.Dir;
import com.moomoohk.Grame.Basics.Entity;
import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Essentials.Coordinates;
import com.moomoohk.Grame.Essentials.GrameManager;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.MovementAI;

/**
 * AI that lets the user control {@link GrameObject}s using the keyboard.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class PlayerMovementAI extends MovementAI
{
	private static final long serialVersionUID = 4329500019408321580L;
	private int player = 0;

	/**
	 * Constructor.
	 * 
	 * @param player
	 *            1 for "wasd", 2 for arrow keys.
	 */
	public PlayerMovementAI(int player)
	{
		super();
		this.player = player;
	}

	public Coordinates getNext(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		Dir d = player == 1 ? GrameManager.dir1 : player == 2 ? GrameManager.dir2 : null;
		if (d == null)
			return pos;
		int layer = -1;
		for (int i = 0; i < b.getGrameObjectLayers().size(); i++)
			if (b.getGrameObjectLayers().get(i).contains(ent1.ID))
				layer = i;
		if (!b.isInBase(MovementAI.slide(b, pos, d, layer)))
			if (b.getWraparound())
				return MovementAI.wraparound(b, MovementAI.slide(b, pos, d, layer), d);
			else
				return pos;
		else
			return MovementAI.slide(b, pos, d, layer);
	}

	public boolean isValid(Coordinates pos, Coordinates target, Base b, Entity ent1, Entity ent2)
	{
		return ent1.isPlayer(b.ID);
	}

	public boolean isOverride()
	{
		return true;
	}

	public String toString()
	{
		return "Player Movement";
	}

	public String author()
	{
		return "moomoohk";
	}
}
