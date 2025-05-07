package com.shinhan.day15;

//CRUD(Create, Read, Update, Delete)
//Read(Select)
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbutil.DBUtil;

public class CRUDTest {
	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = """
				SELECT
				    DEPARTMENT_ID
				    ,MAX(SALARY)
				    ,MIN(SALARY)
				FROM
				    EMPLOYEES
				GROUP BY DEPARTMENT_ID
				HAVING MAX(SALARY) != MIN(SALARY)
				""";
		conn = DBUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt(1);
				int ma = rs.getInt(2);
				int mi = rs.getInt(3);
				System.out.println(id+"\t"+ma+"\t"+mi);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			DBUtil.dbDisConnect(conn, st, rs);
		}
		
	}
	
	public static void f2(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		Connection conn = null; //DB와 연결을 하기 위해 필요한 객체
		Statement st = null; //SQL 쿼리를 실행하는데 사용되는 객체
		ResultSet rs = null; // SQL 쿼리의 결과값을 저장하는 객체
		try {
			Class.forName(driver);
		
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "hr";
			String password = "1111";
			
			conn = DriverManager.getConnection(url,user,password);
			st = conn.createStatement();
			
			String sql ="""
					SELECT
					    DEPARTMENT_ID
					    ,COUNT(DEPARTMENT_ID)
					FROM
					    EMPLOYEES
					GROUP BY DEPARTMENT_ID
					HAVING COUNT(DEPARTMENT_ID) >= 5
					ORDER BY 2 DESC
					""";
			rs=st.executeQuery(sql);//rs는 표와 비슷하다.
			while(rs.next()) {
				int dept_id = rs.getInt(1);
				int cnt = rs.getInt(2);
				System.out.println("부서: "+dept_id+", 건수: "+cnt);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void f_1() {
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		
		try {

			Class.forName(driver);
			System.out.println("JDBC Driver Loading 성공!!");
			
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String user = "test1";
			String password = "1111";
			
			Connection conn = DriverManager.getConnection(url,user,password);
			System.out.println("3.Connection 성공");
			
			String sql = """
					SELECT * 
					FROM FARMER
					""";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
            while (rs.next()) {
                int empid = rs.getInt("EMPLOYEE_ID");
                String fname = rs.getString("FIRST_NAME");
                Date hdate = rs.getDate("HIRE_DATE");
                //double comm = rs.getDouble("COMM");
                System.out.printf("직원번호: %d, 직원이름: %s, 입사일: %s\n",empid,fname,hdate);
            }
		} catch (Exception e) {
			
			System.out.println("JDBC Driver Loading 실패!!");
			e.printStackTrace();
			
		}
		
		
	}
}