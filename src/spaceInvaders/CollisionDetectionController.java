package spaceInvaders;

import java.awt.Rectangle;

public class CollisionDetectionController implements Runnable
{
	private Thread thread;
	private AliensController aliensController;
	private PlayersController playersController;
	private ShieldController shieldController;
	private UFOController ufoController;
	
	public CollisionDetectionController (PlayersController playersController, AliensController aliensController, ShieldController shieldController, UFOController ufoController) {
		this.aliensController = aliensController;
		this.playersController = playersController;
		this.shieldController = shieldController;
		this.ufoController = ufoController;
		
		thread = new Thread(this);
		thread.start();
	}
	
	public void stopGame() {
		thread = null;		
	}


	@Override
	public void run() {
		while(thread == Thread.currentThread()){
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			checkCollision();
		}
	}

	void checkCollision(){
		Shot deadShot = null;
		CharacterFoundation deadChar = null;
		
		synchronized (playersController.shots) {
			for(Shot shot:playersController.shots){
				Rectangle bulletRectangle = shot.getRectangle();
				
				if(ufoController.ufo != null){
					if(ufoController.ufo.CurrentRectange().intersects(bulletRectangle)){
						shot.gone();
						ufoController.deadUFO();
						return;
					}
				}
				
				synchronized (shieldController.shields) {
					for(Shield shield:shieldController.shields){
						if(shield.CurrentRectange().intersects(bulletRectangle)){
							shield.gotDamege();
							shot.gone();
							return;
						}
					}
				}
				
				synchronized (aliensController.aliens) {
					for(Alien alien:aliensController.aliens){
						if(alien.CurrentRectange().intersects(bulletRectangle)){
							deadChar = alien;
							deadShot = shot;
							break;
						}
					}
				}
				if(deadShot != null && deadChar != null){
					break;
				}
			}
		}
		
		if(deadShot != null && deadChar != null){
			aliensController.deadAlien((Alien)deadChar);
			deadShot.gone();
			return;
		}
		
 		synchronized (aliensController.shots) {
 			for(Shot shot:aliensController.shots){
 				
 				synchronized (shieldController.shields) {
					for(Shield shield:shieldController.shields){
						if(shield.CurrentRectange().intersects(shot.getRectangle())){
							deadShot = shot;
							shield.gotDamege();
							break;
						}
					}
				}
				if(deadShot != null){
					deadShot.gone();
					return;
				}
				
 				
 				if(playersController.player1.CurrentRectange().intersects(shot.getRectangle())){
 					deadChar = playersController.player1;
 					deadShot = shot;
 					break;
 				}
 			}
 		}
		if(deadShot != null && deadChar != null){
			playersController.deadPlayer((Player)deadChar);
			deadShot.gone();
			return;
		}
	}	
}