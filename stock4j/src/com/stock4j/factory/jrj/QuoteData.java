package com.stock4j.factory.jrj;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
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
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * http://q.jrjimg.cn/?q=cn|s&i=002302&n=mainQuote&c=l
	 * @throws URISyntaxException 
	 */
	public Quote getQuote(Stock stock) throws ErrorHttpException, NullValueException {
//		String url = "http://q.jrjimg.cn/";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("q", "cn%7Cs");
//		params.put("i", stock.getScode());
//		params.put("n", "mainQuote");
//		params.put("c", "l");
		
		String url = "http://q.jrjimg.cn/?q=cn%7Cs&i=" + stock.getScode() + "&n=mainQuote&c=l";
		String result = super.get(url, null,  "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取分笔数据出错：" + stock);
		
		String data = result.substring(result.indexOf("HqData:"), result.lastIndexOf("}") + 1);
		data = "{" + data.replaceAll("HqData", "\"HqData\"");
		try {
			JsonNode nodes = mapper.readTree(data);
			JsonNode hqdata = nodes.get("HqData");
			if(hqdata.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			
			return parserQuoteData(hqdata.get(0));
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;		
	}
	
	/**
	 * 解析行情数据
	 * @param scode
	 * @param result
	 * @return
	 */
	private Quote parserQuoteData(JsonNode data){
		Quote quote = new Quote();
		quote.setHigh(data.get(3).asDouble());
		quote.setLow(data.get(10).asDouble());
		quote.setZs(data.get(5).asDouble());
		quote.setOpen(data.get(8).asDouble());
		quote.setPrice(data.get(11).asDouble());
		quote.setTotalVol(data.get(13).asLong() * 100);   //成交量
		quote.setTotalAmount(data.get(14).asDouble() * 10000);
		quote.setNp(data.get(25).asLong() * 100);
		quote.setWp(data.get(26).asLong() * 100);
		
		quote.setBuyPrice1(data.get(32).asDouble());
		quote.setBuyPrice2(data.get(32).asDouble());
		quote.setBuyPrice3(data.get(34).asDouble());
		quote.setBuyPrice4(data.get(35).asDouble());
		quote.setBuyPrice5(data.get(36).asDouble());
		quote.setBuyVol1(data.get(37).asLong());
		quote.setBuyVol2(data.get(38).asLong());
		quote.setBuyVol3(data.get(39).asLong());
		quote.setBuyVol4(data.get(40).asLong());
		quote.setBuyVol5(data.get(41).asLong());
		
		quote.setSellPrice1(data.get(43).asDouble());
		quote.setSellPrice2(data.get(43).asDouble());
		quote.setSellPrice3(data.get(44).asDouble());
		quote.setSellPrice4(data.get(45).asDouble());
		quote.setSellPrice5(data.get(46).asDouble());
		quote.setSellVol1(data.get(47).asLong());
		quote.setSellVol2(data.get(48).asLong());
		quote.setSellVol3(data.get(49).asLong());
		quote.setSellVol4(data.get(50).asLong());
		quote.setSellVol5(data.get(51).asLong());
		
		quote.setTime(LocalDateTime.parse(data.get(27).asText(), dateTimeFormatter));
		
		return quote;
	}

	public Map<Stock, Quote> listQuotes(Set<Stock> stocks) throws ErrorHttpException, NullValueException {
		Map<Stock, Quote> quotes = new HashMap<Stock, Quote>();
		if(stocks.isEmpty()) return quotes;
		Set<String> scodes = new HashSet<String>();
		for(Stock stock : stocks){
			scodes.add(stock.getScode());
		}
		
//		String url = "http://q.jrjimg.cn/";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("q", "cn%7Cs");
//		params.put("i", scodes.toString());
//		params.put("n", "mainQuote");
//		params.put("c", "l");
		
		String url = "http://q.jrjimg.cn/?q=cn%7Cs&i=" + StringUtils.join(scodes, ",") + "&n=mainQuote&c=l"; 
		String result = super.get(url, null,  "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取分笔数据出错");
		
		String data = result.substring(result.indexOf("HqData:"), result.lastIndexOf("}") + 1);
		data = "{" + data.replaceAll("HqData", "\"HqData\"");
		try {
			JsonNode nodes = mapper.readTree(data);
			JsonNode hqdata = nodes.get("HqData");
			if(hqdata.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			
			JsonNode node;
			for(int i = 0; i < hqdata.size(); i++){
				node = hqdata.get(i);
				String scode = node.get(1).asText();
				for(Stock stock : stocks){
					if(stock.getScode().equals(scode)){
						quotes.put(stock, parserQuoteData(node));
						break;
					}
				}
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return quotes;
	}
}
