package com.tjoeun.springDI_xml_in_java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

//	xml에서 java 불러오기
public class MainClass {

	public static void main(String[] args) {
		
	
		//	xml 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		
	
		
		//	java 
		//	AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		//	xml 파일에서 설정한 bean을 얻어온다.
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		
		//	java 파일에서 설정한 bean을 얻어온다.
		Student student2 = ctx.getBean("student2", Student.class);
		System.out.println(student2);
	}

}
