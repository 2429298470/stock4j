package com.stock4j;

import java.util.List;
import eu.verdelhan.ta4j.BaseTimeSeries;

/**
 * K线数据（开盘价，最高价，最低价，收盘价）时间序列，请查看{@link BaseTimeSeries}
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class TickTimeSeries extends BaseTimeSeries{

	private static final long serialVersionUID = 1L;
	
	public TickTimeSeries(){
		super();
	}
	
	/**
	 * 行情数据的时间序列
	 * @param ticks
	 */
	public TickTimeSeries(List<Tick> ticks){
		for(Tick tick : ticks){
			super.addTick(tick);
		}
	}
}
