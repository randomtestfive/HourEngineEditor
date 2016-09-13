package hourEngineEditor.selection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
