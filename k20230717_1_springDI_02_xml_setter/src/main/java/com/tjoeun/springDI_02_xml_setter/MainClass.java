package com.tjoeun.springDI_02_xml_setter;

import java.util.ArrayList;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {

		/*
		//	기존 자바 방식
		MyInfo myInfo = new MyInfo();
		myInfo.setName("최진욱");
		myInfo.setHeight(185);
		myInfo.setWeight(83);
		ArrayList<String> hobbies = new ArrayList<String>();
		hobbies.add("축구");
		hobbies.add("게임");
		hobbies.add("농구");
		hobbies.add("음악듣기");
		myInfo.setHobbies(hobbies);
		BMICalculator bmiCalculator = new BMICalculator();
		myInfo.setBmiCalculator(bmiCalculator);
		myInfo.getMyInfo();
		System.out.println(myInfo);
		*/
		
		
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		MyInfo myInfo = ctx.getBean("myInfo", MyInfo.class);
		myInfo.getMyInfo();
	}

}
