import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;


public class BulletModel extends Model{

	private final int DISTANCE_SHOT;
	private boolean isVisible;
	private SpaceshipModel spaceship;
	private int startX = 0, startY = 0;

	public BulletModel(int w, int h, int deltaMove,int distanceShot, BufferedImage image, SpaceshipModel spaceship) {
		super(0, 0, w, h, deltaMove, spaceship.angle, image);
		DISTANCE_SHOT = distanceShot;
		this.spaceship = spaceship;
		isVisible = false;
	}


	public void shooting() {
		if(!isVisible) {
			isVisible = true;
			locationX = spaceship.locationX;
			startX = spaceship.locationX;
			locationY = spaceship.locationY;
			startY = spaceship.locationY;
			sizeAngles = spaceship.sizeAngles;
			angle = spaceship.angle;
		}

	}


	@Override
	public void updateModel() {
		if(isVisible && Point2D.distance(startX, startY, locationX, locationY) <= DISTANCE_SHOT) {

			if( locationX > panelWidth) {
				startX -= Point2D.distance(startX, startY, locationX, locationY) + panelWidth;
				locationX = 0;
			}
			else if( locationX  < 0 ) {
				startX += Point2D.distance(startX, startY, locationX, locationY) + panelWidth;
				locationX = panelWidth;
			}
			if(locationY  > panelHeight) {
				startY -= Point2D.distance(startX, startY, locationX, locationY) + panelHeight;
				locationY =  0;
			}
			else if( locationY  < 0 ) {
				startY += Point2D.distance(startX, startY, locationX, locationY) + panelHeight;
				locationY = (panelHeight );
			}

			locationX += (int)(deltaMove * Math.cos(Math.toRadians(angle*sizeAngles-90)));
			locationY += (int)(deltaMove * Math.sin(Math.toRadians(angle*sizeAngles-90)));
		}else {
			isVisible = false;
			locationX = spaceship.locationX;
			startX = spaceship.locationX;
			locationY = spaceship.locationY;
			startY = spaceship.locationY;
			sizeAngles = spaceship.sizeAngles;
			angle = spaceship.angle;
		}
	}


	//draw image on Grap hics object
	@Override
	public void drawModel(Graphics g)
	{
		if(isVisible) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bufferedImage, rotatingModel(), null);
		}
	}


	public AffineTransform rotatingModel() {

		//create the transform.
		AffineTransform at = new AffineTransform();
		at.translate(locationX, locationY);
		at.rotate(Math.toRadians(angle*sizeAngles));
		at.translate(-imageWidth/2 , -imageHeight - spaceship.imageHeight/2);

		return at;
	}

	public Shape getBgetBoundingBox() {
		Rectangle2D BoundingRect = new Rectangle2D.Double(0, 0, imageWidth, imageWidth);

		AffineTransform at = new AffineTransform();
		at.translate(locationX, locationY);
		at.rotate(Math.toRadians(angle*sizeAngles));
		at.translate(-imageWidth/2 , -imageHeight - spaceship.imageHeight/2);

		Shape rotatedRect = at.createTransformedShape(BoundingRect);
		return rotatedRect;
	}


	public boolean isVisible() {
		return isVisible;
	}


	public void setVisible(boolean isVisible) {
		//
		locationX = spaceship.locationX;
		startX = spaceship.locationX;
		locationY = spaceship.locationY;
		startY = spaceship.locationY;
		sizeAngles = spaceship.sizeAngles;
		angle = spaceship.angle;
		this.isVisible = isVisible;
	}


	public int getStartX() {
		return startX;
	}


	public int getStartY() {
		return startY;
	}



}
