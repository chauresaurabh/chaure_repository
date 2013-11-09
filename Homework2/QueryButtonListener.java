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

		if( !imageDrawingApplet.buildingCheckBox.isSelected() 
				&& !imageDrawingApplet.studentCheckBox.isSelected()
				&& !imageDrawingApplet.asCheckBox.isSelected()) {
			mapComponent.repaint();
		}
		else {
			if(imageDrawingApplet.wholeRegionRadioButton.isSelected()){ 
				drawWholeRegion();
			}
			else if(imageDrawingApplet.pointQueryRadioButton.isSelected()){ 
				drawPointRegion();
			}
			else if(imageDrawingApplet.rangeQueryRadioButton.isSelected()){ 
				drawRangeRegion();
			}else if(imageDrawingApplet.surroundingStudentRadioButton.isSelected()){ 
				drawSurroundingRegion();
			}
		}

	}

	public void drawSurroundingRegion(){
		ArrayList <Integer> studentCoordinates = new ArrayList< Integer >();
		boolean drawFlag = false;
		try {
			String sql 	 =	"  select b.shape.sdo_point from sbc_students b WHERE" +
					" SDO_WITHIN_DISTANCE(b.shape," +
					"		 SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("+mapComponent.surroundingASOnClickList.get(0)+"," +
					mapComponent.surroundingASOnClickList.get(1)+" ,null), null, null), " +
					" 'distance =  "+ mapComponent.surroundingASOnClickList.get(2) +"') = 'TRUE' " ;
			Connection conn  =  DBUtil.getConnection();

			PreparedStatement ps 	 = 	conn.prepareStatement(sql);
			OracleResultSet	res 		= 	 (OracleResultSet)ps.executeQuery();
			while ( res.next() ) 
			{
				STRUCT struct 			= 	(STRUCT) res.getObject(1);
				Object[] data 			= 	struct.getAttributes();

				int x 				= 	((Number) data[0]).intValue();
				int y 				= 	((Number) data[1]).intValue();
				studentCoordinates.add(x);
				studentCoordinates.add(y);
				drawFlag = true;
			}	

		} catch(Exception e ){
			e.printStackTrace();
		}

		if(drawFlag) {
			mapComponent.setSurrStudentCoordinatesList(studentCoordinates);
			mapComponent.setDrawSurrStudentsOnSubmit(true);
			mapComponent.repaint();

		}

	}

	public void drawRangeRegion(){

		ArrayList < ArrayList <Integer> > buildingList = new ArrayList< ArrayList <Integer> >();
		ArrayList <Integer> studentCoordinates = new ArrayList< Integer >();
		ArrayList <Integer> announcementCoordinates = new ArrayList< Integer >();
		boolean drawFlag = false ; 
		if(imageDrawingApplet.buildingCheckBox.isSelected() && mapComponent.pointsRegionList.size()>0){
			drawFlag = true;
			try 
			{
				Connection conn  =  DBUtil.getConnection();
				Statement stmt 	 = 	conn.createStatement();

				String sql  =	"select b.shape.sdo_ordinates from sbc_buildings b  WHERE SDO_RELATE(b.shape," +
						" SDO_GEOMETRY(2003, NULL, NULL," +
						" SDO_ELEM_INFO_ARRAY(1,1003,1)," +
						" SDO_ORDINATE_ARRAY(" + mapComponent.pointsRegionList.get(0) ;

				for (int i = 1; i < mapComponent.pointsRegionList.size(); i++) {
					sql = sql + ","+ mapComponent.pointsRegionList.get(i);
				}
				sql = sql + ","+ mapComponent.pointsRegionList.get(0)+ ","+ mapComponent.pointsRegionList.get(1)+ " )), " +
						"		 'MASK=OVERLAPBDYDISJOINT+OVERLAPBDYINTERSECT+EQUAL+INSIDE+COVEREDBY+CONTAINS+ON')='TRUE' " ;

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

			}catch(Exception e){
				e.printStackTrace();
			}	
			mapComponent.setRangeBuildingList(buildingList);
			mapComponent.setDrawRangeEverything(true);
		}

		// ******************************************************

		if( imageDrawingApplet.studentCheckBox.isSelected() && mapComponent.pointsRegionList.size()>0 ){
			drawFlag = true;
			try 
			{
				Connection conn  =  DBUtil.getConnection();
				Statement stmt 	 = 	conn.createStatement();

				String sql  =	"select  b.shape.sdo_point from sbc_students b " +
						" WHERE SDO_RELATE(b.shape," +
						" SDO_GEOMETRY(2003, NULL, NULL," +
						" SDO_ELEM_INFO_ARRAY(1,1003,1)," +
						" SDO_ORDINATE_ARRAY(" + mapComponent.pointsRegionList.get(0) ;

				for (int i = 1; i < mapComponent.pointsRegionList.size(); i++) {
					sql = sql + ","+ mapComponent.pointsRegionList.get(i);
				}
				sql = sql + ","+ mapComponent.pointsRegionList.get(0)+ ","+ mapComponent.pointsRegionList.get(1)+ " )), " +
						"		 'MASK=OVERLAPBDYDISJOINT+OVERLAPBDYINTERSECT+EQUAL+INSIDE+COVEREDBY+CONTAINS+ON')='TRUE' " ;

				OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);

				while ( res.next() ) 
				{
					STRUCT struct 			= 	(STRUCT) res.getObject(1);
					Object[] data 			= 	struct.getAttributes();

					int x 				= 	((Number) data[0]).intValue();
					int y 				= 	((Number) data[1]).intValue();
					studentCoordinates.add(x);
					studentCoordinates.add(y);
				}

			}catch(Exception e){
				e.printStackTrace();
			}	
			mapComponent.setDrawRangeEverything(true);
			mapComponent.setRangeStudentCoordinates(studentCoordinates);

		}

		// ******************************************************

		if(imageDrawingApplet.asCheckBox.isSelected() && mapComponent.pointsRegionList.size()>0 ){
			drawFlag = true;
			try 
			{
				Connection conn  =  DBUtil.getConnection();

				String sql  =	"select  b.announcement_radius, b.centerx, b.centery  from sbc_announcement b " +
						" WHERE SDO_RELATE(b.shape," +
						" SDO_GEOMETRY(2003, NULL, NULL," +
						" SDO_ELEM_INFO_ARRAY(1,1003,1)," +
						" SDO_ORDINATE_ARRAY(" + mapComponent.pointsRegionList.get(0) ;
				for (int i = 1; i < mapComponent.pointsRegionList.size(); i++) {
					sql = sql + ","+ mapComponent.pointsRegionList.get(i);
				}
				sql = sql + ","+ mapComponent.pointsRegionList.get(0)+ ","+ mapComponent.pointsRegionList.get(1)+ " )), " +
						"		 'MASK=OVERLAPBDYDISJOINT+OVERLAPBDYINTERSECT+EQUAL+INSIDE+COVEREDBY+CONTAINS+ON')='TRUE' " ;

				PreparedStatement ps 	 = 	conn.prepareStatement(sql);
				OracleResultSet res  = 	 (OracleResultSet)ps.executeQuery();

				while (res.next()) 
				{
					int radius = 	res.getInt(1);

					int x = res.getInt(2);
					int y = res.getInt(3);
					announcementCoordinates.add(x);
					announcementCoordinates.add(y);
					announcementCoordinates.add(radius);

				}	
			}catch(Exception e){
				e.printStackTrace();
			}	
			mapComponent.setDrawRangeEverything(true);
			mapComponent.setRangeASCoordinates(announcementCoordinates);

		}
		if(drawFlag && mapComponent.pointsRegionList.size()>0 ){
			mapComponent.repaint();
		}

	}

	public void drawPointRegion(){

		ArrayList < ArrayList <Integer> > buildingList = new ArrayList< ArrayList <Integer> >();
		ArrayList <Integer> studentCoordinates = new ArrayList< Integer >();
		ArrayList <Integer> announcementCoordinates = new ArrayList< Integer >();

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

			// to show students in that area.
			if( imageDrawingApplet.studentCheckBox.isSelected() ){
				try 
				{
					Connection conn  =  DBUtil.getConnection();
					Statement stmt 	 = 	conn.createStatement();
					String studentId="";
					String sql 	 =	"select b.studentid , b.shape.sdo_point from sbc_students b " +
							"	WHERE SDO_NN(b.shape, SDO_GEOMETRY(2001, NULL," +
							" SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null)," +
							"   'sdo_num_res=1',1) = 'TRUE'" +
							" AND SDO_WITHIN_DISTANCE(b.shape, SDO_GEOMETRY(2001, NULL," +
							"  SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null),  'distance=50') = 'TRUE' " ;
					OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);

					while ( res.next() ) 
					{
						studentId = res.getString(1);
						STRUCT struct 			= 	(STRUCT) res.getObject(2);
						Object[] data 			= 	struct.getAttributes();

						int x 				= 	((Number) data[0]).intValue();
						int y 				= 	((Number) data[1]).intValue();
						studentCoordinates.add(x);
						studentCoordinates.add(y);
					}
					// for all buildings , except the one picked in previous query
					// need to check if nothing is returned in 1st query
					sql 	 =	"  select b.shape.sdo_point from sbc_students b WHERE" +
							" studentid !=? AND "+
							" SDO_WITHIN_DISTANCE(b.shape," +
							"		 SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null), null, null), " +
							" 'distance = 50') = 'TRUE' " ;

					PreparedStatement ps 	 = 	conn.prepareStatement(sql);
					ps.setString(1, studentId);
					res 		= 	 (OracleResultSet)ps.executeQuery();
					while ( res.next() ) 
					{
						studentId = res.getString(1);
						STRUCT struct 			= 	(STRUCT) res.getObject(1);
						Object[] data 			= 	struct.getAttributes();

						int x 				= 	((Number) data[0]).intValue();
						int y 				= 	((Number) data[1]).intValue();
						studentCoordinates.add(x);
						studentCoordinates.add(y);
					}		
				}catch(Exception e){
					e.printStackTrace();
				}	
				mapComponent.setDrawPointRegion(true);
				mapComponent.setPointRegionStudentCoordinatesList(studentCoordinates);

			}
			if(imageDrawingApplet.asCheckBox.isSelected()){

				try 
				{
					Connection conn  =  DBUtil.getConnection();
					Statement stmt 	 = 	conn.createStatement();
					String announcementName="";
					String sql 	 =	"select b.announcement_name , b.centerx, b.centery , b.announcement_radius from sbc_announcement b " +
							"	WHERE SDO_NN(b.shape, SDO_GEOMETRY(2001, NULL," +
							" SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null)," +
							"   'sdo_num_res=1',1) = 'TRUE'" +
							" AND SDO_WITHIN_DISTANCE(b.shape, SDO_GEOMETRY(2001, NULL," +
							"  SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null) , null, null),  'distance=50') = 'TRUE' " ;
					OracleResultSet res 		= 	 (OracleResultSet)stmt.executeQuery(sql);

					while ( res.next() ) 
					{
						announcementName = res.getString(1);
						int x = res.getInt(2);
						int y = res.getInt(3);
						int radius = res.getInt(4);

						//STRUCT struct 			= 	(STRUCT) res.getObject(2);
						//Object[] data 			= 	struct.getAttributes();

						//int x 				= 	((Number) data[0]).intValue();
						//int y 				= 	((Number) data[1]).intValue();
						announcementCoordinates.add(x);
						announcementCoordinates.add(y);
						announcementCoordinates.add(radius);

					}

					sql 	 =	" select b.announcement_name , b.centerx, b.centery , b.announcement_radius from sbc_announcement b WHERE" +
							" announcement_name !=? AND "+
							" SDO_WITHIN_DISTANCE(b.shape," +
							"		 SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("+mapComponent.getPointX()+"," +
							mapComponent.getPointY()+" ,null), null, null), " +
							" 'distance = 50') = 'TRUE' " ;
					/* sql 	 =	"  select b.shape.sdo_point, b.announcement_radius from sbc_announcement b WHERE" +
							" announcement_name !=? AND "+
							"   sdo_relate(b.shape, " +
							" SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4)," +
							" SDO_ORDINATE_ARRAY(" + (mapComponent.getPointX() - 50) + "," + mapComponent.getPointY() + "," + 
							"  "+ (mapComponent.getPointX() + 50 ) + " ," + mapComponent.getPointY() + "," + mapComponent.getPointX() +
							 "," + (mapComponent.getPointY() + 50) + ")),"+
							" 'mask=ANYINTERACT') = 'TRUE'"  ;*/

					PreparedStatement ps 	 = 	conn.prepareStatement(sql);
					ps.setString(1, announcementName);
					res 		= 	 (OracleResultSet)ps.executeQuery();
					while ( res.next() ) 
					{
						announcementName = res.getString(1);
						int x = res.getInt(2);
						int y = res.getInt(3);
						int radius = res.getInt(4);

						announcementCoordinates.add(x);
						announcementCoordinates.add(y);
						announcementCoordinates.add(radius);

					}		
				}catch(Exception e){
					e.printStackTrace();
				}	
				mapComponent.setDrawPointRegion(true);
				mapComponent.setPointRegionAnnouncementCoordinatesList(announcementCoordinates);

			}


		}
		if( mapComponent.isDrawPointRegion() )
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
				String sql 				=	"select a.announcement_radius, a.centerx, a.centery from sbc_announcement a" ;					    
				OracleResultSet res 		= 	(OracleResultSet)stmt.executeQuery(sql);
				announcementCoords  = new ArrayList< Integer >();
				displayString += sql+"\n";

				while (res.next()) 
				{
					int radius = 	res.getInt(1);
					/*STRUCT struct 			= 	(STRUCT) res.getObject(2);
					Object[] data 			= 	struct.getAttributes();
					int x 				= 	((Number) data[0]).intValue();
					int y 				= 	((Number) data[1]).intValue();*/
					int x = res.getInt(2);
					int y = res.getInt(3);
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
		if(mapComponent.isDrawWholeRegion())
			mapComponent.repaint();
	}


}
