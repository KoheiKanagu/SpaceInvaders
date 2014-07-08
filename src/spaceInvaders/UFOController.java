package spaceInvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;
import java.util.EventListener;
import java.util.Random;

import javax.swing.ImageIcon;

public class UFOController implements Runnable
{
	private ImageIcon ufoImageIcon;
	public UFO ufo;
	private Explosion explosion;
	private UFOInterface ufoInterface;
	private Thread thread;
	
	
	public UFOController(UFOInterface listener){
		String imageFileName = "resource/ufo.gif";
		Image image = null;
		URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
		if(imageUrl == null){
			image = new ImageIcon(imageFileName).getImage();
		}else{
			image = new ImageIcon(imageUrl).getImage();
		}
		this.ufoImageIcon = new ImageIcon(image);
		this.explosion = new Explosion();
		this.ufoInterface = listener;
		
		thread = new Thread(this);
		thread.start();
	}
	
	private void goUFO(){
		this.ufo = new UFO(ufoImageIcon);
		ufo.xPoint = 256*2;
		ufo.yPoint = 30;
	}
	
	public void drawUFO(Graphics g){
		explosion.drawExplosion(g);
				
		if(this.ufo != null){
			this.ufo.moveLeft();
			if(this.ufo.CurrentRectange().getMaxX() < 0){
				this.ufo = null;
				return;
			}
			ufo.draw(g);
		}
	}

	@Override
	public void run() {
		while(thread == Thread.currentThread()){
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			goUFO();
		}
	}
	
	public void deadUFO(){
		explosion.bomb(this.ufo.CurrentRectange());
		
		Random random = new Random();
		int score = random.nextInt(1000);
		
		ufoInterface.getScore(score/100*100+100);
		this.ufo = null;
	}
	
	public void stopGame(){
		thread = null;
		explosion.thread = null;
	}
}


class UFO extends CharacterFoundation
{
	UFO(ImageIcon imageIcon) {
		super(imageIcon);
	}

	@Override
	public void moveRight() {}
	@Override
	public void moveLeft() {
		xPoint--;
	}
	@Override
	public void moveDown() {}
	
}


interface UFOInterface extends EventListener
{
	public void getScore(int score);
}
