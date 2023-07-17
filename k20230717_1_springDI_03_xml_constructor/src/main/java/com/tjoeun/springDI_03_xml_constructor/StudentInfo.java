package com.tjoeun.springDI_03_xml_constructor;

public class StudentInfo {
	
	private Student student;
	
	//	기본 생성자
	public StudentInfo() {
		
	}
	
	//	넘겨받아 초기화 시키는 생성자
	public StudentInfo(Student student) {
		this.student = student;
	}

	//	getters & setters
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	//	toString()
	@Override
	public String toString() {
		return "StudentInfo [student=" + student + "]";
	}
	
	public void getStudentInfo() {
		System.out.println("이름: " + student.getName());
		System.out.println("나이: " + student.getAge());
		System.out.println("학년: " + student.getGrandNum());
		System.out.println("반: " + student.getClassNum());
	}
	
	
}
