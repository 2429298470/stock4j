package com.stock4j;

/**
 * ��������
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum PeriodType {

	YEAR("����"),
	MONTH("����"),
	WEEK("����"),
	DAY("����"),
	MIN60("60����"),
	MIN30("30����"),
	MIN15("15����"),
	MIN5("5����"),
	MIN1("1����");

	private String name;

	private PeriodType(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * �Ƿ�Ϊ�������µ�����
	 * @return
	 */
	public boolean isBelowDay(){
		return this.name().startsWith("MIN");
	}

}
