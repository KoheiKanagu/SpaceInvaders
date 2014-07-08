package spaceInvaders;

import java.awt.Graphics;
import java.util.ArrayList;

public class ShieldController implements ShieldInterface
{
	public ArrayList<Shield>shields;
	
	public ShieldController(){
		shields = new ArrayList<Shield>();
		shields.add(new Shield(50, 320, this));
		shields.add(new Shield(170, 320, this));
		shields.add(new Shield(290, 320, this));
		shields.add(new Shield(410, 320, this));
	}
	
	public void drawShield(Graphics g){
		for(Shield shield:shields){
			shield.draw(g);			
		}
	}

	//ShieldInterface
	@Override
	public void breakShield(Shield shield) {
		synchronized (shields) {
			shields.remove(shield);			
		}
	}
}
