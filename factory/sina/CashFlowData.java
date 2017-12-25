package com.stock4j.factory.sina;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.CashFlow;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class CashFlowData extends HttpClientPool{
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 获取资金流数据
	 * http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/MoneyFlow.ssl_qsfx_lscjfb?page=1&num=20&sort=opendate&asc=0&daima=sh600517
	 */
	protected List<CashFlow> listCashFlows(Stock stock, int size) throws UnSupportedException, ErrorHttpException, NullValueException{
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/json_v2.php/MoneyFlow.ssl_qsfx_lscjfb";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("num", String.valueOf(size));
		params.put("sort", "opendate");
		params.put("asc", "0");   //倒序
		params.put("daima", scode);
		
		String result = super.post(url, params, "utf-8");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）：" + stock);
		
		if (result.startsWith("null"))
			throw new NullValueException("证券代码不正确/无数据！");
		
		List<CashFlow> cashFlows = new ArrayList<CashFlow>();
		CashFlow cashFlow;
		try {
			//容许key无双引号
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			JsonNode nodes = mapper.readTree(result);
			int len = nodes.size();
			JsonNode json;
			for(int i = len - 1; i >= 0; i--){
				json = nodes.get(i);
				cashFlow = new CashFlow();
				cashFlow.setDate(LocalDate.parse(json.get("opendate").asText(), dateFormatter));
				cashFlow.setZin(json.get("r0").asDouble() + json.get("r1").asDouble());
				cashFlow.setZjin(json.get("r0_net").asDouble() + json.get("r1_net").asDouble());
				
				cashFlow.setMin(json.get("r2").asDouble());
				cashFlow.setMjin(json.get("r2_net").asDouble());
				
				cashFlow.setSin(json.get("r3").asDouble());
				cashFlow.setSjin(json.get("r3_net").asDouble());
				
				cashFlows.add(cashFlow);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}		
		return cashFlows;
	}
	
	/**
	 * 获取即时资金流
	 * http://vip.stock.finance.sina.com.cn/quotes_service/api/jsonp.php/moneyFlowData=/MoneyFlow.ssi_ssfx_flzjtj?daima=sh600517
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected CashFlow getCashFlow(Stock stock) throws UnSupportedException, ErrorHttpException, NullValueException {
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/api/jsonp.php/moneyFlowData=/MoneyFlow.ssi_ssfx_flzjtj?daima=" + scode;
		
		String result = super.get(url, null, "gbk");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("无行情数据（网络错误）：" + stock);

		if (result.startsWith("pv_none_match"))
			throw new NullValueException("证券代码不正确/无数据！");

		result = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		try {
			mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
			JsonNode nodes = mapper.readTree(result);
			CashFlow cashFlow = new CashFlow();
			cashFlow.setZin(nodes.get("r1_in").asDouble());
			cashFlow.setZout(nodes.get("r1_out").asDouble());
			
			cashFlow.setSin(nodes.get("r2_in").asDouble());
			cashFlow.setSout(nodes.get("r2_out").asDouble());
			
			cashFlow.setMin(nodes.get("r3_in").asDouble());
			cashFlow.setMout(nodes.get("r3_out").asDouble());
			
			return cashFlow;
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}
}
