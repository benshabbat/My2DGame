package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
	
	
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		type=2;
		//name="Green Slime";
		speed=1;
		this.direction = "down";
		maxLife=4;
		life=maxLife;
		setSolidAreaMON();
		setDefaultSolidAreaMON();
		getGreenSmileImage();
		
	
	}
	
	public void setDefaultSolidAreaMON() {
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
	}
	public void setSolidAreaMON() {
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		
	}
	public void getGreenSmileImage(){
		up1= setUp("/monsters/greenslime_down_1");
		up2= setUp("/monsters/greenslime_down_2");
		down1= setUp("/monsters/greenslime_down_1");
		down2= setUp("/monsters/greenslime_down_2");
		left1= setUp("/monsters/greenslime_down_1");
		left2= setUp("/monsters/greenslime_down_2");
		right1= setUp("/monsters/greenslime_down_1");
		right2= setUp("/monsters/greenslime_down_2");	
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

	public void damageReaction() {
		this.actionLockCounter=0;
		this.direction=gp.player.direction;
	}
}
