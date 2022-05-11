package object;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;
import tile.Tile;

public class SuperObject {
	
	public BufferedImage keyImage,heart_blank,heart_full,heart_half;
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea= new Rectangle(0,0,48,48);
	public int solidAreaDefaultX=0;
	public int solidAreaDefaultY=0;
	GamePanel gp;
	UtilityTool uTool = new UtilityTool(); 
	
	public SuperObject(GamePanel gp)
	{
		this.gp=gp;
	}
	
	public void draw(Graphics2D g2D)
	{
		int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
		int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

		
		// Stop moving the camera at the edge
		
		if(gp.player.screenX>gp.player.worldX)screenX=worldX;
		if(gp.player.screenY>gp.player.worldY)screenY=worldY;
		
		int rightOffset = gp.screenWidth- gp.player.screenX;
		if(rightOffset>gp.worldWidth-gp.player.worldX)
			screenX = (int) (gp.screenWidth - (gp.worldWidth-worldX));
		
		
		int bottomOffset = gp.screenHeight- gp.player.screenY;
		if(bottomOffset >gp.worldHeight-gp.player.worldY)
			screenY = (int) (gp.screenHeight- (gp.worldHeight-worldY));	
		
		
		if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ){
			g2D.drawImage(image, (int)screenX, (int)screenY ,null);
		}
		else if(gp.player.screenX>gp.player.worldX||
					gp.player.screenY>gp.player.worldY||
					rightOffset>gp.worldWidth-gp.player.worldX||
					bottomOffset >gp.worldHeight-gp.player.worldY) {
				g2D.drawImage(image, (int)screenX, (int)screenY ,null);}
	}
	public void setUp(String imageName, boolean collision)
	{
		setUp(imageName);
		this.collision=collision;
		
		
	}
	public void setUp(String imageName)
	{
		
		try {
			
		image= ImageIO.read(getClass().getResourceAsStream("/objects/"+imageName+".png"));
		image = uTool.scaleImage(image,gp);	
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	public BufferedImage getImage(String imageName)
	{
		setUp(imageName);

		return image;
	}
}