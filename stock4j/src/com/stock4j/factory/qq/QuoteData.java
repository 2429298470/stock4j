package com.stock4j.factory.qq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.stock4j.Quote;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.factory.HttpClientPool;

class QuoteData extends HttpClientPool{
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	public Quote getQuote(Stock stock) throws ErrorHttpException, NullValueException {
		String scode = stock.getMarket().name().toLowerCase() + stock.getScode();
		String url = "http://qt.gtimg.cn/q=" + scode;
		String result = super.get(url, null, "gbk");
		if (StringUtils.isBlank(result))
			throw new ErrorHttpException("���������ݣ�������󣩣�" + stock);

		if (result.startsWith("pv_none_match"))
			throw new NullValueException("֤ȯ���벻��ȷ/�����ݣ�");

		result = result.substring(result.indexOf("\"") + 1, result.lastIndexOf("\""));
		String[] field = result.split("~");
		return parserQuoteData(field);
	}

	/**
	 * ������Ѷ�ƾ�����������
	 * @param stock
	 * @param result
	 * @return
	 */
	private Quote parserQuoteData(String[] field) {
		Quote quote = new Quote();
		// ��Ʊ�����ݽ���
		quote.setPrice(Double.parseDouble(field[3]));
		quote.setZs(Double.parseDouble(field[4]));
		quote.setOpen(Double.parseDouble(field[5]));
		quote.setHigh(Double.parseDouble(field[33]));
		quote.setLow(Double.parseDouble(field[34]));

		quote.setWp(Long.parseLong(field[7]) * 100);
		quote.setNp(Long.parseLong(field[8]) * 100);

		quote.setTotalVol(Long.parseLong(field[36]) * 100);
		quote.setTotalAmount(Double.parseDouble(field[37]));

		quote.setBuyPrice1(Double.parseDouble(field[9]));
		quote.setBuyVol1(Long.parseLong(field[10]) * 100);
		quote.setBuyPrice2(Double.parseDouble(field[11]));
		quote.setBuyVol2(Long.parseLong(field[12]) * 100);
		quote.setBuyPrice3(Double.parseDouble(field[13]));
		quote.setBuyVol3(Long.parseLong(field[14]) * 100);
		quote.setBuyPrice4(Double.parseDouble(field[15]));
		quote.setBuyVol4(Long.parseLong(field[16]) * 100);
		quote.setBuyPrice5(Double.parseDouble(field[17]));
		quote.setBuyVol5(Long.parseLong(field[18]) * 100);

		quote.setSellPrice1(Double.parseDouble(field[19]));
		quote.setSellVol1(Long.parseLong(field[20]) * 100); // ����
		quote.setSellPrice2(Double.parseDouble(field[21]));
		quote.setSellVol2(Long.parseLong(field[22]) * 100);
		quote.setSellPrice3(Double.parseDouble(field[23]));
		quote.setSellVol3(Long.parseLong(field[24]) * 100);
		quote.setSellPrice4(Double.parseDouble(field[25]));
		quote.setSellVol4(Long.parseLong(field[26]) * 100);
		quote.setSellPrice5(Double.parseDouble(field[27]));
		quote.setSellVol5(Long.parseLong(field[28]) * 100);

		quote.setTime(LocalDateTime.parse(field[30], dateTimeFormatter));
		return quote;
	}

	/**
	 * http://qt.gtimg.cn/q=sz000001,sh600517
	 */
	public Map<Stock, Quote> listQuotes(Set<Stock> stocks) throws ErrorHttpException, NullValueException {
		Map<Stock, Quote> quotes = new HashMap<Stock, Quote>();
		if (stocks.isEmpty()) return quotes;
		Set<String> scodes = new HashSet<String>();
		for(Stock stock : stocks){
			scodes.add(stock.getMarket().name().toLowerCase() + stock.getScode());
		}

		String url = "http://qt.gtimg.cn/q=" + StringUtils.join(scodes, ",");
		String result = super.get(url, null, "gbk");
		if (StringUtils.isBlank(result) || result.startsWith("pv_none_match"))
			throw new ErrorHttpException("���������ݻ�֤ȯ�������");

		String[] squotes = result.substring(0, result.length() - 1).split(";");
		for (int i = 0; i < squotes.length; i++) {
			String temp = squotes[i].substring(squotes[i].indexOf("\"") + 1, squotes[i].lastIndexOf("\""));
			String[] field = temp.split("~");
			for(Stock stock : stocks){
				if(stock.getScode().equals(field[2])){
					quotes.put(stock, parserQuoteData(field));
					break;
				}
			}
		}
		return quotes;
	}

}
