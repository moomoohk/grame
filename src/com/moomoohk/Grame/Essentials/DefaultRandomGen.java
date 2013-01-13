package com.moomoohk.Grame.Essentials;

import com.moomoohk.Grame.Interfaces.EntityGenerator;

public class DefaultRandomGen implements EntityGenerator
{
	public String nameGen()
	{
		String random = "";
		int length = (int) (Math.random() * 5.0D) + 3;
		int p = 0;
		char[] letters = new char[26];
		char letter = 'a';
		for (int i = 0; i < letters.length; i++)
		{
			letters[i] = letter;
			letter = (char) (letter + '\001');
		}
		for (int i = 0; i < length; i++)
		{
			p = (int) (Math.random() * 26.0D);
			while (!checkLetter(random, letters[p], i))
				p = (int) (Math.random() * 26.0D);
			random = random + letters[p];
			if (i == 0)
				random = random.toUpperCase();
		}
		String[] suffixes =
		{ "tron", "man", "nar", "ram", "berg", "san" };
		char[] vowels =
		{ 'a', 'e', 'i', 'o', 'u' };
		boolean lastVowel = false;
		for (int i = 0; i < vowels.length; i++)
			if (random.charAt(random.length() - 1) == vowels[i])
				lastVowel = true;
		if (!lastVowel)
			random = random + vowels[(int) (Math.random() * 5.0D)];
		random = random + suffixes[(int) (Math.random() * 6.0D)];
		int pick = (int) (Math.random() * 98.0D);
		if (pick == 70)
			random = "Meshuly";
		if (pick == 10)
			random = "Sholiyompompom";
		return random;
	}

	public String typeGen()
	{
		String[] types =
		{ "elf", "orc", "human" };
		return types[(int) (Math.random() * 3.0D)];
	}

	private static boolean checkLetter(String random, char letter, int i)
	{
		if ((i % 2 == 0) && (letter != 'a') && (letter != 'e') && (letter != 'i') && (letter != 'i') && (letter != 'o') && (letter != 'u'))
			return false;
		if ((letter == 'q') || (letter == 'x') || (letter == 'z') || (letter == 'u') || (letter == 'j'))
			for (int j = 0; j < random.length(); j++)
				if (random.toLowerCase().charAt(j) == letter)
					return false;
		if ((letter == 'o') || (letter == 'a'))
		{
			int count = 1;
			for (int j = 0; j < random.length(); j++)
				if (random.toLowerCase().charAt(j) == letter)
					count++;
			if (count > 2)
				return false;
		}
		if ((i > 0) && ((letter == 'a') || (letter == 'e') || (letter == 'i') || (letter == 'o') || (letter == 'u')) && (random.charAt(i - 1) == letter))
		{
			return false;
		}
		return (i != 0) || (letter != 'u');
	}
}