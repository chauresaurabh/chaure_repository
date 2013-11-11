import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.MouseInputListener;

import oracle.jdbc.driver.OracleResultSet;

public class PanelMouseEventsListener implements MouseInputListener{
	ImageDrawingApplet imageDrawingApplet;
	MapComponent mapComponent;

	ArrayList< Integer > rangeQueryList = new ArrayList< Integer >();
	public PanelMouseEventsListener(ImageDrawingApplet imageDrawingApplet, MapComponent mapComponent) {
		this.imageDrawingApplet = imageDrawingApplet;
		this.mapComponent = mapComponent;
	}
	@Override
	public void mouseClicked(MouseEvent mouse) {
 		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent mouse) {

		if( imageDrawingApplet.pointQueryRadioButton.isSelected() &&  mouse.getButton() == 1  ){
			int x = mouse.getX();
			int y = mouse.getY();
			mapComponent.setPointX(x);
			mapComponent.setPointY(y);
			mapComponent.setDrawPointLocationOnClicked(true);
			mapComponent.repaint();
		}
 		
		if( imageDrawingApplet.surroundingStudentRadioButton.isSelected() &&  mouse.getButton() == 1  ){
			int x = mouse.getX();
			int y = mouse.getY();
			mapComponent.setSurrX(x);
			mapComponent.setSurrY(y);
			
			mapComponent.setDrawSurrStudentsPointOnClick(true);
			drawSurrQueryAnnouncementSystem();
		}
 		
		if( imageDrawingApplet.emergencyQueryRadioButton.isSelected() &&  mouse.getButton() == 1  ){
			int x = mouse.getX();
			int y = mouse.getY();
			mapComponent.setEmerX(x);
			mapComponent.setEmerY(y);
			
			mapComponent.setDrawEmerStudentsPointOnClick(true);
			drawEmergencyQueryAnnouncementSystem();
		}
		
		if( imageDrawingApplet.rangeQueryRadioButton.isSelected() ){

			if( mouse.getButton() == 1 ){
				
				int x = mouse.getX();
				int y = mouse.getY();
				rangeQueryList.add(x);
				rangeQueryList.add(y);
				
 			}
			if( mouse.getButton() == 3 ){
				//System.out.println( rangeQueryList );
				System.out.println(rangeQueryList);
				if( rangeQueryList.size() >= 6 ){ // need to check this condition
					mapComponent.setPointsRegionList( rangeQueryList);
					mapComponent.setDrawRangePointsPoligon(true);
					mapComponent.setPointX(999);
					mapComponent.repaint();
				}
				 rangeQueryList.clear();

			}
 			
		}

	}


	@Override
	public void mouseMoved(MouseEvent mouse) {
		int x = mouse.getX();
		int y = mouse.getY();
		imageDrawingApplet.mouseLocation.setText(x+" -- "+y); 
	}

	public void drawSurrQueryAnnouncementSystem(){
		
		ArrayList <Integer> announcementCoordinates = new ArrayList< Integer >();

		try 
		{
			Connection conn  =  DBUtil.getConnection();
			Statement stmt 	 = 	conn.createStatement();
 
			String sql 	 =	"select  b.centerx, b.centery , b.announcement_radius from sbc_announcement b " +
					"	WHERE SDO_NN( b.centershape , " +
 					" SDO_GEOMETRY(2001, NULL," +
					" SDO_POINT_TYPE("+mapComponent.getSurrX()+"," +
					mapComponent.getSurrY()+" ,null) , null, null)," +
					"   'sdo_num_res=1',1) = 'TRUE'" ;
			
			OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);

			while ( res.next() ) 
			{
 				int x = res.getInt(1);
				int y = res.getInt(2);
				int radius = res.getInt(3);
				
				announcementCoordinates.add(x);
				announcementCoordinates.add(y);
				announcementCoordinates.add(radius);

			}
		}catch(Exception e){
			e.printStackTrace();
		}
  		mapComponent.setSurroundingASOnClickList(announcementCoordinates);
		mapComponent.repaint();

	}
	

public void drawEmergencyQueryAnnouncementSystem(){
		
		ArrayList <Integer> announcementCoordinates = new ArrayList< Integer >();

		try 
		{
			Connection conn  =  DBUtil.getConnection();
			Statement stmt 	 = 	conn.createStatement();
 
			String sql 	 =	"select  b.centerx, b.centery , b.announcement_radius , b.announcement_name from sbc_announcement b " +
					"	WHERE SDO_NN( b.shape , " +
 					" SDO_GEOMETRY(2001, NULL," +
					" SDO_POINT_TYPE("+mapComponent.getEmerX()+"," +
					mapComponent.getEmerY()+" ,null) , null, null)," +
					"   'sdo_num_res=1',1) = 'TRUE'" ;
			
			OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);

			while ( res.next() ) 
			{
 				int x = res.getInt(1);
				int y = res.getInt(2);
				int radius = res.getInt(3);
				String announcement_name = res.getString(4);
				
				announcementCoordinates.add(x);
				announcementCoordinates.add(y);
				announcementCoordinates.add(radius);
				
				mapComponent.setEmergencySelectedAnnouncementName(announcement_name);
 
 			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
  		mapComponent.setEmergencyASOnClickList(announcementCoordinates);
		mapComponent.repaint();

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
}
