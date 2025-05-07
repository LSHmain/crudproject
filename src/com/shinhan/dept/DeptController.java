package com.shinhan.dept;

import java.util.Scanner;

import com.shinhan.common.CommonControllerInterface;

//Controller(사용자의 요청을 처리하고 응답을 보냄 -> Servlet으로 변경 예정)
public class DeptController implements CommonControllerInterface {
	
	static Scanner sc = new Scanner(System.in);
	static DeptService deptService = new DeptService();
	@Override
	public void execute() {
		boolean isStop = false;
		
		while(!isStop) {
			DeptView.menuDisplay();
			String job = sc.nextLine();
			switch(job) {
			case "all"->{ f_selectAll(); }
			case "detail"->{ f_selectById(); }
			case "i"->{ f_insertDept(); }
			case "u"->{ f_updateDept(); }
			case "d"->{ f_deleteDept(); }
			case "exit"->{ isStop = true; }
			}
		}
		DeptView.display("========= Good Bye ========");
	}
	
	private static void f_selectAll() {
		DeptView.display( deptService.selectAll() );
	}
	
	private static void f_selectById() {
		int department_id = Integer.parseInt(dataInsert("조회할 부서ID>> "));
		DeptView.display( deptService.selectById(department_id) );
	}
	
	private static String dataInsert(String column) {
		System.out.print(column);
		return sc.nextLine();
	}
	
	private static DeptDTO makeDept(String title) {
		int department_id = Integer.parseInt(dataInsert(title + "부서ID>> "));
		String department_name = dataInsert(title + "부서명>> ");
		int manager_id = Integer.parseInt(dataInsert(title + "매니저ID>> "));
		int location_id = Integer.parseInt(dataInsert(title + "지역ID>> "));
		
		DeptDTO dept = DeptDTO.builder()
				.department_id(department_id)
				.department_name(department_name)
				.manager_id(manager_id)
				.location_id(location_id)
				.build();
		
		return dept;
	}

	private static void f_insertDept() {
		DeptDTO dept = makeDept("삽입할 ");
		DeptView.display( deptService.insertDept(dept) + "건 삽입" );
	}

	private static void f_updateDept() {
		DeptDTO dept = makeDept("수정할 ");
		DeptView.display( deptService.updateDept(dept) + "건 수정" );
	}

	private static void f_deleteDept() {
		int department_id = Integer.parseInt(dataInsert("삭제할 부서ID>> "));
		DeptView.display( deptService.deleteDept(department_id) + "건 삭제" );
	}
	
}
