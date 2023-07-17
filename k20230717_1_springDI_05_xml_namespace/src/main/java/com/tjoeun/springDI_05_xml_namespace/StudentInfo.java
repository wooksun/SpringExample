package com.tjoeun.springDI_05_xml_namespace;

public class StudentInfo {
	
	private Student student;
	
	//	기본 생성자
	public StudentInfo() { }
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
	
	//	getStudentInfo()
	public void getStudentInfo() {
		System.out.println("이름: " + student.getName());
		System.out.println("나이: " + student.getAge());
		System.out.println("취미: " + student.getHobbies());
		System.out.println("신장: " + student.getHeight());
		System.out.println("체중: " + student.getWeight());
	}
	
}
