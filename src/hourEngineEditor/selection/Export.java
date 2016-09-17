package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import hourEngineEditor.core.Level;
import hourEngineEditor.core.Main;

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
			jc.setFileFilter(new FileNameExtensionFilter("HourEngine Level","hl"));
			int returnVal = jc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) 
			{
				File file = jc.getSelectedFile();
				Level l = Main.level;
				StringBuilder out = new StringBuilder();
				out.append(l.xSize + "x" + l.ySize + "\n");
				for(int y = 0; y < Main.level.ySize; y++)
				{
					for(int x = 0; x < Main.level.xSize; x++)
					{
						out.append(l.getCollide(x, y) + ":" + l.getRotate(x, y) + ",");
					}
					out.append("\n");
				}
				try(FileWriter fw = new FileWriter(jc.getSelectedFile()))
				{
				    fw.write(out.toString());
				}
				catch (IOException e)
				{
					e.printStackTrace();
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
				    String line;
				    if((line = br.readLine()) != null)
				    {
				    	l = new Level(Integer.parseInt(line.split("x")[0]), Integer.parseInt(line.split("x")[1]));
				    }
				    int y = 0;
				    while ((line = br.readLine()) != null)
				    {
				    	int x = 0;
				    	for (String in : line.split(","))
				    	{
				    		int tmp1 = Integer.parseInt(in.split(":")[0]);
				    		int tmp2 = Integer.parseInt(in.split(":")[1]);
				    		l.setCollide(x, y, tmp1);
				    		l.setRotate(x, y, tmp2);
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
			}
		}
	}
}
