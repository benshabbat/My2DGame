package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

public class UI {
	
	GamePanel gp;
	Graphics2D g2D;
	Font arial_40 , arial_80B;
	BufferedImage keyImage,heart_blank,heart_full,heart_half;
	public boolean messageOn = false;
	public boolean messageDialogeueOn = false;
	public String message;
	public int timeMessage =0;
	public boolean gameFinished = false;
	
	
	public String currentDialogue = "";
	
	public double playTime;
	DecimalFormat dFormat= new DecimalFormat("#0.00");
	
	public int commandNum=0;
	public int titleScreenState=0;
	
	
	public UI(GamePanel gp)
	{
		this.gp=gp;
		this.arial_40 = new Font("Arial",Font.PLAIN,40);
		this.arial_80B = new Font("Arial",Font.BOLD,80);
		OBJ_Key key = new OBJ_Key(gp);
		this.keyImage = key.image;
		
		//CREATE HUD OBJECT
		SuperObject heart = new OBJ_Heart(gp);
		heart_blank=heart.heart_blank;
		heart_full=heart.heart_full;
		heart_half=heart.heart_half;
	}
	public void showMessage(String text)
	{
		this.message=text;
		this.messageOn=true;
	}
	public void drawPauseScreen()
	{
		g2D.setFont(arial_40);
		// change size font
		g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN,80));
		
		
		g2D.setColor(Color.white);
		String text = "PAUSED";
		int x=getXforCenteredText(text);
		int y= gp.screenHeight/2;
		g2D.drawString(text, x, y);
	}
	public void draw(Graphics2D g2D)
	{
		this.g2D=g2D;
		//TITLE STATE
		if(gp.gameState==gp.titleState){drawTitleScreen();}
		
		//PAUSED STATE
		else if(gp.gameState==gp.pauseState){drawPauseScreen();
		drawPlayerLife();}
		//FINISHGAME
		else if(this.gameFinished){drawFinishGame();}

		//DIALOGUE STATE
		else if(gp.gameState==gp.dialogueState){drawPlayerLife(); drawDialogueScreen();
		}

		//PLAY STATE
		else if(gp.gameState==gp.playState) {drawGame();
	
		drawPlayerLife();}

		

	}
	public void drawPlayerLife()
	{
		if(!gp.treasureGame) {
			
			int x = gp.tileSize/2;
			int y = gp.tileSize/2;
			int i = 0;
			while(i<gp.player.maxLife/2)
			{
				g2D.drawImage(heart_blank, x, y, null);
				i++;
				x+=gp.tileSize;
			}
			x = gp.tileSize/2;
			i = 0;
			while(i<gp.player.life)
			{
				g2D.drawImage(heart_half, x, y, null);
				i++;
				if(i%2==0)
				{
					if(i<=gp.player.life)
					{
						g2D.drawImage(heart_full, x, y, null);
						x+=gp.tileSize;
					}
				}
			}
		}
	}
	private void drawTitleScreen() {
		
	
			
		if(this.titleScreenState==0) {
			
			
			//TITLE NAME	
			drawGameTitle("My 2D Game");
			int y=gp.tileSize*3;
			
			
			//image boy
			y=drawImageBoyMenu(y);
	
		
			//MENU
			String[] textMenu = {"New Game","Choose Game","Load Game","Quit"};
			drawTextMenu(textMenu,y);
			
	
	
		}
		else if(this.titleScreenState==1)
		{
		
			//TITLE NAME	
			int y=gp.tileSize*3;
			drawGameTitle("My 2D Game");
			
			//MENU
			String[] textMenu = {"blue","green","yellow","red"};
			Color[] color = {Color.blue,Color.green,Color.yellow,Color.red};		
			y=drawBoyMenu(textMenu,color ,y+gp.tileSize*2); 
			
			
			drawBackMenu(textMenu.length,y);
	
	
		}
		else if(this.titleScreenState==2) {
			
			String text = "My 2D Game";
			int x = this.getXforCenteredText(text);
			int y=gp.tileSize*3;
			//TITLE NAME	
			drawGameTitle(text);
			//MENU
			
			String[] textMenu = {"Treasure Game","Battle Game"};
			y=getYdrawTextMenu(textMenu,y);
			g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,36f));	
			
			drawBackMenu(textMenu.length,y);
		}
	
	}
	private void drawBackMenu(int length, int y) {
		g2D.setColor(Color.white);
		
		int x = this.getXforCenteredText("Back");
		y+=gp.tileSize*2;
		g2D.drawString("back", x,y);
		if(this.commandNum==length)g2D.drawString(">", x-gp.tileSize,y);
	}
	private int drawBoyMenu(String[] textMenu, Color[] color, int y) {
		for(int i =0;i<textMenu.length;i++) {
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,36f));	
		

		y+=gp.tileSize;
		g2D.setColor(color[i]);
		int x = this.getXforCenteredText(textMenu[i]+" Boy");
		
		gp.player.setPlayerImage(textMenu[i]);
		g2D.drawString(textMenu[i]+" Boy", x,y);
		g2D.drawImage(gp.player.down1, x-gp.tileSize, y-gp.tileSize,gp.tileSize,gp.tileSize,null);

		if(this.commandNum==i)g2D.drawString(">", x-gp.tileSize*2,y);
		
		}
		return y;
	}
	private int drawImageBoyMenu(int y) {
		int x=gp.screenWidth/2-gp.tileSize;
		y+=gp.tileSize*2;
		g2D.drawImage(gp.player.down1, x, y,gp.tileSize*2,gp.tileSize*2,null);
		return y;
	}
	private int getYdrawTextMenu(String []text, int y) {
		
		for(int i =0; i < text.length;i++)
		{
			//MENU
			
			g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,36f));	
			if(i==0)y+=gp.tileSize*3;
			//else if(i==text.length-1)y+=gp.tileSize*1.5;
			else y+=gp.tileSize;
			int x = this.getXforCenteredText(text[i]);
			g2D.drawString(text[i], x,y);
			if(this.commandNum==i)g2D.drawString(">", x-gp.tileSize,y);
			
		}
		return y;
	}
	private void drawTextMenu(String []text, int y) {
		
		for(int i =0; i < text.length;i++)
		{
			//MENU
			
			g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,36f));	
			if(i==0)y+=gp.tileSize*3;
			else if(i==text.length-1)y+=gp.tileSize*1.5;
			else y+=gp.tileSize;
			int x = this.getXforCenteredText(text[i]);
			g2D.drawString(text[i], x,y);
			if(this.commandNum==i)g2D.drawString(">", x-gp.tileSize,y);
			
		}

	}
	private void drawGameTitle(String text) {
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD,96f));	
		
		int x = this.getXforCenteredText(text);
		int y=gp.tileSize*3;
		
		//Shadow
		g2D.setColor(Color.green);
		g2D.drawString(text, x+5,y+5);
		
		//Main Color
		g2D.setColor(Color.white);
		g2D.drawString(text, x,y);
	}
	public void drawDialogueScreen() {
	
		if(this.messageDialogeueOn) {
			//WINDOW
			
			int x = gp.tileSize*2 ,y = gp.tileSize /2 ,width = gp.screenWidth - (gp.tileSize*4),height= gp.tileSize*4;
			drawSubWindow(x,y,width,height);
			
			
			g2D.setFont(g2D.getFont().deriveFont(Font.PLAIN,28F));
			x += gp.tileSize;
			y += gp.tileSize;
			for(String line : currentDialogue.split("/n"))
			{
				g2D.drawString(line, x, y);
				y+=40;
			}
			this.timeMessage++;
			// time message 2 seconds
			if(this.timeMessage>120)
			{
				this.messageDialogeueOn=false;
				this.timeMessage=0;
				gp.gameState=gp.playState;
			}
		}
	}
	private void drawSubWindow(int x, int y, int width, int height) {
		Color color = new Color(0,0,0,150);
		g2D.setColor(color);
		g2D.fillRoundRect(x, y, width, height, 35, 35);
		
		color = new Color(180,200,180);
		g2D.setColor(color);
		g2D.setStroke(new BasicStroke(4));
		g2D.drawRoundRect(x+3, y+3, width-6, height-6, 29, 29);
	}
	private void drawGame() {
		g2D.setFont(arial_40);
		g2D.setColor(Color.white);
		
		
		if(gp.treasureGame) {
		g2D.drawImage(keyImage, gp.tileSize/2,gp.tileSize/2, gp.tileSize, gp.tileSize,null);
		g2D.drawString("x = " +gp.player.hasKey, gp.tileSize*2, 65);
		
		}
		
		
		//Time
		if(gp.gameState==gp.playState)playTime+=(double)1/60;
		g2D.setColor(Color.white);
		g2D.drawString("Time = " +dFormat.format(playTime), gp.tileSize*11, 65);
		
		//Message
		if(this.messageOn)
		{
			
			drawSubWindow(gp.tileSize/2,gp.tileSize*5,gp.tileSize*8,gp.tileSize*2);
			
			g2D.setFont(g2D.getFont().deriveFont(20f));
			g2D.setColor(Color.white);
			g2D.drawString(message, gp.tileSize/2+gp.tileSize, gp.tileSize*6);

			
			this.timeMessage++;
			// time message 2 seconds
			if(this.timeMessage>120)
			{
				this.messageOn=false;
				this.timeMessage=0;
			}
		}
		
	}
	private void drawFinishGame() {
		
		String text;
		int x , y;
		
		
		g2D.setFont(arial_40);
		g2D.setColor(Color.white);
		text = "You found the treasure";
		x=getXforCenteredText(text);
		y=gp.screenWidth/2 -(gp.tileSize*3);
		g2D.drawString(text, x,y);
		
		
		g2D.setFont(arial_80B);
		g2D.setColor(Color.yellow);
		text = "Congratulations";
		x=getXforCenteredText(text);
		y=gp.screenWidth/2 +(gp.tileSize*2);
		g2D.drawString(text, x,y);
		
		g2D.setFont(arial_40);
		g2D.setColor(Color.white);
		text = "Your Time is: "+dFormat.format(playTime);
		x=getXforCenteredText(text);
		y=gp.screenWidth/2 +(gp.tileSize*4);
		g2D.drawString(text, x,y);
		
		gp.gameThread=null;
	}
	public int getXforCenteredText(String text)
	{
		int textLength = (int)g2D.getFontMetrics().getStringBounds(text, g2D).getWidth();
		int x=gp.screenWidth/2- textLength/2;
		
		return x;
	}
}
