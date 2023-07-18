package com.tjoeun.springDI_java;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		/*
		//	xml 파일에서 설정한 bean 설정 정보를 읽어오려면 아래의 방법을 사용한다.
		String configLocation = "classpath:applicationCTX.xml";
		AbstractApplicationContext ctx = new GenericXmlApplicationContext(configLocation);
		
		//	getBean("id", bean을 생성한 클래스 이름.class);
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		*/
		
		//	java 파일에서 설정한 bean 설정 정보를 읽어오려면 아래의 방법을 사용한다.
		//	AnnotationConfigApplicationContext(bean을 설정한 클래스 이름.class)
		AnnotationConfigApplicationContext ctx = 
				new AnnotationConfigApplicationContext(ApplicationConfig.class);
		//	getBean("메소드 이름", 메소드의 리턴타입.class);
		Student student = ctx.getBean("student", Student.class);
		System.out.println(student);
		
	}

}
