package hourEngineEditor.core;

import java.util.LinkedHashMap;
import java.util.Map;

public class Level
{
	public int xSize;
	public int ySize;
	private Map<Integer, Map<Integer, Integer>> collide = new LinkedHashMap<Integer, Map<Integer, Integer>>();
	public Level(int x, int y)
	{
		xSize = x;
		ySize = y;
	}
	
	public void setCollide(int x, int y, int val)
	{
		if(x >= xSize || y >= ySize)
		{
			return;
		}
		if(collide.get(x)!=null)
		{
			//System.out.println(x + " " + y);
			collide.get(x).put(y, val);
			//System.out.println(collide.get(x).get(y));
		}
		else
		{
			collide.put(x, new LinkedHashMap<Integer, Integer>());
			collide.get(x).put(y, val);
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
		if(x > xSize || y > ySize)
		{
			return 0;
		}
		
		if(collide.get(x)!=null && collide.get(x).get(y)!=null)
		{
			return collide.get(x).get(y);
		}
		else if(collide.get(x)==null)
		{
			collide.put(x, new LinkedHashMap<Integer, Integer>());
			collide.get(x).put(y, 0);
			return collide.get(x).get(y);
		}
		else
		{
			collide.get(x).put(y, 0);
			return collide.get(x).get(y);
		}
	}
}
