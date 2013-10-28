 import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class RadioButtonListener implements ItemListener{
	
	ImageDrawingApplet imageDrawingApplet;
	MapComponent mapComponent;
 	public RadioButtonListener(ImageDrawingApplet imageDrawingApplet, MapComponent mapComponent) {
		this.imageDrawingApplet = imageDrawingApplet;
		this.mapComponent = mapComponent;
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// need to change this if called twice 
		 if(imageDrawingApplet.wholeRegionRadioButton.isSelected()){
 			 	mapComponent.clearViaRadio();
		 }else if(imageDrawingApplet.pointQueryRadioButton.isSelected()){
			 	mapComponent.clearViaRadio();
		 }else if(imageDrawingApplet.rangeQueryRadioButton.isSelected()){
			 	mapComponent.clearViaRadio();
		 }else if(imageDrawingApplet.surroundingStudentRadioButton.isSelected()){
			 	mapComponent.clearViaRadio();
		 }else if(imageDrawingApplet.emergencyQueryRadioButton.isSelected()){
			 	mapComponent.clearViaRadio();
		 }
	}
	 
 
}
