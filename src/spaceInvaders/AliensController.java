package spaceInvaders;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Random;

import javax.swing.ImageIcon;

public class AliensController implements Runnable, ShotInterface
{
	public ArrayList<Alien>aliens;
	public ArrayList<Shot>shots;
	private Thread thread;
	private int moveSpeed = 1500;
	private Rectangle stageRectangle;
	private boolean moveToRight;
	private Explosion explosion;
	private AliensControllerInterface aliensControllerInterface;
	
	public AliensController(Rectangle stageRectangle, AliensControllerInterface listener){
		this.aliens = new ArrayList<Alien>();
		this.shots = new ArrayList<Shot>();
		
		this.stageRectangle = stageRectangle;
		this.moveToRight = true;
		this.explosion = new Explosion();
		
		this.aliensControllerInterface = listener;
		
		String imageFileName = null;
		for(int row=0; row<5; row++){
			ImageIcon alienImageIcon = null;
			switch (row) {
			case 0:
				imageFileName = "resource/alien2.gif";
				break;

			case 1:
			case 2:
				imageFileName = "resource/alien3.gif";
				break;
				
			case 3:
			case 4:
				imageFileName = "resource/alien1.gif";
				break;
			}
			
			Image image = null;
			URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
			if(imageUrl == null){
				image = new ImageIcon(imageFileName).getImage();
			}else{
				image = new ImageIcon(imageUrl).getImage();
			}
			alienImageIcon = new ImageIcon(image);

			
			for(int column=0; column<11; column++){
				Alien alien = new Alien(alienImageIcon);
				alien.setInitialPoint(column, row);
				aliens.add(alien);
			}
		}
		thread = new Thread(this);
		thread.start();
	}
	
	public void deadAlien(Alien alien){
		aliensControllerInterface.getScore(alien.score);
		
		alien.die();
		explosion.bomb(alien.CurrentRectange());
		synchronized (aliens) {
			if(aliens.size() == 44){
				moveSpeed = 1200;
			}else if(aliens.size() == 33){
				moveSpeed = 1000;
			}else if(aliens.size() == 22){
				moveSpeed = 800;
			}else if(aliens.size() == 11){
				moveSpeed = 600;
			}
			
			this.aliens.remove(alien);
		}
		if(aliens.size() == 0){
			aliensControllerInterface.allAlienKilled();
		}
	}
	
	public void stopGame(){
		thread = null;

		synchronized (explosion.thread) {
			explosion.thread = null;
		}
		synchronized (shots) {
			for(Shot shot:shots){
				shot.thread = null;
			}
		}
	}
	
	@Override
	public void run() {
		while(thread == Thread.currentThread()){
			try {
				Thread.sleep(moveSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (aliens) {
				boolean down = false;
				if(moveToRight){
					for(Alien alien:aliens){
						alien.moveRight();
						if(alien.xPoint+24+12 > this.stageRectangle.getWidth()){
							down = true;
							moveToRight = false;
						}
					}
				}else{
					for(Alien alien:aliens){
						alien.moveLeft();
						if(alien.xPoint-12 < 0){
							down = true;
							moveToRight = true;
						}
					}				
				}
				if(down){
					for(Alien alien:aliens){
						alien.moveDown();
					}		
				}
			}
		}
	}
	
	public void drawAliens(Graphics g) {
		explosion.drawExplosion(g);
		
		synchronized (aliens) {
			for(Alien alien:aliens){
				alien.draw(g);
			}			
		}

		Random random = new Random();
		if(random.nextInt()%24 == 0){
			if(aliens.size()>0){
				Alien alien = aliens.get(random.nextInt(aliens.size()));
				synchronized (shots) {
					shots.add(new Shot(alien, (int)this.stageRectangle.getHeight(), this));				
				}
			}
		}
		synchronized (shots) {
			for(Shot shot:shots){
				shot.draw(g);
			}					
		}
	}
	
	//ShotInterface
	@Override
	public void DisappearBullet(Shot shot) {
		synchronized (shots) {
			shots.remove(shot);			
		}
	}
}


interface AliensControllerInterface extends EventListener
{
	public void getScore(int score);
	public void allAlienKilled();
}
