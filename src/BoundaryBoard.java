import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class BoundaryBoard extends JComponent implements MouseListener, KeyListener {
	ArrayList<Boundary> boundaryList = new ArrayList<>();
	RayCenter lightSource;
	boolean secondClick = false;
	int x1;
	int y1;
	int x2;
	int y2;
	
	int rayX = 500;
	int rayY = 500;
	double rayAngle = 0;
	
	public BoundaryBoard() {
		this.addMouseListener(this);
		this.setBackground(Color.cyan);
		lightSource = new RayCenter(rayX,rayY,rayAngle);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.cyan);
		
		Graphics2D g2 = (Graphics2D) g;
		
		if(!boundaryList.isEmpty()) {
			lightSource.calculateIntersections(boundaryList);
			lightSource.draw(g2);
		}else {
			lightSource.draw(g2);
		}
		
		for(Boundary b : boundaryList) {
			b.draw(g2);
		}
		for(int i = 0; i < boundaryList.size() - 1; i++) {
			Boundary b1 = boundaryList.get(i);
			for(int j = i; j < boundaryList.size(); j++) {
				Boundary b2 = boundaryList.get(j);
				Point2D.Double intersectCoord = b1.pointOfIntersection(b2);
				if(intersectCoord.getX() > 0.0 && intersectCoord.getY() > 0) {
					g2.setColor(Color.red);
					Rectangle2D.Double intersection = new Rectangle2D.Double(intersectCoord.getX() - 10 / 2, intersectCoord.getY() - 10 / 2, 10, 10);
					g2.fill(intersection);
				}
			}
		}		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(secondClick == false) {
			x1 = arg0.getX();
			y1 = arg0.getY();
			secondClick = true;
		}else {
			x2 = arg0.getX();
			y2 = arg0.getY();
			secondClick = false;
			boundaryList.add(new Boundary(x1, y1, x2, y2));
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyChar() == 'w') {
			rayY -= 10;
			
		}else if(arg0.getKeyChar() == 'a'){
			rayX -= 10;
			
		}else if(arg0.getKeyChar() == 's') {
			rayY += 10;
			
		}else if(arg0.getKeyChar() == 'd') {
			rayX += 10;
		}else if (arg0.getKeyChar() == 'e') {
			rayAngle += Math.PI / 36;
		}else if(arg0.getKeyChar() == 'q') {
			rayAngle -= Math.PI / 36;
		}
		
		lightSource = new RayCenter(rayX, rayY, rayAngle);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
