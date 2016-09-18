package hourEngineEditor.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileBrush extends Brush
{
	BufferedImage tile;
	int tilenum;
	Map<Integer, BufferedImage[]> tiles = new HashMap<Integer, BufferedImage[]>();
	public TileBrush(int t)
	{
		tilenum = t;
		tile = Main.tilesets.get(Main.tileset).tiles.get(t);
	}
	
	public void addNewTileset(Tileset t, int til)
	{
		BufferedImage[] newTiles = new BufferedImage[4];
		for(int i = 0; i < 4; i++)
		newTiles[i] = rotate(t.tiles.get(tilenum), i);
		tiles.put(til, newTiles);
	}
	
	@Override
	public void draw(int x, int y, int w, int r, int t, Graphics2D g2d)
	{
		if(!tiles.containsKey(t))
		{
			addNewTileset(Main.tilesets.get(t), t);
		}
		if(r<4)
		{
			g2d.drawImage(tiles.get(t)[r], x*w, y*w, w, w, null);
		}
		else if(r==4 || r==6)
		{
			g2d.drawImage(tiles.get(t)[r%4], x*w, y*w+w, w, -w, null);
		}
		else
		{
			g2d.drawImage(tiles.get(t)[r%4], x*w+w, y*w, -w, w, null);
		}
	}

	@Override
	public void drawLight(int x, int y, int w, int r, int t, Graphics2D g2d)
	{
		//System.out.println(Main.tileset);
		if(!tiles.containsKey(t))
		{
			addNewTileset(Main.tilesets.get(t), t);
		}
		Composite c = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
		if(r<4)
		{
			g2d.drawImage(tiles.get(t)[r], x*w, y*w, w, w, null);
		}
		else if(r==4 || r==6)
		{
			g2d.drawImage(tiles.get(t)[r%4], x*w, y*w+w, w, -w, null);
		}
		else
		{
			g2d.drawImage(tiles.get(t)[r%4], x*w+w, y*w, -w, w, null);
		}
		g2d.setComposite(c);
	}
	
	public static BufferedImage rotate(BufferedImage image, int r)
	{
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.translate(image.getWidth(null)/2.0, image.getHeight(null)/2.0);
		bGr.rotate(Math.toRadians(r*90));
		bGr.translate(-image.getWidth(null)/2.0, -image.getHeight(null)/2.0);
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();
		return bimage;
	}
}
