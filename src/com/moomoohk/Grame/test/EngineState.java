package com.moomoohk.Grame.test;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Grame.Interfaces.Render;

public class EngineState implements Serializable
{
	private static final long serialVersionUID = 374960940802675271L;

	private ArrayList<Base> bases;
	private ArrayList<GrameObject> grameObjects;
	private Calendar dateCreated;
	private Date dateSaved;
	private int mainBase;
	private Render mainRender;

	public EngineState()
	{
		this.bases = new ArrayList<Base>();
		this.grameObjects = new ArrayList<GrameObject>();
		this.dateCreated = new GregorianCalendar();
		this.mainBase = -1;
		this.mainRender = null;
	}

	public void addGrameObject(GrameObject go)
	{
		this.grameObjects.add(go);
	}

	public void addBase(Base b)
	{
		this.bases.add(b);
	}

	public void setMainBase(int bID)
	{
		this.mainBase = bID;
	}

	public void setMainRender(Render r)
	{
		this.mainRender = r;
	}

	public void setSaved(Date d)
	{
		this.dateSaved = d;
	}

	public ArrayList<Base> getBases()
	{
		return this.bases;
	}

	public ArrayList<GrameObject> getGrameObjects()
	{
		return this.grameObjects;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public int getMainBase()
	{
		return this.mainBase;
	}

	public Render getMainRender()
	{
		return this.mainRender;
	}

	public Date getSaved()
	{
		return this.dateSaved;
	}

	public String details()
	{
		return "Bases: " + this.bases.size() + ", GrameObjects: " + this.grameObjects.size() + ", Date Created: " + new SimpleDateFormat("hh:mm:ss dd/MM/yyyy").format(dateCreated.getTime()) + ", Main Base: " + this.mainBase + ", Main Render: " + this.mainRender;
	}
}
