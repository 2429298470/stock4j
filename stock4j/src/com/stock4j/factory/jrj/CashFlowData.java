package com.stock4j.factory.jrj;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.CashFlow;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class CashFlowData extends HttpClientPool{
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static ObjectMapper mapper = new ObjectMapper();
	
	public List<CashFlow> listCashFlows(Stock stock) throws UnSupportedException, ErrorHttpException, NullValueException{
		List<CashFlow> cashFlows = new ArrayList<CashFlow>();
		if(stock == null) return cashFlows;

		String url = "http://zj.flashdata2.jrj.com.cn/flowhistory/share/" + stock.getScode() + ".js";
		String result = super.get(url, null, "utf-8");
		if (result == null || result.isEmpty())
			throw new NullValueException("获取资金流向数据出错：" + stock);

		String stockFlow = result.substring(result.indexOf("=") + 1);
		try {
			JsonNode nodes = mapper.readTree(stockFlow);
			JsonNode node;
			CashFlow cashFlow;
			for (int i = 0; i < nodes.size(); i++) {
				node = nodes.get(i);
				cashFlow = new CashFlow();

				String date = node.get("date").asText();
				cashFlow.setDate(LocalDate.parse(date, dateFormatter));
				cashFlow.setMjin(node.get("min").asDouble());  //净流入
				cashFlow.setSjin(node.get("sin").asDouble());
				cashFlow.setZjin(node.get("zin").asDouble());

				cashFlows.add(cashFlow);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return cashFlows;
	}

}
