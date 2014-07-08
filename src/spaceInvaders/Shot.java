package spaceInvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;
import java.util.EventListener;

import javax.swing.ImageIcon;


public class Shot implements Runnable
{	
	public ShotInterface shotInterface;
	protected CharacterFoundation shooter;
	
	private ImageIcon bulletIcon;
	private int xPoint;
	private int yPoint;
	private int stage_height;
	public Thread thread;
	
	public Shot(CharacterFoundation character, int stage_height, ShotInterface listener){
		this.shotInterface = listener;
		this.shooter = character;
		this.stage_height = stage_height;
		
		String imageFileName = null;
		if(character.getClass() == Player.class){
			imageFileName = "resource/bullet.gif";
			
			Player player = (Player)character;
			Rectangle rectangle = player.CurrentRectange();
			
			xPoint = (int)rectangle.getCenterX();
			yPoint = (int)rectangle.getCenterY()-20;
		
		}else if(character.getClass() == Alien.class){
			imageFileName = "resource/beam.gif";

			Alien alien = (Alien)character;
			Rectangle rectangle = alien.CurrentRectange();
			
			xPoint = (int)rectangle.getCenterX();
			yPoint = (int)rectangle.getCenterY()+12;
		}
		Image image = null;
		URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
		if(imageUrl == null){
			image = new ImageIcon(imageFileName).getImage();
		}else{
			image = new ImageIcon(imageUrl).getImage();
		}
		this.bulletIcon = new ImageIcon(image);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public Rectangle getRectangle(){
		return new Rectangle(xPoint, yPoint, this.bulletIcon.getIconWidth(), this.bulletIcon.getIconHeight());
	}
	
	public void draw(Graphics g) {
		g.drawImage(bulletIcon.getImage(), xPoint, yPoint, null);
	}
	
	@Override
	public void run() {
		while(thread == Thread.currentThread()){
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			checkBulletPoint();
		}
	}
	
	private void checkBulletPoint(){
		if(shooter.getClass() == Player.class){
			if(yPoint < 0){
				gone();
			}else{
				yPoint-=3;
			}
		}else if(shooter.getClass() == Alien.class){
			if(yPoint > this.stage_height){
				gone();
			}else{
				yPoint++;
			}
		}
	}
	
	public void gone(){
		thread = null;
		
		this.shotInterface.DisappearBullet(this);
	}
}


interface ShotInterface extends EventListener
{
	public void DisappearBullet(Shot shot);
}
