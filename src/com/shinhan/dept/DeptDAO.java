package com.shinhan.dept;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class DeptDAO {
	//static field
	Connection conn;	//DB연결
	Statement st;	//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;	//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//static final field ... (SQL Query)
	static final String SELECT_ALL = "SELECT * FROM DEPARTMENTS";
	static final String SELECT_DETAIL = "SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?";
	static final String INSERT = "INSERT INTO DEPARTMENTS VALUES( ?, ?, ?, ? )";
	static final String UPDATE = "UPDATE DEPARTMENTS " + 
								 "SET DEPARTMENTS_NAME = ?, " + 
								 "MANAGER_ID = ?, " +
								 "LOCATION_ID = ? " +
								 "WHERE DEPARTMENT_ID = ?";
	static final String DELETE = "DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?";
	
	//0.DeptDTO 만들기
	public DeptDTO makeDept(ResultSet rs) throws SQLException {
		DeptDTO dept = DeptDTO.builder()
				.department_id(rs.getInt(1))
				.department_name(rs.getString(2))
				.manager_id(rs.getInt(3))
				.location_id(rs.getInt(4))
				.build();
		return dept;
	}
	
	//1.(모든 부서 조회) SELECT * FROM DEPARTMENTS 
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptlist = new ArrayList<DeptDTO>();
		conn = DBUtil.getConnection();	//DB연결
		
		try {
			st = conn.createStatement();	//통로 뚫기
			rs = st.executeQuery(SELECT_ALL);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				DeptDTO dept = makeDept(rs);
				deptlist.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return deptlist;
	}
	
	//2.(부서코드로 조회) SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ? 
	public DeptDTO selectById(int department_id) {
		DeptDTO dept = null;
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(SELECT_DETAIL);	//통로 뚫기
			pst.setInt(1, department_id);	//'?'에 매개변수로 받은 부서코드 setting
			rs = pst.executeQuery();		//쿼리문 실행 및 결과값 가져오기
			if(rs.next()) {
				dept = makeDept(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, rs);
		}
		
		return dept;
	}
	
	//3.(새 부서 삽입) INSERT INTO DEPARTMENTS VALUES( ?, ?, ?, ? )
	public int insertDept(DeptDTO dept) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(INSERT);	//통로 뚫기
			pst.setInt(1, dept.getDepartment_id());
			pst.setString(2, dept.getDepartment_name());
			pst.setInt(3, dept.getManager_id());
			pst.setInt(4, dept.getLocation_id());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return resultCount;
	}

	//4.(부서코드로 수정)
	public int updateDept(DeptDTO dept) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(UPDATE);	//통로 뚫기
			pst.setInt(4, dept.getDepartment_id());
			pst.setString(1, dept.getDepartment_name());
			pst.setInt(2, dept.getManager_id());
			pst.setInt(3, dept.getLocation_id());
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return resultCount;
	}
	
	//5.(부서코드로 삭제) DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?
	public int deleteDept(int department_id) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			pst = conn.prepareStatement(DELETE);	//통로 뚫기
			pst.setInt(1, department_id);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return resultCount;
	}
}

























