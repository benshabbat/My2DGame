package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Key extends SuperObject{

	public OBJ_Key(GamePanel gp)
	{
		super(gp);
		name = "key";
		setUp(name, false);
	}
}
