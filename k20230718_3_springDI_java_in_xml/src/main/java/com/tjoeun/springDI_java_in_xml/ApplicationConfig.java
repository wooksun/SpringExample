package com.tjoeun.springDI_java_in_xml;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

//	java 파일에서 xml 파일의 bean 설정 정보를 읽어오려면 @ImportResource 어노테이션으로 읽어들일 xml 파일을 java 파일에 포함시키면 된다.
@ImportResource("classpath:applicationCTX.xml")

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
