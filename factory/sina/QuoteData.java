package com.stock4j.factory.sina;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class QuoteData extends HttpClientPool {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * 同时获取多支股票的盘口报价
	 * @param stock
	 * @return Map<Stock, Quote>
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected Map<Stock, Quote> listQuotes(Set<Stock> stocks) throws ErrorHttpException, NullValueException {
		Map<Stock, Quote> quotes = new HashMap<Stock, Quote>();
		if (stocks.isEmpty())
			return quotes;

		Stock[] stockArray = stocks.toArray(new Stock[stocks.size()]);
		StringBuffer scodes = new StringBuffer();
		for (Stock stock : stockArray) {
			// 对于Sina, http://hq.sinajs.cn/list=sh601003,sh601001
			scodes.append(",").append(stock.getMarket().name().toLowerCase()).append(stock.getScode());
		}

		String url = "http://hq.sinajs.cn/list=" + scodes.substring(1); // 去掉开始的“,”

		String result = super.get(url, null, "gbk");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）");

		String[] squotes = result.substring(0, result.length() - 1).split(";");
		for (int i = 0; i < squotes.length; i++) {
			String line = squotes[i];
			String temp = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
			if (StringUtils.isBlank(temp))
				continue;

			Quote quote = parserQuoteData(temp);
			quotes.put(stockArray[i], quote);
		}

		return quotes;
	}

	/**
	 * 获取某支股票的盘口报价
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected Quote getQuote(Stock stock) throws ErrorHttpException, NullValueException {
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://hq.sinajs.cn/list=" + scode;
		Quote quote = null;

		String result = super.get(url, null, "gbk");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）：" + stock);

		result = result.substring(result.indexOf("\"") + 1, result.lastIndexOf("\""));
		if (StringUtils.isBlank(result))
			throw new NullValueException("证券代码不正确/无数据！");

		quote = parserQuoteData(result);
		return quote;
	}

	/**
	 * 解析新浪的行情数据
	 * 
	 * @param scode
	 * @param result
	 * @return
	 */
	private Quote parserQuoteData(String result) {
		Quote quote = new Quote();

		String[] field = result.split(",");
		quote.setOpen(Double.parseDouble(field[1]));
		quote.setZs(Double.parseDouble(field[2]));
		quote.setPrice(Double.parseDouble(field[3]));
		quote.setHigh(Double.parseDouble(field[4]));
		quote.setLow(Double.parseDouble(field[5]));
		quote.setBuyPrice1(Double.parseDouble(field[6]));
		quote.setSellPrice1(Double.parseDouble(field[7]));
		quote.setTotalVol(Long.parseLong(field[8]));
		quote.setTotalAmount(Double.parseDouble(field[9]));
		quote.setBuyVol1(Long.parseLong(field[10]));

		quote.setBuyVol2(Long.parseLong(field[12]));
		quote.setBuyPrice2(Double.parseDouble(field[13]));
		quote.setBuyVol3(Long.parseLong(field[14]));
		quote.setBuyPrice3(Double.parseDouble(field[15]));
		quote.setBuyVol4(Long.parseLong(field[16]));
		quote.setBuyPrice4(Double.parseDouble(field[17]));
		quote.setBuyVol5(Long.parseLong(field[18]));
		quote.setBuyPrice5(Double.parseDouble(field[19]));

		quote.setSellVol1(Long.parseLong(field[20])); // 股数

		quote.setSellVol2(Long.parseLong(field[22]));
		quote.setSellPrice2(Double.parseDouble(field[23]));
		quote.setSellVol3(Long.parseLong(field[24]));
		quote.setSellPrice3(Double.parseDouble(field[25]));
		quote.setSellVol4(Long.parseLong(field[26]));
		quote.setSellPrice4(Double.parseDouble(field[27]));
		quote.setSellVol5(Long.parseLong(field[28]));
		quote.setSellPrice5(Double.parseDouble(field[29]));

		quote.setTime(LocalDateTime.parse(field[30] + " " + field[31], formatter));

		return quote;
	}
}
