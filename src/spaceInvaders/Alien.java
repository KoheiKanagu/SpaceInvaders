package spaceInvaders;

import javax.swing.ImageIcon;

public class Alien extends CharacterFoundation
{	
	public int score;
	
	Alien(ImageIcon imageIcon) {
		super(imageIcon);
	}

	public void setInitialPoint(int column, int row) {
		xPoint = 10+column*34;
		yPoint = 60+row*25;
		
		switch (row) {
		case 0:
			score = 40;
			break;
		case 1:
		case 2:
			score = 20;
			break;
			
		case 3:
		case 4:
			score = 10;
			break;
		default:
			break;
		}
	}

	@Override
	public void moveRight() {
		xPoint += 12;
	}

	@Override
	public void moveLeft() {
		xPoint -=12;
	}

	@Override
	public void moveDown() {
		yPoint +=30;
	}	
}
