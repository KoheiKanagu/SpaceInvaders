package spaceInvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;

public class Explosion implements Runnable
{
	private Rectangle expRectangle;
	private ImageIcon explosionIcon;
	public Thread thread;
	
	public void bomb(Rectangle rectangle){
		this.expRectangle = new Rectangle(rectangle);
	}
	
	public Explosion(){
		String imageFileName = "resource/explosion.gif";
		Image image = null;
		URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
		if(imageUrl == null){
			image = new ImageIcon(imageFileName).getImage();
		}else{
			image = new ImageIcon(imageUrl).getImage();
		}
		this.explosionIcon = new ImageIcon(image);
		
		thread = new Thread(this);
		thread.start();
	}	
	
	public void drawExplosion(Graphics g) {
		if(this.expRectangle != null){
			g.drawImage(this.explosionIcon.getImage(), (int)expRectangle.getX(), (int)expRectangle.getY(), null);
		}	
	}

	public void run() {
		while(thread == Thread.currentThread()){
			if(this.expRectangle != null){
				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.expRectangle = null; 
			}else{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

