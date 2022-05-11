package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.TileManager;

public class Player extends Entity{
	
	
	KeyHandler keyH;
	TileManager tM;
	
	public final int screenX;
	public final int screenY;

	public int hasKey = 0;
	
	public boolean attackCanceled = false;

	public Player(GamePanel gp,KeyHandler keyH)
	{
		super(gp);
		this.keyH=keyH;
		this.tM = new TileManager(gp);
		screenX= gp.screenWidth/2 - (gp.tileSize/2);
		screenY= gp.screenHeight/2 - (gp.tileSize/2);
		

		setSolidAreaPlayer();
		setDefaultSolidAreaPlayer();

		setDefaultValues();
		getPlayerImage();
		getAttackingPlayerImage();
	}
	public void setDefaultSolidAreaPlayer() {
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
	}
	public void setSolidAreaPlayer() {
		
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
	}
	public void setDefaultValues()
	{
		/*
		Random randomX = new Random();
		Random randomY = new Random();
		Random randomWorld = new Random();
		
		int x = randomX.nextInt(gp.worldWidth);
		int y = randomY.nextInt(gp.worldHeight);
		int num = randomWorld.nextInt(gp.maxWorldRow);
		if(tM.tile[num]!=null){
			if(tM.tile[num].collision == false&&this.collisionOn== false){worldX = x;worldY = y;}}
		 */
		worldX= gp.tileSize*23;
		
		worldY= gp.tileSize*21;
		
		//speed=4;
		speed = gp.worldWidth/600;
		direction = "down";
		
		//PLAYER STATUS
		this.maxLife=6;
		this.life=this.maxLife;
	}
	
	public void getPlayerImage()
	{
		setPlayerImage("blue");
	}

	public void getAttackingPlayerImage()
	{
		setPlayerAttackingImage("blue");
	}
	
	
	public void setPlayerImage(String color)
	{
		
			up1= setUp("/player/"+color+"_boy_up_1");
			up2= setUp("/player/"+color+"_boy_up_2");
			down1= setUp("/player/"+color+"_boy_down_1");
			down2= setUp("/player/"+color+"_boy_down_2");
			left1= setUp("/player/"+color+"_boy_left_1");
			left2= setUp("/player/"+color+"_boy_left_2");
			right1= setUp("/player/"+color+"_boy_right_1");
			right2= setUp("/player/"+color+"_boy_right_2");
	
	}
	public void setPlayerAttackingImage(String color)
	{
		
			attackUp1= setUp("/player/"+color+"_boy_attack_up_1",gp.tileSize,gp.tileSize*2);
			attackUp2= setUp("/player/"+color+"_boy_attack_up_2",gp.tileSize,gp.tileSize*2);
			attackDown1= setUp("/player/"+color+"_boy_attack_down_1",gp.tileSize,gp.tileSize*2);
			attackDown2= setUp("/player/"+color+"_boy_attack_down_2",gp.tileSize,gp.tileSize*2);
			attackLeft1= setUp("/player/"+color+"_boy_attack_left_1",gp.tileSize*2,gp.tileSize);
			attackLeft2= setUp("/player/"+color+"_boy_attack_left_2",gp.tileSize*2,gp.tileSize);
			attackRight1= setUp("/player/"+color+"_boy_attack_right_1",gp.tileSize*2,gp.tileSize);
			attackRight2= setUp("/player/"+color+"_boy_attack_right_2",gp.tileSize*2,gp.tileSize);
	
	}

	public void update()
	{
		if(this.attacking) {attacking();}
		
		// CHECK KEY HANDLER
		else if(keyH.downPressed||keyH.leftPressed||keyH.rightPressed||keyH.upPressed||keyH.enterPressed) {
		keyHandlerPressed();

			
		
		//CHECK TILE COLLISION
		collisionOn = false;
		gp.cChecker.checkTile(this);
		
		// check object collisions
		int objIndex = gp.cChecker.checkObject(this, true);
		pickUpObject(objIndex);
		
		// check npc collisions 
		int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
		interactNPC(npcIndex);
		
		//Check Monster Collisions
		int monsterIndex= gp.cChecker.checkEntity(this, gp.monster);
		//gp.cChecker.check(this, gp.monster[monsterIndex]);
		contactMonster(monsterIndex);
		
		
		//check event
		gp.eHandler.checkEvent();
		//gp.keyH.enterPressed=false;
		if((!this.attackCanceled)&&keyH.enterPressed)
		{
			gp.playSE(7);
			this.attacking=true;
			this.spriteCounter=0;
		}
		this.attackCanceled=false;
		//this.keyH.enterPressed=false;
		//IF COLLISION IS FALSE , PLAYER CAN MOVE
		collisionTile();

		

	
		}
		timeInvincible(1);
	}


	public void attacking() {
		spriteCounter++;
		if(spriteCounter<=5)spriteNum=1;
		if(spriteCounter>5&&spriteCounter<=25) {spriteNum=2;
		
		//save the current worldx/y
		int currentWorldX=worldX;
		int currentWorldY=worldY;
		
		//adjust player's worldx/y for attack area
		switch(direction) {
		case "up": worldY-=attackArea.height;break;
		case "down": worldY+=attackArea.height;break;
		case "left": worldX-=attackArea.width;break;
		case "right": worldX+=attackArea.width;break;}
		
		//attackarea becom solidarea 
		solidArea.height=attackArea.height;
		solidArea.width=attackArea.width;
		
		//check collision monster after updates solidarea and worldx/y
		int monsetrIndex = gp.cChecker.checkEntity(this, gp.monster);
		damageMonster(monsetrIndex);
		
		//aftech checking collision restore the orginal data
		worldX=currentWorldX;
		worldY=currentWorldY;
		setSolidAreaPlayer();
		}
		if(spriteCounter>25){spriteNum=1;spriteCounter=0;attacking = false;}
		
	}
	public void damageMonster(int index) {
		if(index!=-1)
		{
			if(!gp.monster[index].invincible)
			{
				gp.playSE(5);
				gp.monster[index].life--;
				gp.monster[index].damageReaction();
				gp.monster[index].invincible=true;
				if(gp.monster[index].life==0)
					gp.monster[index].dying=true;
				
			}
		}
	}
	public void contactMonster(int index) {
		if(index!=-1)
		{
			if(!this.invincible) {
				gp.playSE(6);
				life--;
				this.invincible=true;
			}
		}
	}
	public void interactNPC(int index) {
		if(keyH.enterPressed) {
			if(index!=-1)
				{
				this.attackCanceled=true;
					gp.gameState=gp.dialogueState;
					gp.ui.messageDialogeueOn=true;
					gp.npc[index].speak();
				}
		}
		
	}
	public void keyHandlerPressed() {
	
		if(keyH.upPressed)direction = "up";
	
		else if(keyH.downPressed)direction = "down";
			
		else if(keyH.leftPressed)direction = "left";
		
		else if(keyH.rightPressed)direction = "right";	
	}
	public void pickUpObject(int index)
	{
		if(index>=0)
		{
			String objName = gp.obj[index].name;
			switch(objName)
			{
			case "key": gp.playSE(1); this.hasKey++; gp.obj[index]=null; gp.ui.showMessage("You got a Key"); break;
			case "door": if(hasKey>0) { gp.playSE(3); gp.obj[index]=null; this.hasKey--; gp.ui.showMessage("You opened a Door");} else {gp.ui.showMessage("You need a Key");}break;
			case "boots": gp.obj[index]=null; gp.playSE(2); gp.ui.showMessage("You are faster whit a Boots"); this.speed+=1; break;
			case "chest": gp.ui.gameFinished= true; gp.stopMusic(); gp.playSE(4);break;
			}
		}
	}
	public void draw(Graphics2D g2D)
	{
		//g2D.setColor(Color.white);
		//g2D.fillRect(x, y, gp.tileSize, gp.tileSize);
		BufferedImage image = null;
		image=imageDirection(image);
		
		
		int x =screenX;
		int y =screenY;
		x=attackScreenX;y=attackScreenY;
		
		
		if(screenX>worldX)x= worldX;
		if(screenY>worldY)y= worldY;
		
		int rightOffset = gp.screenWidth- screenX;
		if(rightOffset >gp.worldWidth-worldX)
			x = gp.screenWidth - (gp.worldWidth-worldX);
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset >gp.worldHeight-worldY)
			y = gp.screenHeight - (gp.worldHeight-worldY);	
		if(this.invincible) {g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		//gp.ui.showMessage("You got a hit from a Monster");
		}
		
		g2D.drawImage(image, x, y,null);
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
	}

}
