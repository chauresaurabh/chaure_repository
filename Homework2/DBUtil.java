import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {

  	public static Connection getConnection(){

		Connection conn = null ;
 		String url = "jdbc:oracle:thin:system/manager@localhost:1521:orclchaure";
		try 
		{
			conn = DriverManager.getConnection(url);
		}catch(Exception e){
			e.printStackTrace();
		}

		return conn;
	}
 	
  	public static void close(Connection conn ){
  		if(conn!=null){
  			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
  		}
  	}
}
