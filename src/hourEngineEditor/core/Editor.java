package hourEngineEditor.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Editor extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener
{
	double rcamerax = 0;
	double rcameray = 0;
	int camerax = 0;
	int cameray = 0;
	int zoom = 30;
	public static TileChangeBrush changebrush;
	
	public Editor()
	{
		//brushes.add(red);
		//brushes.add(yellow);
		//brushes.add(something);
		brushes.add(new TileBrush(0));
		brushes.add(new TileBrush(1));
		brushes.add(new TileBrush(2));
		brushes.add(new TileBrush(3));
		brushes.add(new TileBrush(4));
		changebrush = new TileChangeBrush();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}
	
	public static void reloadBrushes()
	{
		for(int i = 0; i < brushes.size(); i++)
		{
			brushes.set(i, new TileBrush(i));
		}
	}

	public static ArrayList<Brush> brushes = new ArrayList<Brush>();
	@Override
	public void paint(Graphics g)
	{
		
		camerax = (int) rcamerax;
		cameray = (int) rcameray;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.translate(camerax, cameray);
		g2d.setColor(Color.gray.brighter());
		g2d.fillRect(0, 0, Main.level.xSize * zoom, Main.level.ySize * zoom);
		for(int x = 0; x < Main.level.xSize; x++)
		{
			for(int y = 0; y < Main.level.ySize; y++)
			{
				if(Main.level.getCollide(x, y)!=0 && brushes.size()+1 > Main.level.getCollide(x, y))
				{
					brushes.get(Main.level.getCollide(x, y)-1).draw(x, y, zoom, Main.level.getRotate(x, y), Main.level.getTileset(x, y), g2d);
				}
			}
		}
		if(x < Main.level.xSize * zoom && y < Main.level.ySize * zoom && x>0 && y>0 && !start)
		{
			if(Main.brush != 0 && brushes.size()+1 > Main.brush && !Main.tilePainter)
			{
				//System.out.println("ungr");
				if(Main.brushSize >= 1)
				{
					brushes.get(Main.brush-1).drawLight(x/zoom, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
				}
				if(Main.brushSize >= 2)
				{
					if(x>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
					if(y>zoom-1)
					brushes.get(Main.brush-1).drawLight(x/zoom, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight(x/zoom, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
				}
				if(Main.brushSize >= 3)
				{
					if(x>zoom-1 && y>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(x>zoom-1 && y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
				}
			}
			
			if(Main.brush == 0 && !Main.tilePainter)
			{
				if(Main.brushSize >= 1)
				{
					Color c = Color.white;
					c = new Color(c.getRed(), c.getGreen(), c.getBlue(), 100);
					g2d.setColor(c);
					g2d.fillRect(((int)(x/zoom))*zoom, ((int)(y/zoom))*zoom, zoom, zoom);
				}
				if(Main.brushSize >= 2)
				{
					g2d.fillRect(((int)(x/zoom)+1)*zoom, ((int)(y/zoom))*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom)-1)*zoom, ((int)(y/zoom))*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom))*zoom, ((int)(y/zoom)+1)*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom))*zoom, ((int)(y/zoom)-1)*zoom, zoom, zoom);
				}
				if(Main.brushSize >= 3)
				{
					g2d.fillRect(((int)(x/zoom)+1)*zoom, ((int)(y/zoom)+1)*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom)-1)*zoom, ((int)(y/zoom)-1)*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom)-1)*zoom, ((int)(y/zoom)+1)*zoom, zoom, zoom);
					g2d.fillRect(((int)(x/zoom)+1)*zoom, ((int)(y/zoom)-1)*zoom, zoom, zoom);
				}
					
			}
			if(Main.tilePainter)
			{
				//System.out.println("gr");
				if(Main.brushSize >= 1)
				{
					changebrush.drawLight(x/zoom, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
				}
				if(Main.brushSize >= 2)
				{
					if(x>zoom-1)
						changebrush.drawLight((x/zoom)-1, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom))
						changebrush.drawLight((x/zoom)+1, y/zoom, zoom, Main.rotate, Main.tileset, g2d);
					if(y>zoom-1)
						changebrush.drawLight(x/zoom, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(y<((Main.level.ySize-1) * zoom))
						changebrush.drawLight(x/zoom, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
				}
				if(Main.brushSize >= 3)
				{
					if(x>zoom-1 && y>zoom-1)
						changebrush.drawLight((x/zoom)-1, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y<((Main.level.ySize-1) * zoom))
						changebrush.drawLight((x/zoom)+1, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y>zoom-1)
						changebrush.drawLight((x/zoom)+1, (y/zoom)-1, zoom, Main.rotate, Main.tileset, g2d);
					if(x>zoom-1 && y<((Main.level.ySize-1) * zoom))
						changebrush.drawLight((x/zoom)-1, (y/zoom)+1, zoom, Main.rotate, Main.tileset, g2d);
				}
			}
		}
		if(Main.grid)
		{
			g2d.setColor(Color.black);
			for(int i = 0; i < ((Main.level.xSize * zoom) + zoom); i+=zoom)
			{
				g2d.drawLine(i, 0, i, (Main.level.ySize * zoom));
			}
			for(int i = 0; i < ((Main.level.ySize * zoom) + zoom); i+=zoom)
			{
				g2d.drawLine(0, i, (Main.level.xSize * zoom), i);
			}
		}
		g2d.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		x = 10000;
		y = 10000;
	}
	boolean start = false;
	boolean drag = false;
	@Override
	public void mousePressed(MouseEvent arg0)
	{
		if(arg0.getButton() == 1)
		{
			if(Main.tilePainter)
				brushTileset(arg0.getX(), arg0.getY());
			else
				brush(arg0.getX(), arg0.getY());
		}
		else if(arg0.getButton() == 3)
		{
			startx = arg0.getX();
			starty = arg0.getY();
			startcamerax = camerax;
			startcameray = cameray;
			drag = true;
			start = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		start = false;
		drag = false;
		x = arg0.getX() - camerax;
		y = arg0.getY() - cameray;
		rx = arg0.getX();
		ry = arg0.getY();
	}
	int startx;
	int starty;
	int startcamerax;
	int startcameray;
	@Override
	public void mouseDragged(MouseEvent arg0)
	{	
		x = arg0.getX() - camerax;
		y = arg0.getY() - cameray;
		rx = arg0.getX();
		ry = arg0.getY();
		if(SwingUtilities.isLeftMouseButton(arg0))
		{
			if(Main.tilePainter)
				brushTileset(arg0.getX(), arg0.getY());
			else
				brush(arg0.getX(), arg0.getY());
		}
		if(SwingUtilities.isRightMouseButton(arg0))
		{
			rcamerax = startcamerax + (arg0.getX() - startx);
			rcameray = startcameray + (arg0.getY() - starty);
		}
	}
	int x = 0;
	int y = 0;
	int rx;
	int ry;
	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		x = arg0.getX() - camerax;
		y = arg0.getY() - cameray;
		rx = arg0.getX();
		ry = arg0.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		if(!(arg0.getWheelRotation() > 0 && zoom <= 5))
		{
			x = arg0.getX() - camerax;
			y = arg0.getY() - cameray;
			rx = arg0.getX();
			ry = arg0.getY();
			int old = zoom;
			zoom -= arg0.getWheelRotation();
			//.println(x);
			float ratio = (float)zoom/(float)old;
			
			double testx = (rcamerax-rx) * ratio;
			rcamerax = (rx+testx);
			double testy = (rcameray-ry) * ratio;
			rcameray = (ry+testy);
			x = arg0.getX() - camerax;
			y = arg0.getY() - cameray;
			rx = arg0.getX();
			ry = arg0.getY();
		}
	}
	
	public void brushTileset(int x, int y)
	{
		if(Main.brushSize >= 1)
		{
			if(Main.level.getCollide((x-camerax)/zoom, (y-cameray)/zoom)!=0)
			Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom, Main.tileset);
		}
		if(Main.brushSize >= 2)
		{
			if(Main.level.getCollide((x-camerax)/zoom+1, (y-cameray)/zoom)!=0)
			Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom-1, (y-cameray)/zoom)!=0)
			Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom, (y-cameray)/zoom+1)!=0)
			Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom+1, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom, (y-cameray)/zoom-1)!=0)
			Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom-1, Main.tileset);
		}
		if(Main.brushSize >= 3)
		{
			if(Main.level.getCollide((x-camerax)/zoom+1, (y-cameray)/zoom+1)!=0)
			Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom+1, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom-1, (y-cameray)/zoom+1)!=0)
			Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom+1, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom-1, (y-cameray)/zoom-1)!=0)
			Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom-1, Main.tileset);
			if(Main.level.getCollide((x-camerax)/zoom+1, (y-cameray)/zoom-1)!=0)
			Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom-1, Main.tileset);
		}
	}
	
	public void brush(int x, int y)
	{
		if(Main.brushSize >= 1)
		{
			Main.level.setCollide((x-camerax)/zoom, (y-cameray)/zoom, Main.brush);
			if(Main.brush!=0)
			{
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom, Main.rotate);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom, Main.tileset);
			}
			else
			{
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom, 0);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom, 0);
			}
		}
		if(Main.brushSize >= 2)
		{
			Main.level.setCollide((x-camerax)/zoom+1, (y-cameray)/zoom, Main.brush);
			Main.level.setCollide((x-camerax)/zoom-1, (y-cameray)/zoom, Main.brush);
			Main.level.setCollide((x-camerax)/zoom, (y-cameray)/zoom+1, Main.brush);
			Main.level.setCollide((x-camerax)/zoom, (y-cameray)/zoom-1, Main.brush);
			if(Main.brush!=0)
			{
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom+1, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom-1, Main.rotate);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom+1, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom-1, Main.tileset);
			}
			else
			{
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom, 0);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom, 0);
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom+1, 0);
				Main.level.setRotate((x-camerax)/zoom, (y-cameray)/zoom-1, 0);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom, 0);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom, 0);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom+1, 0);
				Main.level.setTileset((x-camerax)/zoom, (y-cameray)/zoom-1, 0);
			}
		}
		if(Main.brushSize >= 3)
		{
			Main.level.setCollide((x-camerax)/zoom+1, (y-cameray)/zoom+1, Main.brush);
			Main.level.setCollide((x-camerax)/zoom-1, (y-cameray)/zoom+1, Main.brush);
			Main.level.setCollide((x-camerax)/zoom-1, (y-cameray)/zoom-1, Main.brush);
			Main.level.setCollide((x-camerax)/zoom+1, (y-cameray)/zoom-1, Main.brush);
			if(Main.brush!=0)
			{
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom+1, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom+1, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom-1, Main.rotate);
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom-1, Main.rotate);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom+1, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom+1, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom-1, Main.tileset);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom-1, Main.tileset);
			}
			else
			{
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom+1, 0);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom+1, 0);
				Main.level.setRotate((x-camerax)/zoom-1, (y-cameray)/zoom-1, 0);
				Main.level.setRotate((x-camerax)/zoom+1, (y-cameray)/zoom-1, 0);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom+1, 0);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom+1, 0);
				Main.level.setTileset((x-camerax)/zoom-1, (y-cameray)/zoom-1, 0);
				Main.level.setTileset((x-camerax)/zoom+1, (y-cameray)/zoom-1, 0);
			}
		}
	}
}
