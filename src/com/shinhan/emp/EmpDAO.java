package com.shinhan.emp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbutil.DBUtil;

//DAO(Data Access Object): DB에 CRUD작업을 한다.(Select, Insert, Update, Delete)
//Statement는 SQL문을 보내는 통로...바인딩 변수를 지원하지 않는다.
//PreparedStatemenr:  Statement를 상속받는다, 바인딩 변수 지원
public class EmpDAO {
	//모든 직원 조회
	public List<EmpDTO> selectAll() {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM EMPLOYEES";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbDisConnect(conn, st, rs);
		}
		return emplist;
	}
	//부서의 직원을 조회
	public List<EmpDTO> selectByDept(int deptid) {
		List<EmpDTO> emplist = new ArrayList<>();
		Connection conn = DBUtil.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = "+deptid;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpDTO emp = makeEmp(rs);
				emplist.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtil.dbDisConnect(conn, st, rs);
		}
		return emplist;
	}
	
	//직원 번호로 직원의 정보를 상세보기
		public EmpDTO selectById(int empid) {
			EmpDTO emp = null;
			Connection conn = DBUtil.getConnection();
			Statement st = null;
			ResultSet rs = null;
			String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = "+empid;
			try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				if (rs.next()) {
					emp = makeEmp(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.dbDisConnect(conn, st, rs);
			}
			return emp;
		}
		//잡으로 직원조회
		public List<EmpDTO> selectByJob(String job) {
			List<EmpDTO> emplist = new ArrayList<>();
//			EmpDTO emp = null;
			Connection conn = DBUtil.getConnection();
//			Statement st = null;
			ResultSet rs = null;
			PreparedStatement st = null;
			String sql = "SELECT * FROM EMPLOYEES WHERE JOB_ID = ?";
			try {
				st = conn.prepareStatement(sql);//sql문을 준비한다.
				st.setString(1,job); //첫번째 '?'에 값을 setting 한다.
				rs = st.executeQuery();
				if (rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.dbDisConnect(conn, st, rs);
			}
			return emplist;
		}
		
		
		
		public List<EmpDTO> selectByJobAndDept(String job, int dept) {
			List<EmpDTO> emplist = new ArrayList<>();

			Connection conn = DBUtil.getConnection();

			ResultSet rs = null;
			PreparedStatement st = null;
			String sql = "SELECT * FROM EMPLOYEES WHERE JOB_ID = ? AND DEPARTMENT_ID = ?";
			try {
				st = conn.prepareStatement(sql);//sql문을 준비한다.
				st.setString(1,job); //첫번째 '?'에 값을 setting 한다.
				st.setInt(2,dept);
				rs = st.executeQuery();
				if (rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.dbDisConnect(conn, st, rs);
			}
			return emplist;
		}

		public List<EmpDTO> selectByCondition(String[] deptarr, String jobid, int salary, String hdate) {
			List<EmpDTO> emplist = new ArrayList<>();
			Connection conn = DBUtil.getConnection();
			ResultSet rs = null;
			PreparedStatement st = null;
			String s="";
			for(String a:deptarr) {
				s = s+a+",";
			}
		
			String sql = "SELECT * FROM EMPLOYEES "
					+ " WHERE JOB_ID LIKE ? "
					+ " AND DEPARTMENT_ID in ( "+s.substring(0,s.length()-1)+" ) "
					+ " AND SALARY >= ? "
					+ " AND HIRE_DATE > ? ";
			try {
				st = conn.prepareStatement(sql);//sql문을 준비한다.
				st.setString(1,"%"+jobid+"%"); //첫번째 '?'에 값을 setting 한다.
				st.setInt(2,salary);
				Date d = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
				st.setDate(3,d); //첫번째 '?'에 값을 setting 한다.
				rs = st.executeQuery();
				if (rs.next()) {
					EmpDTO emp = makeEmp(rs);
					emplist.add(emp);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				DBUtil.dbDisConnect(conn, st, rs);
			}
			return emplist;
		}	
		
		public int empUpdate(EmpDTO emp) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
			PreparedStatement st = null;
			
			Map<String, Object> dynamicSQL = new HashMap<>();
			
			if(emp.getFirst_name()!=null) dynamicSQL.put("FIRST_NAME", emp.getFirst_name());
			if(emp.getLast_name()!=null) dynamicSQL.put("LAST_NAME", emp.getLast_name());
			if(emp.getSalary()!=null) dynamicSQL.put("SALARY", emp.getSalary());
			if(emp.getHire_date()!=null) dynamicSQL.put("HIRE_DATE", emp.getHire_date());
			if(emp.getEmail()!=null) dynamicSQL.put("EMAIL", emp.getEmail());
			if(emp.getPhone_number()!=null) dynamicSQL.put("PHONE_NUMBER", emp.getPhone_number());
			if(emp.getJob_id()!=null) dynamicSQL.put("Job_id", emp.getJob_id());
			if(emp.getCommission_pct()!=null) dynamicSQL.put("COMMISSION_PCT", emp.getCommission_pct());
			if(emp.getManager_id()!=null) dynamicSQL.put("MANAGER_ID", emp.getManager_id());
			if(emp.getDepartment_id()!=null) dynamicSQL.put("DEPARTMENT_ID", emp.getDepartment_id());
			
			System.out.println(dynamicSQL);
			String sql = " update employees set ";
		 	String sql2 = " where EMPLOYEE_ID = ? ";		
			
		 	for(String key:dynamicSQL.keySet()) {
		 		sql += key + "=" + "?,";
		 	}
		 	sql = sql.substring(0,sql.length()-1);
		 	sql += sql2;
//		 	System.out.println(sql);
		 	
			try {
				st = conn.prepareStatement(sql);
				int i=1;
				for(String key:dynamicSQL.keySet()) {
			 		st.setObject(i++, dynamicSQL.get(key));
			 	}
				st.setInt(i, emp.getEmployee_id());
				result = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}



		
		public int empUpdate2(EmpDTO emp) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
			PreparedStatement st = null;
			String sql = """
					UPDATE EMPLOYEES SET
						FIRST_NAME = ?
						,LAST_NAME = ?
						,EMAIL = ?
						,PHONE_NUMBER = ?
						,HIRE_DATE = ?
						,JOB_ID = ?
						,SALARY = ?
						,COMMISSION_PCT = ?
						,MANAGER_ID = ?
						,DEPARTMENT_ID = ?
				    WHERE
					    EMPLOYEE_ID = ?
					""";
			try {
				st = conn.prepareStatement(sql);
				st.setInt(11, emp.getEmployee_id());
				st.setString(1, emp.getFirst_name());
				st.setString(2, emp.getLast_name());
				st.setString(3, emp.getEmail());
				st.setString(4, emp.getPhone_number());
				st.setDate(5, emp.getHire_date());
				st.setString(6, emp.getJob_id());
				st.setDouble(7, emp.getSalary());
				st.setDouble(8, emp.getCommission_pct());
				st.setInt(9, emp.getManager_id());
				st.setInt(10, emp.getDepartment_id());
				result = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		//삭제 특정 직원 삭제
		public int empInsert(EmpDTO emp) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
			PreparedStatement st = null;
			String sql = """
					insert into employees (
					EMPLOYEE_ID
					,FIRST_NAME
					,LAST_NAME
					,EMAIL
					,PHONE_NUMBER
					,HIRE_DATE
					,JOB_ID
					,SALARY
					,COMMISSION_PCT
					,MANAGER_ID
					,DEPARTMENT_ID)
					values(?,?,?,?,?,?,?,?,?,?,?)
					""";
			try {
				st = conn.prepareStatement(sql);
				st.setInt(1, emp.getEmployee_id());
				st.setString(2, emp.getFirst_name());
				st.setString(3, emp.getLast_name());
				st.setString(4, emp.getEmail());
				st.setString(5, emp.getPhone_number());
				st.setDate(6, emp.getHire_date());
				st.setString(7, emp.getJob_id());
				st.setDouble(8, emp.getSalary());
				st.setDouble(9, emp.getCommission_pct());
				st.setInt(10, emp.getManager_id());
				st.setInt(11, emp.getDepartment_id());
				result = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
				
				
		//삭제 특정 직원 삭제
		public int empDeleteById(int empid) {
			int result = 0;
			Connection conn = DBUtil.getConnection();
			PreparedStatement st = null;
			String sql = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
			try {
				st = conn.prepareStatement(sql);
				st.setInt(1,empid);
				result = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		

	private EmpDTO makeEmp(ResultSet rs) throws SQLException {
		EmpDTO emp = EmpDTO.builder()
				.commission_pct(rs.getDouble("commission_pct"))
				.department_id(rs.getInt("department_id"))
				.email(rs.getString("email"))
				.employee_id(rs.getInt("employee_id"))
				.first_name(rs.getString("first_name"))
				.hire_date(rs.getDate("hire_date"))
				.job_id(rs.getString("job_id"))
				.last_name(rs.getString("last_name"))
				.manager_id(rs.getInt("manager_id"))
				.phone_number(rs.getString("phone_number"))
				.salary(rs.getDouble("salary"))
				.build();
		return emp;
	}
	
}
