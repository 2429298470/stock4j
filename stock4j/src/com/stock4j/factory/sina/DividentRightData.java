package com.stock4j.factory.sina;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stock4j.DividentRight;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;
import com.stock4j.factory.HttpClientPool;

class DividentRightData extends HttpClientPool{
	
	/**
	 * 获取股票的权息数据
	 * http://vip.stock.finance.sina.com.cn/corp/go.php/vISSUE_ShareBonus/stockid/600517.phtml
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException{
		String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vISSUE_ShareBonus/stockid/" + stock.getScode() + ".phtml";
		List<DividentRight> rds = new ArrayList<DividentRight>();
		String result = super.get(url, null, "utf-8");
		Document doc = Jsoup.parse(result);
		
		DividentRight rd; 
		Element table = doc.getElementById("sharebonus_1");
		Elements trs = table.getElementsByTag("tbody").first().getElementsByTag("tr");
		for(Element tr : trs){
			Elements tds = tr.getElementsByTag("td");
			rd = new DividentRight();
			String sg = tds.get(1).text();
			String zz = tds.get(2).text();
			String px = tds.get(3).text();
			String date = tds.get(5).text();
			rd.setMgpg(Double.parseDouble(zz) / 10);
			rd.setMgsg(Double.parseDouble(sg) / 10);
			rd.setMghl(Double.parseDouble(px) / 10);
			rd.setPgj(0.0d);
			
			rd.setRdate(LocalDate.parse(date));
			rds.add(rd);
		}
		return rds;
	}

}
