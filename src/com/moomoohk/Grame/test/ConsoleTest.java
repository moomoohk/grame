
package com.moomoohk.Grame.test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

import com.moomoohk.MooConsole.Console;


public class ConsoleTest
{
	public static Console console;
	public static void main(String[] args)
	{
		System.setOut(new ConsoleTest().new OutputOverride(System.out));
		System.setErr(new ConsoleTest().new OutputOverride(System.err));
		console=new Console();
		console.setVisible(true);
		for(int i=1; i<=20; i++)
		{
			System.out.println(new Random().nextInt(10));
			System.err.println(new Random().nextInt(10));
		}
	}
	private class OutputOverride extends PrintStream
	{
		public OutputOverride(OutputStream str)
		{
			super(str);
		}

		@Override
		public void write(byte[] b) throws IOException
		{
			super.write(b);
			String text = new String(b).trim();
			if (!text.equals("") && !text.equals("\n"))
				console.addText("From Console: " + text+"\n");
		}

		@Override
		public void write(byte[] buf, int off, int len)
		{
			super.write(buf, off, len);
			String text = new String(buf, off, len).trim();
			if (!text.equals("") && !text.equals("\n"))
			{
				console.addText("From Console: " + text+"\n");
			}
		}

		@Override
		public void write(int b)
		{
			throw new UnsupportedOperationException("Write(int) is not supported by OutputOverride.");
		}
	}
}

