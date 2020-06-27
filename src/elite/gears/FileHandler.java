package elite.gears;

import java.io.*;
import java.util.HashMap;

class FileHandler 
{
	static String tag[] = new String[500];
	final int MAX_KEY_VALUES = 10;
	
	boolean fileExists(File file)
	{
		if(!file.exists()) return false;
		return true;
	}
	
	void fileCreate(File dir, File file)
	{
		if(!dir.isDirectory()) dir.mkdirs();
		try
		{
			file.createNewFile();
			return;
		}
		catch(IOException e)
		{
		}
		catch(Exception e)
		{
		}
		return;
	}
	
	void fileSave(File file, HashMap<String, String> stat) throws IOException
	{
		FileWriter writer = new FileWriter(file);
		BufferedWriter write = new BufferedWriter(writer);
		
		stat.keySet().toArray(tag);
		
		for(int i = 0; i <= stat.size(); i++) 
		{
			if(tag[i] == null) continue;
			write.write(tag[i] + " = " + stat.get(tag[i]));
			write.newLine();
			continue;
		}
		
		write.close();
		writer.close();
		return;
	}
	
	HashMap<String, String> fileLoad(File file) throws IOException
	{
		HashMap<String, String> map = new HashMap<String, String>();
		FileReader read = new FileReader(file);
		BufferedReader reader = new BufferedReader(read);
		
		for(int k = 0; k <= MAX_KEY_VALUES; k++)
		{
			int equalsPos = -1;
			
			String tag = "";
			String str = reader.readLine();
			
			try
			{
				if(str.equals(null) || str.equals("")) break;
			}
			catch(NullPointerException e)
			{
				return map;
			}
			
			Gears.SCM(str);
			
			for(int n = 0; n <= str.length(); n++)
			{
				if(str.charAt(n) == '=')
				{
					equalsPos = n;
					break;
				}
				else continue;
			}
			
			//if((equalsPos - 2) < 0) continue;
			if((equalsPos + 2) >= str.length()) str = "<NO_VALUE>";
			
			tag = str.substring(0, equalsPos - 1);
			if(!str.equals("NO_VALUE")) str = str.substring(equalsPos + 2, str.length());
			
			Gears.SCM("Tag: " + tag + "Value: " + str);
			
			map.put(tag, str);
			continue;
		}
		
		reader.close();
		read.close();
		return map;
	}
	
	String keyValue(int key)
	{
		switch(key)
		{
			case 0: return "TopSpeed";
			case 1: return "Price";
			case 2: return "EngineType";
			case 3: return "Cylinders";
			case 4: return "Displacement";
			case 5: return "Gears";
			case 6: return "First";
			case 7: return "Second";
			case 8: return "Third";
			case 9: return "Fourth";
			case 10: return "Fifth";
			default: return null;
		}
	}
	
	int keyValueIndex(String str)
	{
		for(int i = 0; i <= MAX_KEY_VALUES; i++)
		{
			if(keyValue(i).equals(str)) return i;
		}
		return -1;
	}
}
