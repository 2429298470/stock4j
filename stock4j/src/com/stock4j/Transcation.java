package com.stock4j;

import java.time.LocalDateTime;

import eu.verdelhan.ta4j.Order.OrderType;

/**
 * ����ͼ��ʱ����
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Transcation {
	private LocalDateTime tdate;
	private Double price;
	/**�ɽ���*/
	private Double amount;
	/**�ɽ���*/
	private Long volume;
	/**����*/
	private Double meanPrice;
	/**��������*/
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
