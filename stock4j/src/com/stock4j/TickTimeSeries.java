package com.stock4j;

import java.util.List;
import eu.verdelhan.ta4j.BaseTimeSeries;

/**
 * K�����ݣ����̼ۣ���߼ۣ���ͼۣ����̼ۣ�ʱ�����У���鿴{@link BaseTimeSeries}
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class TickTimeSeries extends BaseTimeSeries{

	private static final long serialVersionUID = 1L;
	
	public TickTimeSeries(){
		super();
	}
	
	/**
	 * �������ݵ�ʱ������
	 * @param ticks
	 */
	public TickTimeSeries(List<Tick> ticks){
		for(Tick tick : ticks){
			super.addTick(tick);
		}
	}
}
