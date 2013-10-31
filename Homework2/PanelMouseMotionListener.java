import java.awt.event.MouseEvent;
 import java.awt.event.MouseMotionListener;

 
public class PanelMouseMotionListener implements MouseMotionListener {
	ImageDrawingApplet imageDrawingApplet;
 
	public PanelMouseMotionListener(ImageDrawingApplet imageDrawingApplet) 
	{
		this.imageDrawingApplet = imageDrawingApplet;
 	}
   @Override
	public void mouseMoved(MouseEvent mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		imageDrawingApplet.mouseLocation.setText(x+" -- "+y);
 	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
 }

 