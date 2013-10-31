import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class PanelMouseEventsListener implements MouseInputListener{
	ImageDrawingApplet imageDrawingApplet;
	MapComponent mapComponent;

	public PanelMouseEventsListener(ImageDrawingApplet imageDrawingApplet, MapComponent mapComponent) {
		this.imageDrawingApplet = imageDrawingApplet;
		this.mapComponent = mapComponent;
	}
	@Override
	public void mouseClicked(MouseEvent mouse) {
		 if( imageDrawingApplet.pointQueryRadioButton.isSelected() ){
			 	int x = mouse.getX();
			 	int y = mouse.getY();
			 	mapComponent.setPointX(x);
			 	mapComponent.setPointY(y);
			 	mapComponent.setDrawPointLocationOnClicked(true);
				mapComponent.repaint();

		 }
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		imageDrawingApplet.mouseLocation.setText(x+" -- "+y); 
	}

}
