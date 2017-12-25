package com.stock4j.factory.sina;

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

class TranscationData extends HttpClientPool{
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * 获取某日的大单数据，可设置成交量和成交额门槛
	 * http://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill_download.php?symbol=sh600517&num=60&page=1&sort=ticktime&asc=0&volume=40000&amount=0&type=0&day=2017-07-07
	 * @param stock  股票
	 * @param amount  大单的成交额限制，默认40000
	 * @param volume
	 * @param day  日期
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Transcation> listBigTranscation(Stock stock, Double amount, Long volume, LocalDate day) throws ErrorHttpException, NullValueException{
		String date = day.format(dateFormatter);
		String url = "http://vip.stock.finance.sina.com.cn/quotes_service/view/cn_bill_download.php?symbol=sh600517&num=60&page=1&sort=ticktime&asc=0&volume=40000&amount=0&type=0&day=2017-07-07";
		Map<String, String> params = new HashMap<String, String>();
		params.put("symbol", stock.getMarket().name().toLowerCase() + stock.getScode());
		params.put("num", "4000");
		params.put("sort", "ticktime");
		params.put("asc", "1");
		params.put("volume", volume == null ? "40000" : Long.toString(volume));
		params.put("amount", amount == null ? "0" : Double.toString(amount));
		params.put("type", "0");
		params.put("day", date);
		
		String result = super.post(url, params, "gbk");
		if(StringUtils.isBlank(result))
			throw new NullValueException("无大单交易数据");
		
		List<Transcation> transcations = new ArrayList<Transcation>();
		Transcation transcation;
		String[] lines = result.split("\n");
		for(int i = 1; i < lines.length; i++){
			String[] fields = lines[i].split(",");
			transcation = new Transcation();
			transcation.setTdate(LocalDateTime.parse(date + " " + fields[2], formatter));
			transcation.setVolume(Long.parseLong(fields[4]));
			transcation.setPrice(Double.parseDouble(fields[3]));
			
			if(fields[6].equals("卖盘")){
				transcation.setOrderType(OrderType.SELL);
			} else {
				transcation.setOrderType(OrderType.BUY);
			}
			
			transcations.add(transcation);
		}
		
		return transcations;
	}
}