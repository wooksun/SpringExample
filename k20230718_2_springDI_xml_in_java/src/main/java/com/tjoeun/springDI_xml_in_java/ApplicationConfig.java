package com.tjoeun.springDI_xml_in_java;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration	
public class ApplicationConfig {
	
	@Bean
	public Student student2() {
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("축구");
		hobbies.add("영화보기");
		hobbies.add("음악듣기");
		Student student = new Student("최진욱java", 25, hobbies);
		student.setHeight(185);
		student.setWeight(83);
		return student; // 초기화된 bean 객체를 리턴시킨다.
	}
	
}
