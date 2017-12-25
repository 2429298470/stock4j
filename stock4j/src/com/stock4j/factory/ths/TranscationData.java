package com.stock4j.factory.ths;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.Stock;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class TranscationData extends HttpClientPool{

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * 获取分时数据 https://d.10jqka.com.cn/v2/time/sz_002028/lastFive.js
	 * @param stock
	 * @param size 从最后日期向前的最大数据量
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size) throws ErrorHttpException, NullValueException {
		throw null; 
	}

	/**
	 * 获取当日的分时图数据
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Transcation> listTodayTranscations(Stock stock) throws ErrorHttpException, NullValueException {
		String scode = stock.getMarket().name().toLowerCase() + "_" + stock.getScode();

		String url = "https://d.10jqka.com.cn/v2/time/" + scode + "/last.js";
		String result = super.get(url, null, "utf-8");
		if (StringUtils.isBlank(result))
			throw new NullValueException("证券代码不正确/无数据！");

		String body = result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1);
		List<Transcation> transcations = new ArrayList<Transcation>();
		try{
			JsonNode nodes = mapper.readTree(body);
			JsonNode node = nodes.get(scode);
			String date = node.get("date").asText();
			String sname = node.get("name").asText();
			stock.setSname(sname);

			Transcation trans;
			String data = node.get("data").asText();
			String[] fields = data.split(";");
			for (String field : fields) {
				String[] temp = field.split(",");
				trans = new Transcation();
				trans.setTdate(LocalDateTime.parse(date + temp[0], dateTimeFormatter));
				trans.setPrice(Double.parseDouble(temp[1]));
				trans.setAmount(Double.parseDouble(temp[2]));
				trans.setVolume(Long.parseLong(temp[4]));

				transcations.add(trans);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}		
		return transcations;
	}
}
