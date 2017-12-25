package com.stock4j.factory.qq;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	/**
	 * http://ifzq.gtimg.cn/appstock/app/fqkline/get?_var=kline_weekqfq&param=sh600517,week,,,320,qfq&r=0.6707844382617623
	 * @param stock
	 * @param period
	 * @param size
	 * @param rhb
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Tick> listTicks(Stock stock, PeriodType period, int size, ExRightType rhb)
			throws UnSupportedException, ErrorHttpException, NullValueException {
		String url = "http://ifzq.gtimg.cn/appstock/app/";
		String dataFieldName;
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		StringBuffer params = new StringBuffer();
		params.append(scode);

		String dataName;
		switch (period) {
		case MONTH:
			dataName = "month";
			break;
		case WEEK:
			dataName = "week";
			break;
		case DAY:
			dataName = "day";
			break;
		case MIN5:
			dataName = "m5";
			break;
		case MIN15:
			dataName = "m15";
			break;
		case MIN30:
			dataName = "m30";
			break;
		case MIN60:
			dataName = "m60";
			break;
		default:
			dataName = "day";
			break;
		}
		
		// 复权只针对月线、周线和日线
		if (period == PeriodType.WEEK || period == PeriodType.MONTH || period == PeriodType.DAY) {
			params.append(",").append(dataName);
			params.append(",,,").append(size);
			
			switch (rhb) {
			case FORWARD:
				params.append(",qfq");
				dataFieldName = "qfq" + dataName;
				break;
			case BACKWARD:
				params.append(",hfq");
				dataFieldName = "hfq" + dataName;
				break;
			default:
				params.append(",no");
				dataFieldName = dataName;
				break;
			}
			
			url += "fqkline/get";
			
		} else {
			if (rhb != ExRightType.NO)
				throw new UnSupportedException("日线以下周期不支持复权操作");

			dataFieldName = dataName;
			params.append(",").append(dataName);
			params.append(",,").append(size);
			url += "/kline/mkline";
		}

		url = url + "?param=" + params.toString();
		String result = super.get(url, null, "utf-8");
		if (result == null || result.isEmpty())
			throw new ErrorHttpException("获取K线数据失败！");

		List<Tick> ticks = new ArrayList<Tick>();
		try {
			JsonNode nodes = mapper.readTree(result);
			if (nodes.get("code").asInt() == -1)
				throw new NullValueException("证券代码不正确/无数据！");
			JsonNode data = nodes.get("data");
			JsonNode node = data.get(scode).get(dataFieldName);
			JsonNode temp;
			
			Tick tick;
			for (int i = 0; i < node.size(); i++) {
				temp = node.get(i);
				tick = new Tick(period);
				
				if(period.isBelowDay())
					tick.setEndDateTime(LocalDateTime.parse(temp.get(0).asText() + "00", dateTimeFormatter));
				else
					tick.setEndDateTime(LocalDateTime.parse(temp.get(0).asText(), dateFormatter));
					
				tick.setOpen(temp.get(1).asDouble());
				tick.setClose(temp.get(2).asDouble());
				tick.setHigh(temp.get(3).asDouble());
				tick.setLow(temp.get(4).asDouble());
				tick.setTradingVolume(temp.get(5).asLong() * 100);

				ticks.add(tick);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return ticks;
	}

}
