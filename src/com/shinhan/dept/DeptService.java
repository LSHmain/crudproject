package com.shinhan.dept;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import lombok.extern.java.Log;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
@Log
public class DeptService {
	
	DeptDAO deptDAO = new DeptDAO();
	
	//1.(모든 부서 조회) SELECT * FROM DEPARTMENTS 
	public List<DeptDTO> selectAll() {
		List<DeptDTO> deptlist = deptDAO.selectAll();
		log.info("DeptService에서 로그출력: " + deptlist.size() + "건");
		return deptlist;
	}
	
	//2.(부서코드로 조회) SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ? 
	public DeptDTO selectById(int department_id) {
		DeptDTO dept = deptDAO.selectById(department_id);
		log.info("DeptService에서 로그출력: " + dept);
		return dept;
	}
	
	//3.(새 부서 삽입) INSERT INTO DEPARTMENTS VALUES( ?, ?, ?, ? )
	public int insertDept(DeptDTO dept) {
		int result = deptDAO.insertDept(dept);
		log.info("DeptService에서 로그출력: " + result + "건 insert");
		return result;
	}

	//4.(부서코드로 수정)
	public int updateDept(DeptDTO dept) {
		int result = deptDAO.updateDept(dept);
		log.info("DeptService에서 로그출력: " + result + "건 update");
		return result;
	}
	
	//5.(부서코드로 삭제) DELETE FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?
	public int deleteDept(int department_id) {
		int result = deptDAO.deleteDept(department_id);
		log.info("DeptService에서 로그출력: " + result + "건 delete");
		return result;
	}
	
}
