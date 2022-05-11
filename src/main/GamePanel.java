package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	final int orginalTileSize =16;
	final int scale = 3;
	
	
	public int tileSize = orginalTileSize*scale;
	public int maxScreenCol = 16;
	public int maxScreenRow = 12;
	public int screenWidth = maxScreenCol*tileSize;
	public int screenHeight = maxScreenRow*tileSize;
	
	//public static int TIMEMUSIC = 0;
	
	//World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth= maxWorldCol*tileSize;
	public final int worldHeight = maxWorldRow*tileSize;
	
	//FPS
	final int FPS=60;
	final int nanoSecond=1000000000;
	final int milliSecond = 1000;
	
	
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	Sound se = new Sound();
	Sound music = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);	
	public AssetSetter aSetter = new AssetSetter(this);
	
	public UI ui = new UI(this);
	Thread  gameThread;
	
	public EventHandler eHandler = new EventHandler(this);
	
	
	//ENTITY AND OBJECT
	public Entity entity = new Entity(this);
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[]= new Entity[10];
	public Entity monster[] = new Entity[20];	
	ArrayList<Entity> entityList = new ArrayList<>();
	ArrayList<SuperObject> objectList = new ArrayList<>();
	
	
	//GAME STATE
	public int gameState;
	public final int titleState=0;
	public final int playState=1;
	public final int pauseState=2;
	public final int dialogueState=3;
	
	//TREASURE GAME
	public boolean treasureGame = false;
	

	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	
	public void setupGame()
	{
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMON();
		//this.playMusic(0);
		this.gameState=this.titleState;
		
	}
	/*
	public void zoomInOut(int i)
	{
		
		int oldWorldWidth = maxWorldCol*tileSize;
		
		this.tileSize+=i;
		
		double newWorldWidth = maxWorldCol*tileSize;
		
		player.speed = newWorldWidth/600;
		
		double multiplayer = newWorldWidth/oldWorldWidth;
		
		player.worldX = player.worldX* multiplayer;
		player.worldY = player.worldY* multiplayer;
		
	}
	*/
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}
	/*
	@Override
	public void run() 
	{
		double drawInterval =nanoSecond/FPS;
		double nextDrawTime =System.nanoTime() +drawInterval;
		while(gameThread!= null)
		{
			//long currentTime = System.nanoTime();
			update();
			repaint();
			try {
			double remainingTime = nextDrawTime - System.nanoTime();
			remainingTime /= 1000000; // milli second for sleep
			if(remainingTime <0 )
				remainingTime=0;
			
				Thread.sleep((long) remainingTime);
				nextDrawTime+=drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}*/
	@Override
	public void run() 
	{
		double drawInterval =nanoSecond/FPS; //0.01666 seconds
		double delta =0;
		long lastTime = System.nanoTime(); 
		long currentTime;
		long timer=0;
		int drawCount=0;
		
		
		while(gameThread!= null)
		{
			currentTime=System.nanoTime();
			delta += (currentTime-lastTime)/drawInterval;
			timer += (currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1)
			{
				update();
				repaint();
				delta--;
				drawCount++;
				
			}
			if(timer>=nanoSecond)
			{
				System.out.println("FPS:"+ drawCount);
				drawCount=0;
				timer=0;
			}
		}
		
	}
	public void update()
	{
		
		//PLAY STATE
		if(this.gameState==this.playState) {

			entityListUpdate();
		}
		
		// PAUSE STATE
		if(this.gameState==this.pauseState) {
			// nothing.
		}
		
		// DIALOGUE STATE
		if(this.gameState==this.dialogueState)
		{
			
		}
		
	}
	public void entityListUpdate() {
		//PLAYER
		player.update();
		
		//NPC
		for(int i =0;i<npc.length;i++)
		{
			if(npc[i]!=null)
				npc[i].update();
		}
		
		//MONSTER
		for(int i =0;i<monster.length;i++)
		{
			if(monster[i]!=null) {if(monster[i].alive&&(!monster[i].dying))monster[i].update();
			if(!monster[i].alive)monster[i]=null;
			}
		}
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g; 
		
		//DEBUG
		long drawStart=0;
		if(keyH.checkDrawTime)drawStart = System.nanoTime();

		//TITLE SCREEN
		if(this.gameState==this.titleState)
		{
			ui.draw(g2D);
		}

		//OTHERS	
		else {
			//TILE
			tileM.draw(g2D);
			
			//OBJECTS
			addObjectList();
			
			//DRAW OBJECT LIST
			drawObjectList(g2D);
			
			//ADD ENTITY LIST
			addEntityList();
			
			//Sort Collection Class
			sortEntityList();
			
			//DRAW ENTITY LIST
			drawEntityList(g2D);
			
			//UI
			ui.draw(g2D);
			
		}
		

		//DEBUG
		if(keyH.checkDrawTime)
		{
			long drawEnd=System.nanoTime();
			long passed = drawEnd-drawStart;
			g2D.setColor(Color.white);
			g2D.drawString("Draw Time is: "+passed, 10, 400);
			System.out.println("Draw Time is: "+passed);
		}
		g2D.dispose();
	}
	
	public void addObjectList() {
		//if(this.treasureGame)for(int i =0;i<obj.length;i++) {if(obj[i]!=null){obj[i].draw(g2D);}}
		//if(!this.treasureGame)for(int i =0;i<obj.length;i++) {obj[i]=null;}	
		
		if(this.treasureGame)for(int i =0;i<obj.length;i++) {if(obj[i]!=null){this.objectList.add(obj[i]);}}
		if(!this.treasureGame)for(int i =0;i<obj.length;i++) {obj[i]=null;}	
	}


	public void addEntityList() {
		//NPC 
		for(int i =0;i<npc.length;i++) {
			if(npc[i]!=null)
			{
				//npc[i].draw(g2D);
				this.entityList.add(npc[i]);
			}
		}
		
		//PLAYER
		this.entityList.add(player);
		//player.draw(g2D);
		
		//MONSTER
		for(int i =0;i<monster.length;i++) {
			if(monster[i]!=null)
			{
				//monster[i].draw(g2D);
				this.entityList.add(monster[i]);

			}
		}
		
	}

	public void drawObjectList(Graphics2D g2D) {
		for(int i =0 ; i <this.objectList.size();i++)
		{
			this.objectList.get(i).draw(g2D);
		}
		this.objectList.clear();
	}

	public void sortEntityList() {
		Collections.sort(this.entityList,new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.worldY, e2.worldY);
				return result;
			}
			
		});
	}

	public void drawEntityList(Graphics2D g2D) {
		for(int i =0 ; i <this.entityList.size();i++)
		{
			this.entityList.get(i).draw(g2D);
		}
		this.entityList.clear();
	}


	public void playMusic(int i)
	{
		
		this.music.setFile(i);
		this.music.play();
		this.music.loop();
	}
	public void stopMusic() {
		this.music.stop();
	}
	public void playSE(int i)
	{
		this.se.setFile(i);
		this.se.play();
	}
}
