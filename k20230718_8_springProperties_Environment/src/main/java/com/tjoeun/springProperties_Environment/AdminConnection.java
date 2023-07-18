package com.tjoeun.springProperties_Environment;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

public class AdminConnection implements InitializingBean, DisposableBean, EnvironmentAware {
	
	private String adminId;
	private String adminPw; 
	private Environment env; // DI 컨테이너의 환경설정 정보를 저장한다.
	
	//	기본 생성자
	public AdminConnection() {
		System.out.println("AdminConnection 클래스의 bean이 생성되었습니다.");
	}

	//	getters & setters
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminPw() {
		return adminPw;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	public Environment getEnvironment() {
		return env;
	}


	//	toString()
	@Override
	public String toString() {
		return "AdminConnection [adminId=" + adminId + ", adminPw=" + adminPw + "]";
	}


	//	EnvironmentAware 인터페이스를 구현하면 사용할 수 있고, EnvironmentAware 인터페이스가 
	//	구현된 클래스의 bean이 생성된 후 자동으로 실행되는 메소드
	//	setEnvironment() 메소드의 인수인 DI 컨테이너의 환경설정 정보를 기억하는 Environment 인터페이스 타입의 객체 environment에
	//	스프링이 알아서 EnvironmentAware 인터페이스가 구현된 클래스의 bean이 생성되는 순간 DI 컨테이너의 환경설정 정보를 넘겨준다.
	//		=> properties 파일의 정보가 넘어온다.
	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("AdminConnection 클래스의 bean이 생성된 후 자동으로 setEnvironment() 메소드 실행");
//		System.out.println("admin id: " + environment.getProperty("admin.id"));
//		System.out.println("admin pw: " + environment.getProperty("admin.pw"));
		//	Environment 인터페이스 객체로 넘어온 DI 컨테이너의 환경설정 정보를 AdminConnection 클래스에서 사용하기 위해
		//	필드로 선언한 Environment 인터페이스 갹채애 env에 저장한다.
		env = environment;
	}

	//	DisposableBean 인터페이스를 구현하면 사용할 수 있고, bean이 소멸될 때 자동으로 실행되는 메소드
	@Override
	public void destroy() throws Exception {
		System.out.println("AdminConnection 클래스의 bean이 소멸된 후 자동으로 destroy() 메소드 실행");
	}

	//	InitializingBean 인터페이스를 구현하면 사용할 수 있고, bean이 생성될 때 자동으로 실행되는 메소드
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("생성자가 실행되고 난 후 자동으로 afterPropertiesSet() 매소드 실행");
		//	 Environment 인터페이스 객체 env에 저장된 환경설정 정보의 properties 정보를 필드에 넣어준다.
		adminId = env.getProperty("admin.id");
		adminPw = env.getProperty("admin.pw");
		
	}
	
	
}
