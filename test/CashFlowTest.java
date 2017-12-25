package com.stock4j.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stock4j.CashFlow;
import com.stock4j.MarketType;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.sina.SinaFactory;

public class CashFlowTest {
	
	private Stock stock = null;
	private int size = 10;

	@Before
	public void setUp() throws Exception {
		stock = new Stock("600517", MarketType.SH);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSinaFactory() throws UnSupportedException, ErrorHttpException, NullValueException {
		StockFactory stockFactory = new SinaFactory();
		stockFactory.config();
//		List<CashFlow> cashFlows = stockFactory.listCashFlows(stock, size);
//		for(CashFlow cashFlow : cashFlows){
//			System.out.println(cashFlow);
//		}
		
		CashFlow cashFlow = stockFactory.getCashFlow(stock);
		System.out.println(cashFlow);
	}

}
