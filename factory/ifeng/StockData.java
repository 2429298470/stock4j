package com.stock4j.factory.ifeng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.MarketType;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class StockData extends HttpClientPool{
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public List<Stock> suggestStocks(String hits) throws UnSupportedException, ErrorHttpException {
		if(StringUtils.isBlank(hits)) return null;
		
		String url = "http://app.finance.ifeng.com/hq/suggest_v2.php?q=" + hits;
		String result = super.get(url, null, "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取联想数据出错");
		
		List<Stock> stocks = new ArrayList<Stock>();
		String body = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);
		
		try {
			JsonNode nodes = mapper.readTree(body);
			JsonNode node;
			Stock stock;
			for(int i = 0, len = nodes.size(); i < len; i++){
				node = nodes.get(i);
				stock = new Stock();
				stock.setScode(node.get("s").asText());
				stock.setAbbr(node.get("p").asText());
				String type = node.get("t").asText();  //类型
				//暂时不考虑其他类型，只针对股票
				if(! "stock".equalsIgnoreCase(type)) continue;
				
				String code = node.get("c").asText();
				stock.setMarket(MarketType.valueOf(code.substring(0, 2).toUpperCase()));
				stock.setSname(node.get("n").asText());
				
				stocks.add(stock);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return stocks;
	}

}
