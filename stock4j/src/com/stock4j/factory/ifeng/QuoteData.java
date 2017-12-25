package com.stock4j.factory.ifeng;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class QuoteData extends HttpClientPool{
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * https://hq.finance.ifeng.com/q.php?l=sh600517&f=json
	 */
	public Quote getQuote(Stock stock) throws ErrorHttpException, NullValueException {
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "https://hq.finance.ifeng.com/q.php";
		Map<String, String> params = new HashMap<String, String>();
		params.put("l", scode);
		params.put("f", "json");
		
		String result = super.get(url, params, "gbk");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）：" + stock);
		
		result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			JsonNode nodes = mapper.readTree(result);
			JsonNode data = nodes.get(scode);
			if(data.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			
			return parserQuoteData(data);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
	
	private Quote parserQuoteData(JsonNode data){
		Quote quote = new Quote();
		
		quote.setOpen(data.get(4).asDouble());
		quote.setHigh(data.get(5).asDouble());
		quote.setLow(data.get(6).asDouble());
		quote.setPrice(data.get(1).asDouble());
		quote.setZs(data.get(2).asDouble());
		quote.setTotalVol(data.get(9).asLong());   //成交量
		quote.setTotalAmount(data.get(10).asDouble());
		
		quote.setBuyPrice1(data.get(11).asDouble());
		quote.setBuyPrice2(data.get(12).asDouble());
		quote.setBuyPrice3(data.get(13).asDouble());
		quote.setBuyPrice4(data.get(14).asDouble());
		quote.setBuyPrice5(data.get(15).asDouble());
		quote.setBuyVol1(data.get(16).asLong());
		quote.setBuyVol2(data.get(17).asLong());
		quote.setBuyVol3(data.get(18).asLong());
		quote.setBuyVol4(data.get(19).asLong());
		quote.setBuyVol5(data.get(20).asLong());
		
		quote.setSellPrice1(data.get(21).asDouble());
		quote.setSellPrice2(data.get(22).asDouble());
		quote.setSellPrice3(data.get(23).asDouble());
		quote.setSellPrice4(data.get(24).asDouble());
		quote.setSellPrice5(data.get(25).asDouble());
		quote.setSellVol1(data.get(26).asLong());
		quote.setSellVol2(data.get(27).asLong());
		quote.setSellVol3(data.get(28).asLong());
		quote.setSellVol4(data.get(29).asLong());
		quote.setSellVol5(data.get(30).asLong());
		quote.setTime(LocalDateTime.ofEpochSecond(data.get(34).asLong(), 0, ZoneOffset.ofHours(8)));   //数据为秒数
		
		return quote;
	}

	public Map<Stock, Quote> listQuotes(Set<Stock> stocks) throws ErrorHttpException, NullValueException {
		Map<Stock, Quote> quotes = new HashMap<Stock, Quote>();
		if(stocks.isEmpty()) return quotes;
		
		Stock[] stockArray = stocks.toArray(new Stock[stocks.size()]);
		StringBuffer scodes = new StringBuffer();
		for(Stock stock : stockArray){
			scodes.append(",").append(stock.getMarket().name().toLowerCase()).append(stock.getScode());
		}
		
		String url = "https://hq.finance.ifeng.com/q.php";
		Map<String, String> params = new HashMap<String, String>();
		params.put("l", scodes.toString().substring(1));    //去掉开始的“,”
		params.put("f", "json");
		
		String result = super.get(url, params, "gbk");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）：");
		
		result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			JsonNode nodes = mapper.readTree(result);
			JsonNode data;
			for(int i = 0; i < stockArray.length; i++){
				String scode = stockArray[i].getMarket().name().toLowerCase() + stockArray[i].getScode();
				data = nodes.get(scode);
				if(data.isNull()) continue;
				quotes.put(stockArray[i], parserQuoteData(data));
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return quotes;
	}

}
