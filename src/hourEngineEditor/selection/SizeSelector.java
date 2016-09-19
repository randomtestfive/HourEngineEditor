package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hourEngineEditor.core.Main;

@SuppressWarnings("serial")
public class SizeSelector extends JPanel
{
	public static JTextField x;
	public static JTextField y;
	public SizeSelector()
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setMaximumSize(new Dimension(199, 100));
		JLabel label = new JLabel("Level Size");
		label.setPreferredSize(new Dimension(100, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(0.5F);
		this.add(label);
		x = new JTextField(Integer.toString(Main.level.xSize));
		x.setPreferredSize(new Dimension(60, 20));
		y = new JTextField(Integer.toString(Main.level.ySize));
		x.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.level.setSize(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
			}
		});
		y.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.level.setSize(Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
			}
		});
		y.setPreferredSize(new Dimension(60, 20));
		JPanel buttons = new JPanel();
		buttons.add(new JLabel("X:"));
		buttons.add(x);
		buttons.add(new JLabel("Y:"));
		buttons.add(y);
		this.add(buttons);
	}
}
