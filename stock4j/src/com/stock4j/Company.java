package com.stock4j;

import java.time.LocalDate;

/**
 * 公司信息
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Company {
	/**中文名*/
	private String cnName;
	/**英文名*/
	private String enName;
	/**上市日期*/
	private LocalDate ipoDate;
	/** 发行价格*/
	private Double ipoPrice;
	/**注册资本*/
	private Double registerCapital;
	/**网址*/
	private String website;
	/**更名历史*/
	private String historyName;
	/**注册地址*/
	private String registerAddress;
	/**成立日期*/
	private LocalDate registerDate;
	/**办公地址*/
	private String officeAddress;
	/**公司简介*/
	private String companyInfo;
	/**经营范围*/
	private String businessScope;

	/**中文名*/
	public String getCnName() {
		return cnName;
	}

	/**中文名*/
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public LocalDate getIpoDate() {
		return ipoDate;
	}

	public void setIpoDate(LocalDate ipoDate) {
		this.ipoDate = ipoDate;
	}

	public Double getIpoPrice() {
		return ipoPrice;
	}

	public void setIpoPrice(Double ipoPrice) {
		this.ipoPrice = ipoPrice;
	}

	public Double getRegisterCapital() {
		return registerCapital;
	}

	public void setRegisterCapital(Double registerCapital) {
		this.registerCapital = registerCapital;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getHistoryName() {
		return historyName;
	}

	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getCompanyInfo() {
		return companyInfo;
	}

	public void setCompanyInfo(String companyInfo) {
		this.companyInfo = companyInfo;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "Company [cnName=" + cnName + ", enName=" + enName + ", ipoDate=" + ipoDate + ", ipoPrice=" + ipoPrice
				+ ", registerCapital=" + registerCapital + ", website=" + website + ", historyName=" + historyName
				+ ", registerAddress=" + registerAddress + ", registerDate=" + registerDate + ", officeAddress="
				+ officeAddress + ", companyInfo=" + companyInfo + ", businessScope=" + businessScope + "]";
	}
	
}
