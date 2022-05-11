package main;

import entity.Entity;
import entity.Player;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp)
	{
		this.gp=gp;
	}
	
	
	public void directionOn(Entity entity)
	{
		
		switch(entity.direction)
		{
		case "up": entity.solidArea.y -= entity.speed;break;
		
		case "down": entity.solidArea.y += entity.speed;break;
		
		case "left": entity.solidArea.x -= entity.speed;break;
		
		case "right": entity.solidArea.x += entity.speed;break;
		}

		
	}
	public void directionOn(Entity entity,Entity name)
	{
		
		directionOn(entity);
		if(entity.solidArea.intersects(name.solidArea)) {
			if(name!=entity) {entity.collisionOn=true;}}
	}
	public Entity getEntitySolidAreaPosition(Entity entity)
	{
		// get player's solid area position
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;
		return entity;
	}
	public void check( Entity entity,Entity name)
	{
				// get player's solid area position
				entity=getEntitySolidAreaPosition(entity);
				
				// get npc's solid area position
				name=getEntitySolidAreaPosition(name);	
				
				directionOn(entity,name);
				backSolidAreaDefault(entity,name);

		}	
	public void checkTile(Entity entity)
	{
		int entityLeftWorldX = (int) (entity.worldX + entity.solidArea.x);
		int entityRightWorldX = (int) (entity.worldX + entity.solidArea.x + entity.solidArea.width);
		int entityTopWorldY = (int) (entity.worldY + entity.solidArea.y);
		int entityBottomWorldY = (int) (entity.worldY + entity.solidArea.y + entity.solidArea.height);
		
		
		int entityLeftCol = (int) (entityLeftWorldX/gp.tileSize);
		int entityRightCol = (int) (entityRightWorldX/gp.tileSize);
		int entityTopRow = (int) (entityTopWorldY/gp.tileSize);
		int entityBottomRow = (int) (entityBottomWorldY/gp.tileSize);
		
		
		
		
		int tileNum1 = 0, tileNum2 = 0;
		
		switch(entity.direction)
		{
		case "up":
			entityTopRow= (int) ((entityTopWorldY-entity.speed)/gp.tileSize);
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			break;
		case "down":
			entityBottomRow= (int) ((entityBottomWorldY+entity.speed)/gp.tileSize);
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			break;
		case "left":
			entityLeftCol= (int) ((entityLeftWorldX-entity.speed)/gp.tileSize);
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			break;
		case "right":
			entityRightCol= (int) ((entityRightWorldX+entity.speed)/gp.tileSize);
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			break;
		}
		if(gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true)
			entity.collisionOn=true;
	}
	public int checkObject(Entity entity,boolean player)
	{
		int index = -1;
		for(int i=0;i < gp.obj.length;i++)
		{
			if(gp.obj[i]!=null)
			{
				
				// get player's solid area position
				entity=getEntitySolidAreaPosition(entity);
				
				// get object's solid area position
				gp.obj[i].solidArea.x += gp.obj[i].worldX;
				gp.obj[i].solidArea.y += gp.obj[i].worldY;
				
				switch(entity.direction)
				{
				case "up": entity.solidArea.y -= entity.speed; break;
				case "down": entity.solidArea.y += entity.speed;break;
				case "left": entity.solidArea.x -= entity.speed;break;
				case "right": entity.solidArea.x += entity.speed;break;}
				if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
					if(gp.obj[i].collision){entity.collisionOn=true;}
					if(player)index=i;}
				
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
			
		}
		return index;
	}
	public int checkEntity(Entity entity ,Entity[] target) {
		int index = -1;
		for(int i=0;i < target.length;i++)
		{
			if(target[i]!=null)
			{
				
				// get player's solid area position
				entity=getEntitySolidAreaPosition(entity);
				
				// get npc's solid area position
				target[i]=getEntitySolidAreaPosition(target[i]);

				
			//	index=directionOn(entity,target[i],i);
				directionOn(entity);
				if(entity.solidArea.intersects(target[i].solidArea)){ 
					if(	target[i]!=entity) {entity.collisionOn=true;index=i;}}
				
				backSolidAreaDefault(entity,target[i]);


			}
			
		}
		return index;
		
	}
	public void backSolidAreaDefault(Entity entity, Entity target) {
		// TODO Auto-generated method stub
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
		target.solidArea.x = target.solidAreaDefaultX;
		target.solidArea.y = target.solidAreaDefaultY;
	}


	public boolean checkPlayer(Entity entity)
	{
		boolean contactPlayer = false;
		// get player's solid area position
		getEntitySolidAreaPosition(entity);
		
		// get npc's solid area position
		gp.player=(Player) getEntitySolidAreaPosition(gp.player);

		directionOn(entity);
		if(entity.solidArea.intersects(gp.player.solidArea)) {
			if(gp.player!=entity) {entity.collisionOn=true;
			contactPlayer=true;}}
		backSolidAreaDefault(entity,gp.player);
		return contactPlayer;
	}

}

