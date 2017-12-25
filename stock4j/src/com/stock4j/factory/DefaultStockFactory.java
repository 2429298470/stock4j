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
import com.stock4j.factory.hexun.HexunFactory;
import com.stock4j.factory.ifeng.IfengFactory;
import com.stock4j.factory.jrj.JrjFactory;
import com.stock4j.factory.qq.QQFactory;
import com.stock4j.factory.sina.SinaFactory;
import com.stock4j.factory.ths.ThsFactory;
import com.stock4j.factory.wy.WangyiFactory;

/**
 * 股票行情工厂，用于获取相关数据
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class DefaultStockFactory extends HttpClientPool implements StockFactory{
	
	private static StockFactory stockFactory = null;
	
	/**
	 * sina行情服务器
	 */
	private StockFactory sina = new SinaFactory();
	/**
	 * 凤凰行情服务器
	 */
	private StockFactory ifeng = new IfengFactory();
	/**
	 * QQ行情服务器
	 */
	private StockFactory qq = new QQFactory();
	/**
	 * 金融街行情服务器
	 */
	private StockFactory jrj = new JrjFactory();
	/**
	 * 163行情服务器
	 */
	private StockFactory wangyi = new WangyiFactory();
	/**
	 * 和讯行情服务器
	 */
	private StockFactory hexun = new HexunFactory();
	/**
	 * 和讯行情服务器
	 */
	private StockFactory ths = new ThsFactory();
	
	private DefaultStockFactory(){
		super();
	}
	
	public static StockFactory build(){
		if(stockFactory == null){
			stockFactory = new DefaultStockFactory();
			stockFactory.config();   //配置HttpClientPool
		}
		return stockFactory;
	}

	@Override
	public List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		try {
			return qq.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		try {
			return sina.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		try {
			return ifeng.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		try {
			return hexun.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		try {
			return ths.listTicks(stock, period, size, rhb);
		} catch (UnSupportedException | ErrorHttpException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取行情数据：" + stock.getScode());
		return null;
	}
	
	@Override
	public Quote getQuote(Stock stock) throws ErrorHttpException, NullValueException, UnSupportedException {
		try {
			return sina.getQuote(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return ifeng.getQuote(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return jrj.getQuote(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return qq.getQuote(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.getQuote(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取盘口数据：" + stock.getScode());
		return null;
	}

	@Override
	public Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		try {
			return sina.listQuotes(stocks);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return ifeng.listQuotes(stocks);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return jrj.listQuotes(stocks);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return qq.listQuotes(stocks);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.listQuotes(stocks);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取盘口数据");
		return null;
	}

	@Override
	public List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		try {
			return hexun.listTranscations(stock, sdate, size);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.listTranscations(stock, sdate, size);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取股票的分时数据:" + stock.getScode());
		return null;
	}

	@Override
	public List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		try {
			return hexun.listTodayTranscations(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.listTodayTranscations(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取当日的分时数据：" + stock.getScode());
		return null;
	}

	@Override
	public CashFlow getCashFlow(Stock stock) throws UnSupportedException, ErrorHttpException, NullValueException {
		try {
			return sina.getCashFlow(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取股票的资金流数据：" + stock.getScode());
		return null;
	}

	@Override
	public List<CashFlow> listCashFlows(Stock stock, int size)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		try {
			return sina.listCashFlows(stock, size);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取股票的资金流数据：" + stock.getScode());
		return null;
	}

	@Override
	public List<Stock> suggestStocks(String hits) throws UnSupportedException, ErrorHttpException {
		try {
			return ifeng.suggestStocks(hits);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return sina.suggestStocks(hits);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return wangyi.suggestStocks(hits);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return jrj.suggestStocks(hits);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法根据提示信息获取股票");
		return null;
	}

	@Override
	public Company getCompanyInformation(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		try {
			return sina.getCompanyInformation(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取公司的信息：" + stock.getScode());
		return null;
	}

	/**
	 * 获取权息数据
	 * @param 股票
	 */
	@Override
	public List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		try {
			return wangyi.listDividentRight(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		try {
			return sina.listDividentRight(stock);
		} catch (ErrorHttpException | UnSupportedException e) {
			e.printStackTrace();
		}
		
		logger.error("无法获取权息数据：" + stock.getScode());
		return null;
	}
}
