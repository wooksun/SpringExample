package com.tjoeun.springProfile_java;

public class ServerInfo {
	
	private String ipNumber;
	private String portNumber;
	
	//	getter & setter
	public String getIpNumber() {
		return ipNumber;
	}
	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	
	//	toString()
	@Override
	public String toString() {
		return "ServerInfo [ipNumber=" + ipNumber + ", portNumber=" + portNumber + "]";
	}
	
	
	
}
