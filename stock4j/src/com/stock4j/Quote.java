package com.stock4j;

import java.time.LocalDateTime;

/**
 * 盘口报价
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Quote {
	private LocalDateTime time;
	/**当前价格*/
	private Double price;
	/**成交额*/
	private Double amount;
	/**昨日收盘价*/
	private Double zs;
	/**今日开盘价*/
	private Double open;
	/**最低价*/
	private Double low;
	/**最高价*/
	private Double high;
	/**今日总成交量，单位：股*/
	private Long totalVol; 
	/**今日总成交额，单位：元*/
	private Double totalAmount;
	/**内盘*/
	private Long np;
	/**外盘*/
	private Long wp;

	private Double buyPrice1;
	private Long buyVol1;
	private Double buyPrice2;
	private Long buyVol2;
	private Double buyPrice3;
	private Long buyVol3;
	private Double buyPrice4;
	private Long buyVol4;
	private Double buyPrice5;
	private Long buyVol5;

	private Double sellPrice1;
	private Long sellVol1;
	private Double sellPrice2;
	private Long sellVol2;
	private Double sellPrice3;
	private Long sellVol3;
	private Double sellPrice4;
	private Long sellVol4;
	private Double sellPrice5;
	private Long sellVol5;

	public Quote() {
		super();
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getZs() {
		return zs;
	}

	public void setZs(Double zs) {
		this.zs = zs;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Long getTotalVol() {
		return totalVol;
	}

	public void setTotalVol(Long totalVol) {
		this.totalVol = totalVol;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getNp() {
		return np;
	}

	public void setNp(Long np) {
		this.np = np;
	}

	public Long getWp() {
		return wp;
	}

	public void setWp(Long wp) {
		this.wp = wp;
	}

	public Double getBuyPrice1() {
		return buyPrice1;
	}

	public void setBuyPrice1(Double buyPrice1) {
		this.buyPrice1 = buyPrice1;
	}

	/**买一量*/
	public Long getBuyVol1() {
		return buyVol1;
	}
	
	/**买一量*/
	public void setBuyVol1(Long buyVol1) {
		this.buyVol1 = buyVol1;
	}

	/**买二价*/
	public Double getBuyPrice2() {
		return buyPrice2;
	}

	/**买二价*/
	public void setBuyPrice2(Double buyPrice2) {
		this.buyPrice2 = buyPrice2;
	}

	/**买二量*/
	public Long getBuyVol2() {
		return buyVol2;
	}

	/**买二量*/
	public void setBuyVol2(Long buyVol2) {
		this.buyVol2 = buyVol2;
	}

	public Double getBuyPrice3() {
		return buyPrice3;
	}

	public void setBuyPrice3(Double buyPrice3) {
		this.buyPrice3 = buyPrice3;
	}

	public Long getBuyVol3() {
		return buyVol3;
	}

	public void setBuyVol3(Long buyVol3) {
		this.buyVol3 = buyVol3;
	}

	public Double getBuyPrice4() {
		return buyPrice4;
	}

	public void setBuyPrice4(Double buyPrice4) {
		this.buyPrice4 = buyPrice4;
	}

	public Long getBuyVol4() {
		return buyVol4;
	}

	public void setBuyVol4(Long buyVol4) {
		this.buyVol4 = buyVol4;
	}

	public Double getBuyPrice5() {
		return buyPrice5;
	}

	public void setBuyPrice5(Double buyPrice5) {
		this.buyPrice5 = buyPrice5;
	}

	public Long getBuyVol5() {
		return buyVol5;
	}

	public void setBuyVol5(Long buyVol5) {
		this.buyVol5 = buyVol5;
	}

	public Double getSellPrice1() {
		return sellPrice1;
	}

	public void setSellPrice1(Double sellPrice1) {
		this.sellPrice1 = sellPrice1;
	}

	public Long getSellVol1() {
		return sellVol1;
	}

	public void setSellVol1(Long sellVol1) {
		this.sellVol1 = sellVol1;
	}

	public Double getSellPrice2() {
		return sellPrice2;
	}

	public void setSellPrice2(Double sellPrice2) {
		this.sellPrice2 = sellPrice2;
	}

	public Long getSellVol2() {
		return sellVol2;
	}

	public void setSellVol2(Long sellVol2) {
		this.sellVol2 = sellVol2;
	}

	public Double getSellPrice3() {
		return sellPrice3;
	}

	public void setSellPrice3(Double sellPrice3) {
		this.sellPrice3 = sellPrice3;
	}

	public Long getSellVol3() {
		return sellVol3;
	}

	public void setSellVol3(Long sellVol3) {
		this.sellVol3 = sellVol3;
	}

	public Double getSellPrice4() {
		return sellPrice4;
	}

	public void setSellPrice4(Double sellPrice4) {
		this.sellPrice4 = sellPrice4;
	}

	public Long getSellVol4() {
		return sellVol4;
	}

	public void setSellVol4(Long sellVol4) {
		this.sellVol4 = sellVol4;
	}

	public Double getSellPrice5() {
		return sellPrice5;
	}

	public void setSellPrice5(Double sellPrice5) {
		this.sellPrice5 = sellPrice5;
	}

	public Long getSellVol5() {
		return sellVol5;
	}

	public void setSellVol5(Long sellVol5) {
		this.sellVol5 = sellVol5;
	}

	@Override
	public String toString() {
		return "Quote [time=" + time + ", price=" + price + ", amount=" + amount + ", zs=" + zs
				+ ", open=" + open + ", low=" + low + ", high=" + high + ", totalVol=" + totalVol + ", totalAmount="
				+ totalAmount + ", np=" + np + ", wp=" + wp + ", buyPrice1=" + buyPrice1 + ", buyVol1=" + buyVol1
				+ ", buyPrice2=" + buyPrice2 + ", buyVol2=" + buyVol2 + ", buyPrice3=" + buyPrice3 + ", buyVol3="
				+ buyVol3 + ", buyPrice4=" + buyPrice4 + ", buyVol4=" + buyVol4 + ", buyPrice5=" + buyPrice5
				+ ", buyVol5=" + buyVol5 + ", sellPrice1=" + sellPrice1 + ", sellVol1=" + sellVol1 + ", sellPrice2="
				+ sellPrice2 + ", sellVol2=" + sellVol2 + ", sellPrice3=" + sellPrice3 + ", sellVol3=" + sellVol3
				+ ", sellPrice4=" + sellPrice4 + ", sellVol4=" + sellVol4 + ", sellPrice5=" + sellPrice5 + ", sellVol5="
				+ sellVol5 + "]";
	}

}
