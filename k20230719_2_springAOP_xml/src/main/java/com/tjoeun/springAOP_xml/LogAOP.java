package com.tjoeun.springAOP_xml;

import org.aspectj.lang.ProceedingJoinPoint;

//	공통 기능 메소드를 모아놓은 클래스
public class LogAOP {

	//	before
	public void before() {
		System.out.println("LogAOP 클래스의 before() 메소드가 실행 됨");
	}

	//	after-returning
	public void afterReturning() {
		System.out.println("LogAOP 클래스의 afterReturning() 메소드가 실행 됨");
	}
	
	//	after-throwing
	public void afterThrowing() {
		System.out.println("LogAOP 클래스의 afterThrowing() 메소드가 실행 됨");
	}
	
	//	after
	public void after() {
		System.out.println("LogAOP 클래스의 after() 메소드가 실행 됨");
	}
	
	//	around
	//	1. around AOP 메소드는 핵심 기능이 실행되고 난 후 리턴되는 데이터 타입을 예측할 수 없으므로 모든 데이터 타입을 포함할 수 있는
	//	자바의 최상위 클래스인 Object로 지정한다.
	//	2. around AOP 메소드의 인수인 ProceedingJoinPoint 인터페이스 타입의 객체로 스프링이 자동으로 실행할 핵심 기능(메소드)을 넘겨준다.
	//		=> 반드시 인수로 ProceedingJoinPoint 인터페이스 타입의 객체를 사용한다.
	//		=> pom.xml 파일에 aspectjweaver dependency에 <scope>runtime</scope>이 있으면 ProceedingJoinPoint 인터페이스르
	//			사용할 수 없으므로 <scope>runtime</scope>를 삭제하거나 주석으로 처리한다.
	//	3. around AOP 메소드는 try ~ finally 형태로 실행하며 catch는 throws Throwable로 처리한다.
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		//	핵심 기능이 실행되지 전 실행할 내용을 코딩한다.
		System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 전");
		long start = System.currentTimeMillis(); // 시작 시간
		try {
			//	핵심 기능을 실행한다.
			System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 중");
			//	핵심 기능을 실행한 결과를 리턴시킨다.
			//	ProceedingJoinPoint 인터페이스 객체로 넘어온 핵심 기능을 실행하고 실행결과를 Object 클래스 객체에 저장해서 리턴시킨다.
			Thread.sleep(2000);
			Object object = joinPoint.proceed(); // 핵심기능을 실행한다.
			return object;
		} finally {
			//	핵심 기능이 실행되고 난 후 실행할 내용을 코딩한다.
			System.out.println("LogAop 클래스의 around() 메소드가 실행됨 - 핵심 기능 실행 후");
			long end = System.currentTimeMillis(); // 종료 시간
			System.out.println("핵심 기능이 실행되는데 경과된 시간: " + (end - start) / 1000. + "초");
		}
	}
}
