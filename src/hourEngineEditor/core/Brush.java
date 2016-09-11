package hourEngineEditor.core;

import java.awt.Graphics2D;

public abstract class Brush
{
	abstract public void draw(int x, int y, int w, Graphics2D g2d);
	abstract public void drawLight(int x, int y, int w, Graphics2D g2d);
}
