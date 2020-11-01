import java.awt.Dimension;

import javax.swing.JFrame;

public class MainProgram {
	
	// w for up
	// s for down
	// a for left
	// d for right
	// q and e for rotate

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame myFrame = new JFrame();
		myFrame.setSize(new Dimension(1000,1000));
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.add(new BoundaryBoard());
		myFrame.setVisible(true);
	}

}
