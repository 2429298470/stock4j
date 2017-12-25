package com.stock4j.factory.jrj;

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
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.MarketType;

class StockData extends HttpClientPool{
	
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * http://code.jrjimg.cn/code?areapri=cn|hk|us|stb|ylbtg&typepri=s|w|i|f|b.hg&key=60051
	 */
	public List<Stock> suggestStocks(String hits) throws UnSupportedException, ErrorHttpException {
		String url = "http://code.jrjimg.cn/code";
		Map<String, String> params = new HashMap<String, String>();
		params.put("areapri", "cn|hk|us|stb|ylbtg");
		params.put("typepri", "s|w|i|f|b.hg");
		params.put("key", hits);
		
		String result = super.get(url, params,  "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取联想数据出错");
		
		List<Stock> stocks = new ArrayList<Stock>();
		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			JsonNode nodes = mapper.readTree(body);
			JsonNode data = nodes.get("CodeData");
			JsonNode field;
			
			Stock stock;
			for(int i = 0, len = data.size(); i < len; i++){
				field = data.get(i);
				String mrkt = field.get("mrkt").asText();
				if(mrkt.endsWith("sz") || mrkt.endsWith("sh")){
					stock = new Stock();
					stock.setMarket(MarketType.valueOf(StringUtils.substringAfter(mrkt, ".").toUpperCase()));
					stock.setSname(field.get("name").asText());
					stock.setAbbr(field.get("shrt").asText());
					stock.setScode(field.get("code").asText());
					
					stocks.add(stock);
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return stocks;
	}
}
