import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.Ellipse2D;

import java.awt.image.BufferedImage;


public class AstroidModel extends Model {

	private boolean isVisible;

	public AstroidModel(int x, int y, int w, int h, int deltaMove, int angle, int sizeAngles, BufferedImage img) {
		super(x, y, w, h, deltaMove, angle, img);
		this.sizeAngles = sizeAngles;
		isVisible = false;
	}



	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	//draw image on Graphics object
	@Override
	public void drawModel(Graphics g)
	{
		if(isVisible) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.drawImage(bufferedImage, locationX, locationY, null);
		}
	}

	//update location
	@Override
	public void updateModel()
	{	
		if(isVisible) {

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
	}


	public Ellipse2D getBgetBoundingBox() {
		if(isVisible)
			return new Ellipse2D.Double(locationX, locationY, imageWidth, imageHeight);
		else
			return new Ellipse2D.Double(-panelWidth, -panelHeight, imageWidth, imageHeight);
	}	
}
