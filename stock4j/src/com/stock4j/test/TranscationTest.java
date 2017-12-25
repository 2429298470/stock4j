package com.stock4j.test;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.stock4j.MarketType;
import com.stock4j.Stock;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.hexun.HexunFactory;
import com.stock4j.factory.ths.ThsFactory;
import com.stock4j.factory.wy.WangyiFactory;

public class TranscationTest {
	
	private Stock stock = null;
	private LocalDateTime sdate = LocalDateTime.of(2017, 12, 22, 14, 23, 0);
	private int size = 10;
	
	@Before
	public void setUp() throws Exception {
		stock = new Stock("600517", MarketType.SH);
	}
	
	@Test
	public void testHexunFactory() throws ErrorHttpException, NullValueException, UnSupportedException{
		StockFactory stockFactory = new HexunFactory();
		stockFactory.config();
//		List<Transcation> transcations = stockFactory.listTranscations(stock, sdate, size);
		List<Transcation> transcations = stockFactory.listTodayTranscations(stock);
		for(Transcation transcation : transcations){
			System.out.println(transcation);
		}
	}
	
	@Test
	public void testThsFactory() throws ErrorHttpException, NullValueException, UnSupportedException{
		StockFactory stockFactory = new ThsFactory();
		stockFactory.config();
		List<Transcation> transcations = stockFactory.listTranscations(stock, sdate, size);
//		List<Transcation> transcations = stockFactory.listTodayTranscations(stock);
		for(Transcation transcation : transcations){
			System.out.println(transcation);
		}
	}
	
	@Test
	public void testWangyiFactory() throws ErrorHttpException, NullValueException, UnSupportedException{
		StockFactory stockFactory = new WangyiFactory();
		stockFactory.config();
//		List<Transcation> transcations = stockFactory.listTranscations(stock, sdate, size);
		List<Transcation> transcations = stockFactory.listTodayTranscations(stock);
		for(Transcation transcation : transcations){
			System.out.println(transcation);
		}
	}

}
