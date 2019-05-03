/**
 * 
 */
package lifelifelp.DBIO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author K.Schulz
 *
 */
public class DBCommon {
	private static String user;
	private static String pw;
	private static String url;
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	
	public static boolean testdb(String user, String pw, String url) {
		DBCommon.user = user;
		DBCommon.pw = pw;
		DBCommon.url = url;
		System.out.println(DBCommon.user);
		System.out.println(DBCommon.pw);
		System.out.println(DBCommon.url);
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
		    conn = DriverManager.getConnection("jdbc:mysql://"+DBCommon.url+"/discor?" +"user="+DBCommon.user+"&password="+DBCommon.pw+"&useSSL=false&serverTimezone=UTC");
		    conn.close();
		    return true;

		    // Do something with the Connection
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    return false;
		}	
	}
}
