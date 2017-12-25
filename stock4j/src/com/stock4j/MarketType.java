package com.stock4j;

/**
 * 市场类型：上证A股、深证A股
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum MarketType {
	/** 上海证券交易所  */
	SH("上海证券交易所"),
	/** 深圳证券交易所 */
	SZ("深圳证券交易所");

	private String name;
	
	private MarketType(String name){
		this.setName(name);
	}

	/**
	 * 交易所的名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**交易所的名称*/
	public void setName(String name) {
		this.name = name;
	}
}
