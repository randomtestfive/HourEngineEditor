package hourEngineEditor.selection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import hourEngineEditor.core.Editor;
import hourEngineEditor.core.Main;

@SuppressWarnings("serial")
public class Preview extends JPanel
{
	public Preview()
	{
		super();
		this.setPreferredSize(new Dimension(32, 32));
	}
	
	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.gray);
		g2d.fillRect(0, 0, 32, 32);
		if(Main.brush!=0)
		Editor.brushes.get(Main.brush-1).draw(0, 0, 32, Main.rotate, g2d);
		g2d.setColor(Color.black);
		g2d.drawRect(0, 0, 31, 31);
	}
}
