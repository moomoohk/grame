package com.moomoohk.Grame.test;

import java.io.Serializable;
import java.util.ArrayList;

import com.moomoohk.Grame.Essentials.Base;
import com.moomoohk.Grame.Interfaces.GrameObject;
import com.moomoohk.Mootilities.ObjectUtils.ObjectUtils;

public class EngineState implements Serializable
{
	private static final long serialVersionUID = 374960940802675271L;

	private ArrayList<Base> bases;
	private ArrayList<GrameObject> grameObjects;

	public EngineState()
	{
		this.bases = new ArrayList<Base>();
		this.grameObjects = new ArrayList<GrameObject>();
	}

	public void addGrameObject(GrameObject go)
	{
		this.grameObjects.add(go);
	}

	public void addBase(Base b)
	{
		this.bases.add(b);
	}

	public ArrayList<Base> getBases()
	{
		return this.bases;
	}

	public ArrayList<GrameObject> getGrameObjects()
	{
		return this.grameObjects;
	}

	public boolean save(String path, String fileName, String extension)
	{
		return ObjectUtils.save(this, path, fileName, extension);
	}
}
