package com.stock4j;

/**
 * 复权类型：前复权、后复权、不复权
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum ExRightType {

	NO("不复权"),
	FORWARD("前复权"),
	BACKWARD("后复权");

	private String name;

	private ExRightType(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
