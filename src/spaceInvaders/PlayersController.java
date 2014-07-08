package spaceInvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlayersController implements KeyInputInterface, ShotInterface
{
	public Player player1;
	public ArrayList<Shot>shots;
	private ImageIcon playerImageIcon;
	Explosion explosion = new Explosion();

	private Rectangle stageRectangle;
	private KeyInputController keyInputController;
	private PlayersControllerInterface playersControllerInterface;
	
	public PlayersController(Rectangle stageRectangle, JPanel keySpeaker, PlayersControllerInterface listener){
		this.stageRectangle = stageRectangle;
		this.playersControllerInterface = listener;
		
		String imageFileName = "resource/me.gif";
		URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
		if(imageUrl == null){
			playerImageIcon = new ImageIcon(imageFileName);
		}else{
			playerImageIcon = new ImageIcon(imageUrl);
		}
		
		player1 = new Player(playerImageIcon);
		shots = new ArrayList<Shot>();
		
		keyInputController = new KeyInputController(this);
		keySpeaker.addKeyListener(keyInputController);
	}
	
	public void deadPlayer(Player player){
		player.die();
		explosion.bomb(player.CurrentRectange());
		
		playersControllerInterface.gameOver();
	}
	
	public void drawPlayer(Graphics g) {
		player1.draw(g);	
		synchronized (shots) {
			for(Shot shot:shots){
				shot.draw(g);
			}			
		}
	}
	
	synchronized public void stopGame() {		
		synchronized (shots) {
			for(Shot shot:shots){
				shot.thread = null;
			}			
		}
		synchronized (explosion.thread) {
			explosion.thread = null;
		}
	}
	
	//ShotInterface
	@Override
	public void DisappearBullet(Shot shot) {
		synchronized (shots) {
			shots.remove(shot);			
		}
	}
	
	//KeyInputInterface
	@Override
	public void moveToRight_local() {
		player1.moveRight();
		if(player1.CurrentRectange().getMaxX() > this.stageRectangle.getWidth()){
			player1.moveLeft();
		}
	}
	
	@Override
	public void moveToLeft_local() {
		player1.moveLeft();
		if(player1.CurrentRectange().getMinX() < 0){
			player1.moveRight();
		}
	}

	@Override
	public void shot_local() {
		if(shots.size() < 2){
			synchronized (shots) {
				shots.add(new Shot(player1, (int)this.stageRectangle.getHeight(), this));				
			}
		}
	}
}


interface PlayersControllerInterface extends EventListener
{
	public void gameOver();
}
