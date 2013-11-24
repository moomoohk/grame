package com.moomoohk.Grame.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UpdateLibs
{
	public static void main(String[] args)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			File workspace = new File("/Users/MeshulamSilk/Documents/workspace");
			File target = new File("/Users/MeshulamSilk/Documents/workspace/Grame/res/Libraries");
			String[] libraries = { "MooCommands", "MooConsole", "Mootilities" };
			for (int i = 0; i < libraries.length; i++)
			{
				inStream = new FileInputStream(workspace + "/" + libraries[i] + "/Build/" + libraries[i] + ".jar");
				outStream = new FileOutputStream(target + "/" + libraries[i] + ".jar");
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inStream.read(buffer)) > 0)
					outStream.write(buffer, 0, length);
				inStream.close();
				outStream.close();
				System.out.println(libraries[i] + " updated successfully!");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
