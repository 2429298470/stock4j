package com.stock4j;

import java.time.LocalDate;

/**
 * ��˾��Ϣ
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class Company {
	/**������*/
	private String cnName;
	/**Ӣ����*/
	private String enName;
	/**��������*/
	private LocalDate ipoDate;
	/** ���м۸�*/
	private Double ipoPrice;
	/**ע���ʱ�*/
	private Double registerCapital;
	/**��ַ*/
	private String website;
	/**������ʷ*/
	private String historyName;
	/**ע���ַ*/
	private String registerAddress;
	/**��������*/
	private LocalDate registerDate;
	/**�칫��ַ*/
	private String officeAddress;
	/**��˾���*/
	private String companyInfo;
	/**��Ӫ��Χ*/
	private String businessScope;

	/**������*/
	public String getCnName() {
		return cnName;
	}

	/**������*/
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
