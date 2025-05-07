package com.shinhan.day15;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dbutil.DBUtil;

public class CRUDTest2 {
	public static void f(String[] args) {
		
	}
	
	
	
	public static void f6(String[] args) throws SQLException {
		
		//2가지 작업을 수행하고,하나라도 실패하면 ROLLBACK을 진행한다.
		
		Connection conn = null;
		Statement st = null;
		Statement st2 = null;
		int result =0;
		String sql1 = """
				INSERT
					INTO
					EMP1 (EMPLOYEE_ID,
					FIRST_NAME,
					LAST_NAME,
					HIRE_DATE,
					JOB_ID,
					EMAIL)
				VALUES(4, 'aa', 'bb', SYSDATE, 'IT', 'cc')
				""";
		
		String sql2 = """
				UPDATE
					EMP1
				SET
					SALARY = 3000
				WHERE
					EMPLOYEE_ID = 198
				""";
		
		conn = DBUtil.getConnection();
		conn.setAutoCommit(false);
		st = conn.createStatement();
		int result1 = st.executeUpdate(sql1); //commit;
		st2 = conn.createStatement();
		int result2 = st2.executeUpdate(sql2); //commit;
		if(result1 >=1 && result2 >=1) {
			conn.commit();
		}else {
			conn.rollback();
		}
	}
	
	
	public static void f4(String[] args) throws SQLException {
		Connection con = null;
		Statement st = null;
		int result =0;
		String sql = """
				DELETE
				FROM
					EMP1
				WHERE
				    EMPLOYEE_ID < 100
				""";
		con = DBUtil.getConnection();
		st = con.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result>=1?result+"UPDATE success":"UPDATE fail");
	}
	
	
	
	public static void f3(String[] args) throws SQLException {
		Connection con = null;
		Statement st = null;
		int result =0;
		String sql = """
			UPDATE EMP1
				SET
				DEPARTMENT_ID =(
				SELECT
					DEPARTMENT_ID
				FROM
					EMPLOYEES
				WHERE
					EMPLOYEE_ID = 102)
				,
				SALARY = (
				SELECT
					SALARY
				FROM
					EMPLOYEES
				WHERE
					EMPLOYEE_ID = 103
				)
				WHERE
				EMPLOYEE_ID = 999

				""";
		con = DBUtil.getConnection();
		st = con.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result>=1?"UPDATE succes":"UPDATE fail");
	}
	
	
	public static void f2(String[] args) throws SQLException {
		Connection con = null;
		Statement st = null;
		int result =0;
		String sql = """
			UPDATE EMP1
				SET
				DEPARTMENT_ID =(
				SELECT
					DEPARTMENT_ID
				FROM
					EMPLOYEES
				WHERE
					EMPLOYEE_ID = 102)
				,
				SALARY = (
				SELECT
					SALARY
				FROM
					EMPLOYEES
				WHERE
					EMPLOYEE_ID = 103
				)
				WHERE
				EMPLOYEE_ID = 999

				""";
		con = DBUtil.getConnection();
		st = con.createStatement();
		result = st.executeUpdate(sql);
		System.out.println(result>=1?"succes":"fail");
	}
	
	public static void main(String[] args) throws SQLException {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		String sql = """
				SELECT *
				FROM EMPLOYEES
				""";
		
		con = DBUtil.getConnection();
		st = con.createStatement();
		rs = st.executeQuery(sql);
		while(rs.next()) {
//			int a = rs.getString(1);
			int b = rs.getInt(1);
//			int c = rs.getInt(3);
			System.out.println("\t"+b+"\t");
		}
		
		DBUtil.dbDisConnect(con, st, rs);

	}
}
