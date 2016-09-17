package hourEngineEditor.core;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tileset
{
	BufferedImage tileset;
	public ArrayList<BufferedImage> tiles = new ArrayList<BufferedImage>();
	int tilesize;
	public Tileset(BufferedImage bi)
	{
		tileset = bi;
		tilesize = new Color(tileset.getRGB(0, 0)).getRed();
		for(int i = 0; i < 4; i++)
		tiles.add(tileset.getSubimage(tilesize+(tilesize*i), 0, tilesize, tilesize));
	}
}
