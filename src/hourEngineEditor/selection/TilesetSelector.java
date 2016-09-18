package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import hourEngineEditor.core.Main;
import hourEngineEditor.core.TextureLoader;
import hourEngineEditor.core.Tileset;

@SuppressWarnings("serial")
public class TilesetSelector extends JPanel implements ActionListener
{
	JFileChooser jc;
	public static JComboBox<String> cb;
	public TilesetSelector()
	{
		cb = new JComboBox<String>();
		cb.addItem("Default");
		cb.setPreferredSize(new Dimension(80, 22));
		this.setMaximumSize(new Dimension(190, 50));
		cb.setAlignmentY(TOP_ALIGNMENT);
		this.setAlignmentX(CENTER_ALIGNMENT);
		JButton i = new JButton("Import");
		i.addActionListener(this);
		cb.addActionListener(this);
		this.add(i);
		this.add(cb);
		jc = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(!arg0.getSource().equals(cb))
		{
			jc.setFileFilter(new FileNameExtensionFilter("PNG File","png"));
			int returnVal = jc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				File file = jc.getSelectedFile();
				BufferedImage in;
				try
				{
					in = ImageIO.read(file);
					String n = file.getName();
					if (n.lastIndexOf(".") > 0)
					{
					    n = n.substring(0, n.lastIndexOf("."));
					}
					Main.tilesets.add(new Tileset(in, n));
					cb.addItem(n);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				
				
			}
		}
		else
		{
			Main.tileset = cb.getSelectedIndex();
			BrushSelector.p.repaint();
		}
	}
}
