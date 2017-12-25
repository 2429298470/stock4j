package com.stock4j.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.stock4j.Company;
import com.stock4j.MarketType;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.StockFactory;
import com.stock4j.factory.sina.SinaFactory;

public class CompanyTest {
	
	private Stock stock;

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
		Company company = stockFactory.getCompanyInformation(stock);
		System.out.println(company);
	}

}
