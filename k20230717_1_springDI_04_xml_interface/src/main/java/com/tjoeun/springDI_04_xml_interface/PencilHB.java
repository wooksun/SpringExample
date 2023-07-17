package com.tjoeun.springDI_04_xml_interface;

public class PencilHB implements Pencil {

	@Override
	public void use() {
		System.out.println("HB 연필로 그림을 그립니다.");
	}

}
