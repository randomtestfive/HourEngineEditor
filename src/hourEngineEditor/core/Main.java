package hourEngineEditor.core;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import hourEngineEditor.selection.Selection;

public class Main
{
	public static Level level;
	static JFrame frame;
	public static int brush = 0;
	public static int brushSize = 1;
	public static boolean grid = true;
	static boolean render = true;
	public static TextureLoader tl;
	public static Tileset tileset;
	
	public static void AddTextures()
	{
		tl.addMap("/textures/start.png", "something");
		tl.addMap("/textures/tiletest.png", "tileset");
		tl.loadTextures();
	}
	
	public static void main(String[] args)
	{
		tl = new TextureLoader();
		AddTextures();
		tileset = new Tileset(tl.bTextureFromName("tileset"));
		level = new Level(10, 10);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Editor e = new Editor();
//		e.addMouseListener(e);
//		e.addMouseMotionListener(e);
		Selection s = new Selection();
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, e, s);
		split.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent pce)
			{
			    render = false;
			}
		});
		frame = new JFrame("HourEngine Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 750);
		frame.add(split);
		frame.setLocationRelativeTo(null);
		split.addComponentListener(new ComponentAdapter()
		{
			Dimension last = frame.getSize();
		    @Override
		    public void componentResized(ComponentEvent e)
		    {
		    	if(last.width != frame.getSize().width)
		    	{
		            split.setDividerLocation(frame.getSize().width - 200);
		            last = frame.getSize();
		    	}
		    }
		});
		frame.setVisible(true);
		split.setDividerLocation(frame.getSize().width - 200);
		while(true)
		{
			if(render)
			{
				e.repaint();
			}
			render = true;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
