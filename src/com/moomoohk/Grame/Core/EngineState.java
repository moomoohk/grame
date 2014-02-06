package com.moomoohk.Grame.Core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Represents the state of the engine. Used for saving games.
 * 
 * @author Meshulam Silk (moomoohk@ymail.com)
 * @since Feb 6, 2014
 */
public class EngineState implements Serializable
{
	private static final long serialVersionUID = 374960940802675271L;

	private ArrayList<Grid> grids;
	private ArrayList<GrameObject> grameObjects;
	private Calendar dateCreated;
	private Date dateSaved;
	private int mainGrid;
	private Render mainRender;

	/**
	 * Constructor
	 */
	public EngineState()
	{
		this.grids = new ArrayList<Grid>();
		this.grameObjects = new ArrayList<GrameObject>();
		this.dateCreated = new GregorianCalendar();
		this.mainGrid = -1;
		this.mainRender = null;
	}

	/**
	 * Adds a {@link GrameObject} to the list of {@link GrameObject}s this state is tracking.
	 * 
	 * @param go
	 *            {@link GrameObject} to add
	 */
	public void addGrameObject(GrameObject go)
	{
		this.grameObjects.add(go);
	}

	/**
	 * Adds a {@link Grid} to the list of {@link Grid}s this state is tracking.
	 * 
	 * @param g
	 *            {@link Grid} to add
	 */
	public void addGrid(Grid g)
	{
		this.grids.add(g);
	}

	/**
	 * Sets the main {@link Grid} of this state.
	 * 
	 * @param gID
	 *            {@link Grid} to make main
	 */
	public void setMainGrid(int gID)
	{
		this.mainGrid = gID;
	}

	/**
	 * Sets the main {@link Render} of this state.
	 * 
	 * @param r
	 *            {@link Render} to make main
	 */
	public void setMainRender(Render r)
	{
		this.mainRender = r;
	}

	/**
	 * Sets the date of the saving of this state.
	 * 
	 * @param d
	 *            Date to set
	 */
	public void setSaved(Date d)
	{
		this.dateSaved = d;
	}

	/**
	 * Gets the list of {@link Grid}s that this state is tracking.
	 * 
	 * @return A list of {@link Grid}s that this state is tracking
	 */
	public ArrayList<Grid> getGrids()
	{
		return this.grids;
	}

	/**
	 * Gets the list of {@link GrameObject}s that this state is tracking.
	 * 
	 * @return A list of {@link GrameObject}s that this state is tracking
	 */
	public ArrayList<GrameObject> getGrameObjects()
	{
		return this.grameObjects;
	}

	/**
	 * Gets the date of this state's creation.
	 * 
	 * @return The date of this state's creation
	 */
	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	/**
	 * Gets the {@link Grid#ID} of the main {@link Grid} of this state.
	 * 
	 * @return The {@link Grid#ID} of the main {@link Grid} of this state
	 */
	public int getMainGrid()
	{
		return this.mainGrid;
	}

	/**
	 * Gets the main {@link Render} of this state.
	 * 
	 * @return The main {@link Render} of this state
	 */
	public Render getMainRender()
	{
		return this.mainRender;
	}

	/**
	 * Gets the date of this state's last save.
	 * 
	 * @return The date of this state's last save
	 */
	public Date getSaved()
	{
		return this.dateSaved;
	}

	/**
	 * Gets some human readable info about this state.
	 * 
	 * @return Some human readable info about this state
	 */
	public String details()
	{
		return "Grids: " + this.grids.size() + ", GrameObjects: " + this.grameObjects.size() + ", Date Created: " + new SimpleDateFormat("hh:mm:ss dd/MM/yyyy").format(dateCreated.getTime()) + ", Main Grid: " + this.mainGrid + ", Main Render: " + this.mainRender;
	}
}
