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

public class TileBrush extends Brush
{
	BufferedImage tile;
	public TileBrush(int t)
	{
		tile = Main.tileset.tiles.get(t);
	}
	
	@Override
	public void draw(int x, int y, int w, int r, Graphics2D g2d)
	{
		if(r<4)
		{
			g2d.drawImage(rotate(tile,r), x*w, y*w, w, w, null);
		}
		else if(r==4 || r==6)
		{
			g2d.drawImage(rotate(tile,r), x*w, y*w+w, w, -w, null);
		}
		else
		{
			g2d.drawImage(rotate(tile,r), x*w+w, y*w, -w, w, null);
		}
	}

	@Override
	public void drawLight(int x, int y, int w, int r, Graphics2D g2d)
	{
		Composite c = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
		if(r<4)
		{
			g2d.drawImage(rotate(tile,r), x*w, y*w, w, w, null);
		}
		else if(r==4 || r==6)
		{
			g2d.drawImage(rotate(tile,r), x*w, y*w+w, w, -w, null);
		}
		else
		{
			g2d.drawImage(rotate(tile,r), x*w+w, y*w, -w, w, null);
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
