package com.stock4j.factory.hexun;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.ExRightType;
import com.stock4j.PeriodType;
import com.stock4j.Stock;
import com.stock4j.Tick;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class TickData extends HttpClientPool{
	
	private static ObjectMapper mapper = new ObjectMapper();
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	/**
	 * Tick数据，时间倒序
	 * 最大数量
	 * type : 1、  5分钟  2、 15分钟  3、 30分钟  4、 60分钟  5、日线
	 * http://webstock.quote.hermes.hexun.com/a/kline?code=szse000001&start=20161027150000&number=-1000&type=5&callback=callback
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException 
	 * @throws NullValueException 
	 */
	public List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		if (rhb != ExRightType.NO)
			throw new UnSupportedException("不支持复权操作");
		if (period == PeriodType.MIN1)
			throw new UnSupportedException("不支持1分钟K线数据");
		
		String url = "http://webstock.quote.hermes.hexun.com/a/kline";
		Map<String, String> params = new HashMap<String, String>();
		
		switch (stock.getMarket()) {
		case SZ:
			params.put("code", "szse" + stock.getScode());
			break;
		case SH:
			params.put("code", "sse" + stock.getScode());
			break;
		default:
			throw new UnsupportedOperationException("不支持的市场数据操作");
		}
		
		params.put("start", LocalDateTime.now().format(formatter));
		params.put("number", String.valueOf(-size));
		
		//数据周期
		switch(period){
		case DAY:
			params.put("type", String.valueOf(5));
			break;
		case MIN5:
			params.put("type", String.valueOf(1));
			break;
		case MIN15:
			params.put("type", String.valueOf(2));
			break;
		case MIN30:
			params.put("type", String.valueOf(3));
			break;
		case MIN60:
			params.put("type", String.valueOf(4));
			break;
		default:
			params.put("type", String.valueOf(5));
			break;
		}
		
		String result = super.get(url, params,  "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("获取分笔数据出错：" + stock);
		
		//解析
		result = result.substring(1, result.length() - 2);
		List<Tick> ticks = new ArrayList<Tick>();
		try {
			JsonNode nodes = mapper.readTree(result);
			JsonNode data = nodes.get("Data").get(0);
			if(data.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			
			Tick tick;
			JsonNode temp;
			//倒序排列
			for(int i = data.size() - 1; i >=0 ; i--){
				temp = data.get(i);
				tick = new Tick(period);
				tick.setEndDateTime(LocalDateTime.parse(temp.get(0).asText(), formatter));
				tick.setOpen(temp.get(2).asDouble() / 100);
				tick.setClose(temp.get(3).asDouble()  / 100);
				tick.setHigh(temp.get(4).asDouble() / 100);
				tick.setLow(temp.get(5).asDouble() / 100);
				tick.setTradingVolume(temp.get(6).asLong());
				tick.setTurnover(temp.get(7).asDouble());
				
				ticks.add(tick);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ticks;
	}
}
