package com.stock4j;

/**
 * ��Ʊ������Ϣ
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Stock {
	/**��Ʊ����*/
	private String scode;
	/**֤ȯ����*/
	private String sname;
	/**ƴ������*/
	private String abbr;
	/**�����г�*/
	private MarketType market;

	public Stock() {
		super();
	}
	
	/**
	 * @param scode���Զ��жϲ����������г�������
	 */
	public Stock(String scode) {
		super();
		scode = scode.toUpperCase();
		if(scode.startsWith("SH") || scode.startsWith("SZ")){
			this.scode = scode.substring(2);
			this.market = MarketType.valueOf(scode.substring(0, 2));
		} else {
			this.scode = scode;
			if(scode.startsWith("6")){
				this.market = MarketType.SH;
			}else{
				this.market = MarketType.SZ;
			}
		}
	}

	public Stock(String scode, MarketType market) {
		super();
		this.scode = scode;
		this.market = market;
	}

	public String getScode() {
		return scode;
	}

	/**
	 * ���г���֤ȯ����
	 * @return
	 */
	public String getFullScode() {
		return market.name().toLowerCase() + scode;
	}

	/**
	 * ��Ʊ����
	 * @param scode
	 */
	public void setScode(String scode) {
		this.scode = scode;
	}

	/**֤ȯ����*/
	public String getSname() {
		return sname;
	}

	/**֤ȯ����*/
	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((market == null) ? 0 : market.hashCode());
		result = prime * result + ((scode == null) ? 0 : scode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (market != other.market)
			return false;
		if (scode == null) {
			if (other.scode != null)
				return false;
		} else if (!scode.equals(other.scode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stock [scode=" + scode + ", sname=" + sname + ", abbr=" + abbr + ", market=" + market + "]";
	}

	
}
