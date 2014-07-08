package spaceInvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.EventListener;

import javax.swing.JPanel;

public class GameController extends JPanel implements Runnable, PlayersControllerInterface, AliensControllerInterface, UFOInterface
{
	private static final long serialVersionUID = 1L;

	public static final int STAGE_WIDTH = 256*2;
	public static final int STAGE_HEIGHT = 224*2;
	
	private Thread thread;
	private GameControllerInterface gameControllerInterface;
	private AliensController aliensController;
	private PlayersController playersController;
	private ShieldController shieldController;
	private UFOController ufoController;
	private CollisionDetectionController collisionDetectionController;
	
	public GameController(GameControllerInterface listener){
		gameControllerInterface = listener;
 		this.setPreferredSize(new Dimension(STAGE_WIDTH, STAGE_HEIGHT));
		this.setBounds(0, 0, STAGE_WIDTH, STAGE_HEIGHT);
	}

	void startGame(){
		this.requestFocus();
		
		playersController = new PlayersController(this.getBounds(), this, this);
		aliensController = new AliensController(this.getBounds(), this);
		shieldController = new ShieldController();
		ufoController = new UFOController(this);
		collisionDetectionController = new CollisionDetectionController(playersController, aliensController, shieldController, ufoController);		
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		while(thread == Thread.currentThread()){
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	protected void paintComponent(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, STAGE_WIDTH, STAGE_HEIGHT);
		
		if(playersController!=null && aliensController!=null && shieldController!=null && ufoController!=null){
			playersController.drawPlayer(g);
			aliensController.drawAliens(g);
			shieldController.drawShield(g);
			ufoController.drawUFO(g);
		}
	}

	
	//PlayersControllerInterface
	@Override
	public void gameOver() {
 		thread = null;
		collisionDetectionController.stopGame();
		
 		aliensController.stopGame();
		playersController.stopGame();
		ufoController.stopGame();
		
		gameControllerInterface.gameOver();
	}

	
	//AliensControllerInterface
	@Override
	public void getScore(int score) {
		gameControllerInterface.getScore(score);
	}
	
	@Override
	public void allAlienKilled() {
		gameOver();
		gameControllerInterface.gameClear();
	}
}


interface GameControllerInterface extends EventListener
{
	public void gameOver();
	public void getScore(int score);
	public void gameClear();
}

