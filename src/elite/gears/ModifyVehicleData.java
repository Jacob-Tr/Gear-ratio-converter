package elite.gears;

import java.io.*;
import java.util.*;

class ModifyVehicleData 
{
	static FileHandler handler = new FileHandler();
	static List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	
	static HashMap<String, String> value = new HashMap<String, String>();
	static HashMap<String, String> map = new HashMap<String, String>();
	
	static String gears[] = new String[5];
	
	public void modifyFiles(File dir)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		loadFiles(dir);
		
		/*Gears.SCM(list.get(401).get("Gears")); // Returns 0 when value is 5. Saves as 0.
		return;*/
		
		for(int i = 400; i <= 611; i++)
		{
			
			File file = new File(dir + "\\" + Gears.roundStrToInt(Gears.toString(i)) + ".ini");
			Gears.SCM(file.toString());
			if(!handler.fileExists(file))
			{
				map.clear();
				list.add(i, map);
				continue;
			}
			
			map = list.get(i);
			
			if(!map.containsKey("Cylinders") || map.get("Cylinders").equals("<NO_VALUE>")) map.put("Cylinders", "4");
			if(!map.containsKey("Displacement") || map.get("Displacement").equals("<NO_VALUE>")) map.put("Displacement", "2.0");
			
			list.remove(i);
			list.add(i, organizeMap(map));
			
			Gears.SCM(i + " finished.");
			continue;
		}
		
		saveFiles(dir);
		return;
	}
	
	public void loadFiles(File dir)
	{
		HashMap<String, String> map = new HashMap<String, String>();
		
		for(int i = 0; i < 400; i++)
		{
			list.add(i, map);
		}
		
		for(int i = 400; i <= 611; i++)
		{
			File file = new File(dir + "\\" + Gears.roundStrToInt(Gears.toString(i)) + ".ini");
			if(!handler.fileExists(file))
			{
				list.add(i, map);
				continue;
			}
			
			try
			{
				list.add(i, handler.fileLoad(file));
				
				if(i == 401)
				{
					Gears.SCM(list.get(i).toString());
					break;
				}
			}
			catch(IOException e)
			{
				continue;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		
		return;
	}
	
	public void saveFiles(File dir)
	{
		for(int i = 400; i <= 611; i++)
		{
			File file = new File(dir + "\\" + Gears.roundStrToInt(Gears.toString(i)) + ".ini");
			if(!handler.fileExists(file)) continue;
			
			try
			{
				handler.fileSave(file, list.get(i));
			}
			catch(IOException e)
			{
				continue;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		
		return;
	}
	
	public HashMap<String, String> organizeMap(HashMap<String, String> map)
	{
		HashMap<String, String> nMap = new HashMap<String, String>();
		
		if(map.isEmpty()) return map;
		
		for(int i = 0; i <= handler.MAX_KEY_VALUES; i++)
		{
			nMap.put(handler.keyValue(i), map.get(handler.keyValue(i)));
		}
		
		return nMap;
	}
	
	public void addTransmissions()
	{
		HashMap<String, String> Map = new HashMap<String, String>();
		
		int topSpeed;
		@SuppressWarnings("unused")
		int engineUsed = 0;
		String speedData;
		double gearRatio = 0.0;
		
		for(int i = 400; i <= 611; i++)
		{
			value.clear();
			map.clear();
			topSpeed = 0;
			engineUsed = 0;
			gearRatio = 0.0;
			
			File file = new File(Gears.directory + "\\" + i);
			File iniFile = new File(file + ".ini");
			
			if(!handler.fileExists(file)) 
			{
				Gears.SCM("File Doesn't exist: #" + i); 
				continue;
			}
			
			try 
			{
				map.putAll(handler.fileLoad(file));
			} 
			catch (IOException e) 
			{
				continue;
			}
			
			topSpeed = Integer.parseInt(map.get("TopSpeed"));
			
			if(!value.containsKey("Gears") && topSpeed > -1)
			{
				for(int ii = 0; ii <= 4; ii++)
				{	
					switch(ii)
					{
						case 0: 
						{
							gearRatio = 0.27;
							break;
						}
						case 1:
						{
							gearRatio = 0.39; // 12
							break;
						}
						case 2:
						{
							gearRatio = 0.53; // 14
							break;
						}
						case 3: 
						{
							gearRatio = 0.69; // 17
							break;
						}
						case 4: 
						{
							gearRatio = 0.91; // 22
							break;
						}
					}
					
					speedData = Gears.roundStrToInt(Gears.roundStrToDecimalPlace(Gears.toString(topSpeed * gearRatio), 1));
					
					gears[ii] = speedData;
					engineUsed += Integer.parseInt(speedData);
				}
				
				map.put("Gears", "5");
				map.put("First", gears[0]);
				map.put("Second", gears[1]);
				map.put("Third", gears[2]);
				map.put("Fourth", gears[3]);
				map.put("Fifth", gears[4]);
			}
			
			value.putAll(map);
			
			if(value.containsKey(null)) value.remove(null);
			
			if(!handler.fileExists(iniFile)) handler.fileCreate(Gears.directory, iniFile);
			
			try 
			{
				handler.fileSave(iniFile, organizeMap(value));
			} 
			catch (IOException e) 
			{
				continue;
			}
		}
	}
}