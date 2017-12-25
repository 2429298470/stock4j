package com.stock4j.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.ifeng.IfengFactory;
import com.stock4j.factory.jrj.JrjFactory;
import com.stock4j.factory.sina.SinaFactory;
import com.stock4j.factory.wy.WangyiFactory;

public class StockTest {
	
	private String hits = "60051";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIfengFactory() throws UnSupportedException, ErrorHttpException {
		StockFactory stockFactory = new IfengFactory();
		stockFactory.config();
		List<Stock> stocks = stockFactory.suggestStocks(hits);
		for(Stock stock : stocks){
			System.out.println(stock);
		}
	}
	
	@Test
	public void testSinaFactory() throws UnSupportedException, ErrorHttpException {
		StockFactory stockFactory = new SinaFactory();
		stockFactory.config();
		List<Stock> stocks = stockFactory.suggestStocks(hits);
		for(Stock stock : stocks){
			System.out.println(stock);
		}
	}
	
	@Test
	public void testWangyiFactory() throws UnSupportedException, ErrorHttpException {
		StockFactory stockFactory = new WangyiFactory();
		stockFactory.config();
		List<Stock> stocks = stockFactory.suggestStocks(hits);
		for(Stock stock : stocks){
			System.out.println(stock);
		}
	}
	
	@Test
	public void testJrjFactory() throws UnSupportedException, ErrorHttpException {
		StockFactory stockFactory = new JrjFactory();
		stockFactory.config();
		List<Stock> stocks = stockFactory.suggestStocks(hits);
		for(Stock stock : stocks){
			System.out.println(stock);
		}
	}

}
