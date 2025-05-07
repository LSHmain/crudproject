package com.shinhan.emp;

import java.util.List;
//Service: business logic 수행
//1) 이체업무(Service): (인출하기(DAO), 입금하기(DAO))
//2) 비밀번호 암호화(Service)
public class EmpService {
	
	EmpDAO empDAO = new EmpDAO();
	
	public List<EmpDTO> selectAll(){
		//...추가 로직
		return empDAO.selectAll();
	}
	
	public EmpDTO selectById(int empid) {
		return empDAO.selectById(empid);
	}
	
	
	public List<EmpDTO> selectByDept(int deptid) {
		return empDAO.selectByDept(deptid);
	}
	
	public List<EmpDTO> selectByJob(String job) {
		return empDAO.selectByJob(job);
	}
	
	
	public List<EmpDTO> selectByJobAndDept(String job, int dept){
		return empDAO.selectByJobAndDept(job, dept);
	}

//	public List<EmpDTO> selectByCondition(int deptid, String jobid, int salary, String hdate) {
//		return empDAO.selectByCondition(deptid,jobid,salary,hdate);
//	}
//	
	
	public List<EmpDTO> selectByCondition(String[] deptarr, String jobid, int salary, String hdate) {
		return empDAO.selectByCondition(deptarr,jobid,salary,hdate);
	}
	public int empDeleteById(int empid) {
		return empDAO.empDeleteById(empid);
	}
	
	
	public int empInsert(EmpDTO emp) {
		return empDAO.empInsert(emp);
	}
	
	
	public int empUpdate(EmpDTO emp) {
		return empDAO.empUpdate(emp);
	}
}
