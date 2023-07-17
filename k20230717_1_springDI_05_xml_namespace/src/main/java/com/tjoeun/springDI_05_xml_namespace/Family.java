package com.tjoeun.springDI_05_xml_namespace;

public class Family {
	
	private String papaName;
	private String maniName;
	private String sisterName;
	private String brotherName;
	
	//	기본생성자
	public Family() {}
	public Family(String papaName, String maniName) {
		super();
		this.papaName = papaName;
		this.maniName = maniName;
	}
	
	//	getters & setters
	public String getPapaName() {
		return papaName;
	}

	public void setPapaName(String papaName) {
		this.papaName = papaName;
	}

	public String getManiName() {
		return maniName;
	}

	public void setManiName(String maniName) {
		this.maniName = maniName;
	}

	public String getSisterName() {
		return sisterName;
	}

	public void setSisterName(String sisterName) {
		this.sisterName = sisterName;
	}

	public String getBrotherName() {
		return brotherName;
	}

	public void setBrotherName(String brotherName) {
		this.brotherName = brotherName;
	}

	//	toString()
	@Override
	public String toString() {
		return "Family [papaName=" + papaName + ", maniName=" + maniName + ", sisterName=" + sisterName
				+ ", brotherName=" + brotherName + "]";
	}
	
	
}
