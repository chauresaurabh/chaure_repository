import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
 import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
 
public class populate {

	public static void main(String[] args) {
 
		String buildingFileLocation = "F:/buildings.xy";
		String studentFileLocation = "F:/students.xy";
		String asSystemFileLocation = "F:/announcementSystems.xy";

		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		populateBuildingData(buildingFileLocation);
		populateStudentData(studentFileLocation);
		populateASData(asSystemFileLocation);
	}

	public static void populateBuildingData(String buildingFileName)
	{
		int i;											 
		ArrayList<String> data=new ArrayList<String>(); 
		String line, sql;
		Connection conn = null;
		PreparedStatement statement = null;
		try 
		{
			FileInputStream inputStream = new FileInputStream(buildingFileName);
			DataInputStream dataStream = new DataInputStream(inputStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
			conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement("truncate table sbc_buildings");
			ps.execute();
			System.out.println("Data for Buildings Truncated");
			while ((line = reader.readLine()) != null) 
			{
				data.clear();
				String result[] = line.split(", ");
				data=new ArrayList<String>(Arrays.asList(result)); 

				sql=	"insert into sbc_buildings values(" +
						"'"+ data.get(0)+"'" +
						",'"+ data.get(1)+"'" +
						",'"+ data.get(2)+"'" +
						" ,SDO_GEOMETRY(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1)," +
						" SDO_ORDINATE_ARRAY("+
						data.get(3)
						;
				i=4;
				while(i<data.size())
				{
					sql=	sql+
							","+
							data.get(i);
					i++;
				}
 				sql= sql+ ")))";
				statement = conn.prepareStatement(sql);
				statement.execute();
	 
			}
			dataStream.close();
			System.out.println("Data for Buildings Inserted in Database");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		finally{
				try {
					if(conn!=null)
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
 	}

	public static void populateStudentData(String studentFileName)
	{
 		ArrayList<String> data=new ArrayList<String>(); 
		String line, sql;
		Connection conn = null;
		PreparedStatement statement = null;
		try 
		{
			FileInputStream inputStream = new FileInputStream(studentFileName);
			DataInputStream dataStream = new DataInputStream(inputStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
			conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement("truncate table sbc_students");
			ps.execute();
			System.out.println("Data for Students Truncated");
			while ((line = reader.readLine()) != null) 
			{
				data.clear();
				String result[] = line.split(", ");
				data=new ArrayList<String>(Arrays.asList(result)); 

				sql=	"insert into sbc_students values(" +
						"'"+ data.get(0)+"',"+
						"SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(" +
						data.get(1)+","+
						data.get(2)+",NULL),NULL,NULL))";
			 
				statement = conn.prepareStatement(sql);
				statement.execute();
	 
			}
			dataStream.close();
			System.out.println("Data for Students Inserted in Database");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		finally{
				try {
					if(conn!=null)
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
 	}
	
	public static void populateASData(String asSystemFileLocation)
	{
 		ArrayList<String> data=new ArrayList<String>(); 
		String line, sql;
		Connection conn = null;
		PreparedStatement statement = null;
		try 
		{
			FileInputStream inputStream = new FileInputStream(asSystemFileLocation);
			DataInputStream dataStream = new DataInputStream(inputStream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
			conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement("truncate table sbc_announcement");
			ps.execute();
			System.out.println("Data for Address System Truncated");
			while ((line = reader.readLine()) != null) 
			{
				data.clear();
				String result[] = line.split(", ");
				data=new ArrayList<String>(Arrays.asList(result)); 
				int centerX, centerY , radius;
				centerX = Integer.parseInt(data.get(1));
				centerY = Integer.parseInt(data.get(2));
				radius = Integer.parseInt( data.get(3) );
				
				int px1,py1, px2, py2, px3, py3;
				px1 = centerX - radius;
				py1 = centerY;
				px2 = centerX;
				py2 = centerY - radius;
				px3 = centerX + radius;
				py3 = centerY;
				
 				sql =	"insert into sbc_announcement values(" +
						"'"+ data.get(0)+"',"+
						" SDO_GEOMETRY( 2003, NULL, NULL," +
						" SDO_ELEM_INFO_ARRAY(1,1003,4)," +
						" SDO_ORDINATE_ARRAY("+px1 +"," + py1 + "," +px2 +"," + py2 + "," +px3 +"," + py3   +"  ) ),"+
						data.get(3)+ "," +
						data.get(1)	+	"," +
						data.get(2)	+  ", SDO_GEOMETRY(2001,NULL,SDO_POINT_TYPE(" +
                                data.get(1)+","+
                                data.get(2)+",NULL),NULL,NULL)"+")" ;
			 
				statement = conn.prepareStatement(sql);
				statement.execute();
	 
			}
			dataStream.close();
			System.out.println("Data for Address System in Database");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}	
		finally{
				try {
					if(conn!=null)
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
 	}
	

}
