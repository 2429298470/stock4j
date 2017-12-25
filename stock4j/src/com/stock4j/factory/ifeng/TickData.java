package com.stock4j.factory.ifeng;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Tick数据，时间倒序
	 * 最大数量
	 * type : 1、  5分钟  2、 15分钟  3、 30分钟  4、 60分钟  5、日线
	 * http://api.finance.ifeng.com/akmin?scode=sh601989&type=5
	 * http://api.finance.ifeng.com/akdaily/?code=sh601989&type=last
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
		
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		Map<String, String> params = new HashMap<String, String>();
		String scale;
		switch (period){
		case MIN5:
			scale="akmin";
			params.put("scode", scode);
			params.put("type", "5");
			break;
		case MIN15:
			scale="akmin";
			params.put("scode", scode);
			params.put("type", "15");
			break;
		case MIN30:
			scale="akmin";
			params.put("scode", scode);
			params.put("type", "30");
			break;
		case MIN60:
			scale="akmin";
			params.put("scode", scode);
			params.put("type", "60");
			break;
		case DAY:
			scale="akdaily/";
			params.put("code", scode);
			params.put("type", "last");
			break;
		case YEAR:
			scale="ayear/";
			params.put("code", scode);
			params.put("type", "last");
			break;
		case MONTH:
			scale="akmonthly/";
			params.put("code", scode);
			params.put("type", "last");
			break;
		case WEEK:
			scale="akweekly/";
			params.put("code", scode);
			params.put("type", "last");
			break;
		default:
			scale="akdaily/";
			params.put("code", scode);
			params.put("type", "last");
			break;
		}
		
		String url = "http://api.finance.ifeng.com/" + scale;
		String result = super.get(url, params, "gbk");
		if(StringUtils.isBlank(result)) 
			throw new NullValueException("证券代码不正确/无数据！");
		
		List<Tick> ticks = new ArrayList<Tick>();
		try {
			JsonNode nodes = mapper.readTree(result);
			JsonNode node = nodes.get("record");
			if(node.isNull())
				throw new NullValueException("证券代码不正确/无数据！");
			
			int length = node.size();
			int start = length - size;
			if(size > length){
				logger.warn("获取的数据数量小于设定值，原因：1）新股；2）网络数据量限制");
				start = 0;
			}
			
			JsonNode data;
			Tick tick;
			for(int i = start; i < length; i++){
				data = node.get(i);
				tick = new Tick(period);
				String str = data.get(0).asText();
				if(str.indexOf(":") < 0)
					str += " 00:00:00";
				
				tick.setEndDateTime(LocalDateTime.parse(str, dateTimeFormatter));
				tick.setOpen(data.get(1).asDouble());
				tick.setHigh(data.get(2).asDouble());
				tick.setClose(data.get(3).asDouble());
				tick.setLow(data.get(4).asDouble());
				tick.setTradingVolume((long) (data.get(5).asDouble() * 100));
				
				ticks.add(tick);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return ticks;
	}
}
