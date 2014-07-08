package spaceInvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.util.EventListener;

public class SubPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private SubPanelInterface subPanelInterface;
	
	private JLabel scoreLabel;
	private JLabel highScoreLabel;
	private JLabel messageLabel;
	private JButton startButton;
	
	private int score=0;
	private int highScore=0;
	
	public SubPanel(SubPanelInterface listener){
		this.setPreferredSize(new Dimension(256, 224*2));
		this.setBounds(0, 0, 328, 224*2);
		this.setLayout(null);
		this.subPanelInterface = listener;
		
		startButton = new JButton("START");
		startButton.setFont(new Font("Warp Drive", Font.BOLD, 12));
		startButton.setBounds(6, 221, 244, 29);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				messageLabel.setText("");
				score = 0;
				scoreLabel.setText(String.valueOf(score));
				subPanelInterface.startGame();
			}
		});
		add(startButton);
		
		JLabel lblNewLabel = new JLabel("Score");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Warp Drive", Font.BOLD, 16));
		lblNewLabel.setBounds(6, 36, 128, 16);
		add(lblNewLabel);
		
		JLabel label = new JLabel("High Score");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Warp Drive", Font.BOLD, 16));
		label.setBounds(6, 64, 128, 16);
		add(label);
		
		scoreLabel = new JLabel("0");
		scoreLabel.setFont(new Font("Warp Drive", Font.BOLD, 16));
		scoreLabel.setBounds(146, 36, 104, 16);
		add(scoreLabel);
		
		highScoreLabel = new JLabel("0");
		highScoreLabel.setFont(new Font("Warp Drive", Font.BOLD, 16));
		highScoreLabel.setBounds(146, 64, 104, 16);
		add(highScoreLabel);
		
		messageLabel = new JLabel("Push START Button");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("Warp Drive", Font.BOLD, 18));
		messageLabel.setBounds(6, 180, 244, 29);
		add(messageLabel);
	}
	
	public void gameOver(){
		messageLabel.setText("Game Over");
		startButton.setText("RESTART");
		startButton.setEnabled(true);
	}
	
	public void gameClear(){
		messageLabel.setText("GameClear!");
		startButton.setText("RESTART");
		startButton.setEnabled(true);
	}
	
	public void addScore(int score){
		this.score += score;
		scoreLabel.setText(String.valueOf(this.score));
		messageLabel.setText(String.format("+%d", score));
		
		if(this.score > this.highScore){
			this.highScore = this.score;
			highScoreLabel.setText(String.valueOf(this.highScore));
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());		
	}
}


interface SubPanelInterface extends EventListener
{
	public void startGame();
}