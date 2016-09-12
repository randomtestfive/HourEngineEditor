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
		AddButtonPair("Remove", "Add", 0, 1, this);
		AddButtonPair("Yellow", "Something", 2, 3, this);
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
			}
		});
		add.setPreferredSize(new Dimension(70, 30));
		JPanel buttons = new JPanel();
		buttons.add(remove);
		buttons.add(add);
		this.add(buttons);
	}
}
