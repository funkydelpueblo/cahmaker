/** Sets up the application frame
 * @author funkydelpueblo
 * @version 0.1
 */

import javax.swing.JFrame;


public class CAHMaker {
	//
	// Create JFrame
	//
	
	public static void main(String[] args){		
		View view = new View();
		
		JFrame frame = new JFrame("CAH Maker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(view.makeMenuBar());
		frame.add(view.makePanel());
		frame.setMinimumSize(new java.awt.Dimension(300, 400));
		frame.pack();
		frame.setVisible(true);
		frame.pack();
	}
}
