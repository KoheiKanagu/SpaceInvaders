package spaceInvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;


public class KeyInputController implements KeyListener
{
	public KeyInputInterface inputInterface;
	
	public KeyInputController(KeyInputInterface listener){
		this.inputInterface = listener;		
	}	
	
	public void removeListener(){
		this.inputInterface = null;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == ' '){
			inputInterface.shot_local();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			inputInterface.moveToLeft_local();
			break;
		case KeyEvent.VK_RIGHT:
			inputInterface.moveToRight_local();
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}


interface KeyInputInterface extends EventListener
{
	public void moveToRight_local();
	public void moveToLeft_local();
	public void shot_local();
}


