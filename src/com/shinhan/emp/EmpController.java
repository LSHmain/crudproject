package com.shinhan.emp;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

//MVC2모델
//FrontController -> Controller선택 -> Service -> DAO -> DB 이를 반대로 순환이 됨

public class EmpController implements CommonControllerInterface{

	static Scanner sc = new Scanner(System.in);
	static EmpService empService = new EmpService();
	
	@Override
	public void execute() {
		boolean isStop = false;
		while(!isStop) {
			menuDisplay();
			int job = sc.nextInt();
			switch(job) {
			case 1->{f_selectAll();}
			case 2->{f_selectById();}
			case 3->{f_selectByDept();}
			case 4->{f_selectByJob();}
			case 5->{f_selectByJobAndDept();}
			case 6->{f_selectByCondition();}
			case 7->{f_deleteById();}
			case 8->{f_insertById();}
			case 9->{f_updateById();}
			case 10->{isStop = true;}
			}
		}
		System.out.println("Good Bye");
	}

	
	private static void f_updateById() {
		System.out.print("수정할 직원ID>>");
		int employee_id = sc.nextInt();
		EmpDTO exist_emp = empService.selectById(employee_id);
		if(exist_emp==null) {
			EmpView.display("존재하지 않는 직원입니다.");
			return;
		}
		EmpView.display(exist_emp);
	    int result = empService.empUpdate(makeEmp(employee_id));
	    EmpView.display(result+"건 변경");
		
	}

	static EmpDTO makeEmp(int employee_id) {
		System.out.print("first_name>>");
		String first_name = sc.next();
		
		System.out.print("last_name>>");
		String last_name = sc.next();
		
		System.out.print("email>>");
		String email = sc.next();
		
		System.out.print("phone_number>>");
		String phone_number = sc.next();
		
		System.out.print("hdate(yyyy-MM-dd)>>");
		String hdate = sc.next();
		Date hire_date = null;
		if(!hdate.equals("0"))
		   hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
		
		System.out.print("job_id(FK:IT_PROG)>>");
		String job_id = sc.next();
		
		System.out.print("salary>>");
		Double salary = sc.nextDouble();
		System.out.print("commission_pct(0.2)>>");
		Double commission_pct = sc.nextDouble();
		System.out.print("manager_id(FK:100)>>");
		Integer manager_id = sc.nextInt();
		System.out.print("department_id(FK:60,90)>>");
		Integer department_id = sc.nextInt();
		
		if(first_name.equals("0")) first_name = null;
		if(last_name.equals("0")) last_name = null;
		if(email.equals("0")) email = null;
		if(phone_number.equals("0")) phone_number = null;
		if(job_id.equals("0")) job_id = null;
		if(salary==0) salary = null;
		if(commission_pct==0) commission_pct = null;
		if(manager_id==0) manager_id = null;
		if(department_id==0) department_id = null;
		
		EmpDTO emp = EmpDTO.builder().commission_pct(commission_pct).department_id(department_id).email(email)
				.employee_id(employee_id).first_name(first_name).hire_date(hire_date).job_id(job_id)
				.last_name(last_name).manager_id(manager_id).phone_number(phone_number).salary(salary).build();
		return emp;
	}
	
	static EmpDTO makeEmp2(int employee_id1) {
		int employee_id = employee_id1;
		
		System.out.print("first_name>>");
		String first_name = sc.next();
		
		System.out.print("last_name>>");
	    String last_name = sc.next();
	    
	    System.out.print("email>>");
	    String email = sc.next();
	    
	    System.out.print("phone_number(000.000.000)>>");
	    String phone_number = sc.next();
	    
	    System.out.print("hire_date(yyyy-mm-dd)>>");
	    String hdate = sc.next();
	    Date hire_date = null;
	    if(!hdate.equals("0")) {
	    	hire_date = DateUtil.convertToSQLDate(DateUtil.convertToDate(hdate));
		}
	 
	    System.out.print("job_id(FK IT_PROG)>>");
	    String job_id = sc.next();
	    
	    System.out.print("salary>>");
	    Double salary = sc.nextDouble();
	    
	    System.out.print("commission_pct(0.00)>>");
	    Double commission_pct = sc.nextDouble();
	    
	    System.out.print("manager_id(FK)>>");
	    Integer manager_id = sc.nextInt();
	    
	    System.out.print("department_id(FK)>>");
	    Integer department_id = sc.nextInt();
	    
	    if(first_name.equals("0")) {
	    	first_name = null;
		}
	    if(last_name.equals("0")) {
	    	last_name = null;
		}
	    if(email.equals("0")) {
	    	email = null;
		}
	    if(phone_number.equals("0")) {
	    	email = null;
		}
	   
	    if(job_id.equals("0")) {
	    	job_id = null;
		}
	    if(salary ==0.0) {
	    	salary = null;
		}
	    if(commission_pct ==0.0)  {
	    	commission_pct = null;
		}
	    if(manager_id==0) {
	    	manager_id = null;
		}
	    if(department_id==0) {
	    	department_id = null;
		}
	 
	    EmpDTO emp = EmpDTO.builder()
	    		.employee_id(employee_id)
	    		.first_name(first_name)
	    		.last_name(last_name)
	    		.email(email)
	    		.phone_number(phone_number)
	    		.hire_date(hire_date)
	    		.job_id(job_id)
	    		.salary(salary)
	    		.commission_pct(commission_pct)
	    		.manager_id(manager_id)
	    		.department_id(department_id)
	    		.build();
	    return emp;
	}

	private static void f_insertById() {
		System.out.print("삽입할 직원ID>>");
		int employee_id = sc.nextInt();
	    int result = empService.empInsert(makeEmp(employee_id));
	    EmpView.display(result+"건 삽입");
	}
	
	


	private static void f_deleteById() {
		System.out.print("삭제할 직원 ID>>");
		int empid = sc.nextInt();
		int result = empService.empDeleteById(empid);
		EmpView.display(result+"건 삭제");
	}


	private static void f_selectByCondition() {
		//=부서, like 직책, >= 급여, >= 입사일
		
		System.out.print("조회할 부서 ID>>");
		String deptid = sc.next();
		String[] arr = deptid.split(",");
		
		System.out.print("조회할 직책>>");
		String jobid = sc.next();
		
		System.out.print("조회할 급여>>");
		int salary = sc.nextInt();
		
		System.out.print("조회할 입사일(yyy-mm-dd)>>");
		String hdate = sc.next();
		
		List<EmpDTO> emplist = empService.selectByCondition(arr,jobid,salary,hdate);
		EmpView.display(emplist);
	}


	private static void f_selectByJobAndDept() {
		System.out.print("조회할 직책, 부서 ID>>");
		String data = sc.next();
		String[] arr = data.split(",");
		String jobid = arr[0];
		int deptid = Integer.parseInt(arr[1]);
		List<EmpDTO> emplist = empService.selectByJobAndDept(jobid,deptid);
		EmpView.display(emplist);
		
	}


	private static void f_selectByJob() {
		System.out.print("조회할 직책 ID>>");
		String jobid = sc.next();
		List<EmpDTO> emplist = empService.selectByJob(jobid);
		EmpView.display(emplist);
	}
		
	private static void f_selectByDept() {
		System.out.print("조회할 부서ID>>");
		int deptid = sc.nextInt();
		List<EmpDTO> emplist = empService.selectByDept(deptid);
		EmpView.display(emplist);
	}

	private static void f_selectById() {
		System.out.print("조회할 ID>>");
		int empid = sc.nextInt();
		EmpDTO emp = empService.selectById(empid);
		EmpView.display(emp);
	}

	private static void f_selectAll() {
		List<EmpDTO> emplist = empService.selectAll();
		EmpView.display(emplist);
	}

	private static void menuDisplay() {
		System.out.println("------------------------------------------------------------------");
		System.out.println("1.모두조회 | 2.조회(직원번호) | 3.조회(부서) | 4.조회(직책) | 5.조회(직책,부서)");
		System.out.println("6.조회(부서,직책,급여,입사일) | 7.삭제(직원번호) | 8. 삽입 | 9.수정 | 10.끝");
		System.out.println("-------------------------------------------------------------------");
		System.out.print("작업을 선택하세요>>");
	}

}
