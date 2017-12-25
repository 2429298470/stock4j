package com.stock4j.factory.sina;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.core.JsonParser.Feature;
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
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/var%20_sh600517_15_1496458232095=/CN_MarketData.getKLineData?symbol=sh600517&scale=5&ma=no&datalen=1023
	 * @param stock
	 * @param period
	 * @param size
	 * @param rhb
	 * @return 数据按时间升序
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 * @throws UnSupportedException
	 */
	protected List<Tick> listStockTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		if (rhb != ExRightType.NO)
			throw new UnSupportedException("不支持复权操作");
		if (period == PeriodType.MIN1)
			throw new UnSupportedException("不支持1分钟K线数据");

		String sname = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://money.finance.sina.com.cn/quotes_service/api/jsonp_v2.php/" + sname
				+ "/CN_MarketData.getKLineData";
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", sname);
		// K线周期
		switch (period) {
		case MIN5:
			params.put("scale", String.valueOf(5));
			break;
		case MIN15:
			params.put("scale", String.valueOf(15));
			break;
		case MIN30:
			params.put("scale", String.valueOf(30));
			break;
		case MIN60:
			params.put("scale", String.valueOf(60));
			break;
		default:
			throw new UnSupportedException("不支持日线以上数据获取");
		}
		params.put("ma", "no");
		// 数据数量
		params.put("datalen", String.valueOf(size));

		String result = super.get(url, params, "utf-8");
		if (result == null || result.isEmpty())
			throw new ErrorHttpException("获取K线数据失败！");

		List<Tick> ticks = new ArrayList<Tick>();
		String content = StringUtils.substringAfterLast(result, sname);
		String data = content.substring(1, content.length() - 1);

		if (StringUtils.isBlank(data) || "null".equals(data))
			throw new NullValueException("证券代码不正确/无数据！");
		
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			JsonNode nodes = mapper.readTree(data);
			JsonNode node;
			Tick tick;
			for (int i = 0, length = nodes.size(); i < length; i++) {
				node = nodes.get(i);
				tick = new Tick(period);
				tick.setEndDateTime(LocalDateTime.parse(node.get("day").asText(), formatter));
				tick.setOpen(node.get("open").asDouble());
				tick.setHigh(node.get("high").asDouble());
				tick.setLow(node.get("low").asDouble());
				tick.setClose(node.get("close").asDouble());
				tick.setTradingVolume(node.get("volume").asLong());
	
				ticks.add(tick);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ticks;
	}
}
