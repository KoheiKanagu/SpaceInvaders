package spaceInvaders;

import javax.swing.ImageIcon;

public class Player extends CharacterFoundation
{
	Player(ImageIcon imageIcon) {
		super(imageIcon);
		xPoint = 250;
		yPoint = 400;
	}
	
	@Override
	public void moveRight() {
		xPoint += this.charImageIcon.getIconWidth()/2;
	}

	@Override
	public void moveLeft() {
		xPoint -= this.charImageIcon.getIconWidth()/2;
	}
	
	@Override
	public void moveDown() {}
}
