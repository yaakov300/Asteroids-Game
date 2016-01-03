
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Model{
	protected int panelWidth, panelHeight; // panel's dimensions.
	protected BufferedImage bufferedImage; // image buffer.
	protected int imageWidth, imageHeight; // image dimensions.
	protected int locationX, locationY, deltaMove; //image location and movement
	protected int sizeAngles = 0, angle;
	

	public Model(int x, int y, int w, int h, int deltaMove, int angle, BufferedImage img) 
	{	
		//initiate dimensions, location and movement
		panelWidth = w;
		panelHeight = h;
		locationX = x;
		locationY = y;
		this.deltaMove = deltaMove;
		this.angle = angle;
		bufferedImage = img;
		if(bufferedImage != null)
		{
			imageWidth = bufferedImage.getWidth(null);
			imageHeight = bufferedImage.getHeight(null);
		}
		else
			setImageDimensions();     
	}

	//default dimensions.
	public void setImageDimensions()
	{
		imageWidth = 20;
		imageHeight = 20;
	}

	//getters and setters
	public Rectangle getBoundingBox()
	{
		//return bounding of the shape
		return new Rectangle(getLocationX(), getLocationY(), imageWidth, imageHeight);
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}

	
	public int getImageWidth() { 
		return imageWidth;
	}

	public int getImageHeight()
	{
		return imageHeight;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}


	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}



	//draw image on Graphics object
	public void drawModel(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bufferedImage, locationX, locationY, null);
	}

	//update location
	public void updateModel()
	{
		locationX += (int)(deltaMove * Math.cos(Math.toRadians(angle*sizeAngles-90)));
		locationY += (int)(deltaMove * Math.sin(Math.toRadians(angle*sizeAngles-90)));
	}

	public int getSizeAngles() {
		return sizeAngles;
	}

	public void setSizeAngles(int sizeAngles) {
		this.sizeAngles = sizeAngles;
	}

}

