package object;

import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Door extends SuperObject{

	public OBJ_Door(GamePanel gp)
	{
		super(gp);
		name = "door";
		setUp(name, true);
	}
}
