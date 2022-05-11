package main;
import javax.swing.JFrame;

public class MyFrame extends JFrame {
	
	public MyFrame()
	{
		GamePanel gamePanel = new GamePanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.add(gamePanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
	}
}
