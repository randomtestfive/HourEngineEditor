package hourEngineEditor.selection;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hourEngineEditor.core.Main;

@SuppressWarnings("serial")
public class BrushSelector extends JPanel
{
	public static Preview p;
	public BrushSelector()
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMaximumSize(new Dimension(199, 100));
		this.setAlignmentY(TOP_ALIGNMENT);
		JLabel label = new JLabel("Brush Selection");
		label.setAlignmentX(0.5F);
		this.add(label);
		JSpinner spin = new JSpinner(new SpinnerNumberModel(1, 1, 3, 1));
		spin.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				Main.brushSize = (int)spin.getValue();
			}
			
		});
		JPanel size = new JPanel();
		size.add(new JLabel("Size:"));
		size.add(spin);
		size.add(new JLabel("Grid:"));
		JCheckBox grid = new JCheckBox();
		grid.setSelected(true);
		grid.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0)
			{
				Main.grid = grid.isSelected();
			}	
		});
		size.add(grid);
		this.add(size);
		p = new Preview();
		JButton remove = new JButton("Remove");
		remove.setPreferredSize(new Dimension(70, 30));
		remove.setMargin(new Insets(0, 0, 0, 0));
		remove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.brush = 0;
				enableAllButtons();
				remove.setEnabled(false);
				p.repaint();
			}
		});
		JPanel buttons = new JPanel();
		buttons.add(remove);
		buttons.add(p);
		add(buttons);
		AddButtonPair("Square", "Slant", 1, 2, this);
		AddButtonPair("B Slant", "T Slant", 3, 4, this);
		JButton ccw = new JButton("CCW");
		ccw.setPreferredSize(new Dimension(30, 30));
		ccw.setMargin(new Insets(0, 0, 0, 0));
		ccw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(Main.rotate == 0) { Main.rotate = 3; p.repaint(); return; }
				if(Main.rotate == 4) { Main.rotate = 7; p.repaint(); return; }
				Main.rotate--;
				p.repaint();
			}
		});
		JButton hflip = new JButton("H Flip");
		hflip.setPreferredSize(new Dimension(40, 30));
		hflip.setMargin(new Insets(0, 0, 0, 0));
		hflip.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(Main.rotate <= 3)
				{
					if(Main.rotate == 0 || Main.rotate == 2)
					{
						Main.rotate = (Main.rotate += 2) % 4 + 4;
					}
					else if(Main.rotate == 1 || Main.rotate == 3)
					{
						Main.rotate += 4;
					}
				}
				else
				{
					if(Main.rotate == 6)
					{
						Main.rotate = 0;
					}
					else if(Main.rotate == 4)
					{
						Main.rotate = 2;
					}
					else if(Main.rotate == 5 || Main.rotate == 7)
					{
						Main.rotate -= 4;
					}
				}
				p.repaint();
			}
		});
		JButton vflip = new JButton("V Flip");
		vflip.setPreferredSize(new Dimension(40, 30));
		vflip.setMargin(new Insets(0, 0, 0, 0));
		vflip.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(Main.rotate);
				if(Main.rotate <= 3)
				{
					if(Main.rotate == 0 || Main.rotate == 2)
					{
						Main.rotate += 4;
					}
					else if(Main.rotate == 1)
					{
						Main.rotate = 7;
					}
					else if(Main.rotate == 3)
					{
						Main.rotate = 5;
					}
				}
				else
				{
					if(Main.rotate == 6 || Main.rotate == 4)
					{
						Main.rotate -= 4;
					}
					else if(Main.rotate == 5)
					{
						Main.rotate = 3;
					}
					else if(Main.rotate == 7)
					{
						Main.rotate = 1;
					}
				}
				System.out.println(Main.rotate);
				p.repaint();
			}
		});
		JButton cw = new JButton("CW");
		cw.setPreferredSize(new Dimension(30, 30));
		cw.setMargin(new Insets(0, 0, 0, 0));
		cw.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(Main.rotate == 3) { Main.rotate = 0; p.repaint(); return; }
				if(Main.rotate == 7) { Main.rotate = 4; p.repaint(); return; }
				Main.rotate++;
				p.repaint();
			}
		});
		JPanel buttons2 = new JPanel();
		buttons2.add(ccw);
		buttons2.add(hflip);
		buttons2.add(vflip);
		buttons2.add(cw);
		add(buttons2);
	}
	
	public void enableAllButtons()
	{
		for(Component c : this.getComponents())
		{
			if(c instanceof JPanel)
			{
				JPanel jp = (JPanel)c;
				for(Component c2 : jp.getComponents())
				{
					if(c2 instanceof JButton)
					{
						JButton jb = (JButton)c2;
						jb.setEnabled(true);
					}
				}
			}
		}
	}
	
	void AddButtonPair(String name1, String name2, int num1, int num2, JPanel source)
	{
		JButton remove = new JButton(name1);
		remove.setPreferredSize(new Dimension(70, 30));
		//remove.setEnabled(false);
		remove.setMargin(new Insets(0, 0, 0, 0));
		JButton add = new JButton(name2);
		add.setMargin(new Insets(0, 0, 0, 0));
		remove.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.brush = num1;
				enableAllButtons();
				remove.setEnabled(false);
				p.repaint();
			}
		});
		add.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.brush = num2;
				enableAllButtons();
				add.setEnabled(false);
				p.repaint();
			}
		});
		add.setPreferredSize(new Dimension(70, 30));
		JPanel buttons = new JPanel();
		buttons.add(remove);
		buttons.add(add);
		this.add(buttons);
	}
}
