package com.tjoeun.springAOP_java;

public class Student {

	private String name;
	private int age;
	private int grandNum;
	private int classNum;
	
	//	기본 생성자
	public Student() { }

	//	넘겨받아 초기화 시키는 생성자
	public Student(String name, int age, int grandNum, int classNum) {
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
	
	//	Student 클래스의 핵심 기능
	public void getStudentInfo() {
		System.out.println("Student 클래스의 핵심 기능");
		System.out.println("이름: " + name);
		System.out.println("나이: " + age);
		System.out.println("학년: " + grandNum);
		System.out.println("반: " + classNum);
	}
	
	public void test() {
		System.out.println("Student 클래스의 test() 메소드 실행");
		int i = 10 / 0;
	}
}
