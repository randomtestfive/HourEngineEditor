package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import hourEngineEditor.core.Level;
import hourEngineEditor.core.Main;

@SuppressWarnings("serial")
public class Shift extends JPanel
{
	public Shift()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(CENTER_ALIGNMENT);
		JButton up = new JButton("\u25B2");
		up.setAlignmentX(CENTER_ALIGNMENT);
		up.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MoveLevelUp();
			}
		});
		JButton down = new JButton("\u25BC");
		down.setAlignmentX(CENTER_ALIGNMENT);
		down.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MoveLevelDown();
			}
		});
		JPanel middle = new JPanel();
		JButton left = new JButton("\u25C0");
		left.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MoveLevelLeft();
			}
		});
		JButton right = new JButton("\u25B6");
		right.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				MoveLevelRight();
			}
		});
		middle.add(left);
		middle.add(right);
		middle.setMaximumSize(new Dimension(150, 50));
		this.add(up);
		this.add(middle);
		this.add(down);
	}
	
	public void MoveLevelUp()
	{
		Level n = Main.level;
		Level tmp = new Level(n.xSize, n.ySize);
		for(int x = 0; x < n.xSize; x++)
		{
			for(int y = 0; y < n.ySize; y++)
			{
				tmp.setCollide(x, y-1, n.getCollide(x, y));
				tmp.setRotate(x, y-1, n.getRotate(x, y));
				tmp.setTileset(x, y-1, n.getTileset(x, y));
			}
		}
		Main.level = tmp;
	}
	
	public void MoveLevelDown()
	{
		Level n = Main.level;
		Level tmp = new Level(n.xSize, n.ySize);
		for(int x = 0; x < n.xSize; x++)
		{
			for(int y = 0; y < n.ySize; y++)
			{
				tmp.setCollide(x, y+1, n.getCollide(x, y));
				tmp.setRotate(x, y+1, n.getRotate(x, y));
				tmp.setTileset(x, y+1, n.getTileset(x, y));
			}
		}
		Main.level = tmp;
	}
	
	public void MoveLevelLeft()
	{
		Level n = Main.level;
		Level tmp = new Level(n.xSize, n.ySize);
		for(int x = 0; x < n.xSize; x++)
		{
			for(int y = 0; y < n.ySize; y++)
			{
				tmp.setCollide(x-1, y, n.getCollide(x, y));
				tmp.setRotate(x-1, y, n.getRotate(x, y));
				tmp.setTileset(x-1, y, n.getTileset(x, y));
			}
		}
		Main.level = tmp;
	}
	
	public void MoveLevelRight()
	{
		Level n = Main.level;
		Level tmp = new Level(n.xSize, n.ySize);
		for(int x = 0; x < n.xSize; x++)
		{
			for(int y = 0; y < n.ySize; y++)
			{
				tmp.setCollide(x+1, y, n.getCollide(x, y));
				tmp.setRotate(x+1, y, n.getRotate(x, y));
				tmp.setTileset(x+1, y, n.getTileset(x, y));
			}
		}
		Main.level = tmp;
	}
}
