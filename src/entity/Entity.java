package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {
	public GamePanel gp;
	public int worldX,worldY;
	public int speed;
	
	
	public BufferedImage up1,up2,down1,down2,left1,left2,right1,right2,
	attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
	public boolean attacking = false;
	
	
	public boolean alive = true;
	public boolean dying = false;
	public int dyingCounter=0;
	
	public boolean hpBarOn = false;
	public int hpBarCounter = 0;
	
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	
	public Rectangle solidArea= new Rectangle(0,0,48,48);
	public int solidAreaDefaultX,solidAreaDefaultY;
	public boolean collisionOn = false;
	
	public int actionLockCounter=0;
	
	public String[] dialogues = new String[20];
	public int indexDialogue=0;
	
	public boolean invincible = false;
	public int invincibleCounter = 0;
	
	public int type; // 0 == player ,1 == npc ,2 == monster 
	
	//CHARACTER STATUS
	public int maxLife;
	public int life;
	
	public Rectangle attackArea= new Rectangle(0,0,36,36);
	public int attackScreenX;
	public int attackScreenY;
	
	public Entity(GamePanel gp)
	{
		this.gp=gp;
	}
	
	public void setAction() {}
	public void damageReaction() {}
	public void speak() {
		if(dialogues[indexDialogue]==null)indexDialogue=0;
		
		gp.ui.currentDialogue= dialogues[indexDialogue++];
		//indexDialogue++;
		
		switch(gp.player.direction)
		{
		case "up":direction = "down";break;
		case "down":direction = "up";break;
		case "left":direction = "right";break;
		case "right":direction = "left";break;
		}
	}
	public void update()
	{
		setAction();
		
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		//gp.cChecker.check(this,gp.player);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.npc);
		boolean contactPlayer=gp.cChecker.checkPlayer(this);
		if(contactPlayer&&type==2) {
			if(gp.player.invincible==false)
			{
				gp.playSE(6);
				gp.player.life--;
				gp.player.invincible=true;
			}
		}
		
		//gp.cChecker.check(gp.player, this);
		//IF COLLISION IS FALSE , PLAYER CAN MOVE
		collisionTile();
		timeInvincible(0.8);
		
	}
	public void timeInvincible(double i) {

		if(this.invincible) {
			this.invincibleCounter++;
			if(this.invincibleCounter>60*i){
				this.invincible=false;
				this.invincibleCounter=0;
				}
			}		
	}
	public BufferedImage setUp(String imagePath)
	{

		return setUp(imagePath,gp.tileSize,gp.tileSize);
	}
	public BufferedImage setUp(String imagePath, int width,int height)
	{
		UtilityTool uTool = new UtilityTool(); 
		BufferedImage image = null;
		try {
		image= ImageIO.read(getClass().getResourceAsStream(imagePath+".png"));
		image = uTool.scaleImage(image,width,height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
	public void collisionTile() {
		if((!collisionOn)&&(!this.gp.keyH.enterPressed))
		{
			switch(direction)
			{
			case "up": if(worldY >0) {worldY-=speed;} break;
			case "down": if(worldY < gp.worldHeight -gp.tileSize) {worldY+=speed;} break;
			case "left": if(worldX > 0) { worldX-=speed;} break;
			case "right": if(worldX < gp.worldWidth -gp.tileSize) {worldX+=speed;} break;
			}
		}
		
		this.gp.keyH.enterPressed=false;
		
		spriteCounter++;
		if(spriteCounter>10)
		{
			if(spriteNum==1)
				spriteNum=2;
			else
				spriteNum=1;
			spriteCounter=0;
		}
	}
	public BufferedImage imageDirection(BufferedImage image) {
		
		attackScreenX=gp.player.screenX;
		attackScreenY=gp.player.screenY;
		switch(direction)
		{
		case "up":
			if(this.attacking)
			{
				attackScreenY=gp.player.screenY-gp.tileSize;
				if(spriteNum==1)image=this.attackUp1;
				if(spriteNum==2)image=this.attackUp2;
				
			}
			else {
				if(spriteNum==1)image=up1;
				if(spriteNum==2)image=up2;
				}
			break;
		case "down":
			if(this.attacking)
			{
				if(spriteNum==1)image=this.attackDown1;
				if(spriteNum==2)image=this.attackDown2;
			}
			else {
				if(spriteNum==1)image=this.down1;
				if(spriteNum==2)image=this.down2;
				}
			break;
		case "left":
			if(this.attacking)
			{
				attackScreenX=gp.player.screenX-gp.tileSize;
				if(spriteNum==1)image=this.attackLeft1;
				if(spriteNum==2)image=this.attackLeft2;
			}
			else {
				if(spriteNum==1)image=left1;
				if(spriteNum==2)image=left2;
				}
			break;
		case "right":
			if(this.attacking)
			{
				if(spriteNum==1)image=this.attackRight1;
				if(spriteNum==2)image=this.attackRight2;
			}
			else {
				if(spriteNum==1)image=right1;
				if(spriteNum==2)image=right2;
				}
			break;
		}
		return image;
	}
	public void draw(Graphics2D g2D)
	{
		BufferedImage image = null;
		int screenX = (int) (worldX - gp.player.worldX + gp.player.screenX);
		int screenY = (int) (worldY - gp.player.worldY + gp.player.screenY);

		
		// Stop moving the camera at the edge
		
		if(gp.player.screenX>gp.player.worldX)screenX=worldX;
		if(gp.player.screenY>gp.player.worldY)screenY=worldY;
		
		int rightOffset = gp.screenWidth- gp.player.screenX;
		if(rightOffset>gp.worldWidth-gp.player.worldX)
			screenX = gp.screenWidth - (gp.worldWidth-worldX);
		
		
		int bottomOffset = gp.screenHeight- gp.player.screenY;
		if(bottomOffset >gp.worldHeight-gp.player.worldY)
			screenY =  gp.screenHeight- (gp.worldHeight-worldY);	
		
		
		if( worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ){
			
			
			image=imageDirection(image);
			
			//Monster Hp Bar
		
	
			if(type==2&&hpBarOn) {
				double oneScale = gp.tileSize/this.maxLife;
				double hpBarValue = oneScale*life;
				g2D.setColor(new Color(35,35,35));
				g2D.fillRect(screenX-1, screenY-9, gp.tileSize+2, 12);
				g2D.setColor(new Color(255,0,30));
				g2D.fillRect(screenX, screenY-8, (int) hpBarValue, 10);
				
				this.hpBarCounter++;
				if(this.hpBarCounter>600)
				{
					this.hpBarOn=false;
					this.hpBarCounter=0;
				}
			}
			
			if(this.invincible) {changeAlpha(g2D,0.4f);
			hpBarOn = true;
			this.hpBarCounter=0;
			//gp.ui.showMessage("You  hit a Monster");
			}
			if(this.dying)dyingAnimation(g2D);
				g2D.drawImage(image, screenX, screenY ,null);
				changeAlpha(g2D,1f);
			}
		
		else if(gp.player.screenX>gp.player.worldX||
					gp.player.screenY>gp.player.worldY||
					rightOffset>gp.worldWidth-gp.player.worldX||
					bottomOffset >gp.worldHeight-gp.player.worldY) {
				g2D.drawImage(image, screenX, screenY ,null);}
	}
	public void dyingAnimation(Graphics2D g2D) {
		
		dyingCounter++;
	
		if(dyingCounter%5==0) changeAlpha(g2D,0f);
		else changeAlpha(g2D,1f);
		if(dyingCounter>40) {this.alive=false;this.dying=false;}
		
		
	}
	public void changeAlpha(Graphics2D g2D,float alphaValue)
	{
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
	}
	public void getEntitySolidAreaPosition()
	{
		// get player's solid area position
		solidArea.x += worldX;
		solidArea.y += worldY;
	}
}
