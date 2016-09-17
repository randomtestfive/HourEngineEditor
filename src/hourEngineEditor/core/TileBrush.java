package hourEngineEditor.core;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TileBrush extends Brush
{
	BufferedImage tile;
	public TileBrush(int t)
	{
		tile = Main.tileset.tiles.get(t);
	}
	
	@Override
	public void draw(int x, int y, int w, Graphics2D g2d)
	{
		g2d.drawImage(tile, x*w, y*w, w, w, null);
	}

	@Override
	public void drawLight(int x, int y, int w, Graphics2D g2d)
	{
		Composite c = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
		g2d.drawImage(tile, x*w, y*w, w, w, null);
		g2d.setComposite(c);
	}
}
