package hourEngineEditor.core;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tileset
{
	public BufferedImage tileset;
	public ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	int tilesize;
	public String n;
	public Tileset(BufferedImage bi, String name)
	{
		tileset = bi;
		n = name;
		tilesize = new Color(tileset.getRGB(0, 0)).getRed();
		for(int i = 0; i < 5; i++)
		tiles.add(tileset.getSubimage(tilesize+(tilesize*i), 0, tilesize, tilesize));
	}
}
