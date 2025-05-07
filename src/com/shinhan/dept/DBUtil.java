package com.shinhan.dept;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	//DB 연결 함수
	
	public static Connection getConnection() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection conn = null;
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
//		String url = "jdbc:oracle:thin:@192.168.0.18:1521:XE";
		
		
		String user = "hr";
		String password = "hr";
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	//DB 연결시 사용한 자원 해제
	public static void dbDisconnect(
			Connection conn, Statement st, ResultSet rs ) {
		try {
			if(rs!=null)rs.close();
			if(st!=null)st.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
