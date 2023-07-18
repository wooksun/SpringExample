package com.tjoeun.springProfile_java;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("실행할 작업 환경을 입력하세요 (1: dev / 2: run) : ");
		int info = scanner.nextInt();
		String config = "";
		switch (info) {
		case 1:
			config = "dev";
			break;
		case 2:
			config = "run";
			break;
		default:
			break;
		}
		
		//	xml로 할 때 방식
		//	profile이 설정된 xml 파일의 bean을 읽어오기 위해서는 DI 컨테이너를 먼저 만든 후 읽어올 bean의 profile을 지정한 다음
		//	해당 profile이 지정된 bean을 load시켜야 한다.
		//	GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); // xml
		
		//	java로 할 때 방식
		//	profile이 설정된 xml 파일의 bean을 읽어오기 위해서는 DI 컨테이너를 먼저 만든 후 읽어올 bean의 profile을 지정한 다음
		//	해당 profile이 지정된 bean을 load시켜야 한다.
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(); // java
		
		//	읽어올 bean의 profile을 넣어준다. (xml, java 동일)
		ctx.getEnvironment().setActiveProfiles(config);
		
		//	xml로 할 때 방식
		//	ctx.load("classpath:applicationCTX_dev.xml", "classpath:applicationCTX_run.xml");
		
		//	java로 할 때 방식
		//	GenericXmlApplicationContext 클래스로 xml 파일에서 설정한 bean 설정 정보를 DI 컨테이너에 넣어주기 위해서 load() 메소드를
		//	사용했지만 AnnotationConfigApplicationContext 클래스로 java 파일에서 @Profile 어노테이션을 붙여서 
		//	설정한 bean 설정 정보를 넣어주려면
		ctx.register(ApplicationConfigDev.class, ApplicationConfigRun.class); // java
		ctx.refresh();
		
		ServerInfo serverInfo = ctx.getBean("serverInfo", ServerInfo.class);
		System.out.println(serverInfo);
		
		ctx.close();
	}

}
