package com.stock4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import eu.verdelhan.ta4j.Decimal;

/**
 * K线数据（开盘价，最高价，最低价，收盘价），继承于{@link eu.verdelhan.ta4j.Tick}
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Tick implements Comparable<Tick>, eu.verdelhan.ta4j.Tick{

	private static final long serialVersionUID = 1L;
	/**数据周期，默认为日线*/
	private Duration timePeriod = Duration.ofDays(1);
	/**该K线的开始时间*/
    private LocalDateTime beginDateTime;
    /**该K线的结束时间*/
    private LocalDateTime endDateTime;
    /**开盘价*/
	private Double open;
	/**最高价*/
	private Double high;
	/**最低价*/
	private Double low;
	/**收盘价*/
	private Double close;
	/**成交量，单位股*/
	private Long tradingVolume;
	/**成交额，单位元*/
	private Double turnover;
	/**交易次数*/
	private int trades = 0;
	
	public Tick(){}
	
	/**
	 * @param period 周期
	 */
	public Tick(PeriodType period){
		switch(period){
		case MIN1:
			this.timePeriod = Duration.ofMinutes(1);
			break;
		case MIN5:
			this.timePeriod = Duration.ofMinutes(5);
			break;
		case MIN15:
			this.timePeriod = Duration.ofMinutes(15);
			break;
		case MIN30:
			this.timePeriod = Duration.ofMinutes(30);
			break;
		case MIN60:
			this.timePeriod = Duration.ofMinutes(60);
			break;
		case DAY:
			this.timePeriod = Duration.ofDays(1);
			break;
		case WEEK:
			this.timePeriod = Duration.ofDays(7);
			break;
		case MONTH:
			this.timePeriod = Duration.ofDays(30);
			break;
		case YEAR:
			this.timePeriod = Duration.ofDays(365);
			break;	
		default:
			break;
		}
	}
	
	public Tick(Duration timePeriod){
		this.timePeriod = timePeriod;
	}

	public void setTimePeriod(Duration timePeriod) {
		this.timePeriod = timePeriod;
	}

	public LocalDateTime getBeginDateTime() {
		return beginDateTime;
	}

	public void setBeginDateTime(LocalDateTime beginDateTime) {
		this.beginDateTime = beginDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	/**
	 * 开盘价
	 * @return
	 */
	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	/**
	 * 最高价
	 * @return
	 */
	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getClose() {
		return close;
	}

	public void setClose(Double close) {
		this.close = close;
	}

	/**
	 * 成交量
	 * @return
	 */
	public Long getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(Long tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	/**
	 * 成交额S
	 * @return
	 */
	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}
	
	public void setTrades(int trades) {
		this.trades = trades;
	}

	@Override
	public Decimal getOpenPrice() {
		return Decimal.valueOf(open);
	}

	@Override
	public Decimal getMinPrice() {
		return Decimal.valueOf(low);
	}

	@Override
	public Decimal getMaxPrice() {
		return Decimal.valueOf(high);
	}

	@Override
	public Decimal getClosePrice() {
		return Decimal.valueOf(close);
	}

	@Override
	public Decimal getVolume() {
		return Decimal.valueOf(tradingVolume);
	}

	@Override
	public int getTrades() {
		return trades;
	}

	@Override
	public Decimal getAmount() {
		return Decimal.valueOf(turnover);
	}

	@Override
	public Duration getTimePeriod() {
		return timePeriod;
	}

	@Override
	public ZonedDateTime getBeginTime() {
		return ZonedDateTime.of(beginDateTime, ZoneId.systemDefault());
	}

	@Override
	public ZonedDateTime getEndTime() {
		return ZonedDateTime.of(endDateTime, ZoneId.systemDefault());
	}

	@Override
	public void addTrade(Decimal tradeVolume, Decimal tradePrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Tick tick) {
		return this.endDateTime.compareTo(tick.getEndDateTime());
	}

	@Override
	public String toString() {
		return "Tick [endDateTime=" + endDateTime + ", open=" + open + ", high=" + high + ", low=" + low + ", close="
				+ close + ", tradingVolume=" + tradingVolume + ", turnover=" + turnover + "]";
	}
}
