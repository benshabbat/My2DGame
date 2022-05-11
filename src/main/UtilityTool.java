package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	 int width;
	 int height;
	GamePanel gp;


	public BufferedImage scaleImage(BufferedImage orginalImage, int width, int height)
	{
		BufferedImage scaledImage = new BufferedImage(width,height,orginalImage.getType());
		Graphics2D g2D = scaledImage.createGraphics();
		g2D.drawImage(orginalImage, 0,0, width, height,null);
		g2D.dispose();
		
		return scaledImage;
	}
	
	public BufferedImage scaleImage(BufferedImage orginalImage,GamePanel gp)
	{
		return scaleImage(orginalImage, gp.tileSize, gp.tileSize);
	}
	public BufferedImage scaleImage(BufferedImage orginalImage)
	{
		return scaleImage(orginalImage, gp);
	}
	public int getSizeGp()
	{
		return this.gp.tileSize;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public void setHeight(int height)
	{
		this.height=height;
	}
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
}
