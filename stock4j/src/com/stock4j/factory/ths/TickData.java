package com.stock4j.factory.ths;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.ExRightType;
import com.stock4j.Stock;
import com.stock4j.Tick;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class TickData extends HttpClientPool{
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * 获取股票的日线数据
	 * https://d.10jqka.com.cn/v2/line/sz_002028/00/last365.js 年线
	 * https://d.10jqka.com.cn/v2/line/sz_002028/00/last31.js
	 * @param stock
	 * @param size   数量
	 * @param rhb  复权方式
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Tick> listDayTicks(Stock stock, int size, ExRightType rhb)
			throws ErrorHttpException, NullValueException {
		if(size <= 0){
			logger.warn("错误的参数：数量小于零");
			return null;
		}

		String scode = stock.getMarket().name().toLowerCase() + "_" + stock.getScode();
		String url = "https://d.10jqka.com.cn/v2/line/" + scode + "/";
		
		switch (rhb) {
		case NO:
			url += "00";
			break;
		case FORWARD:
			url += "01";
			break;
		case BACKWARD:
			url += "02";
			break;
		default:
			url += "00";
			break;
		}
		
		url += "/last" + size + ".js";
		
		String result = super.get(url, null, "utf-8");
		if (StringUtils.isBlank(result))
			throw new NullValueException("证券代码不正确/无数据！");

		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		List<Tick> ticks = new ArrayList<Tick>();
		try {
			JsonNode node = mapper.readTree(body);
			stock.setSname(node.get("name").asText());
			String data = node.get("data").asText();
			Tick tick;
			String[] fields = data.split(";");
			for(String field : fields){
				tick = new Tick();
				String[] temp = field.split(",");
				tick.setEndDateTime(LocalDateTime.parse(temp[0], dateFormatter));
				tick.setOpen(Double.parseDouble(temp[1]));
				tick.setHigh(Double.parseDouble(temp[2]));
				tick.setLow(Double.parseDouble(temp[3]));
				tick.setClose(Double.parseDouble(temp[4]));
				tick.setTradingVolume(Long.parseLong(temp[5]) * 100);
				tick.setTurnover(Double.parseDouble(temp[6]));
				
				ticks.add(tick);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return ticks;
	}

	/**
	 * 获取当日的K线数据
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected Tick getTodayTick(Stock stock, ExRightType rht) throws ErrorHttpException, NullValueException {
		String scode = "hs_" + stock.getScode();
		String url = "http://d.10jqka.com.cn/v2/line/" + scode + "/";

		switch (rht) {
		case NO:
			url += "00";
			break;
		case FORWARD:
			url += "01";
			break;
		case BACKWARD:
			url += "02";
			break;
		default:
			url += "00";
			break;
		}

		url += "/today.js";
		String result = super.get(url, null, "utf-8");
		if (StringUtils.isBlank(result))
			throw new NullValueException("证券代码不正确/无数据！");

		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try{
			JsonNode nodes = mapper.readTree(body);
			JsonNode data = nodes.get(scode);
			Tick tick = new Tick();  //日线
			tick.setOpen(data.get("7").asDouble());
			tick.setHigh(data.get("8").asDouble());
			tick.setLow(data.get("9").asDouble());
			tick.setClose(data.get("11").asDouble());
			tick.setTradingVolume(data.get("13").asLong());
			tick.setTurnover(data.get("19").asDouble());

			tick.setEndDateTime(LocalDateTime.parse(data.get("1").asText(), dateFormatter));

			return tick;
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}

		return null;
	}

}
