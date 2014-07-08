package spaceInvaders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SpaceInvaders extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("SpaceInvaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BasePanel());
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
}

class BasePanel extends JPanel implements GameControllerInterface, SubPanelInterface
{	
	private static final long serialVersionUID = 1L;
	private GameController gameController;
	private SubPanel subPanel;
	
 	public BasePanel(){
 		this.setPreferredSize(new Dimension(768, 224*2));
 		this.setFocusable(true);
 		this.setLayout(new BorderLayout());
 		
 		gameController = new GameController(this);
 		this.add(gameController, BorderLayout.WEST);
 		
 		subPanel = new SubPanel(this);
 		this.add(subPanel, BorderLayout.EAST);
 	}
 	
 	//GameCtrlInterface
	@Override
	protected void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());		
	}
	
	@Override
	public void gameOver(){
		subPanel.gameOver();
	}
	
	@Override
	public void getScore(int score) {
		subPanel.addScore(score);
	}

	@Override
	public void gameClear() {
		subPanel.gameClear();
	}
	
	
	//SubPanelInterface
	@Override
	public void startGame() {
 		gameController.startGame();
	}

}
