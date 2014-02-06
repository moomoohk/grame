package com.moomoohk.Grame.Core.Graphics.PostProcessing;

import java.awt.Color;
import java.awt.Font;

public class Label
{
	private int paddingTop, paddingLeft, paddingRight, paddingBottom, centerX, centerY;
	private String text;
	private Color textColor, backColor;
	private Font font;

	public Label(int centerX, int centerY, String text, Font font, Color textColor, Color backColor, int paddingTop, int paddingLeft, int paddingRight, int paddingBottom)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.text = text;
		this.font = font;
		this.textColor = textColor;
		this.backColor = backColor;
		this.paddingTop = paddingTop;
		this.paddingLeft = paddingLeft;
		this.paddingRight = paddingRight;
		this.paddingBottom = paddingBottom;
	}

	public void setPaddingRight(int paddingRight)
	{
		this.paddingRight = paddingRight;
	}

	public int getPaddingRight()
	{
		return paddingRight;
	}

	public void setCenterX(int centerX)
	{
		this.centerX = centerX;
	}

	public int getCenterX()
	{
		return centerX;
	}

	public void setPaddingLeft(int paddingLeft)
	{
		this.paddingLeft = paddingLeft;
	}

	public int getPaddingLeft()
	{
		return paddingLeft;
	}

	public void setPaddingBottom(int paddingBottom)
	{
		this.paddingBottom = paddingBottom;
	}

	public int getPaddingBottom()
	{
		return paddingBottom;
	}

	public void setPaddingTop(int paddingTop)
	{
		this.paddingTop = paddingTop;
	}

	public int getPaddingTop()
	{
		return paddingTop;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public void setTextColor(Color textColor)
	{
		this.textColor = textColor;
	}

	public Color getTextColor()
	{
		return textColor;
	}

	public void setCenterY(int centerY)
	{
		this.centerY = centerY;
	}

	public int getCenterY()
	{
		return centerY;
	}

	public void setBackColor(Color backColor)
	{
		this.backColor = backColor;
	}

	public Color getBackColor()
	{
		return backColor;
	}

	public void setFont(Font font)
	{
		this.font = font;
	}

	public Font getFont()
	{
		return font;
	}
}