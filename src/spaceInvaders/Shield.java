package spaceInvaders;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.URL;
import java.util.EventListener;

import javax.swing.ImageIcon;

public class Shield
{
	private int hitPoint=15;
	private ImageIcon shieldImageIcon;
	private int xPoint, yPoint;
	private ShieldInterface shieldInterface;
	
	public Shield(int x, int y, ShieldInterface listener){
		shieldImageIcon = getResource("resource/shield4.gif");
		xPoint = x;
		yPoint = y;
		shieldInterface = listener;
	}
	
	public void draw(Graphics g){
		g.drawImage(shieldImageIcon.getImage(), xPoint, yPoint, null);
	}
	
	public void gotDamege(){
		hitPoint--;
		if(hitPoint == 12){
			shieldImageIcon = getResource("resource/shield3.gif");
		}else if(hitPoint == 9){
			shieldImageIcon = getResource("resource/shield2.gif");
		}else if(hitPoint == 6){
			shieldImageIcon = getResource("resource/shield1.gif");
		}else if(hitPoint == 3){
			shieldImageIcon = getResource("resource/shield0.gif");
		}else if(hitPoint == 0){
			shieldInterface.breakShield(this);
		}	
	}
	
	public Rectangle CurrentRectange() {
		Rectangle rectangle = new Rectangle(xPoint, yPoint, this.shieldImageIcon.getIconWidth(), this.shieldImageIcon.getIconHeight());
		return rectangle;
	}

	
	private ImageIcon getResource(String fileName){
		URL imageUrl = this.getClass().getClassLoader().getResource(fileName);
		ImageIcon imageIcon;
		
		if(imageUrl == null){
			imageIcon = new ImageIcon(fileName);
		}else{
			imageIcon = new ImageIcon(imageUrl);
		}
		return imageIcon;
	}
}

interface ShieldInterface extends EventListener
{
	public void breakShield(Shield shield);
}