package elite.gears;

import java.io.*;
import java.util.*;

class Gears		// Finished at 7:05 PM (And I also want to go download a mod for Skyrim to gain 3 points per level.)
{	
	static File directory = new File("C:\\Users\\jacob\\Desktop\\Development Environment\\scriptfiles\\VSpec\\");
	
	static FileHandler handler = new FileHandler();
	
	public static void main(String[] Args) throws IOException
	{
		ModifyVehicleData data = new ModifyVehicleData();
		
		//data.modifyFiles(directory);
		data.addTransmissions();
		return;
	}
	
	static Boolean isNumeric(String str)
	{
		@SuppressWarnings("unused")
		double attempt;
		
		try
		{
			attempt = Double.parseDouble(str);
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}
	
	static String roundStrToInt(String str)
	{
		String output = str;
		
		if(!isNumeric(output)) return "NaN";
		
		if(str.length() < 2) return output;
		for(int i = 0; i <= str.length(); i++)
		{
			if(str.charAt(i) == '.') return str.substring(0, i);
		}
		
		return output;
	}
	
	static String toString(double val)
	{
		String str = ("" + val);
		if(str.length() > 2)
		{
			if(str.substring(str.length() - 2, str.length()) == ".0") return str.substring(0, str.length() - 2);
		}
		return "" + val;
	}
	
	static void SCM(String str)
	{
		System.out.println("\n" + str);
		return;
	}
	
	static void SCM2(String str)
	{
		System.out.println(str);
		return;
	}
	
	static String roundStrToDecimalPlace(String str, int places)
	{
		Boolean encounteredDecimal = false;
		int placesPast = 0;
		
		if(places < 1) return "Error: Value less than one";
		
		for(int i = 0; i < str.length(); i++)
		{
			if(i + 1 == str.length()) break;
			
			if(encounteredDecimal == false && i != 0) if(str.charAt(i) == '.') encounteredDecimal = true;
			else if(encounteredDecimal) placesPast++;
			
			if(placesPast >= places) return str.substring(0, i);
		}
		
		return str;
	}
}