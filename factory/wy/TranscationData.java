package com.stock4j.factory.wy;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.Stock;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class TranscationData extends HttpClientPool{
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 获取当日的分时图数据
	 * http://img1.money.126.net/data/hs/time/today/0600510.json
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 * @throws UnSupportedException 
	 */
	protected List<Transcation> listTodayTranscations(Stock stock)
			throws NullValueException, UnSupportedException, ErrorHttpException{
		String scode = stock.getScode();		
		switch (stock.getMarket()){
		case SH:
			scode = "0" + scode;
			break;
		case SZ:
			scode = "1" + scode;
			break;
		default:
			throw new UnSupportedException("不支持的数据操作");
		}

		String url = "http://img1.money.126.net/data/hs/time/today/" + scode + ".json";
		List<Transcation> transcations = new ArrayList<Transcation>();
		String result = "";
		try {
			result = super.get(url, null, "utf-8");
		} catch (ErrorHttpException e) {
			throw new NullValueException("证券代码不正确/无数据！");
		}
		
		try {
			JsonNode nodes = mapper.readTree(result);
			stock.setSname(nodes.get("name").asText());
			String date = nodes.get("date").asText();
			Transcation transcation;
			JsonNode data = nodes.get("data");
			JsonNode temp;
			for(int i = 0, len = data.size(); i < len; i++){
				temp = data.get(i);
				transcation = new Transcation();
				transcation.setTdate(LocalDateTime.parse(date + temp.get(0).asText(), formatter));
				transcation.setPrice(temp.get(1).asDouble());
				transcation.setMeanPrice(temp.get(2).asDouble());
				transcation.setVolume(temp.get(3).asLong());
				
				transcations.add(transcation);
			}
			
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}		
		return transcations;
	}
	
	/**
	 * 从网易取分时数据
	 * http://img1.money.126.net/data/hs/time/4days/0600510.json
	 * @param stock 股票，起始时间
	 * @return
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 * @throws UnSupportedException 
	 */
	protected List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException, UnSupportedException {
		if(size > 242 * 5) logger.warn("超过最大数据访问量");
		
		String scode = stock.getScode();		
		switch (stock.getMarket()){
		case SH:
			scode = "0" + scode;
			break;
		case SZ:
			scode = "1" + scode;
			break;
		default:
			throw new UnSupportedException("不支持的数据操作");
		}

		String url = "http://img1.money.126.net/data/hs/time/4days/" + scode + ".json";
		String result;
		List<Transcation> transcations = new ArrayList<Transcation>();
		try {
			result = super.get(url, null, "utf-8");
		} catch (ErrorHttpException e) {
			throw new NullValueException("证券代码不正确/无数据！");
		}
		
		try {
			JsonNode nodes = mapper.readTree(result);
			stock.setSname(nodes.get("name").asText());
			Transcation transcation;
			JsonNode node = nodes.get("data");
			JsonNode data;
			for(int i = node.size() - 1; i >= 0; i--){
				data = node.get(i);
				String date = data.get("date").asText();
				JsonNode dataBody = data.get("data");
				for(int j = 0, len = dataBody.size() ; j < len; j++){
					JsonNode temp = dataBody.get(j);
					LocalDateTime dateTime = LocalDateTime.parse(date + temp.get(0).asText(), formatter);
					if(dateTime != null && dateTime.isBefore(sdate)) continue;
					
					transcation = new Transcation();
					transcation.setTdate(dateTime);
					transcation.setPrice(temp.get(1).asDouble());
					transcation.setMeanPrice(temp.get(2).asDouble());
					transcation.setVolume(temp.get(3).asLong());
					
					transcations.add(transcation);
				}
			}
			
			transcations.addAll(listTodayTranscations(stock));  //当日的
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		int len = Math.min(size, transcations.size());
		return transcations.subList(0, len);
	}

}
