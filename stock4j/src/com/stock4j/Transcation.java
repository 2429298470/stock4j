package com.stock4j;

import java.time.LocalDateTime;

import eu.verdelhan.ta4j.Order.OrderType;

/**
 * 闪电图分时数据
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Transcation {
	private LocalDateTime tdate;
	private Double price;
	/**成交额*/
	private Double amount;
	/**成交量*/
	private Long volume;
	/**均价*/
	private Double meanPrice;
	/**交易类型*/
	private OrderType orderType;

	public LocalDateTime getTdate() {
		return tdate;
	}

	public void setTdate(LocalDateTime tdate) {
		this.tdate = tdate;
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

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}
	
	public Double getMeanPrice() {
		return meanPrice;
	}

	public void setMeanPrice(Double meanPrice) {
		this.meanPrice = meanPrice;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return "Transcation [tdate=" + tdate + ", price=" + price + ", amount=" + amount + ", volume=" + volume
				+ ", meanPrice=" + meanPrice + ", orderType=" + orderType + "]";
	}
}
