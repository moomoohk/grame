package com.moomoohk.Grame.Essentials;

import java.awt.Color;
import java.util.ArrayList;

import com.moomoohk.Grame.Essentials.GrameUtils.MessageLevel;
import com.moomoohk.Grame.Interfaces.GrameObject;

/**
 * Bases are the backbone of Grame. They serve as the map in which objects move around and interact with each other.
 * 
 * @author Meshulam Silk <moomoohk@ymail.com>
 * @version 1.0
 * @since 2013-04-05
 */
public class Base
{
	/**
	 * The ID of this Base.
	 * <p>
	 * This ID is unique and cannot be changed.<br>
	 * It is this Base's place in the {@link GrameManager}'s list of Bases.
	 */
	public final int ID;
	private ArrayList<GrameObjectLayer> layers;
	private ColorLayer floor;
	private Color[] colors;
	private int width, height;
	private int goLayer;
	private boolean wraparound;
	private String title;

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            Width of the Base.
	 * @param height
	 *            Height of the Base.
	 */
	public Base(int width, int height)
	{
		this(width, height, Color.white, "");
	}

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            Width of the Base.
	 * @param height
	 *            Height of the Base.
	 * @param title
	 *            Title of the Base.
	 */
	public Base(int width, int height, String title)
	{
		this(width, height, Color.white, title);
	}

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            Width of the Base.
	 * @param height
	 *            Height of the Base.
	 * @param floorColor
	 *            Floor color of the Base.
	 */
	public Base(int width, int height, Color floorColor)
	{
		this(width, height, floorColor, "");
	}

	/**
	 * Constructor.
	 * 
	 * @param width
	 *            Width of the Base.
	 * @param height
	 *            Height of the Base.
	 * @param floorColor
	 *            Floor color of the Base.
	 * @param title
	 *            Title of the Base.
	 */
	public Base(int width, int height, Color floorColor, String title)
	{
		this.layers = new ArrayList<GrameObjectLayer>();
		this.colors = new Color[width * height];
		this.floor = new ColorLayer(width, height);
		this.floor.setAll(floorColor);
		this.width = width;
		this.height = height;

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				this.colors[x + y * width] = this.floor.getColor(new Coordinates(x, y));
		this.layers.add(new GrameObjectLayer(width, height));
		this.goLayer = 0;
		this.setTitle(title);
		this.wraparound = false;
		this.ID = GrameManager.add(this);
	}

	/**
	 * Ticks the Base.
	 * <p>
	 * Users will never need to call this method (The {@link GrameManager} takes care of ticking everything).
	 */
	public void tick()
	{
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
			{
				// this.colors[x + y * width] = calcColor(new Coordinates(x, y),
				// pos.size() - 1);
				this.colors[x + y * width] = calcColor(new Coordinates(x, y));
			}
	}

	/**
	 * Calculates the color of a square based on the objects that are occupying it.
	 * 
	 * @param pos
	 *            Set of {@link Coordinates} to calculate the color of.
	 * @return The color at the {@link Coordinates}.
	 */
	private Color calcColor(Coordinates pos)
	{
		for (int i = this.layers.size() - 1; i >= 0; i--)
			if (this.layers.get(i).getObject(pos) != null)
				return this.layers.get(i).getObject(pos).getColor();
		return floor.getColor(pos);
	}

	/*
	 * private Color calcColor(Coordinates pos, int i) { if (i == 0) return
	 * floor.getColor(pos); GrameObject temp = this.pos.get(i).getObject(pos);
	 * Color front = this.floor.getColor(pos); if (temp != null) front =
	 * temp.getColor(); return front; }
	 */

	/**
	 * Gets the number of rows (height) of this Base.
	 * 
	 * @return Row count.
	 */
	public int getRows()
	{
		return this.height;
	}

	/**
	 * Gets the number of columns (width) of this Base.
	 * 
	 * @return Column count.
	 */
	public int getColumns()
	{
		return this.width;
	}

	/**
	 * Checks whether a set of {@link Coordinates} are in this Base.
	 * 
	 * @param pos
	 *            {@link Coordinates} to check.
	 * @return True if the {@link Coordinates} are in this Base, else false.
	 */
	public boolean isInBase(Coordinates pos)
	{
		return (pos.y >= 0) && (pos.y <= this.height - 1) && (pos.x >= 0) && (pos.x <= this.width - 1);
	}

	/**
	 * Gets the current color of a square in this Base.
	 * 
	 * @param pos
	 *            {@link Coordinates} of square to get color of.
	 * @return The color of a square.
	 */
	public Color getColor(Coordinates pos)
	{
		return this.colors[pos.x + pos.y * width];
	}

	/**
	 * Adds a {@link GrameObjectLayer} to this base at a specified layer.
	 * 
	 * @param gol
	 *            {@link GrameObjectLayer} to add.
	 * @param place
	 *            Place within this Base's list of {@link GrameObjectLayer}s.
	 */
	public void addGrameObjectLayer(GrameObjectLayer gol, int place)
	{
		if (gol.getHeight() == this.height && gol.getWidth() == this.width)
		{
			this.layers.add(place, gol);
			if (place <= this.goLayer)
				this.goLayer++;
			GrameUtils.print("Added layer " + gol.toString() + " to Base ID:" + ID, MessageLevel.DEBUG);
		}
		else
		{
			GrameUtils.print("Incompatible layer! (Wrong dimensions: is " + gol.toString() + ", should be " + this.width + "x" + this.height + ")", MessageLevel.ERROR);
			return;
		}
	}

	/**
	 * Sets the floor color for all the squares in this Base.
	 * 
	 * @param c
	 *            The color to use.
	 */
	public void setFloorColor(Color c)
	{
		this.floor.setAll(c);
	}

	/**
	 * Sets the floor color of a square in this Base.
	 * 
	 * @param pos
	 *            {@link Coordinates} of square to switch.
	 * @param c
	 *            The color to use.
	 */
	public void setFloorColor(Coordinates pos, Color c)
	{
		this.floor.setColor(pos, c);
	}

	/**
	 * Adds a {@link GrameObject} to this Base.
	 * 
	 * @param go
	 *            {@link GrameObject} to add.
	 * @param pos
	 *            {@link Coordinates} to add the object to.
	 * @param layer
	 *            The layer number to add the object to.
	 * @see Base#addGrameObject(GrameObject, Coordinates)
	 */
	public void addGrameObject(GrameObject go, Coordinates pos, int layer)
	{
		if (layer >= this.layers.size())
		{
			GrameUtils.print("Layer " + layer + " out of range (" + (this.layers.size()) + ")!", MessageLevel.ERROR);
			return;
		}
		GrameObject temp = this.layers.get(layer).getObject(pos);
		if (this.layers.get(layer).setObject(pos, go))
		{
			if (temp != null)
				go.consume(temp);
			calcColor(pos);
		}
	}

	/**
	 * Adds a {@link GrameObject} to the default layer of this Base.
	 * 
	 * @param go
	 *            {@link GrameObject} to add.
	 * @param pos
	 *            {@link Coordinates} to add the object to.
	 * @see Base#addGrameObject(GrameObject, Coordinates, int)
	 */
	public void addGrameObject(GrameObject go, Coordinates pos)
	{
		addGrameObject(go, pos, goLayer);
	}

	/**
	 * Checks whether this Base contains a certain {@link GrameObject}.
	 * 
	 * @param goID
	 *            ID of {@link GrameObject} to look for.
	 * @return True if this Base contains the {@link GrameObject}, else false.
	 */
	public boolean containsGrameObject(int goID)
	{
		for (GrameObjectLayer gol : this.layers)
			if (gol.contains(goID))
				return true;
		return false;
	}

	/**
	 * Returns the position of a {@link GrameObject} that is in this Base.
	 * 
	 * @param goID
	 *            {@link GrameObject#ID} of the {@link GrameObject}.
	 * @return {@link Coordinates} of the {@link GrameObject}. If it is not found null will be returned.
	 */
	public Coordinates getGrameObjectPos(int goID)
	{
		for (int i = 0; i < this.layers.size(); i++)
			if (this.layers.get(i).contains(goID))
				return this.layers.get(i).getObjectPos(goID);
		return null;
	}

	/**
	 * Returns the default {@link GrameObjectLayer} of this Base.
	 * 
	 * @return The default {@link GrameObjectLayer} of this Base.
	 */
	public GrameObjectLayer getGrameObjectLayer()
	{
		return this.layers.get(goLayer);
	}

	/**
	 * Moves a {@link GrameObject} from its current position to a new position on the same layer.
	 * <p>
	 * If the new position already contains a collidable {@link GrameObject} it will be consumed by the {@link GrameObject} that is being moved there.
	 * 
	 * @param goID
	 *            The {@link GrameObject#ID} of the {@link GrameObject} to move.
	 * @param pos
	 *            {@link Coordinates} to which the object should move.
	 */
	public void moveGrameObject(int goID, Coordinates pos)
	{
		for (int i = 0; i < this.layers.size(); i++)
			if (this.layers.get(i).contains(goID) && (this.layers.get(i).getObject(pos) == null || !this.layers.get(i).getObject(pos).isCollidable()))
			{
				Coordinates prev = this.layers.get(i).getObjectPos(goID);
				GrameObject temp = this.layers.get(i).getObject(pos);
				this.layers.get(i).setObject(prev, null);
				calcColor(prev);
				this.layers.get(i).setObject(pos, GrameManager.findGrameObject(goID));
				if (temp != null)
					GrameManager.findGrameObject(goID).consume(temp);
				GrameManager.findGrameObject(goID).setPos(ID, pos);
				calcColor(pos);
			}
	}

	/**
	 * Checks whether a set of {@link Coordinates} is occupied on the default layer.
	 * 
	 * @param pos
	 *            {@link Coordinates} to check.
	 * @return True if the {@link Coordinates} are occupied on the default layer, else false.
	 * @see Base#isOccupied(Coordinates, int)
	 */
	public boolean isOccupied(Coordinates pos)
	{
		return isOccupied(pos, goLayer);
	}

	/**
	 * Checks whether a set of {@link Coordinates} is occupied on a specified layer.
	 * 
	 * @param pos
	 *            {@link Coordinates} to check.
	 * @param layer
	 *            Layer to check.
	 * @return True if the {@link Coordinates} are occupied on the specified layer, else false.
	 */
	public boolean isOccupied(Coordinates pos, int layer)
	{
		if (this.layers.get(layer).getObject(pos) != null)
			return true;
		return false;
	}

	/**
	 * Sets the "wraparound" aspect of this Base.
	 * <p>
	 * "Wraparound" means that if a {@link GrameObject} tries to go off one side of this Base it will be placed on the other side.<br>
	 * Think of Snake and Pac-Man.
	 * 
	 * @param f
	 *            True for wraparound, else false.
	 */
	public void setWraparound(boolean f)
	{
		this.wraparound = f;
	}

	/**
	 * Gets the "wraparound" aspect of this Base.
	 * <p>
	 * "Wraparound" means that if a {@link GrameObject} tries to go off one side of this Base it will be placed on the other side.<br>
	 * Think of Snake and Pac-Man.
	 * 
	 * @return True if this Base is a wraparound Base, else false.
	 */
	public boolean getWraparound()
	{
		return this.wraparound;
	}

	/**
	 * Returns the length of the diagonal of this Base.
	 * 
	 * @return The length of the diagonal of this Base.
	 */
	public int getDiagonal()
	{
		return Math.max(new Coordinates(0, 0).distance(new Coordinates(this.getColumns() - 1, this.getRows() - 1)), new Coordinates(this.getColumns() - 1, 0).distance(new Coordinates(0, this.getRows() - 1)));
	}

	/**
	 * Sets the title of this Base.
	 * 
	 * @param title
	 *            The title to use.
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Returns the title of this Base.
	 * 
	 * @return The title of this Base.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Prints useful information about this Base.
	 */
	public String toString()
	{
		String st = "Base ID:" + ID + "\n";
		st += "Contains: " + this.layers.size() + " Grame Object layers.\n";
		st += "Contains ";
		int count = 0;
		for (int i = 0; i < this.layers.size(); i++)
			count += this.layers.get(i).getTotalObjects();
		st += count + " Grame Objects.";
		return st;
	}
}
