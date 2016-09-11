package hourEngineEditor.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;


public class TextureLoader extends Loader
{
	public final int Player = 0;
	
	/*private ArrayList<String> textureSrc;
	private ArrayList<String> textureName;*/
	//@Override
	protected Map<String, Image> textureMap;
	protected Map<String, BufferedImage> bTextureMap;

	public Image[] textures;
	
	public TextureLoader()
	{
		super();
		textureMap = new HashMap<String, Image>();
		bTextureMap = new HashMap<String, BufferedImage>();
	}
	
	/*public void addTexture(String src, String name)
	{
		Src.add(src);
		Name.add(name);
	}*/
	
	public Image textureFromName(String src)
	{
		return textureMap.get(src);
	}
	
	public BufferedImage bTextureFromName(String src)
	{
		return bTextureMap.get(src);
	}
	
	public void loadTextures()
	{
		textures = new Image[Src.size()];
		int loadingTexture = 0;
		for (String src : Src)
		{
			textures[loadingTexture] = new ImageIcon(this.getClass().getResource(src)).getImage();
			textureMap.put(Name.get(loadingTexture), textures[loadingTexture]);
			bTextureMap.put(Name.get(loadingTexture), toBufferedImage(textures[loadingTexture]));
			new ImageIcon(textures[loadingTexture]);
			loadingTexture++;
		}
	}
	
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
