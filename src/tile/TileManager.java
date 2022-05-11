package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	
	GamePanel gp;
	public Tile [] tile;
	public int mapTileNum[][];
	//public int number;
	
	public TileManager(GamePanel gp)
	{
		this.gp=gp;
		
		tile = new Tile[50];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		
		setTileImage();
		loadMap("/maps/worldV2.txt");
	}
	
	public void setTileImage()
	{
		
		for(int i =0 ; i<=11 ; i++)
		{
			setUp(i,"grass00",false);
		}
		for(int i =0 ; i<=13 ; i++)
		{
			if(i<10)setUp(12+i,"water0"+i,true);
			else setUp(12+i,"water"+i,true);
		}
		for(int i =0 ; i<=12 ; i++)
		{
			if(i<10)setUp(26+i,"road0"+i,false);
			else setUp(26+i,"road"+i,false);
		}
		setUp(11,"grass01",false);
		setUp(39,"earth",false);
		setUp(40,"wall",true);
		setUp(41,"tree",true);
		
	}
	public void setUp(int index,String imageName, boolean collision)
	{
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image= ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image= uTool.scaleImage(tile[index].image, gp);
			tile[index].collision=collision;
		}
		catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	public void loadMap(String filePath)
	{
		try {
		InputStream is = getClass().getResourceAsStream(filePath);
		//InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		int col = 0;
		int row = 0;

		
		while(col<gp.maxWorldCol&&row<gp.maxWorldRow)
		{
			String line = br.readLine();
			
			while(col<gp.maxWorldCol)
			{
				String numbers[] = line.split(" ");
				int num = Integer.parseInt(numbers[col]);
				mapTileNum[col][row]=num;
				col++;
				
			}
			if(col==gp.maxWorldCol)
			{
				col=0;
				row++;
			}
		}
		br.close();
		}
		catch(Exception e)
		{
			
		}
	}
	public void draw(Graphics2D g2D)
	{
		
		int worldCol = 0;
		int worldRow = 0;

		while(worldCol<gp.maxWorldCol&&worldRow<gp.maxWorldRow)
		{
			int tileNum=mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
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
				g2D.drawImage(tile[tileNum].image, (int)screenX, (int)screenY ,null);
			} 
			else if(gp.player.screenX>gp.player.worldX||
					gp.player.screenY>gp.player.worldY||
					rightOffset>gp.worldWidth-gp.player.worldX||
					bottomOffset >gp.worldHeight-gp.player.worldY) {
				g2D.drawImage(tile[tileNum].image, (int)screenX, (int)screenY ,null);}
			worldCol++;
			
			if(worldCol==gp.maxWorldCol)
			{
				worldCol=0;
				worldRow++;
	
			}
		}
	}
}
