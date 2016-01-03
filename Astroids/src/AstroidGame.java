import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;

//controller of the game
public class AstroidGame {

	private SpaceshipModel spaceship; 
	private StackBullets stack;
	private AstroidModel[] astroidArray;
	private BufferedImage spaceshipImg;
	private BufferedImage bulletImg;
	private BufferedImage astroidImg1;
	private BufferedImage astroidImg2;
	private BufferedImage astroidImg3;
	private boolean isGameOver; // if true the game is over.
	private int whidthScreen, heightScreen;
	private int numAstroid = 4;
	private int angle = 5;
	private Random rand;
	private int randomXLocation;
	private int randomYLocation;
	private int randomSizeAngels;
	private int lives = 3;
	public AstroidGame(int w, int h) {

		whidthScreen = w;
		heightScreen = h;
		isGameOver = false;
		//read models image
		try 
		{	
			spaceshipImg = ImageIO.read(new File("src/resources/spaceship.png"));
			bulletImg =  ImageIO.read(new File("src/resources/bullet.png"));
			astroidImg1 = ImageIO.read(new File("src/resources/astroid1.png"));
			astroidImg2 = ImageIO.read(new File("src/resources/astroid2.png"));
			astroidImg3 = ImageIO.read(new File("src/resources/astroid3.png"));
		} 
		catch (IOException e) 
		{
			System.out.println("ERROR - can't read image");
		}
		//create models (spaceship, astroid and stack bullets)
		spaceship = new SpaceshipModel((whidthScreen)/2, (heightScreen)/2,
				whidthScreen, heightScreen, 5, angle, spaceshipImg);

		stack = new StackBullets(4, whidthScreen, heightScreen, 8, 250, bulletImg, spaceship);


		astroidArray = new AstroidModel[numAstroid + numAstroid*2 + numAstroid*4];
		rand = new Random();
		for (int i = 0; i < astroidArray.length; i++) {
			randomXLocation = rand.nextInt(whidthScreen - astroidImg1.getWidth()) + astroidImg1.getWidth()/2;
			randomYLocation = rand.nextInt(heightScreen - astroidImg1.getHeight()) +astroidImg1.getHeight()/2;

			randomSizeAngels = rand.nextInt(360/5);

			if(Math.abs(randomXLocation-(whidthScreen/2)) < astroidImg1.getWidth())
				randomXLocation += astroidImg1.getWidth();
			if(Math.abs(randomYLocation-(heightScreen/2)) < astroidImg1.getHeight())
				randomYLocation += astroidImg1.getHeight();

			if(i<numAstroid) {
				astroidArray[i] = new AstroidModel(randomXLocation, randomYLocation,
						whidthScreen, heightScreen, 2, angle, randomSizeAngels, astroidImg1);
				astroidArray[i].setVisible(true);
			}
			else if(i<numAstroid*2 + numAstroid) {
				astroidArray[i] = new AstroidModel(0, 0, whidthScreen, heightScreen, 3, angle, randomSizeAngels, astroidImg2);
			}
			else {
				astroidArray[i] = new AstroidModel(0, 0, whidthScreen, heightScreen, 5, angle, randomSizeAngels, astroidImg3);
			}
		}

	}

	//draw the model
	public void drawModel(Graphics dbg){
		Graphics2D g = (Graphics2D) dbg;
		for (int i = 0; i < lives; i++) {
			g.drawImage(spaceshipImg, spaceshipImg.getWidth()*i+10*(i+1), 0, null);	
		}
		
		
		spaceship.drawModel(dbg);	
		stack.drawStack(dbg);
		for (int i = 0; i < astroidArray.length; i++) {
			astroidArray[i].drawModel(dbg);
			//g.draw(astroidArray[i].getBgetBoundingBox());
		}
		//g.draw(spaceship.getBgetBoundingBox());
	}


	public void updateModel() {	
		if(lives <= 0)
		{
			isGameOver = true;
		}
		spaceship.updateModel();
		stack.updateStack();
		for (int i = 0; i < astroidArray.length; i++) {
			astroidArray[i].updateModel();
		}

		BulletModel[] stackBullet = stack.getStack();
		for (int i = 0; i < astroidArray.length; i++) {
			//intersects
			for (int j = 0; j < stackBullet.length; j++) {
				if(stackBullet[j].isVisible()) {
					//bullet hit astroid 
					if(stackBullet[j].getBgetBoundingBox().intersects(astroidArray[i].getBoundingBox())&&astroidArray[i].isVisible()) {
						if(i < numAstroid + 2*numAstroid && astroidArray[i].isVisible())
						{
							//create the sun of astroid
							if(!astroidArray[i*2 + numAstroid].isVisible()) {
								astroidArray[i*2 + numAstroid].setLocationX(astroidArray[i].getLocationX());
								astroidArray[i*2 + numAstroid].setLocationY(astroidArray[i].getLocationY());
								astroidArray[i*2 + numAstroid].setVisible(true);								
							}
							if(!astroidArray[i*2 + numAstroid + 1].isVisible()) {								
								astroidArray[i*2 + numAstroid + 1].setLocationX(astroidArray[i].getLocationX());
								astroidArray[i*2 + numAstroid + 1].setLocationY(astroidArray[i].getLocationY());
								astroidArray[i*2 + numAstroid + 1].setVisible(true);								
							}	
						}
						astroidArray[i].setVisible(false);
						stackBullet[j].setVisible(false);						
					}
				}
			}
			
			 //tha asteroid hit spaceship
			if(spaceship.getBgetBoundingBox().intersects(astroidArray[i].getBoundingBox())&&astroidArray[i].isVisible()) {
				lives--;
				astroidArray[i].setVisible(false);
				spaceship.setLocationX(whidthScreen/2);
				spaceship.setLocationY(heightScreen/2);
				spaceship.setSizeAngles(0);
				if(i < numAstroid + 2*numAstroid)
				{
					if(!astroidArray[i*2 + numAstroid].isVisible()) {
						astroidArray[i*2 + numAstroid].setLocationX(astroidArray[i].getLocationX());
						astroidArray[i*2 + numAstroid].setLocationY(astroidArray[i].getLocationY());
						astroidArray[i*2 + numAstroid].setVisible(true);
					}
					if(!astroidArray[i*2 + numAstroid + 1].isVisible()) {								
						astroidArray[i*2 + numAstroid + 1].setLocationX(astroidArray[i].getLocationX());
						astroidArray[i*2 + numAstroid + 1].setLocationY(astroidArray[i].getLocationY());
						astroidArray[i*2 + numAstroid + 1].setVisible(true);					
					}	
				}
			}
		}

	}


	public boolean isGameOver() {
		return isGameOver;
	}


	//events:
	public void keyPressedUp() {
		spaceship.move();
	}

	public void keyPressedLeft() {
		spaceship.rotateLeft();
	}

	public void keyPressedRight() {
		spaceship.rotateRight();
	}

	public void keyReleasedUp() {
		spaceship.stopMove();
	}

	public void keyPressedSpace() {
		stack.shooting();
	}

	public void keyReleasedLeft() {
		spaceship.stopRotateLeft();
	}

	public void keyReleasedRight() {
		spaceship.stopRotateRight();
	}

	public void keyReleasedSpace() {
		stack.stopShooting();
	}



}
