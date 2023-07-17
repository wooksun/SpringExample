package com.tjoeun.springDI_03_xml_constructor;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
//		Student student = new Student();
//		student.setName("최진욱");
//		student.setAge(25);
//		student.setGrandNum(15);
//		student.setClassNum(3);
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
			
//		Student student2 = new Student("홍길동", 12, 5, 15);
//		System.out.println(student2);
		
		Student student2 = ctx.getBean("student2", Student.class);
		System.out.println(student2);
		
//		StudentInfo studentInfo = new StudentInfo();
//		studentInfo.setStudent(student);
//		studentInfo.getStudentInfo();
		
		StudentInfo studentInfo = ctx.getBean("studentInfo", StudentInfo.class);
		studentInfo.getStudentInfo();
		
//		StudentInfo studentInfo2 = new StudentInfo(student2);
//		studentInfo.getStudentInfo();
		
		StudentInfo studentInfo2 = ctx.getBean("studentInfo2", StudentInfo.class);
		studentInfo2.getStudentInfo();
	}

}
