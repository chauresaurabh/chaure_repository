import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MapComponent extends Component 
{
	File mapFile = new File("F:/map.jpg");	// this will be some dynamic path
	BufferedImage mapImage = null;

	ArrayList <Integer> asCoordinates 	= 	null;
	ArrayList <Integer> buildingCoordinates 	= 	null;
	ArrayList <Integer> studentCoordinates 	= 	null;

	ArrayList < ArrayList <Integer> > buildingList = null;
	ArrayList < ArrayList <Integer> > pointRegionBuildingList = null;

	public int  imageHeight, imageWidth;

	public boolean drawWholeRegion = false;
	public boolean drawPointLocationOnClick = false;
	public boolean drawPointRegion = false;

	int pointX = -1, pointY = -1;


	public ArrayList<ArrayList<Integer>> getPointRegionBuildingList() {
		return pointRegionBuildingList;
	}

	public void setPointRegionBuildingList(
			ArrayList<ArrayList<Integer>> pointRegionBuildingList) {
		this.pointRegionBuildingList = pointRegionBuildingList;
	}

	public int getPointX() {
		return pointX;
	}

	public void setPointX(int pointX) {
		this.pointX = pointX;
	}

	public int getPointY() {
		return pointY;
	}

	public void setPointY(int pointY) {
		this.pointY = pointY;
	}

	public boolean isDrawPointLocationOnClicked() {
		return drawPointLocationOnClick;
	}

	public void setDrawPointLocationOnClicked(boolean drawPointLocationOnClick) {
		this.drawPointLocationOnClick = drawPointLocationOnClick;
	}

	public ArrayList<ArrayList<Integer>> getBuildingList() {
		return buildingList;
	}

	public void setBuildingList(ArrayList<ArrayList<Integer>> buildingList) {
		this.buildingList = buildingList;
	}


	public boolean isDrawPointRegion() {
		return drawPointRegion;
	}

	public void setDrawPointRegion(boolean drawPointRegion) {
		this.drawPointRegion = drawPointRegion;
	}

	public boolean isDrawWholeRegion() {
		return drawWholeRegion;
	}

	public void setDrawWholeRegion(boolean drawWholeRegion) {
		this.drawWholeRegion = drawWholeRegion;
	}

	public ArrayList<Integer> getAsCoordinates() {
		return asCoordinates;
	}

	public void setAsCoordinates(ArrayList<Integer> asCoordinates) {
		this.asCoordinates = asCoordinates;
	}

	public ArrayList<Integer> getBuildingCoordinates() {
		return buildingCoordinates;
	}

	public void setBuildingCoordinates(ArrayList<Integer> buildingCoordinates) {
		this.buildingCoordinates = buildingCoordinates;
	}

	public ArrayList<Integer> getStudentCoordinates() {
		return studentCoordinates;
	}

	public void setStudentCoordinates(ArrayList<Integer> studentCoordinates) {
		this.studentCoordinates = studentCoordinates;
	}

	public MapComponent()
	{
		try
		{
			mapImage = ImageIO.read(mapFile);
			imageHeight = mapImage.getHeight();
			imageWidth = mapImage.getWidth();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void clearViaRadio(){
		repaint();
	}

	public void paint(Graphics g) 
	{
		Graphics2D map 			= 	(Graphics2D) g;
		map.drawImage(mapImage, 0, 0, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight, null);
		if(drawWholeRegion){
			drawWholeRegion(map);

			buildingList = null;
			drawWholeRegion = false;
			asCoordinates 	= 	null;
			buildingCoordinates 	= 	null;
			studentCoordinates 	= 	null;
		}
		if(drawPointLocationOnClick){	// i.e mouse was clicked and need to draw a point with circle.
			drawPointLocation(map);
		}
		if(drawPointRegion){	// i.e point query is pressed .. so need to draw all the other things.
			drawPointRegion = false;
			drawPointLocationOnClick = false;
			setPointX(-1);
			setPointY(-1);
			drawPointRegion(map);
		}
	}

	// this method is just to draw the a square and a circle of a point clicked on the map.
	public void drawPointRegion(Graphics map){

		if(pointRegionBuildingList!=null && pointRegionBuildingList.size()>0 ){
			for (int j = 0; j < pointRegionBuildingList.size(); j++) {
				// in this list , the first building is the nearest one.
				if(j==0){
					map.setColor(Color.YELLOW);
				}
				else{
					map.setColor(Color.GREEN);
				}
				ArrayList <Integer> buildingCoordinates  = pointRegionBuildingList.get(j);

				int size = buildingCoordinates.size();
				for (int i = 0; i <= ( size - 4 ); i+=2 ) {
					map.drawLine( buildingCoordinates.get(i), buildingCoordinates.get(i+1),
							buildingCoordinates.get(i+2), buildingCoordinates.get(i+3) );
				}
				map.drawLine( buildingCoordinates.get(size-2), buildingCoordinates.get(size-1),
						buildingCoordinates.get(0), buildingCoordinates.get(1) );
			}
		}
	}

	// this method is just to draw the a square and a circle of a point clicked on the map.
	public void drawPointLocation(Graphics map){
		map.setColor(Color.RED);
		map.fillRect(pointX-3 , pointY-3, 5, 5);
		map.drawOval(pointX-50, pointY-50, 100, 100 );
	}
	public void drawWholeRegion(Graphics map){
		if(asCoordinates!=null && asCoordinates.size()>0){
			map.setColor(Color.RED);
			if(asCoordinates!=null && asCoordinates.size()>0 ){

				for (int i = 0; i < asCoordinates.size(); i+=3) {
					//need to check below code again
					map.fillRect(asCoordinates.get(i)-7 , asCoordinates.get(i+1)-7, 15, 15);
					map.drawOval(asCoordinates.get(i)-asCoordinates.get(i+2)/2, asCoordinates.get(i+1)-asCoordinates.get(i+2)/2,
							asCoordinates.get(i+2), asCoordinates.get(i+2) );
				}
			}
		}
		if(buildingList!=null && buildingList.size()>0 ){
			map.setColor(Color.YELLOW);
			for (int j = 0; j < buildingList.size(); j++) {

				buildingCoordinates = buildingList.get(j);

				int size = buildingCoordinates.size();
				for (int i = 0; i <= ( size - 4 ); i+=2 ) {
					map.drawLine( buildingCoordinates.get(i), buildingCoordinates.get(i+1),
							buildingCoordinates.get(i+2), buildingCoordinates.get(i+3) );
				}
				map.drawLine( buildingCoordinates.get(size-2), buildingCoordinates.get(size-1),
						buildingCoordinates.get(0), buildingCoordinates.get(1) );
			}
		}
		if(studentCoordinates!=null && studentCoordinates.size()>0 ){
			map.setColor(Color.GREEN);
			if(studentCoordinates!=null && studentCoordinates.size()>0 ){

				for (int i = 0; i < studentCoordinates.size(); i+=2) {
					map.fillRect(studentCoordinates.get(i)-5, studentCoordinates.get(i+1)-5, 10, 10);
				}
			}
		}
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(820, 580);
	}
}