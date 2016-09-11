package hourEngineEditor.selection;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import hourEngineEditor.core.Main;

@SuppressWarnings("serial")
public class Selection extends JPanel
{	
	public Selection()
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new SizeSelector());
		this.add(new BrushSelector());
		this.add(new JPanel());
	}
}
