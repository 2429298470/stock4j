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
 * ��Ʊ���ݹ����ӿ�
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public interface StockFactory {
	
	void config();
	
	/**
	 * ��ȡ��������
	 * @param stock ���루��Ʊ����ǰ׺�������г�
	 * @param period  ��������
	 * @param size   ���ݴ�С
	 * @param rhb   �Ƿ�Ȩ
	 * @return   ����K�����ݼ��ϣ�����������
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * ��ȡĳ֧��Ʊ���̿ڱ���
	 * @param stock
	 * @return Quote
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	Quote getQuote(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * ͬʱ��ȡ��֧��Ʊ���̿ڱ���
	 * @param stock
	 * @return Map<Stock, Quote>
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * ��ȡ��ʱ����
	 * @param stock
	 * @param sdate  ��ʼʱ��
	 * @param size   ���ݴ�С
	 * @return List<Transcation>
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException;
	
	
	/**
	 * ��ȡ���յķ�ʱ����
	 * @param stock
	 * @return
	 * @throws NullValueException
	 * @throws UnSupportedException
	 */
	List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException;

	/**
	 * ��ȡĳֻ��Ʊ�ĵ����ʽ�����
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	CashFlow getCashFlow(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * ��ȡĳֻ��Ʊ����ʷ�ʽ�����
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<CashFlow> listCashFlows(Stock stock, int size) 
			throws UnSupportedException, ErrorHttpException, NullValueException;

	/**
	 * ͨ���������д�����Ʊ
	 * @param hits
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 */
	List<Stock> suggestStocks(String hits)
			throws UnSupportedException, ErrorHttpException;
	
	/**
	 * ĳ��Ʊ�Ĺ�˾��Ҫ��Ϣ
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	Company getCompanyInformation(Stock stock) 
			throws ErrorHttpException, NullValueException, UnSupportedException;
	
	/**
	 * ��ȡ��Ʊ��ȨϢ����
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException;
}
