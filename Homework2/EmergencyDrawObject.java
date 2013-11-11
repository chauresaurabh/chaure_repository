import java.awt.Color;
import java.util.ArrayList;


public class EmergencyDrawObject {

	Color color;
	int asX, asY, asRadius;
	ArrayList < Integer > studentCordinates = new ArrayList<Integer>();
	
	
	 
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public int getAsX() {
		return asX;
	}
	public void setAsX(int asX) {
		this.asX = asX;
	}
	public int getAsY() {
		return asY;
	}
	public void setAsY(int asY) {
		this.asY = asY;
	}
	public int getAsRadius() {
		return asRadius;
	}
	public void setAsRadius(int asRadius) {
		this.asRadius = asRadius;
	}
	public ArrayList<Integer> getStudentCordinate() {
		return studentCordinates;
	}
	public void setStudentCordinate(ArrayList<Integer> studentCordinates) {
		this.studentCordinates = studentCordinates;
	}
	
	
	
	
}
