package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);
		setSolidAreaNPC();
		setDefaultSolidAreaNPC();

		

		this.direction = "down";
		this.speed =1;
		this.getOldManImage();
		setDialogue();
	}
private void setSolidAreaNPC() {
		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
	}
private void setDefaultSolidAreaNPC() {
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
	}
public void getOldManImage(){
		up1= setUp("/npc/oldman_up_1");
		up2= setUp("/npc/oldman_up_2");
		down1= setUp("/npc/oldman_down_1");
		down2= setUp("/npc/oldman_down_2");
		left1= setUp("/npc/oldman_left_1");
		left2= setUp("/npc/oldman_left_2");
		right1= setUp("/npc/oldman_right_1");
		right2= setUp("/npc/oldman_right_2");
	}

public void setDialogue()
{
	dialogues[0]= "hey Boy";
	dialogues[1]= "I hope is going you well";
	dialogues[2]= "good luck to find treasure";
	dialogues[3]= "see ya";
	
}
public void setAction()
{
	
	actionLockCounter++;
	
	if(actionLockCounter>120) {
		Random random = new Random();
		int i = random.nextInt(100)+1;
	
		if (i<=25)
		{
			this.direction =  "up";
		}
		else if(i<=50){
			this.direction= "down";
		}
		else if(i<=75){
			this.direction= "left";
		}
		else {
			this.direction= "right";
		}

		actionLockCounter=0;
		
		}
}
public void speak()
{
	super.speak();
}


}
