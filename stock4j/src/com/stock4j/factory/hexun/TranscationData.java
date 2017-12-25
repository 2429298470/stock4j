package com.stock4j.factory.hexun;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock4j.Stock;
import com.stock4j.Transcation;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class TranscationData extends HttpClientPool{
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * ��ȡ���յķ�ʱͼ����
	 * @param stock
	 * @return
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<Transcation> listTodayTranscations(Stock stock)
			throws ErrorHttpException, NullValueException{
		LocalDateTime sdate = LocalDateTime.now().withHour(15).withMinute(0);
		return listTranscations(stock, sdate, -240);
	}

	/**
	 * �Ӻ�Ѷ��ȥ��ʱ����
	 * http://webstock.quote.hermes.hexun.com/a/minute?code=szse000001&start=20161219093000&number=1000
	 * @param stock ��Ʊ����ʼʱ��
	 * @return
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	protected List<Transcation> listTranscations(Stock stock, LocalDateTime sdate, int size)
			throws ErrorHttpException, NullValueException {
		String url = "http://webstock.quote.hermes.hexun.com/a/minute";
		Map<String, String> params = new HashMap<String, String>();
		switch (stock.getMarket()) {
		case SZ:
			params.put("code", "szse" + stock.getScode());
			break;
		case SH:
			params.put("code", "sse" + stock.getScode());
			break;
		default:
			throw new UnsupportedOperationException("��֧�ֵ��г����ݲ���");
		}
		params.put("start", sdate.format(formatter));
		params.put("number", String.valueOf(size));
		String result = super.get(url, params,  "utf-8");
		if(StringUtils.isBlank(result))
			throw new ErrorHttpException("��ȡ�ֱ����ݳ���" + stock);
		
		List<Transcation> transs = new ArrayList<Transcation>();
		//����
		result = result.substring(1, result.length() - 2);
		try {
			JsonNode nodes = mapper.readTree(result);
			JsonNode data = nodes.get("Data").get(0);
			if(data.isNull() || !data.has(0))
				throw new NullValueException("֤ȯ���벻��ȷ/�����ݣ�");
			
			Transcation trans = null;
			JsonNode temp;
			for(int i = 0; i < data.size(); i++){
				temp = data.get(i);
				trans = new Transcation();
				trans.setTdate(LocalDateTime.parse(temp.get(0).asText(), formatter));
				trans.setPrice(temp.get(1).asDouble() / 100);  //�۸���
				trans.setAmount(temp.get(2).asDouble());
				trans.setVolume(temp.get(3).asLong());
				
				transs.add(trans);
			}
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
		return transs;
	}
}
