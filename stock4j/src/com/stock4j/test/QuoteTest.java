package com.stock4j.test;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stock4j.MarketType;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.ifeng.IfengFactory;
import com.stock4j.factory.jrj.JrjFactory;
import com.stock4j.factory.qq.QQFactory;
import com.stock4j.factory.sina.SinaFactory;
import com.stock4j.factory.wy.WangyiFactory;

public class QuoteTest {
	
	private Stock stock = null;
	private Set<Stock> stocks = new HashSet<Stock>();

	@Before
	public void setUp() throws Exception {
		stock = new Stock("600517", MarketType.SH);
		stocks.add(stock);
		stock = new Stock("000001", MarketType.SZ);
		stocks.add(stock);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSinaFactory() throws ErrorHttpException, NullValueException, UnSupportedException {
		StockFactory stockFactory = new SinaFactory();
		stockFactory.config();
//		Quote quote = stockFactory.getQuote(stock);
//		System.out.println(quote);
		Map<Stock, Quote> quotes = stockFactory.listQuotes(stocks);
		for(Entry<Stock, Quote> quote : quotes.entrySet()){
			System.out.println(quote.getValue());
		}
	}
	
	@Test
	public void testIfengFactory() throws ErrorHttpException, NullValueException, UnSupportedException {
		StockFactory stockFactory = new IfengFactory();
		stockFactory.config();
//		Quote quote = stockFactory.getQuote(stock);
//		System.out.println(quote);
		Map<Stock, Quote> quotes = stockFactory.listQuotes(stocks);
		for(Entry<Stock, Quote> quote : quotes.entrySet()){
			System.out.println(quote.getValue());
		}
	}
	
	@Test
	public void testJrjFactory() throws ErrorHttpException, NullValueException, UnSupportedException {
		StockFactory stockFactory = new JrjFactory();
		stockFactory.config();
//		Quote quote = stockFactory.getQuote(stock);
//		System.out.println(quote);
		Map<Stock, Quote> quotes = stockFactory.listQuotes(stocks);
		for(Entry<Stock, Quote> quote : quotes.entrySet()){
			System.out.println(quote.getValue());
		}
	}
	
	@Test
	public void testQQFactory() throws ErrorHttpException, NullValueException, UnSupportedException {
		StockFactory stockFactory = new QQFactory();
		stockFactory.config();
//		Quote quote = stockFactory.getQuote(stock);
//		System.out.println(quote);
		Map<Stock, Quote> quotes = stockFactory.listQuotes(stocks);
		for(Entry<Stock, Quote> quote : quotes.entrySet()){
			System.out.println(quote.getValue());
		}
	}
	
	@Test
	public void testWangyiFactory() throws ErrorHttpException, NullValueException, UnSupportedException {
		StockFactory stockFactory = new WangyiFactory();
		stockFactory.config();
//		Quote quote = stockFactory.getQuote(stock);
//		System.out.println(quote);
		Map<Stock, Quote> quotes = stockFactory.listQuotes(stocks);
		for(Entry<Stock, Quote> quote : quotes.entrySet()){
			System.out.println(quote.getValue());
		}
	}

}
