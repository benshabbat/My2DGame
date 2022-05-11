package object;

import main.GamePanel;

public class OBJ_Heart extends SuperObject{

	
	public OBJ_Heart(GamePanel gp)
	{
		super(gp);
		

		heart_blank=getImage("heart_blank");
		
		heart_full=getImage("heart_full");
		
		heart_half=getImage("heart_half");
	}
	
}