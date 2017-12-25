package com.stock4j.factory.ths;

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
 * 同花顺股票
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class ThsFactory extends HttpClientPool implements StockFactory{
	
	@Override
	public List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		if(period != PeriodType.DAY)
			throw new UnSupportedException("不支持的数据周期操作");
		TickData tickData = new TickData();
		return tickData.listDayTicks(stock, size, rhb);
	}

	@Override
	public Quote getQuote(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException{
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws ErrorHttpException, NullValueException, UnSupportedException{
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public CashFlow getCashFlow(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public List<Stock> suggestStocks(String hits)
			throws UnSupportedException, ErrorHttpException, UnSupportedException {
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public List<CashFlow> listCashFlows(Stock stock, int size)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public Company getCompanyInformation(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		throw new UnSupportedException("不支持的数据操作");
	}

	@Override
	public List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		TranscationData transcationData = new TranscationData();
		return transcationData.listTodayTranscations(stock);
	}

	@Override
	public List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		throw new UnSupportedException("不支持的数据操作");
	}
}
