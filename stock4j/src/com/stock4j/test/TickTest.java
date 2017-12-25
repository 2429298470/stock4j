package com.stock4j.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stock4j.ExRightType;
import com.stock4j.MarketType;
import com.stock4j.PeriodType;
import com.stock4j.Stock;
import com.stock4j.Tick;
import com.stock4j.TickTimeSeries;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.DefaultStockFactory;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.ifeng.IfengFactory;
import com.stock4j.factory.qq.QQFactory;
import com.stock4j.factory.sina.SinaFactory;
import com.stock4j.factory.ths.ThsFactory;
import com.stock4j.factory.wy.WangyiFactory;

import eu.verdelhan.ta4j.indicators.SMAIndicator;
import eu.verdelhan.ta4j.indicators.helpers.ClosePriceIndicator;

public class TickTest {
	
	private Stock stock = null;
	private PeriodType period = PeriodType.MIN5;
	private ExRightType rhb = ExRightType.NO;
	private int size = 100;

	@Before
	public void setUp() throws Exception {
//		stock = new Stock("600517", MarketType.SH);
		stock = new Stock("600517");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testDefaultFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = DefaultStockFactory.build();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		TickTimeSeries series = new TickTimeSeries(ticks);
		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		SMAIndicator sma = new SMAIndicator(closePrice, 5);
		for(int i = 0, len = series.getTickCount(); i < len; i++){
			System.err.println(series.getTick(i).getEndTime() + "----->" + sma.getValue(i));
		}
	}

	@Test
	public void testSinaFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new SinaFactory();
		stockFactory.config();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		for(Tick tick : ticks){
			System.out.println(tick);
		}
	}
	
	@Test
	public void testWyFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new WangyiFactory();
		stockFactory.config();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		for(Tick tick : ticks){
			System.out.println(tick);
		}
	}
	
	@Test
	public void testQQFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new QQFactory();
		stockFactory.config();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		for(Tick tick : ticks){
			System.out.println(tick);
		}
	}
	
	@Test
	public void testIfengFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new IfengFactory();
		stockFactory.config();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		for(Tick tick : ticks){
			System.out.println(tick);
		}
	}
	
	@Test
	public void testThsFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new ThsFactory();
		stockFactory.config();
		List<Tick> ticks = stockFactory.listTicks(stock, period, size, rhb);
		for(Tick tick : ticks){
			System.out.println(tick);
		}
	}
}
