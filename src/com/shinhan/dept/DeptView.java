package com.shinhan.dept;

import java.util.List;

//View(Web에서 JSP로 변경될 예정이다)
public class DeptView {
	//여러 건 출력
	public static void display(List<DeptDTO> deptlist) {
		if(deptlist.size() == 0) {
			display("존재하는 데이터가 없습니다.");
		} else {
			System.out.println("======= 부서목록 =======");
			deptlist.stream().forEach(dept->display(dept));
		}
	}
	
	//한 건 출력
	public static void display(DeptDTO dept) {
		System.out.println("부서번호: " + dept.getDepartment_id());
		System.out.println("부서이름: " + dept.getDepartment_name());
		System.out.println("매니저 번호: " + dept.getManager_id());
		System.out.println("지역코드: " + dept.getLocation_id());
		System.out.println("-----------------------------");
	}
	
	//메시지 출력
	public static void display(String message) {
		System.out.println("알림: " + message);
	}

	public static void menuDisplay() {
		System.out.println("-------------");
		System.out.println("1.모든 부서 조회(all)");
		System.out.println("2.부서코드로 조회(detail)");
		System.out.println("3.새 부서 삽입(i)");
		System.out.println("4.부서코드로 수정(u)");
		System.out.println("5.부서코드로 삭제(d)");
		System.out.println("9.프로그램 종료(exit)");
		System.out.println("-------------");
		System.out.print("작업>> ");
	}
}
