package com.stock4j.factory.wy;

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
	 * http://quotes.money.163.com/f10/fhpg_600517.html
	 * @param stock
	 * @return
	 * @throws UnSupportedException
	 * @throws ErrorHttpException
	 * @throws NullValueException
	 */
	protected List<DividentRight> listDividentRight(Stock stock)
			throws UnSupportedException, ErrorHttpException, NullValueException{
		String url = "http://quotes.money.163.com/f10/fhpg_" + stock.getScode() + ".html";
		List<DividentRight> rds = new ArrayList<DividentRight>();
//		Document doc = Jsoup.connect(url).timeout(5000).get();
		String result = super.get(url, null, "utf-8");
		Document doc = Jsoup.parse(result);
		Element tbody = doc.select("table.table_bg001").first().getElementsByTag("tbody").first();
		Elements trs = tbody.getElementsByTag("tr");
		
		DividentRight dr; 
		for(Element tr : trs){
			Elements tds = tr.getElementsByTag("td");
			dr = new DividentRight();
			String sg = tds.get(2).text();
			String zz = tds.get(3).text();
			String px = tds.get(4).text();
			String date = tds.get(6).text();
			dr.setMgpg("--".equals(zz) ? 0.0d : Double.parseDouble(zz) / 10);
			dr.setMgsg("--".equals(sg) ? 0.0d : Double.parseDouble(sg) / 10);
			dr.setMghl("--".equals(px) ? 0.0d : Double.parseDouble(px) / 10);
			dr.setPgj(0.0d);
			
			dr.setRdate(LocalDate.parse(date));
			rds.add(dr);
		}
		return rds;
	}
}
