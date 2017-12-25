package com.stock4j.factory;

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

/**
 * 股票数据工厂接口
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public interface StockFactory {
	
	void config();
	
	/**
	 * 获取行情数据
	 * @param stock 代码（股票不带前缀）、含市场
	 * @param period  数据周期
	 * @param size   数据大小
	 * @param rhb   是否复权
	 * @return   返回K线数据集合，按日期升序
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * 获取某支股票的盘口报价
	 * @param stock
	 * @return Quote
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	Quote getQuote(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * 同时获取多支股票的盘口报价
	 * @param stock
	 * @return Map<Stock, Quote>
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * 获取分时数据
	 * @param stock
	 * @param sdate  开始时间
	 * @param size   数据大小
	 * @return List<Transcation>
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException;
	
	
	/**
	 * 获取当日的分时数据
	 * @param stock
	 * @return
	 * @throws NullValueException
	 * @throws UnSupportedException
	 */
	List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * 获取某只股票的当日资金流向
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	CashFlow getCashFlow(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * 获取某只股票的历史资金流向
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<CashFlow> listCashFlows(Stock stock, int size) 
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * 通过代码和缩写联想股票
	 * @param hits
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 */
	List<Stock> suggestStocks(String hits)
			throws UnSupportedException, ErrorHttpException;
	
	/**
	 * 某股票的公司简要信息
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	Company getCompanyInformation(Stock stock) 
			throws ErrorHttpException, NullValueException, UnSupportedException;
	
	/**
	 * 获取股票的权息数据
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException;
}
