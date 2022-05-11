package main;

import entity.Entity;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.SuperObject;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp)
	{
		this.gp=gp;
	}
	
	public void setObject()
	{
		
		gp.obj[0] = new OBJ_Key(gp);
		setPositionObj(gp.obj[0] ,23,7);

		gp.obj[1] = new OBJ_Key(gp);
		setPositionObj(gp.obj[1] ,23,40);
	
		
		gp.obj[2] = new OBJ_Key(gp);
		setPositionObj(gp.obj[2] ,38,8);
	
		gp.obj[3] = new OBJ_Door(gp);
		setPositionObj(gp.obj[3] ,10,12);

		
		gp.obj[4] = new OBJ_Door(gp);
		setPositionObj(gp.obj[4] ,8,28);

		
		gp.obj[5] = new OBJ_Door(gp);
		setPositionObj(gp.obj[5] ,12,23);

		
		gp.obj[6] = new OBJ_Chest(gp);
		setPositionObj(gp.obj[6] ,10,8);

		
		gp.obj[7] = new OBJ_Boots(gp);
		setPositionObj(gp.obj[7] ,37,42);

		
		
	}
	public void setNPC()
	{
		gp.npc[0] = new NPC_OldMan(gp);
		setPositionEntity(gp.npc[0],21, 21);
		//setPositionEntity(gp.npc[0],9, 10);
		/*
		gp.npc[1] = new NPC_OldMan(gp);
		gp.npc[1].worldX = 20 * gp.tileSize;
		gp.npc[1].worldY = 20 * gp.tileSize;
		
		gp.npc[2] = new NPC_OldMan(gp);
		gp.npc[2].worldX = 11 * gp.tileSize;
		gp.npc[2].worldY = 11 * gp.tileSize;
		*/
	}
	public void setMON()
	{
		gp.monster[0] = new MON_GreenSlime(gp);
		setPositionEntity(gp.monster[0],23, 36);
		//setPositionEntity(gp.monster[0],10, 10);
		gp.monster[1] = new MON_GreenSlime(gp);
		//setPositionEntity(gp.monster[1],11, 10);
		setPositionEntity(gp.monster[1],23, 37);
		gp.monster[2] = new MON_GreenSlime(gp);
		setPositionEntity(gp.monster[2],23, 38);
	}
	public void setPositionObj(SuperObject object ,int x, int y)
	{
		object.worldX = x * gp.tileSize;
		object.worldY = y * gp.tileSize;
	}
	public void setPositionEntity(Entity entity,int x, int y)
	{
		entity.worldX = x * gp.tileSize;
		entity.worldY = y * gp.tileSize;
	}
}
