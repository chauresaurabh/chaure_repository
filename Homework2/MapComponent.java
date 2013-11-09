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

	ArrayList <Integer> pointRegionStudentCoordinatesList 	= 	null;
	ArrayList <Integer> pointRegionAnnouncementCoordinatesList 	= 	null;
	ArrayList <Integer> surrStudentCoordinatesList 	= 	null;

	public ArrayList <Integer> pointsRegionList  = 	new ArrayList <Integer>();

	ArrayList < ArrayList <Integer> > rangeBuildingList = null;
	ArrayList <Integer> rangeStudentCoordinates 	= 	null;
	ArrayList <Integer> rangeASCoordinates 	= 	null;

	ArrayList <Integer> surroundingASOnClickList 	= 		new ArrayList <Integer>();

	

	public ArrayList<Integer> getSurrStudentCoordinatesList() {
		return surrStudentCoordinatesList;
	}

	public void setSurrStudentCoordinatesList(
			ArrayList<Integer> surrStudentCoordinatesList) {
		this.surrStudentCoordinatesList = surrStudentCoordinatesList;
	}

	public ArrayList<Integer> getSurroundingASOnClickList() {
		return surroundingASOnClickList;
	}

	public void setSurroundingASOnClickList(
			ArrayList<Integer> surroundingASOnClickList) {
		this.surroundingASOnClickList = surroundingASOnClickList;
	}

	public ArrayList<Integer> getRangeASCoordinates() {
		return rangeASCoordinates;
	}

	public void setRangeASCoordinates(ArrayList<Integer> rangeASCoordinates) {
		this.rangeASCoordinates = rangeASCoordinates;
	}

	public ArrayList<Integer> getRangeStudentCoordinates() {
		return rangeStudentCoordinates;
	}

	public void setRangeStudentCoordinates(
			ArrayList<Integer> rangeStudentCoordinates) {
		this.rangeStudentCoordinates = rangeStudentCoordinates;
	}

	public ArrayList<ArrayList<Integer>> getRangeBuildingList() {
		return rangeBuildingList;
	}

	public void setRangeBuildingList(ArrayList<ArrayList<Integer>> rangeBuildingList) {
		this.rangeBuildingList = rangeBuildingList;
	}

	public int  imageHeight, imageWidth;

	public boolean drawWholeRegion = false;
	public boolean drawPointLocationOnClick = false;
	public boolean drawPointRegion = false;
	public boolean drawRangePointsPoligon = false;
	public boolean drawRangeEverything = false;
	public boolean drawSurrStudentsPointOnClick = false;

	public boolean drawSurrStudentsOnSubmit = false;

	int pointX = -1, pointY = -1;
	int surrX = -1, surrY = -1;


	
	public boolean isDrawSurrStudentsOnSubmit() {
		return drawSurrStudentsOnSubmit;
	}

	public void setDrawSurrStudentsOnSubmit(boolean drawSurrStudentsOnSubmit) {
		this.drawSurrStudentsOnSubmit = drawSurrStudentsOnSubmit;
	}

	public boolean isDrawSurrStudentsPointOnClick() {
		return drawSurrStudentsPointOnClick;
	}

	public void setDrawSurrStudentsPointOnClick(boolean drawSurrStudentsPointOnClick) {
		this.drawSurrStudentsPointOnClick = drawSurrStudentsPointOnClick;
	}

	public int getSurrX() {
		return surrX;
	}

	public void setSurrX(int surrX) {
		this.surrX = surrX;
	}

	public int getSurrY() {
		return surrY;
	}

	public void setSurrY(int surrY) {
		this.surrY = surrY;
	}

	public boolean isDrawRangeEverything() {
		return drawRangeEverything;
	}

	public void setDrawRangeEverything(boolean drawRangeEverything) {
		this.drawRangeEverything = drawRangeEverything;
	}

	public ArrayList<Integer> getPointsRegionList() {
		return pointsRegionList;
	}

	public void setPointsRegionList(ArrayList<Integer> pointsRegionList) {
		this.pointsRegionList.clear();
		this.pointsRegionList.addAll(pointsRegionList);
	}

	public boolean isDrawRangePointsPoligon() {
		return drawRangePointsPoligon;
	}

	public void setDrawRangePointsPoligon(boolean drawRangeRegionPointsPoligon) {
		this.drawRangePointsPoligon = drawRangeRegionPointsPoligon;
	}

	public ArrayList<Integer> getPointRegionAnnouncementCoordinatesList() {
		return pointRegionAnnouncementCoordinatesList;
	}

	public void setPointRegionAnnouncementCoordinatesList(
			ArrayList<Integer> pointRegionAnnouncementCoordinatesList) {
		this.pointRegionAnnouncementCoordinatesList = pointRegionAnnouncementCoordinatesList;
	}

	public ArrayList<Integer> getPointRegionStudentCoordinatesList() {
		return pointRegionStudentCoordinatesList;
	}

	public void setPointRegionStudentCoordinatesList(
			ArrayList<Integer> pointRegionStudentCoordinatesList) {
		this.pointRegionStudentCoordinatesList = pointRegionStudentCoordinatesList;
	}

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
		if(drawRangePointsPoligon){	// i.e right click mouse .. draw poligon.
			drawRangePointsPoligon(map);
			//pointsRegionList.clear();
			drawRangePointsPoligon = false;
		}
		if(drawPointRegion){	// i.e point query is pressed .. so need to draw all the other things.
			drawPointRegion = false;
			drawPointLocationOnClick = false;
			//setPointX(-1);
			//setPointY(-1);
			drawPointRegion(map);
		}

		if(drawRangeEverything){
			drawRangePointsPoligon(map);
			drawRangeEverything(g);
		}

		if(drawSurrStudentsPointOnClick){
			drawSurrStudentsPoint(g);
		}
		
		if(drawSurrStudentsOnSubmit){
			drawSurrStudentsOnSubmit(g);
		}
	}

	public void drawSurrStudentsOnSubmit( Graphics map ){
		
		if(surrStudentCoordinatesList!=null && surrStudentCoordinatesList.size()>0 ){
			map.setColor(Color.GREEN);
			if(surrStudentCoordinatesList!=null && surrStudentCoordinatesList.size()>0 ){

				for (int i = 0; i < surrStudentCoordinatesList.size(); i+=2) {
					map.fillRect(surrStudentCoordinatesList.get(i)-5, surrStudentCoordinatesList.get(i+1)-5, 10, 10);
				}
				
				surrStudentCoordinatesList.clear();
			}
		}
		
	}
	public void drawSurrStudentsPoint( Graphics map ){
		map.setColor(Color.RED);
		map.drawLine(surrX, surrY, surrX, surrY);
		int i =0;
		if(surroundingASOnClickList!=null && surroundingASOnClickList.size()>0) {
			map.fillRect(surroundingASOnClickList.get(i)-7 , surroundingASOnClickList.get(i+1)-7, 15, 15);
			map.drawOval(surroundingASOnClickList.get(i)-surroundingASOnClickList.get(i+2), surroundingASOnClickList.get(i+1)-surroundingASOnClickList.get(i+2),
					surroundingASOnClickList.get(i+2)*2, surroundingASOnClickList.get(i+2)*2 );
			//surroundingASOnClickList.clear();
		}
	}
	public 	void drawRangeEverything( Graphics map ) {

		if(rangeBuildingList!=null && rangeBuildingList.size()>0 ){
			map.setColor(Color.YELLOW);
			for (int j = 0; j < rangeBuildingList.size(); j++) {

				ArrayList <Integer> buildingCoordinates = rangeBuildingList.get(j);

				int size = buildingCoordinates.size();
				for (int i = 0; i <= ( size - 4 ); i+=2 ) {
					map.drawLine( buildingCoordinates.get(i), buildingCoordinates.get(i+1),
							buildingCoordinates.get(i+2), buildingCoordinates.get(i+3) );
				}
				map.drawLine( buildingCoordinates.get(size-2), buildingCoordinates.get(size-1),
						buildingCoordinates.get(0), buildingCoordinates.get(1) );
			}
			rangeBuildingList.clear();
		}

		if(rangeStudentCoordinates!=null && rangeStudentCoordinates.size()>0 ){
			map.setColor(Color.GREEN);
			if(rangeStudentCoordinates!=null && rangeStudentCoordinates.size()>0 ){

				for (int i = 0; i < rangeStudentCoordinates.size(); i+=2) {
					map.fillRect(rangeStudentCoordinates.get(i)-5, rangeStudentCoordinates.get(i+1)-5, 10, 10);
				}
			}
		}

		if(rangeASCoordinates!=null && rangeASCoordinates.size()>0){
			map.setColor(Color.RED);

			for (int i = 0; i < rangeASCoordinates.size(); i+=3) {
				//need to check below code again
				map.fillRect(rangeASCoordinates.get(i)-7 , rangeASCoordinates.get(i+1)-7, 15, 15);
				map.drawOval(rangeASCoordinates.get(i)-rangeASCoordinates.get(i+2), rangeASCoordinates.get(i+1)-rangeASCoordinates.get(i+2),
						rangeASCoordinates.get(i+2)*2, rangeASCoordinates.get(i+2)*2 );
			}
		}

		//pointsRegionList.clear();
		rangeStudentCoordinates.clear();
		rangeASCoordinates.clear();
		rangeBuildingList.clear();

		drawRangeEverything = false;

	}

	public void drawRangePointsPoligon( Graphics map ){

		map.setColor(Color.RED);

		int size = pointsRegionList.size();
		if(size>0) {
			for (int i = 0; i <= ( size - 4 ); i+=2 ) {
				map.drawLine( pointsRegionList.get(i), pointsRegionList.get(i+1),
						pointsRegionList.get(i+2), pointsRegionList.get(i+3) );
			}
			map.drawLine( pointsRegionList.get(size-2), pointsRegionList.get(size-1),
					pointsRegionList.get(0), pointsRegionList.get(1) );
		}

	}
	// this method is just to draw the a square and a circle of a point clicked on the map.
	public void drawPointRegion(Graphics map){
		drawPointLocation(map);
		//draw buildings
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

			pointRegionBuildingList.clear();
		}

		//to draw students
		if(pointRegionStudentCoordinatesList!=null & pointRegionStudentCoordinatesList.size()>0){

			map.setColor(Color.GREEN);

			for (int i = 0; i < pointRegionStudentCoordinatesList.size(); i+=2) {
				if( i == 0){
					map.setColor(Color.YELLOW);
				}else{
					map.setColor(Color.GREEN);
				}
				map.fillRect(pointRegionStudentCoordinatesList.get(i)-5, pointRegionStudentCoordinatesList.get(i+1)-5, 10, 10);
			}

			pointRegionStudentCoordinatesList.clear();
		}

		if(pointRegionAnnouncementCoordinatesList!=null && pointRegionAnnouncementCoordinatesList.size()>0){

			for (int i = 0; i < pointRegionAnnouncementCoordinatesList.size(); i+=3) {
				if( i == 0){
					map.setColor(Color.YELLOW);
				}else{
					map.setColor(Color.GREEN);
				}
				//need to check below code again
				map.fillRect(pointRegionAnnouncementCoordinatesList.get(i)-7 , pointRegionAnnouncementCoordinatesList.get(i+1)-7, 15, 15);
				map.drawOval(pointRegionAnnouncementCoordinatesList.get(i)-pointRegionAnnouncementCoordinatesList.get(i+2),
						pointRegionAnnouncementCoordinatesList.get(i+1)-pointRegionAnnouncementCoordinatesList.get(i+2),
						pointRegionAnnouncementCoordinatesList.get(i+2)*2, pointRegionAnnouncementCoordinatesList.get(i+2)*2 );
			}

			 pointRegionAnnouncementCoordinatesList.clear(); // check if this affects
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

			for (int i = 0; i < asCoordinates.size(); i+=3) {
				//need to check below code again
				map.fillRect(asCoordinates.get(i)-7 , asCoordinates.get(i+1)-7, 15, 15);
				map.drawOval(asCoordinates.get(i)-asCoordinates.get(i+2), asCoordinates.get(i+1)-asCoordinates.get(i+2),
						asCoordinates.get(i+2)*2, asCoordinates.get(i+2)*2 );
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