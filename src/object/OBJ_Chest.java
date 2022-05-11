package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Chest extends SuperObject {
	
	
	
	public OBJ_Chest(GamePanel gp)
	{
		super(gp);
		name = "chest";
		setUp(name, false);
	}

}
