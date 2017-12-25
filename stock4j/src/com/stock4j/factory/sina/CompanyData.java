package com.stock4j.factory.sina;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.stock4j.Company;
import com.stock4j.Stock;
import com.stock4j.exception.ErrorHttpException;
import com.stock4j.exception.NullValueException;
import com.stock4j.exception.UnSupportedException;

class CompanyData{
	
	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpInfo/stockid/600517.phtml
	 * @param stock
	 * @return
	 * @throws ErrorHttpException 
	 * @throws NullValueException 
	 */
	protected Company getCompanyInformation(Stock stock)
			throws ErrorHttpException, NullValueException, UnSupportedException{
		Company company = null;
		try {
			String url = "http://vip.stock.finance.sina.com.cn/corp/go.php/vCI_CorpInfo/stockid/" + stock.getScode() + ".phtml";
			Document doc = Jsoup.connect(url).timeout(5000).get();
			Element table = doc.getElementById("comInfo1");
			Elements tds = table.getElementsByTag("td");
			if(tds.size() != 50)
				throw new NullValueException("公司信息格式不正确");
			
			company = new Company();
			company.setCnName(tds.get(1).text());
			company.setEnName(tds.get(3).text());
			company.setIpoDate(LocalDate.parse(tds.get(7).text(), dateFormatter));
			company.setIpoPrice(Double.parseDouble(tds.get(9).text()));
			company.setRegisterDate(LocalDate.parse(tds.get(13).text(), dateFormatter));
			company.setRegisterCapital(Double.parseDouble(tds.get(15).text().replaceAll("万", "").replaceAll("元", "")));
			company.setWebsite(tds.get(35).text());
			company.setHistoryName(tds.get(41).text());
			company.setRegisterAddress(tds.get(43).text());
			company.setOfficeAddress(tds.get(45).text());
			company.setCompanyInfo(tds.get(47).text().substring(4));
			company.setBusinessScope(tds.get(49).text());
			
		} catch (IOException e) {
			throw new ErrorHttpException("获取公司信息出错：" + e.getMessage());
		}
		
		return  company;
	}
}
