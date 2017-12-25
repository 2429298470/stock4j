package com.stock4j.factory.qq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.stock4j.Stock;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

import eu.verdelhan.ta4j.Order.OrderType;

public class TranscationData extends HttpClientPool{
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * 获取大单数据
	 * http://stock.finance.qq.com/sstock/list/view/dadan.php?c=sh600512&max=1000&o=1
	 * @param stock
	 * @return
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	protected List<Transcation> listBigTranscation(Stock stock, Double amount, Long volume, LocalDate day) throws ErrorHttpException, NullValueException{
		logger.warn("时间参数无效，仅为当日的大单数据，默认大单成交额100万，大单成交量100手");
		amount = (amount == null) ? 1000000 : amount;
		volume = (volume == null) ? 100 : volume;
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://stock.finance.qq.com/sstock/list/view/dadan.php";
		Map<String, String> params = new HashMap<String, String>();
		params.put("c", scode);
		params.put("max", "10000");
		params.put("o", "1");
		
		List<Transcation> transcations = new ArrayList<Transcation>();
		Transcation transcation;
		String date = LocalDateTime.now().format(dateFormatter);
		
		String result = super.get(url, params, "utf-8");
		String body = result.substring(result.indexOf("'") + 1, result.lastIndexOf("'"));
		String[] lines = StringUtils.split(body, "^");
		for(String line : lines){
			String[] fields = line.split("~");
			long volumeTemp = Long.parseLong(fields[3]);
			double amountTemp = Double.parseDouble(fields[4]) * 10000;   //元
			if(volumeTemp < volume || amountTemp < amount)
				continue;
			
			transcation = new Transcation();
			transcation.setTdate(LocalDateTime.parse(date + " " + fields[1], timeFormatter));
			transcation.setPrice(Double.parseDouble(fields[2]));
			transcation.setVolume(volumeTemp);
			transcation.setAmount(amountTemp);
			
			if(fields[5].equals("S")){
				transcation.setOrderType(OrderType.SELL);
			} else {
				transcation.setOrderType(OrderType.BUY);
			}
			
			transcations.add(transcation);
		}
		
		return transcations;
	}

//时间段：http://stock.gtimg.cn/data/index.php?appn=detail&c=sh600517  决定了页码
//http://stock.gtimg.cn/data/index.php?appn=detail&action=data&c=sh600517&p=10
}
