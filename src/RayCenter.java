import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class RayCenter {
	
	int x;
	int y;
	double angle;
	
	ArrayList<Point2D.Double> perimeterPoints = new ArrayList<>();
	ArrayList<Point2D.Double> closestIntersection = new ArrayList<>();
	
	public RayCenter(int x, int y, double angle)
	{
		this.x = x;
		this.y = y;
		this.angle = angle;
		
		for(double ang = 0; ang < Math.PI / 3; ang = ang + Math.PI / 200) 
		{
			// Cheat and make these as long as possible for now...
			perimeterPoints.add(new Point2D.Double(x + 1000 * Math.cos(angle + ang), y + 1000 * Math.sin(angle + ang)));
		}
	
	}
	
	public void calculateIntersections(ArrayList<Boundary> boundariesList)
	{
		closestIntersection = new ArrayList<>();
		// For each ray
		for(int i = 0; i < perimeterPoints.size(); i++) {
			Boundary tempBoundary = new Boundary(x, y, perimeterPoints.get(i).getX(), perimeterPoints.get(i).getY());
			ArrayList<Point2D.Double> intersectingPoints = new ArrayList<>();
			// For each boundary
			for(int j = 0; j < boundariesList.size(); j++)
			{
				Point2D.Double intersectionPoint = tempBoundary.pointOfIntersection(boundariesList.get(j));
				if(intersectionPoint.getX() > 0 && intersectionPoint.getY() > 0) {
					intersectingPoints.add(new Point2D.Double(intersectionPoint.getX(), intersectionPoint.getY()));
				}
			}
			
			// Find the closest point of intersection
			if(intersectingPoints.size() > 0) {
				Point2D.Double closest = intersectingPoints.get(0);
				double closestDistance = Math.sqrt(Math.pow(x - closest.getX(), 2) + Math.pow(y - closest.getY(), 2));
				for(Point2D.Double anInt : intersectingPoints)
				{
					double newDistance = Math.sqrt(Math.pow(x - anInt.getX(), 2) + Math.pow(y - anInt.getY(), 2));
					if(newDistance <= closestDistance)
					{
						closest = anInt;
						closestDistance = newDistance;							
					}
				}
				closestIntersection.add(closest);
			}
			
			if(intersectingPoints.isEmpty()) 
			{
				closestIntersection.add(perimeterPoints.get(i));
			}
			
		}
	}
	
	public double computeDistance(double x1, double y1, double x2, double y2)
	{
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	public double map(double s, double a1, double a2, double b1, double b2)
	{
		return b1 + ((s-a1) * (b2 - b1))/(a2 - a1);
	}
	
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		for(Point2D.Double aPoint : closestIntersection)
		{
			double x2 = aPoint.getX();
			double y2 = aPoint.getY();
			Line2D.Double rayPart = new Line2D.Double(x, y, x2, y2);
			Rectangle2D.Double rect = new Rectangle2D.Double(x2 - 10 / 2, y2 - 10 / 2, 10, 10);
			g2.fill(rect);
			g2.draw(rayPart);
		}
		
		if(closestIntersection.size() == 0){
			for(Point2D.Double aPoint : perimeterPoints)
			{
				double x2 = aPoint.getX();
				double y2 = aPoint.getY();
				Line2D.Double rayPart = new Line2D.Double(x, y, x2, y2);
				g2.draw(rayPart);
			}
		}
		
		// Draw the 3D represention of the casting... based on distances
		g2.setColor(Color.white);
		Rectangle2D.Double clearRect = new Rectangle2D.Double(0, 700, 1000, 300);
		g2.fill(clearRect);
		
		int numColumns = closestIntersection.size();
		int columnX = 0;
		
		for(Point2D.Double aPoint : closestIntersection) {
			// Calculate the distance to the point
			int columnWidth = 1000 / numColumns;
			double d = computeDistance(x, y, aPoint.getX(), aPoint.getY());
			
			if(d < 500) {
				double height = map(d, 0, 500, 300, 0);
				Rectangle2D.Double someRect = new Rectangle2D.Double(columnX, 850 - height / 2, columnWidth, height);
				int color = (int) map(d, 0, 500, 0, 255);
				g2.setColor(new Color(color, color, color));
				g2.fill(someRect);
			}
			
			columnX += columnWidth;
			
		}
	}

}
