package com.moomoohk.Grame.test;

import java.awt.Color;
import java.util.ArrayList;

import com.moomoohk.Grame.Core.Coordinates;
import com.moomoohk.Grame.Core.GrameManager;
import com.moomoohk.Grame.Core.GrameObject;
import com.moomoohk.Grame.Core.Graphics.RenderManager;
import com.moomoohk.Grame.Core.Graphics.PostProcessing.Label;

public class Dialog implements Runnable
{
	private ArrayList<String> lines;
	private Label label;
	private long interval;
	private GrameObject go;

	public Dialog(ArrayList<String> lines, long interval, GrameObject go)
	{
		this.label = new Label();
		this.label.setBackColor(Color.black);
		this.label.setTextColor(Color.white);
		this.label.setPadding(10);
		this.lines = lines;
		this.interval = interval;
		this.go = go;
		RenderManager.addLabel(this.label);
	}

	public void start()
	{
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		GrameManager.pauseAllGrameObjects(true);
		int squareWidth = RenderManager.getMainCanvas().getWidth() / GrameManager.findGrid(RenderManager.getMainGrid()).getColumns(), squareHeight = RenderManager.getMainCanvas().getHeight() / GrameManager.findGrid(RenderManager.getMainGrid()).getRows();
		Coordinates goPos = this.go.getPos(RenderManager.getMainGrid());
		this.label.setCenterX(goPos.getX() * squareWidth + squareWidth / 2);
		this.label.setCenterY(goPos.getY() * squareHeight);
		for (String line : this.lines)
		{
			for (int i = 0; i <= line.length(); i++)
			{
				this.label.setText(line.substring(0, i));
				try
				{
					Thread.sleep(50);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				Thread.sleep(this.interval);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		RenderManager.removeLabel(this.label);
		GrameManager.pauseAllGrameObjects(false);
	}
}
