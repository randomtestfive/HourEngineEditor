package hourEngineEditor.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Level
{
	public int xSize;
	public int ySize;
	private Map<Integer, Map<Integer, Integer>> collide = new LinkedHashMap<Integer, Map<Integer, Integer>>();
	private Map<Integer, Map<Integer, Integer>>	rotate = new LinkedHashMap<Integer, Map<Integer, Integer>>();
	private Map<Integer, Map<Integer, Integer>>	tileset = new LinkedHashMap<Integer, Map<Integer, Integer>>();
	public Level(int x, int y)
	{
		xSize = x;
		ySize = y;
	}
	
	public void setCollide(int x, int y, int val)
	{
		collide = setProperty(x, y, val, collide);
	}
	
	public void setRotate(int x, int y, int val)
	{
		rotate = setProperty(x, y, val, rotate);
	}
	
	public void setTileset(int x, int y, int val)
	{
		tileset = setProperty(x, y, val, tileset);
	}
	
	private Map<Integer, Map<Integer, Integer>> setProperty(int x, int y, int val, Map<Integer, Map<Integer, Integer>> map)
	{
		if(x >= xSize || y >= ySize || x < 0 || y < 0)
		{
			return map;
		}
		if(map.get(x)!=null)
		{
			map.get(x).put(y, val);
		}
		else
		{
			map.put(x, new LinkedHashMap<Integer, Integer>());
			map.get(x).put(y, val);
		}
		return map;
	}
	
	private int getProperty(int x, int y, Map<Integer, Map<Integer, Integer>> map)
	{
		if(x > xSize || y > ySize)
		{
			return 0;
		}
		
		if(map.get(x)!=null && map.get(x).get(y)!=null)
		{
			return map.get(x).get(y);
		}
		else if(map.get(x)==null)
		{
			map.put(x, new LinkedHashMap<Integer, Integer>());
			map.get(x).put(y, 0);
			return map.get(x).get(y);
		}
		else
		{
			map.get(x).put(y, 0);
			return map.get(x).get(y);
		}
	}
	
	public void setSize(int x, int y)
	{
		xSize = x;
		ySize = y;
		for(Map.Entry<Integer, Map<Integer, Integer>> m : collide.entrySet())
		{
			if(m.getKey() >= xSize)
			{
				collide.put(m.getKey(), new LinkedHashMap<Integer, Integer>());
			}
			for(Map.Entry<Integer, Integer> m2 : m.getValue().entrySet())
			{
				if(m2.getKey() >= ySize)
				{
					collide.get(m.getKey()).put(m2.getKey(), 0);
				}
			}
		}
	}
	
	public int getCollide(int x, int y)
	{
		return getProperty(x, y, collide);
	}
	
	public int getRotate(int x, int y)
	{
		return getProperty(x, y, rotate);
	}
	
	public int getTileset(int x, int y)
	{
		return getProperty(x, y, tileset);
	}
}
