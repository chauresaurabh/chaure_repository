import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import oracle.jdbc.driver.OracleResultSet;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

public class QueryButtonListener implements ActionListener{

	ImageDrawingApplet imageDrawingApplet;
	MapComponent mapComponent;
	static int counter = 1;
	// Need to set variables for queries to show in textbox along with counter
	String displayString = "";
	public QueryButtonListener(ImageDrawingApplet imageDrawingApplet, MapComponent mapComponent) {
		this.imageDrawingApplet = imageDrawingApplet;
		this.mapComponent = mapComponent;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(imageDrawingApplet.wholeRegionRadioButton.isSelected()){ 
			drawWholeRegion();
		}
		if(imageDrawingApplet.pointQueryRadioButton.isSelected()){ 
			drawPointRegion();
		}
	}
	public void drawPointRegion(){

		ArrayList < ArrayList <Integer> > buildingList = new ArrayList< ArrayList <Integer> >();

		if(mapComponent.getPointX()!=-1 && mapComponent.getPointY()!=-1)
		{
			if(imageDrawingApplet.buildingCheckBox.isSelected()){
				try 
				{

					Connection conn  =  DBUtil.getConnection();
					Statement stmt 	 = 	conn.createStatement();
					
					String sql 	 =	"select b.building_id , b.shape.sdo_ordinates from sbc_buildings b " +
							"	WHERE SDO_NN(b.shape, SDO_GEOMETRY(2001, NULL," +
							" SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null)," +
							"   'sdo_num_res=1',1) = 'TRUE'" +
							" AND SDO_WITHIN_DISTANCE(b.shape, SDO_GEOMETRY(2001, NULL," +
							"  SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null),  'distance=50') = 'TRUE' " ;
					OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);
					String building = "";
					while ( res.next() ) 
					{
						building = res.getString(1);
						oracle.sql.ARRAY arr	= 	(ARRAY) res.getObject(2);
						BigDecimal values[]		=	(BigDecimal[]) arr.getArray();
						ArrayList<Integer> list = new ArrayList<Integer>();
						for(BigDecimal v : values)
						{
							list.add(v.intValue());
						}
						buildingList.add(list);
					}
					System.out.println("Nearest Building : " + building);
					// for all buildings , except the one picked in previous query
					// need to check if nothing is returned in 1st query
					  sql 	 =	"  select b.shape.sdo_ordinates from sbc_buildings b WHERE" +
							  " building_id !=? AND "+
							" SDO_WITHIN_DISTANCE(b.shape," +
							"		 SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null), null, null), " +
							" 'distance = 50') = 'TRUE' " ;

					  PreparedStatement ps 	 = 	conn.prepareStatement(sql);
					  ps.setString(1, building);
					  res 		= 	 (OracleResultSet)ps.executeQuery();
					while ( res.next() ) 
					{
						oracle.sql.ARRAY arr	= 	(ARRAY) res.getObject(1);
						BigDecimal values[]		=	(BigDecimal[]) arr.getArray();
						ArrayList<Integer> list = new ArrayList<Integer>();
						for(BigDecimal v : values)
						{
							list.add(v.intValue());
						}
						buildingList.add(list);
					}
				}catch(Exception e){
					e.printStackTrace();
				}	
				mapComponent.setDrawPointRegion(true);
				mapComponent.setPointRegionBuildingList(buildingList);
			}

		}
		if(mapComponent.isDrawPointRegion() == true )
			mapComponent.repaint();

	}
	// NEED TO VALIDATE IF NONE OF THE CHECKBOXES ARE CHECKED.
	public void drawWholeRegion(){
		if(imageDrawingApplet.asCheckBox.isSelected()){
			ArrayList <Integer> announcementCoords = null;
			try 
			{
				Connection conn  =  DBUtil.getConnection();
				Statement stmt 				= 	conn.createStatement();
				String sql 				=	"select a.announcement_radius, a.shape.sdo_point from sbc_announcement a" ;					    
				OracleResultSet res 		= 	(OracleResultSet)stmt.executeQuery(sql);
				announcementCoords  = new ArrayList< Integer >();
				displayString += sql+"\n";

				while (res.next()) 
				{
					int radius = 	res.getInt(1);
					STRUCT struct 			= 	(STRUCT) res.getObject(2);
					Object[] data 			= 	struct.getAttributes();
					int x 				= 	((Number) data[0]).intValue();
					int y 				= 	((Number) data[1]).intValue();
					announcementCoords.add(x);
					announcementCoords.add(y);
					announcementCoords.add(radius);

				}
			}catch (SQLException e) 
			{
				e.printStackTrace();
			}finally{
				// close connections using DBUtil.close.
			}
			mapComponent.setAsCoordinates(announcementCoords);
			mapComponent.setDrawWholeRegion(true);
		}

		if(imageDrawingApplet.buildingCheckBox.isSelected()){

			ArrayList < ArrayList <Integer> > buildingList = new ArrayList< ArrayList <Integer> >();
			Connection conn = null;

			try 
			{
				conn =  DBUtil.getConnection();
				Statement stmt 				= 	 conn.createStatement();
				String sql 				=	"select b.shape.sdo_ordinates from sbc_buildings b order by b.building_id " ;
				displayString += sql+"\n";

				OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);
				while ( res.next() ) 
				{
					oracle.sql.ARRAY arr	= 	(ARRAY) res.getObject(1);
					BigDecimal values[]		=	(BigDecimal[]) arr.getArray();
					ArrayList<Integer> list = new ArrayList<Integer>();
					for(BigDecimal v : values)
					{
						list.add(v.intValue());
					}
					buildingList.add(list);
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}finally{
				// close connections using DBUtil.close.
			}

			mapComponent.setBuildingList(buildingList);
			mapComponent.setDrawWholeRegion(true);
		}
		if(imageDrawingApplet.studentCheckBox.isSelected()){
			ArrayList <Integer> studentCoordinates = null;
			try 
			{

				Connection conn  =  DBUtil.getConnection();
				Statement stmt 				= 	conn.createStatement();
				String sql 				=	"select s.shape.sdo_point from sbc_students s" ;					    
				OracleResultSet res 		= 	(OracleResultSet)stmt.executeQuery(sql);
				studentCoordinates  = new ArrayList< Integer >();
				displayString += sql+"\n";

				while (res.next()) 
				{
					STRUCT struct 			= 	(STRUCT) res.getObject(1);
					Object[] data 			= 	struct.getAttributes();
					int x 				= 	((Number) data[0]).intValue();
					int y 				= 	((Number) data[1]).intValue();
					studentCoordinates.add(x);
					studentCoordinates.add(y);
				}
			}catch (SQLException e) 
			{
				e.printStackTrace();
			}finally{
				// close connections using DBUtil.close.
			}
			mapComponent.setStudentCoordinates(studentCoordinates);
			mapComponent.setDrawWholeRegion(true);
			String text= imageDrawingApplet.queryDisplayArea.getText();
			imageDrawingApplet.queryDisplayArea.setText(text+counter+" : "+displayString + "\n");
			counter ++;
			displayString = "";
		}

		mapComponent.repaint();
	}


}
