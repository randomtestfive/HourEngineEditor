package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import hourEngineEditor.core.Editor;
import hourEngineEditor.core.Level;
import hourEngineEditor.core.Main;
import hourEngineEditor.core.Tileset;

@SuppressWarnings("serial")
public class Export extends JPanel implements ActionListener
{
	JButton save;
	JButton load;
	JTextField tf;
	JFileChooser jc;
	public Export()
	{
		super();
		save = new JButton("Export");
		load = new JButton("Import");
		save.addActionListener(this);
		load.addActionListener(this);
		tf = new JTextField("mylevel.hl");
		jc = new JFileChooser();
		tf.setColumns(16);
		this.add(save);
		this.add(load);
		this.add(tf);
		this.setPreferredSize(new Dimension(200, 60));
		this.setMaximumSize(new Dimension(200, 60));
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource().equals(save))
		{
			jc.setSelectedFile(new File(tf.getText()));
			jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			//jc.setFileFilter(new FileNameExtensionFilter("HourEngine Level","hl"));
			int returnVal = jc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				File folder = jc.getSelectedFile();
				if(!folder.exists())
				{
					folder.mkdir();
				}
				File file = new File(folder.getAbsolutePath() + "/level.hl");
				Level l = Main.level;
				StringBuilder out = new StringBuilder();
				out.append(l.xSize + "x" + l.ySize + ":" + Main.tilesets.size() + "\n");
				for(Tileset ts : Main.tilesets)
				{
					out.append(ts.n + "\n");
				}
				for(int y = 0; y < Main.level.ySize; y++)
				{
					for(int x = 0; x < Main.level.xSize; x++)
					{
						out.append(l.getCollide(x, y) + ":" + l.getRotate(x, y) + ":" + l.getTileset(x, y) + ",");
					}
					out.append("\n");
				}
				try(FileWriter fw = new FileWriter(file))
				{
				    fw.write(out.toString());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				for(Tileset ts : Main.tilesets)
				{
					try {
						ImageIO.write(ts.tileset, "png", new File(folder.getAbsolutePath() + "/" + ts.n + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			jc.setSelectedFile(new File(tf.getText()));
			jc.setFileFilter(new FileNameExtensionFilter("HourEngine Level","hl"));
			int returnVal = jc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				File file = jc.getSelectedFile();
				Level l = null;
				try (BufferedReader br = new BufferedReader(new FileReader(file)))
				{
					int tilesets = 0;
				    String line;
				    if((line = br.readLine()) != null)
				    {
				    	String size = line.split(":")[0];
				    	String ts = line.split(":")[1];
				    	l = new Level(Integer.parseInt(size.split("x")[0]), Integer.parseInt(size.split("x")[1]));
				    	tilesets = Integer.parseInt(ts);
				    }
				    //System.out.println(tilesets);
				    ArrayList<Tileset> tiletmp = new ArrayList<Tileset>();
				    String[] names = new String[tilesets];
				    for(int i = 0; i < tilesets; i++)
				    {
				    	if((line = br.readLine()) != null)
				    	{
				    		File tmpfile = new File(file.getParent() + "/" + line + ".png");
				    		//System.out.println(tmpfile.getAbsolutePath());
				    		BufferedImage in = ImageIO.read(tmpfile);
							tiletmp.add(new Tileset(in, line));
							names[i] = line;
							
				    	}
				    }
				    //System.out.println(tiletmp.size());
			    	Main.tilesets = tiletmp;
			    	DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>(names);
			    	TilesetSelector.cb.setModel(model);
				    int y = 0;
				    while ((line = br.readLine()) != null)
				    {
				    	int x = 0;
				    	for (String in : line.split(","))
				    	{
				    		int tmp1 = Integer.parseInt(in.split(":")[0]);
				    		int tmp2 = Integer.parseInt(in.split(":")[1]);
				    		int tmp3 = Integer.parseInt(in.split(":")[2]);
				    		l.setCollide(x, y, tmp1);
				    		l.setRotate(x, y, tmp2);
				    		l.setTileset(x, y, tmp3);
				    		x++;
				    	}
				    	y++;
				    }
				    Main.level = l;
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				Editor.reloadBrushes();
			}
		}
	}
}
