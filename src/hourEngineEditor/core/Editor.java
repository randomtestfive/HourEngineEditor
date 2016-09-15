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
	
	public Editor()
	{
		brushes.add(red);
		brushes.add(yellow);
		brushes.add(something);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
	}
	
	Brush red = new Brush()
	{
		@Override
		public void draw(int x, int y, int w, Graphics2D g2d)
		{
			g2d.setColor(Color.red);
			g2d.fillRect(x * w, y * w, w, w);
		}

		@Override
		public void drawLight(int x, int y, int w, Graphics2D g2d)
		{
			Color c = Color.red;
			
			c = new Color(Math.min(c.getRed()+50, 255), Math.min(c.getGreen()+50, 255), Math.min(c.getBlue()+50, 255), 100);
			g2d.setColor(c);
			g2d.fillRect(x * w, y * w, w, w);
		}
	};
	
	Brush yellow = new Brush()
	{
		@Override
		public void draw(int x, int y, int w, Graphics2D g2d)
		{
			g2d.setColor(Color.yellow);
			g2d.fillRect(x * w, y * w, w, w);
		}

		@Override
		public void drawLight(int x, int y, int w, Graphics2D g2d)
		{
			Color c = Color.yellow;
			
			c = new Color(Math.min(c.getRed()+50, 255), Math.min(c.getGreen()+50, 255), Math.min(c.getBlue()+50, 255), 100);
			g2d.setColor(c);
			g2d.fillRect(x * w, y * w, w, w);
		}
	};
	
	Brush something = new Brush()
	{
		Image i = Main.tl.textureFromName("something");
		@Override
		public void draw(int x, int y, int w, Graphics2D g2d)
		{
			g2d.drawImage(i, x*w, y*w, w, w, null);
		}

		@Override
		public void drawLight(int x, int y, int w, Graphics2D g2d)
		{
			Composite c = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
			g2d.drawImage(i, x*w, y*w, w, w, null);
			g2d.setComposite(c);
		}
	};
	ArrayList<Brush> brushes = new ArrayList<Brush>();
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
					brushes.get(Main.level.getCollide(x, y)-1).draw(x, y, zoom, g2d);
				}
			}
		}
		if(x < Main.level.xSize * zoom && y < Main.level.ySize * zoom && x>0 && y>0 && !start)
		{
			if(Main.brush != 0 && brushes.size()+1 > Main.brush)
			{
				if(Main.brushSize >= 1)
				{
					brushes.get(Main.brush-1).drawLight(x/zoom, y/zoom, zoom, g2d);
				}
				if(Main.brushSize >= 2)
				{
					if(x>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, y/zoom, zoom, g2d);
					if(x<((Main.level.xSize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, y/zoom, zoom, g2d);
					if(y>zoom-1)
					brushes.get(Main.brush-1).drawLight(x/zoom, (y/zoom)-1, zoom, g2d);
					if(y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight(x/zoom, (y/zoom)+1, zoom, g2d);
				}
				if(Main.brushSize >= 3)
				{
					if(x>zoom-1 && y>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, (y/zoom)-1, zoom, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, (y/zoom)+1, zoom, g2d);
					if(x<((Main.level.xSize-1) * zoom) && y>zoom-1)
					brushes.get(Main.brush-1).drawLight((x/zoom)+1, (y/zoom)-1, zoom, g2d);
					if(x>zoom-1 && y<((Main.level.ySize-1) * zoom))
					brushes.get(Main.brush-1).drawLight((x/zoom)-1, (y/zoom)+1, zoom, g2d);
				}
			}
			if(Main.brush == 0)
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
			if(Main.brushSize >= 1)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom, Main.brush);
			}
			if(Main.brushSize >= 2)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom-1, Main.brush);
			}
			if(Main.brushSize >= 3)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom-1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom-1, Main.brush);
			}
			
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
		// TODO Auto-generated method stub
		
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
			if(Main.brushSize >= 1)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom, Main.brush);
			}
			if(Main.brushSize >= 2)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom, (arg0.getY()-cameray)/zoom-1, Main.brush);
			}
			if(Main.brushSize >= 3)
			{
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom+1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom-1, (arg0.getY()-cameray)/zoom-1, Main.brush);
				Main.level.setCollide((arg0.getX()-camerax)/zoom+1, (arg0.getY()-cameray)/zoom-1, Main.brush);
			
			}	
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
			//System.out.println(x);
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
}
