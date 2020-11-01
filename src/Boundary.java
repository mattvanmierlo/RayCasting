import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Boundary {
	
	double x1;
	double y1;
	double x2;
	double y2;
	
	public Boundary(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
	public Point2D.Double pointOfIntersection(Boundary a){
		double x3 = a.x1;
		double y3 = a.y1;
		double x4 = a.x2;
		double y4 = a.y2;
		Point2D.Double intersection = new Point2D.Double(0,0);
		
		// Nudge the coordinates if these are close to vertical
		if(Math.abs(x2-x1) < 0.01) {
			x2 += 1;
		}
		if(Math.abs(x4-x3) < 0.01) {
			x4 += 1;
		}
		
		double xCoordInt = (- 1 * (x3 * (y4 - y3) / (x4 - x3)) + (x1 * (y2 - y1) / (x2 - x1)) + y3 - y1)/((y2 - y1) / (x2 - x1) - (y4 - y3) / (x4 - x3));
		double yCoordInt = (y2 - y1) / (x2 - x1) * (xCoordInt - x1) + y1;
		intersection.x = xCoordInt;
		intersection.y = yCoordInt;
		
		double distance1 = Math.sqrt(Math.pow(x1 - intersection.getX(), 2) + Math.pow(y1 - intersection.getY(), 2));
		double distance2 = Math.sqrt(Math.pow(x2 - intersection.getX(), 2) + Math.pow(y2 - intersection.getY(), 2));
		double distance3 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		
		double distance4 = Math.sqrt(Math.pow(x3 - intersection.getX(), 2) + Math.pow(y3 - intersection.getY(), 2));
		double distance5 = Math.sqrt(Math.pow(x4 - intersection.getX(), 2) + Math.pow(y4 - intersection.getY(), 2));
		double distance6 = Math.sqrt(Math.pow(x4 - x3, 2) + Math.pow(y4 - y3, 2));
		
		// Only return the point of intersection if it falls on the current line...
		if(x1 <= x2 && y1 <= y2) {
			boolean validBounds = x1 < intersection.getX() && intersection.getX() < x2 && y1 < intersection.getY() && intersection.getY() < y2;
			// Check to make sure we have valid distances	
			
			if(validBounds && distance1 <= distance3 && distance2 <= distance3
					&& distance4 <= distance6 && distance5 <= distance6) return intersection;
		}			
		if(x1 >= x2 && y1 <= y2) {
			boolean validBounds = x2 < intersection.getX() && intersection.getX() < x1 && y1 < intersection.getY() && intersection.getY() < y2;
			// Check to make sure we have valid distances
			
			if(validBounds && distance1 <= distance3 && distance2 <= distance3
					&& distance4 <= distance6 && distance5 <= distance6) return intersection;
		}
		if(x1 >= x2 && y1 >= y2) {
			boolean validBounds = x2 < intersection.getX() && intersection.getX() < x1 && y2 < intersection.getY() && intersection.getY() < y1;
			// Check to make sure we have valid distances
			
			if(validBounds && distance1 <= distance3 && distance2 <= distance3
					&& distance4 <= distance6 && distance5 <= distance6) return intersection;
		}
		if(x1 <= x2 && y1 >= y2) {
			boolean validBounds = x1 < intersection.getX() && intersection.getX() < x2 && y2 < intersection.getY() && intersection.getY() < y1;
			// Check to make sure we have valid distances
			
			if(validBounds && distance1 <= distance3 && distance2 <= distance3
					&& distance4 <= distance6 && distance5 <= distance6) return intersection;
		}
		
		return new Point2D.Double(0,0);		
	}

	public void draw(Graphics2D g2) {
		Line2D.Double boundary = new Line2D.Double(x1, y1, x2, y2);
		g2.setColor(Color.blue);
		g2.draw(boundary);
	}
}
