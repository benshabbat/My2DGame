package main;

import java.awt.Rectangle;

import entity.Player;

public class EventHandler {

	GamePanel gp;
	//Rectangle eventRect;
	//int eventRectDefaultX ,eventRectDefaultY;
	EventRect eventRect[][];
	
	int perviousEventX ,perviousEventY;
	boolean canTouch = true;
	public EventHandler(GamePanel gp)
	{
		this.gp=gp;
		eventRect =  new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col=0;
		int row=0;
		while (col<gp.maxWorldCol&&row<gp.maxWorldRow)
		{
			this.eventRect[col][row] = new EventRect(23,23,2,2);
			this.eventRect[col][row].eventRectDefaultX=this.eventRect[col][row].x;
			this.eventRect[col][row].eventRectDefaultY=this.eventRect[col][row].y;
			
			col++;
			if(col==gp.maxWorldCol)
			{
				col=0;
				row++;
			}
		}
		
		
		//this.eventRect = new Rectangle(23,23,2,2);
		//this.eventRectDefaultX=this.eventRect.x;
		//this.eventRectDefaultY=this.eventRect.y;
		
		
	}
	public boolean hit(int col, int row, String reqDirection)
	{
		boolean hit = false;
		
		gp.player=(Player) gp.cChecker.getEntitySolidAreaPosition(gp.player);
		this.eventRect[col][row].x+=col*gp.tileSize;
		this.eventRect[col][row].y+=row*gp.tileSize;
		
		if(gp.player.solidArea.intersects(eventRect[col][row])&& (!this.eventRect[col][row].eventDone))
		{
			if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any")) {
				hit = true;
				
				perviousEventX = gp.player.worldX;
				perviousEventY = gp.player.worldY;
				//if(hit)this.eventRect[col][row].eventDone=true;
			}
		}
		
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		this.eventRect[col][row].x=this.eventRect[col][row].eventRectDefaultX;
		this.eventRect[col][row].y=this.eventRect[col][row].eventRectDefaultY;
		return hit;
	}
	
	public void checkEvent()
	{
		//check if play in the area 
		int xDistance = Math.abs(gp.player.worldX-this.perviousEventX);
		int yDistance = Math.abs(gp.player.worldY-this.perviousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance>gp.tileSize)this.canTouch = true;
		
		if(this.canTouch) {
		if(hit(27,16,"any"))damagPit();
		//if(hit(27,16,"right"))teleport(gp.dialogueState);
		//if(hit(27,16,"right"))teleport();
		//if(hit(27,16,"right"))teleport();
		//if(hit(27,16,"right"))teleport(22,12);
		if(hit(23,12,"up"))healingPool();
		//if(hit(12,21,"any"))teleport();
		}
		
	}
	
	
	private void dialogueEvent(String text) {
		
		gp.gameState=gp.dialogueState;
		gp.ui.currentDialogue=text;
		gp.ui.messageDialogeueOn=true;
	}

	private void teleport() {
		teleport(10,10);
	}
	private void teleport(int x,int y) 
	{
		dialogueEvent("Teleport");
		gp.player.worldX=gp.tileSize*x;
		gp.player.worldY=gp.tileSize*y;
		gp.player.direction="down";
	}
	private void damagPit() {
		dialogueEvent("you fall into a pit");
		gp.playSE(6);
		gp.player.life--;
		this.canTouch=false;
		
		
	}
	private void healingPool()
	{
		if(gp.keyH.enterPressed) {
			gp.player.attackCanceled=true;
			gp.playSE(2);
			dialogueEvent("your life is full");
			gp.player.life=gp.player.maxLife;
		}
		
	}
/*
	public boolean hit(int eventCol, int eventRow, String reqDirection)
	{
		boolean hit = false;
		
		gp.player=(Player) gp.cChecker.getEntitySolidAreaPosition(gp.player);
		this.eventRect.x+=eventCol*gp.tileSize;
		this.eventRect.y+=eventRow*gp.tileSize;
		
		if(gp.player.solidArea.intersects(eventRect))
		{
			if(gp.player.direction.contentEquals(reqDirection)||reqDirection.contentEquals("any")) {
				hit = true;
			}
		}
		
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		this.eventRect.x=this.eventRectDefaultX;
		this.eventRect.y=this.eventRectDefaultY;
		return hit;
	}
	*/
	
	
	
	
	
}
