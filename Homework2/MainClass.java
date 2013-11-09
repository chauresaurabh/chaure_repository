import java.awt.*;
 
import javax.swing.*;

public class MainClass {
	public static void main(String[] args) {
		
    		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e)
		{ 
			 System.out.println("Exception while setting Look and feel for the assignment, will use defaults");
		}
		try 
		{
			//Load the class here itself than later on each request.
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		JFrame mainFrame = new JFrame("Saurabh Chaure - 9277945110");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		JApplet imageDrawingApplet = new ImageDrawingApplet("F:/map.jpg");
		imageDrawingApplet.init();
		imageDrawingApplet.start();
		mainFrame.add("Center", imageDrawingApplet);
		mainFrame.pack();
		mainFrame.setSize(new Dimension(1000,700));	 
		mainFrame.setVisible(true);
	}
}
