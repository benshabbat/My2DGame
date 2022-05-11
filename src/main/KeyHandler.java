package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean upPressed, downPressed, leftPressed,rightPressed,enterPressed;
	GamePanel gp;
	
	//DEBUG
	boolean checkDrawTime = false;
	
	public KeyHandler(GamePanel gp)
	{
		this.gp=gp;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		//TITLE STATE 
		if(gp.gameState==gp.titleState) {
			if(gp.ui.titleScreenState==0) {
				if(code==KeyEvent.VK_W)
				{
					gp.ui.commandNum--;
					if(gp.ui.commandNum<0)gp.ui.commandNum=3;
				}
				if(code==KeyEvent.VK_S)
				{
					gp.ui.commandNum++;
					if(gp.ui.commandNum>3)gp.ui.commandNum=0;
				}
				if(code==KeyEvent.VK_ENTER)
				{
					if(gp.ui.commandNum==0)
					{
						gp.ui.titleScreenState=1;
						gp.ui.commandNum=0;
					}
					else if(gp.ui.commandNum==1) {
						gp.ui.titleScreenState=2;
						gp.ui.commandNum=0;
					}
						//in soon
					
					else if(gp.ui.commandNum==2) {}
					//in soon
					
					else if(gp.ui.commandNum==3)
					{
						System.exit(0);
					}
				}

			}
			else if(gp.ui.titleScreenState==1) {
				
				if(code==KeyEvent.VK_W)
				{
					gp.ui.commandNum--;
					if(gp.ui.commandNum<0)gp.ui.commandNum=4;
				}
				if(code==KeyEvent.VK_S)
				{
					gp.ui.commandNum++;
					if(gp.ui.commandNum>4)gp.ui.commandNum=0;
				}
				if(code==KeyEvent.VK_ENTER)
				{
					if(gp.ui.commandNum==0)
					{
						gp.player.setPlayerImage("blue");
						gp.gameState=gp.playState;
						//gp.playMusic(0);
					}
					if(gp.ui.commandNum==1)
					{
						gp.player.setPlayerImage("green");
						gp.gameState=gp.playState;
						//gp.playMusic(0);
					}
					if(gp.ui.commandNum==2)
					{
						gp.player.setPlayerImage("yellow");
						gp.gameState=gp.playState;
						//gp.playMusic(0);
					}
					if(gp.ui.commandNum==3)
					{
						gp.player.setPlayerImage("red");
						gp.gameState=gp.playState;
						//gp.playMusic(0);
					}
					if(gp.ui.commandNum==4)
					{
						gp.ui.commandNum=0;
						gp.ui.titleScreenState=0;
					}
				}
			}
			else if(gp.ui.titleScreenState==2) {
				
				if(code==KeyEvent.VK_W)
				{
					gp.ui.commandNum--;
					if(gp.ui.commandNum<0)gp.ui.commandNum=2;
				}
				if(code==KeyEvent.VK_S)
				{
					gp.ui.commandNum++;
					if(gp.ui.commandNum>2)gp.ui.commandNum=0;
				}
				if(code==KeyEvent.VK_ENTER)
				{
				// IN SOON
					if(gp.ui.commandNum==0)
					{
						gp.treasureGame=true;
						gp.ui.titleScreenState=1;
						

					}
					if(gp.ui.commandNum==1)
					{
						gp.ui.commandNum=0;
						gp.ui.titleScreenState=1;
					}
					if(gp.ui.commandNum==2)
					{
						gp.ui.commandNum=0;
						gp.ui.titleScreenState=0;
					}
				}
			}
		}
		
		
		//PLAY STATE
		else if(gp.gameState==gp.playState) {
			if(code==KeyEvent.VK_W)
			{
				upPressed = true;
			}
			if(code==KeyEvent.VK_S)
			{
				downPressed = true;
			}
			if(code==KeyEvent.VK_A)
			{
				leftPressed = true;
			}
			if(code==KeyEvent.VK_D)
			{
				rightPressed = true;
			}
			if(code==KeyEvent.VK_P)
			{
				gp.gameState=gp.pauseState;
				gp.stopMusic();
				
			}
			if(code==KeyEvent.VK_ENTER)
			{
				enterPressed = true;
				
			}
			/*
			if(code==KeyEvent.VK_UP)
			{
				if(gp.tileSize<200) 
				gp.zoomInOut(1);
			}
			if(code==KeyEvent.VK_DOWN)
			{
				if(gp.tileSize>10) 
				gp.zoomInOut(-1);
				
			}
			*/
			if(code==KeyEvent.VK_T)
			{
				if(!this.checkDrawTime) this.checkDrawTime=true;
				else this.checkDrawTime=false;
				
			}
		}
		//PAUSE STATE
		else if(gp.gameState==gp.pauseState) {
			if(code==KeyEvent.VK_P)
			{
				 gp.gameState=gp.playState;
				 gp.playMusic(0);
			}
		}
		//DIALOGUE STATE
		else if(gp.gameState==gp.dialogueState) {
			if(code==KeyEvent.VK_ENTER)
			{
				gp.gameState=gp.playState;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		int code = e.getKeyCode();
		
		if(code==KeyEvent.VK_W)
		{
			upPressed = false;
		}
		if(code==KeyEvent.VK_S)
		{
			downPressed = false;
		}
		if(code==KeyEvent.VK_A)
		{
			leftPressed = false;
		}
		if(code==KeyEvent.VK_D)
		{
			rightPressed = false;
		}
		if(code==KeyEvent.VK_ENTER)
		{
			this.enterPressed=false;
		}
	}

}
