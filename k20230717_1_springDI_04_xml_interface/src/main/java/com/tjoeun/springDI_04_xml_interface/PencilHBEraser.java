package com.tjoeun.springDI_04_xml_interface;

public class PencilHBEraser implements Pencil {

	@Override
	public void use() {
		System.out.println("지우개 달린 연필로 그림을 그립니다.");
	}

}
