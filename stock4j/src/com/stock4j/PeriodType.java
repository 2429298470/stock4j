package com.stock4j;

/**
 * 数据周期
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum PeriodType {

	YEAR("年线"),
	MONTH("月线"),
	WEEK("周线"),
	DAY("日线"),
	MIN60("60分钟"),
	MIN30("30分钟"),
	MIN15("15分钟"),
	MIN5("5分钟"),
	MIN1("1分钟");

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
	 * 是否为日线以下的周期
	 * @return
	 */
	public boolean isBelowDay(){
		return this.name().startsWith("MIN");
	}

}
