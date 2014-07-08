package spaceInvaders;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.net.URL;

import javax.swing.ImageIcon;

abstract class CharacterFoundation
{
	protected ImageIcon charImageIcon;	
	protected Rectangle charRectangle;
	
	public int xPoint;
	public int yPoint;

	CharacterFoundation(ImageIcon imageIcon){
		this.charImageIcon = imageIcon;
		charRectangle = new Rectangle(charImageIcon.getIconWidth(), charImageIcon.getIconHeight());
	}
	
	public Rectangle CurrentRectange() {
		Rectangle rectangle = new Rectangle(xPoint, yPoint, this.charImageIcon.getIconWidth(), this.charImageIcon.getIconHeight());
		return rectangle;
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.charImageIcon.getImage(), xPoint, yPoint, null);
	}
	
	public void die(){
		String imageFileName = "resource/explosion.gif";
		Image image = null;
		URL imageUrl = this.getClass().getClassLoader().getResource(imageFileName);
		if(imageUrl == null){
			image = new ImageIcon(imageFileName).getImage();
		}else{
			image = new ImageIcon(imageUrl).getImage();
		}
		this.charImageIcon = new ImageIcon(image);
		charRectangle = new Rectangle(charImageIcon.getIconWidth(), charImageIcon.getIconHeight());
	}
	
	abstract public void moveRight();
	abstract public void moveLeft();
	abstract public void moveDown();
}
