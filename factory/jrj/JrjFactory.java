package com.stock4j.factory.jrj;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.stock4j.CashFlow;
import com.stock4j.Company;
import com.stock4j.DividentRight;
import com.stock4j.ExRightType;
import com.stock4j.PeriodType;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.Tick;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;
import com.stock4j.factory.StockFactory;

/**
 * �ӽ��ڽ���վ��ȡ�����������
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class JrjFactory extends HttpClientPool implements StockFactory{

	@Override
	public List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		throw new UnSupportedException("��֧�ֵ����ݲ���");
	}

	@Override
	public Quote getQuote(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		QuoteData quoteData = new QuoteData();
		Quote quote = quoteData.getQuote(stock);
		return quote;
	}

	/**
	 * ȥ�������г��Ĺ�Ʊ
	 */
	@Override
	public Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		QuoteData quoteData = new QuoteData();
		return quoteData.listQuotes(stocks);
	}

	@Override
	public List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		return null;
	}
	
	@Override
	public List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CashFlow getCashFlow(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		CashFlowData cashFlowData = new CashFlowData();
		List<CashFlow> cashFlows = cashFlowData.listCashFlows(stock);
		if(cashFlows == null || cashFlows.isEmpty()) return null;
		return cashFlows.get(0);
	}

	@Override
	public List<CashFlow> listCashFlows(Stock stock, int size)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		CashFlowData cashFlowData = new CashFlowData();
		return cashFlowData.listCashFlows(stock);
	}

	@Override
	public List<Stock> suggestStocks(String hits)
			throws UnSupportedException, ErrorHttpException, UnSupportedException {
		StockData stockData = new StockData();
		return stockData.suggestStocks(hits);
	}
	

	@Override
	public Company getCompanyInformation(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		throw new UnSupportedException("��֧�ֵ����ݲ���");
	}
	
	@Override
	public List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		// TODO Auto-generated method stub
		return null;
	}
}
