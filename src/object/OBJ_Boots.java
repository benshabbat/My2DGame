package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Boots extends SuperObject{

	public OBJ_Boots(GamePanel gp)
	{
		super(gp);
		name = "boots";
		setUp(name, true);
	}
}