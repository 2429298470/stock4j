package com.stock4j;

/**
 * ��Ȩ���ͣ�ǰ��Ȩ����Ȩ������Ȩ
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum ExRightType {

	NO("����Ȩ"),
	FORWARD("ǰ��Ȩ"),
	BACKWARD("��Ȩ");

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
