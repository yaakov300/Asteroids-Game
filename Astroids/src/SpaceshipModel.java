import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SpaceshipModel extends Model{

	private boolean isMoving, isRotateRight, isRotateLeft;
	private static final int BRAKING_DISTANCE = 22;
	private int braking; 

	public SpaceshipModel(int x, int y, int w, int h, int deltaMove, int angle, BufferedImage image)
	{ 
		super(x, y, w, h, deltaMove, angle, image);
		isMoving = isRotateRight = isRotateLeft =  false;
		braking = BRAKING_DISTANCE; 
	}

	@Override
	public void updateModel()
	{	
		if(isMoving) {	

			if( (locationX - imageWidth / 2) > panelWidth)
				locationX = -imageWidth/2;
			else if( (locationX + imageWidth / 2) < 0 ) 
				locationX = (panelWidth - imageWidth/ 2);

			if( (locationY - imageHeight / 4) > panelHeight)
				locationY =  -imageHeight/ 4;
			else if( (locationY + imageHeight / 4) < 0 )
				locationY = (panelHeight );

			locationX += (int)(deltaMove * Math.cos(Math.toRadians(angle*sizeAngles-90)));
			locationY += (int)(deltaMove * Math.sin(Math.toRadians(angle*sizeAngles-90)));
		}


		if(isRotateLeft) {
			if((sizeAngles - 1) * angle == -360)
				sizeAngles = 0;
			else
				sizeAngles--;
		}

		if (isRotateRight) {
			if((sizeAngles + 1) * angle == 360)
				sizeAngles = 0;
			else
				sizeAngles++;
		}

	}


	//draw image on Graphics object
	@Override
	public void drawModel(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bufferedImage, rotatingModel(), null);
	}

	
	public AffineTransform rotatingModel() {

		//create the transform.
		AffineTransform at = new AffineTransform(); 
		at.translate(locationX, locationY);
		at.rotate(Math.toRadians(angle*sizeAngles));
		at.translate(-imageWidth/2, -imageHeight/2);
		
		return at;
	}
	
	
	public Shape getBgetBoundingBox() {
		/*Rectangle2D BoundingRect = new Rectangle2D.Double(0, 0, imageWidth, imageHeight);
		
		AffineTransform at = new AffineTransform(); 
		at.translate(locationX, locationY);
		at.rotate(Math.toRadians(angle*sizeAngles));
		at.translate(-imageWidth/2, -imageHeight/2);
		
		Shape rotatedRect = at.createTransformedShape(BoundingRect);
		return rotatedRect;*/
		
		Ellipse2D elilipse =  new Ellipse2D.Double(0, 0, imageWidth, imageHeight);
		AffineTransform at = new AffineTransform(); 
		at.translate(locationX, locationY);
		at.rotate(Math.toRadians(angle*sizeAngles));
		 at.scale(0.5, 0.5);
		at.translate(-imageWidth/2, -imageHeight/2);
		
		Shape rotatedRect = at.createTransformedShape(elilipse);
		return rotatedRect;
	}


	public void move() {
		isMoving = true;
	}

	public void rotateLeft() {
		isRotateLeft = true;
	}

	public void rotateRight() {
		isRotateRight =true;
	}

	public void stopMove() {
		isMoving = false;
	}

	public void stopRotateLeft()
	{
		isRotateLeft = false;
	}

	public void stopRotateRight()
	{
		isRotateRight = false;
	}

}
