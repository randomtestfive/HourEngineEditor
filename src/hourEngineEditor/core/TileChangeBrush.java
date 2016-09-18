package hourEngineEditor.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TileChangeBrush extends Brush
{

	BufferedImage tile;
	Map<Integer, BufferedImage[][]> tiles = new HashMap<Integer, BufferedImage[][]>();
	public void addNewTileset(Tileset t, int til)
	{
		BufferedImage[][] moreTiles = new BufferedImage[5][4];
		for(int w = 0; w < 5; w++)
		{
			BufferedImage[] newTiles = new BufferedImage[4];
			for(int i = 0; i < 4; i++)
			{
				newTiles[i] = rotate(t.tiles.get(w), i);
			}
			moreTiles[w] = newTiles;
		}
		tiles.put(til, moreTiles);
	}
	
	@Override
	public void draw(int x, int y, int w, int r, int t, Graphics2D g2d)
	{
		int collide = Main.level.getCollide(x, y);
		int rot = Main.level.getRotate(x, y);
		if(collide !=0)
		{
			if(!tiles.containsKey(t))
			{
				addNewTileset(Main.tilesets.get(t), t);
			}
			if(r<4)
			{
				g2d.drawImage(tiles.get(t)[collide][rot], x*w, y*w, w, w, null);
			}
			else if(rot==4 || rot==6)
			{
				g2d.drawImage(tiles.get(t)[collide][rot%4], x*w, y*w+w, w, -w, null);
			}
			else
			{
				g2d.drawImage(tiles.get(t)[collide][rot%4], x*w+w, y*w, -w, w, null);
			}
		}
	}

	@Override
	public void drawLight(int x, int y, int w, int r, int t, Graphics2D g2d)
	{
		Composite c = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
		int collide = Main.level.getCollide(x, y);
		//System.out.println(collide);
		int rot = Main.level.getRotate(x, y);
		if(collide!=0)
		{
			if(!tiles.containsKey(t))
			{
				addNewTileset(Main.tilesets.get(t), t);
			}
			if(rot<4)
			{
				g2d.drawImage(tiles.get(t)[collide-1][rot], x*w, y*w, w, w, null);
			}
			else if(rot==4 || rot==6)
			{
				g2d.drawImage(tiles.get(t)[collide-1][rot%4], x*w, y*w+w, w, -w, null);
			}
			else
			{
				g2d.drawImage(tiles.get(t)[collide-1][rot%4], x*w+w, y*w, -w, w, null);
			}
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
