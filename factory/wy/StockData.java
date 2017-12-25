package com.stock4j.factory.wy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.MarketType;

class StockData extends HttpClientPool{
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * http://quotes.money.163.com/stocksearch/json.do?count=10&word=60051
	 * @param hits
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 */
	protected List<Stock> suggestStocks(String hits) throws UnSupportedException, ErrorHttpException{
		String url = "http://quotes.money.163.com/stocksearch/json.do";
		Map<String, String> params = new HashMap<String, String>();
		params.put("count", "10");
		params.put("word", hits);
		
		String result = super.get(url, params, "utf-8");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("获取线数据失败！");
		
		String body = result.substring(result.indexOf("(") + 1, result.lastIndexOf(")"));
		
		List<Stock> stocks = new ArrayList<Stock>();
		try {
			JsonNode nodes = mapper.readTree(body);
			JsonNode node;
			Stock stock;
			for(int i = 0; i < nodes.size(); i++){
				node = nodes.get(i);
				stock = new Stock();
				stock.setScode(node.get("symbol").asText());
				stock.setAbbr(node.get("spell").asText());
				stock.setSname(node.get("name").asText());
				stock.setMarket(MarketType.valueOf(node.get("type").asText()));
				
				stocks.add(stock);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return stocks;
	}
}
