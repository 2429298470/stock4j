package com.stock4j.factory.sina;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.stock4j.Stock;
import com.stock4j.MarketType;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class StockData extends HttpClientPool{

	/**
	 * http://suggest3.sinajs.cn/suggest/key=zxd
	 * http://suggest3.sinajs.cn/suggest/type=11,12,13,14,15&key=6005
	 */
	protected List<Stock> suggestStocks(String hits) throws UnSupportedException, ErrorHttpException {
		String url = "http://suggest3.sinajs.cn/suggest/key=" + hits;
		String result = super.get(url, null, "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取联想数据出错");
		
		List<Stock> stocks = new ArrayList<Stock>();
		String body = result.substring(result.indexOf("\"") + 1, result.lastIndexOf("\""));
		if(StringUtils.isBlank(body)) return null;

		String[] fields = body.split(";");
		Stock stock;
		for(String field : fields){
			String[] temp = field.split(",");
			String scode = temp[3];
			if(scode.startsWith("sz") || scode.startsWith("sh")){
				stock = new Stock();
				stock.setScode(temp[2]);
				stock.setMarket(MarketType.valueOf(scode.substring(0, 2).toUpperCase()));
				stock.setAbbr(temp[5]);
				stock.setSname(temp[4]);
				
				stocks.add(stock);
			}
		}
		return stocks;
	}
}
