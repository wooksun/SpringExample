package com.tjoeun.springProperties_Environment;

import java.io.IOException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class MainClass {

	public static void main(String[] args) {
		
		//	ConfigurableApplicationContext 인터페이스 타입으로 환경설정 정보를 읽어올 객체(컨테이너)를 만든다.
		ConfigurableApplicationContext ctx = new GenericXmlApplicationContext();
		
		//	ConfigurableApplicationContext 인터페이스 타입의 객체에서 컨테이너 환경 설정 정보를 getEnvironment() 메소드로 얻어와서
		//	ConfigurableEnvironment 인터페이스 객체에 저장한다.
		ConfigurableEnvironment env = ctx.getEnvironment();
		
		//	properties 정보를 저장하는 MutablePropertySources 클래스 타입의 객체에 ConfigurableEnvironment 인터페이스 타입의 객체에서
		//	getPropertySources() 메소드로 properties 정보만 얻어와 저장한다.
		MutablePropertySources mutablePropertySources =  env.getPropertySources();
		
		try {
			//	DI 컨테이너에서 얻어온 properties 정보가 저장되는 MutablePropertySources 클래스 객체
			//	mutablePropertySources에 addLast() 매소드를 사용해서 맨 마지막 위치에 admin.properties 파일의
			//	properties 내용을 읽어 추가한다.
			mutablePropertySources.addLast(new ResourcePropertySource("classpath:admin.properties"));
			//	DI 컨테이너 환경에 추가된 properties 파일의 내용은 ConfigurableEnvironment 인터페이스 객체에서 getProperty() 메소드로
			//	읽어올 수 있다.
			System.out.println("admin.id: " + env.getProperty("admin.id"));
			System.out.println("admin.pw: " + env.getProperty("admin.pw"));
			System.out.println("properties 파일의 내용을 읽어서 DI 컨테이너에 저장한 후 얻어온다.");
		} catch (IOException e) {
			System.out.println("admin.properties 파일을 읽어올 수 없습니다.");
			e.printStackTrace();
		}
		System.out.println("============================================================================");
		
		//	AdminConnection 클래스의 bean을 얻어온다.
		AbstractApplicationContext gCtx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");	
		AdminConnection adminConnection = gCtx.getBean("adminConnection", AdminConnection.class);
		System.out.println(adminConnection); // null, null
		System.out.println("============================================================================");
		//	AdminConnection 클래스의 bean이 생성된 후 환경설정 정보에 저장된 admin.properties 파일의 정보를 넘겨준다.
		System.out.println("AdminConnection 클래스의 bean이 생성된 후 환경설정 정보에 저장된 properties 파일의 정보를 넣어준다.");
		adminConnection.setAdminId(env.getProperty("admin.id"));
		adminConnection.setAdminPw(env.getProperty("admin.pw"));
		System.out.println(adminConnection); // admin.properties에 저장된 id와 pw값이 출력
	}

}
