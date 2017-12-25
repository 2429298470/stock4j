package com.stock4j.factory.wy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.MarketType;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class QuoteData extends HttpClientPool{
	
	private static DateTimeFormatter dateTimeQuoteFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 同时获取多支股票的盘口报价
	 * http://api.money.126.net/data/feed/0600510
	 * @param stock
	 * @return Map<Stock, Quote>
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	protected Map<Stock, Quote> listQuotes(Set<Stock> stocks)
			throws UnSupportedException, ErrorHttpException, NullValueException{
		List<String> scodes = new ArrayList<String>(stocks.size());
		for(Stock stock : stocks){
			if(stock.getMarket() == MarketType.SH){
				scodes.add("0" + stock.getScode());
			} else if (stock.getMarket() == MarketType.SZ){
				scodes.add("1" + stock.getScode());
			} else {
				continue;
			}
		}
		
		if(scodes.isEmpty())
			throw new NullValueException("不正确的证券代码/无数据");
		
		Map<Stock, Quote> quotes = new HashMap<Stock, Quote>();
		String url = "http://api.money.126.net/data/feed/" + StringUtils.join(scodes, ",");		
		String result = super.get(url, null, "utf-8");
		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			JsonNode nodes = mapper.readTree(body);
			JsonNode node;
			for(String scode : scodes){
				node = nodes.get(scode);
				if(node.isNull()) continue;
				
				String realScode = node.get("code").asText().substring(1);
				for(Stock stock : stocks){
					if(stock.getScode().equals(realScode)){
						stock.setSname(node.get("name").asText());
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
	
	
	/**
	 * 获取某支股票的盘口报价
	 */
	protected Quote getQuote(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException{
		String scode = stock.getScode();		
		switch (stock.getMarket()){
		case SH:
			scode = "0" + scode;
			break;
		case SZ:
			scode = "1" + scode;
			break;
		default:
			throw new UnSupportedException("不支持的数据操作");
		}
		
		String url = "http://api.money.126.net/data/feed/" + scode;
		String result = super.get(url, null, "utf-8");
		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			JsonNode nodes = mapper.readTree(body);
			JsonNode data = nodes.get(scode);
			if(data.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			stock.setSname(data.get("name").asText());
			return parserQuoteData(data);
			
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
	
	private Quote parserQuoteData(JsonNode data){
		Quote quote = new Quote();
		quote.setHigh(data.get("high").asDouble());
		quote.setLow(data.get("low").asDouble());
		quote.setOpen(data.get("open").asDouble());
		quote.setZs(data.get("yestclose").asDouble());
		quote.setPrice(data.get("price").asDouble());
		quote.setBuyPrice1(data.get("bid1").asDouble());
		quote.setSellPrice1(data.get("ask1").asDouble());
		quote.setTotalVol(data.get("volume").asLong());
		quote.setTotalAmount(data.get("turnover").asDouble());
		quote.setBuyVol1(data.get("bidvol1").asLong());
		
		quote.setBuyVol2(data.get("bidvol2").asLong());
		quote.setBuyPrice2(data.get("bid2").asDouble());
		quote.setBuyVol3(data.get("bidvol3").asLong());
		quote.setBuyPrice3(data.get("bid3").asDouble());
		quote.setBuyVol4(data.get("bidvol4").asLong());
		quote.setBuyPrice4(data.get("bid4").asDouble());
		quote.setBuyVol5(data.get("bidvol5").asLong());
		quote.setBuyPrice5(data.get("bid5").asDouble());
		
		quote.setSellVol1(data.get("askvol1").asLong());  //股数
		
		quote.setSellVol2(data.get("askvol2").asLong());
		quote.setSellPrice2(data.get("ask2").asDouble());
		quote.setSellVol3(data.get("askvol3").asLong());
		quote.setSellPrice3(data.get("ask3").asDouble());
		quote.setSellVol4(data.get("askvol4").asLong());
		quote.setSellPrice4(data.get("ask4").asDouble());
		quote.setSellVol5(data.get("askvol5").asLong());
		quote.setSellPrice5(data.get("ask5").asDouble());
		
		quote.setTime(LocalDateTime.parse(data.get("time").asText(), dateTimeQuoteFormatter));
		return quote;
	}
}
