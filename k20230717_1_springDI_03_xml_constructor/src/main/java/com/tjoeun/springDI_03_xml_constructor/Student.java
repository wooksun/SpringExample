package com.tjoeun.springDI_03_xml_constructor;

public class Student {

	private String name;
	private int age;
	private int grandNum;
	private int classNum;
	
	//	기본 생성자
	public Student() { 
		System.out.println("Student 클래스의 기본 생성자로 bean을 만든다.");
	}

	//	넘겨받아 초기화 시키는 생성자
	public Student(String name, int age, int grandNum, int classNum) {
		System.out.println("데이터를 전달받아 초기화시키는 생성자 실행");
		this.name = name;
		this.age = age;
		this.grandNum = grandNum;
		this.classNum = classNum;
	}

	// getters & setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getGrandNum() {
		return grandNum;
	}

	public void setGrandNum(int grandNum) {
		this.grandNum = grandNum;
	}

	public int getClassNum() {
		return classNum;
	}

	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}

	//	toString()
	@Override
	public String toString() {
		return "Student [name=" + name + ", age=" + age + ", grandNum=" + grandNum + ", classNum=" + classNum + "]";
	}
	
	
	
}
