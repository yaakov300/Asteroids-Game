import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class StackBullets {
	private BulletModel[] stack;
	private int numBullets;
	private SpaceshipModel spaceship;
	private boolean isShoting; 
	private boolean isStackEmpty;
	
	public StackBullets(int numBullets,int w, int h, int deltaMove, int distanceShot,
			BufferedImage image ,SpaceshipModel spaceship) {

		this.numBullets = numBullets;
		this.spaceship = spaceship;
		stack =  new BulletModel[this.numBullets];
		isShoting = false;
		isStackEmpty = false;
		for (int i = 0; i < this.numBullets; i++) {
			stack[i] = new BulletModel(w, h, deltaMove, distanceShot, image, spaceship);
		}

	}


	public void updateStack() {
		for (int i = 0; i < numBullets; i++) {
			stack[i].updateModel();
		}
		if(isShoting && !isStackEmpty) {
			int back;
			for (int i = 0; i < numBullets; i++) {

				if(i == 0)
					back = numBullets - 1;
				else
					back = i - 1;

				if(Point2D.distance(stack[back].getStartX(), stack[back].getStartY(),
						stack[back].getLocationX(), stack[back].getLocationY()) >= 50 || allTheBulletsInTheStack()){
					stack[i].shooting();
					
				}
			}
			isStackEmpty = true; 
		}

	}

	public void drawStack(Graphics g)
	{
		for (int i = 0; i < numBullets; i++) {
			stack[i].drawModel(g);
		}
	}

	public void shooting() {
		isShoting = true;
	}
	
	public void stopShooting() {
		isShoting = false;
		isStackEmpty = false;
	}

	public boolean allTheBulletsInTheStack() {
		for(int i = 0; i < numBullets; i++) {
			if(stack[i].isVisible() == true)
				return false;
		}
		return true;
	}


	public BulletModel[] getStack() {
		return stack;
	}


	public void setStackEmpty(boolean isStackEmpty) {
		this.isStackEmpty = isStackEmpty;
	}


}
