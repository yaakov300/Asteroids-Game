import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class AstroidGUI  extends JPanel implements Runnable
{	
	private static final int PANELWIDTH = Astroid.WHIDTH * Astroid.SCALE,
			PANELHEIGHT = Astroid.HEIGHT * Astroid.SCALE;
	private static final int FRAMES_PER_SECOND = 30;
	private static final int SKIP_TICKS = 1000 / FRAMES_PER_SECOND;	
	private AstroidGame controllGame;
	private boolean running; //loop game.
	private BufferedImage backgroundImage; //background image.
	private BufferedImage screenBufferImage = null; //screen buffer.


	public AstroidGUI() {

		setFocusable(true);
		requestFocusInWindow();
		addKeyListener(new Listener()); //listener for left and right arrow keys.

		try {
			backgroundImage = ImageIO.read(new File("src/resources/background.png"));			

		} catch (IOException e) {
			System.out.println("ERROR - can't read image");
		}
		controllGame = new AstroidGame(PANELWIDTH, PANELHEIGHT);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameRender();
		g.drawImage(screenBufferImage, 0, 0, this);
	}


	public void run() {

		long nextTicks, sleepTime;
		nextTicks = System.currentTimeMillis();
		running = true;
		sleepTime = 0;

		while(running) {
			gameUpdate(); //update current game logic 
			gameRender(); //draw the game into buffer.
			paintScreen(); // active rendering.			

			//Frames Per Second.

			nextTicks += SKIP_TICKS; 
			sleepTime  = nextTicks - System.currentTimeMillis();
			if(sleepTime <= 5)
				sleepTime = 5;

			try {
				Thread.sleep(sleepTime);
			}catch(InterruptedException e){}
		}
	}



	//draw all the elements of game into buffer.
	public void gameRender(){
		Graphics dbg;
		//create the buffer.
		screenBufferImage = new BufferedImage(PANELWIDTH, PANELHEIGHT, BufferedImage.TYPE_INT_ARGB );
		dbg = screenBufferImage.createGraphics();		
		dbg.setColor(Color.WHITE);	
		//draw the background.
		dbg.drawImage(backgroundImage, 0, 0, this);

		// draw game elements		
		controllGame.drawModel(dbg);


		//check if the game is over.
		if (controllGame.isGameOver())
		{
			gameOverMessage(dbg);
			running = false;
		}

	}

	//activy render
	public synchronized void paintScreen()
	{
		Graphics g;
		try {
			g = getGraphics();
			if(g != null && screenBufferImage != null)
			{	
				g.drawImage(screenBufferImage, 0, 0, this);

			}
		}
		catch(Exception e)
		{
			System.out.println("Graphics error");
			e.printStackTrace();
		}
	}


	//update current game logic 
	public void gameUpdate()
	{
		controllGame.updateModel();
	}

	//events
	private class Listener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				controllGame.keyPressedLeft();
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				controllGame.keyPressedRight();
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				controllGame.keyPressedUp();
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				controllGame.keyPressedSpace();
		}
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_UP)			
				controllGame.keyReleasedUp();
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)			
				controllGame.keyReleasedLeft();
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)			
				controllGame.keyReleasedRight();
			if(e.getKeyCode() == KeyEvent.VK_SPACE)			
				controllGame.keyReleasedSpace();
		}		
	}


	// only start the animation once the JPanel has been added to the JFrame
	public void addNotify() { 
		super.addNotify();   // creates the peer
		startGame();    // start the thread
	}
	public void startGame() {
		(new Thread(this)).start();
	}


	//draw game over screen.
	private void gameOverMessage(Graphics g) {	
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, PANELWIDTH, PANELHEIGHT);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.setColor(Color.WHITE);
		g.drawString("Game Over", PANELWIDTH/2 - 90, PANELHEIGHT/2);
	}


}
