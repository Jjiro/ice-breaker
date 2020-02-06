import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class IceBreakerBlockPanel extends JPanel implements KeyListener {

	ArrayList<IceBreakerBlock> iceBreakerBlocks;
	IceBreakerBlock ball;
	IceBreakerBlock paddle;
	
	JFrame mainFrame, startScreen;
	
	Thread thread;
	
	public void reset() {
		iceBreakerBlocks = new ArrayList<>();
		//iceBreakerBlocks = new ArrayList<IceBreakerBlock>();
		ball = new IceBreakerBlock(237, 435, 25, 20, "img/ball.png");
		paddle = new IceBreakerBlock(180, 480, 150, 20, "img/paddle.png");
		for (int i = 0; i < 8; i++){
			iceBreakerBlocks.add(new IceBreakerBlock((i * 60 + 1),0,60,23, "img/blue.png"));}
		for (int i = 0; i < 8; i++){
			iceBreakerBlocks.add(new IceBreakerBlock((i * 60 + 1),20,60,23, "img/green.png"));}
		for (int i = 0; i < 8; i++){
			iceBreakerBlocks.add(new IceBreakerBlock((i * 60 + 1),40,60,23, "img/yellow.png"));}
		for (int i = 0; i < 8; i++){
			iceBreakerBlocks.add(new IceBreakerBlock((i * 60 + 1),60,60,23, "img/red.png"));}
		for (int i = 0; i < 8; i++){
			iceBreakerBlocks.add(new IceBreakerBlock((i * 60 + 1),80,60,23, "img/purple.png"));}

		addKeyListener(this);
		setFocusable(true);
	}

	public IceBreakerBlockPanel(JFrame frame, JFrame startScreen) {
		this.mainFrame = frame;
		this.startScreen = startScreen;

		reset();
		
		thread = new Thread(() -> {
			while (true) {
				update();
				try {
					Thread.sleep(10);
				} catch (InterruptedException err) {
					err.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public void paintComponent(Graphics g) {
		iceBreakerBlocks.forEach(iceBreakerBlock ->
			iceBreakerBlock.draw(g, this)
		);
		ball.draw(g, this);
		paddle.draw(g, this);
	}

	public void update() {
		ball.x += ball.movX;
		
		if(ball.x > (getWidth() - 25) || ball.x < 0)
			ball.movX *= -1;
		
		if(ball.y < 0 || ball.intersects(paddle))
			ball.movY *= -1;
		
		ball.y += ball.movY;
		
		if(ball.y > getHeight()) {
			thread = null;
			reset();
			mainFrame.setVisible(false);
			startScreen.setVisible(true);
		}
		
		iceBreakerBlocks.forEach(iceBreakerBlock -> {
			if(ball.intersects(iceBreakerBlock) && !iceBreakerBlock.destroyed) {
				ball.movY *= -1;
				iceBreakerBlock.destroyed = true;
			}
		});
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddle.x < (getWidth() - paddle.width)) {
			paddle.x += 15;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT && paddle.x > 0) {
			paddle.x -= 15;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
