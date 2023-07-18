package com.tjoeun.springLifeCycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Person implements InitializingBean, DisposableBean {
	
	private String name;
	private int age;
	
	//	기본 생성자
	public Person() {
		System.out.println("기본 생성자");
	}
	
	//	인수 넘겨받아 초기화 시키는 생성자
	public Person(String name, int age) {
		System.out.println("name, age를 인수로 넘겨받아 초기화 시키는 생성자");
		this.name = name;
		this.age = age;
	}

	//	getters & setters
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

	//	toString()
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

	//	implements InitializingBean을 쓰고, Person에 빨간줄 나오면 add Method하면 나옴
	//	InitializingBean 인터페이스를 구현받으면, afterPropertiesSet() 메소드를 반드시 Override 해서 사용해야 하고,
	//	afterPropertiesSet() 메소드에는 bean이 생성(생성자가 실행)된 후 자동으로 실행할 내용을 구현한다.
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet() 메소드 실행");
	}

	//	DisposableBean 인터페이스를 추가하면 Person에 빨간줄 -> add Method하면 나옴
	//	DisposableBean 인터페이스를 구현 받으면 destroy() 메소드를 반드시 Override 해서 사용해야 하고, destroy() 메소드는 bean이
	//	소멸(DI 컨테이너에서 close() 메소드가 실행)된 후, 자동으로 실행할 내용을 구현한다.
	@Override
	public void destroy() throws Exception {
		System.out.println("destroy() 메소드 실행");
	}
	
	
	
}
