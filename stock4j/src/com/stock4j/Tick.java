package com.stock4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import eu.verdelhan.ta4j.Decimal;

/**
 * K�����ݣ����̼ۣ���߼ۣ���ͼۣ����̼ۣ����̳���{@link eu.verdelhan.ta4j.Tick}
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Tick implements Comparable<Tick>, eu.verdelhan.ta4j.Tick{

	private static final long serialVersionUID = 1L;
	/**�������ڣ�Ĭ��Ϊ����*/
	private Duration timePeriod = Duration.ofDays(1);
	/**��K�ߵĿ�ʼʱ��*/
    private LocalDateTime beginDateTime;
    /**��K�ߵĽ���ʱ��*/
    private LocalDateTime endDateTime;
    /**���̼�*/
	private Double open;
	/**��߼�*/
	private Double high;
	/**��ͼ�*/
	private Double low;
	/**���̼�*/
	private Double close;
	/**�ɽ�������λ��*/
	private Long tradingVolume;
	/**�ɽ����λԪ*/
	private Double turnover;
	/**���״���*/
	private int trades = 0;
	
	public Tick(){}
	
	/**
	 * @param period ����
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
	 * ���̼�
	 * @return
	 */
	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	/**
	 * ��߼�
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
	 * �ɽ���
	 * @return
	 */
	public Long getTradingVolume() {
		return tradingVolume;
	}

	public void setTradingVolume(Long tradingVolume) {
		this.tradingVolume = tradingVolume;
	}

	/**
	 * �ɽ���S
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
