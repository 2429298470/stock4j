package com.stock4j;

import java.time.LocalDate;

/**
 * �ʽ�����
 * @author QQ2429298470
 * see(http://www.sigmagu.com/)
 */
public class CashFlow {
	
	/**����*/
	private LocalDate date;
	/**��������*/
	private Double zin;
	/**��������*/
	private Double zout;
	/**����������*/
	private Double zjin;
	/**�е�����*/
	private Double min;
	/**�е�����*/
	private Double mout;
	/**�е����뾻��*/
	private Double mjin;
	/**С������*/
	private Double sin;
	/**С������*/
	private Double sout;
	/**С�����뾻��*/
	private Double sjin;
	
	public CashFlow() {
		super();
	}

	/**����*/
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**��������*/
	public Double getZin() {
		return zin;
	}
	
	/**��������*/
	public void setZin(Double zin) {
		this.zin = zin;
	}

	/**��������*/
	public Double getZout() {
		return zout;
	}

	/**��������*/
	public void setZout(Double zout) {
		this.zout = zout;
	}
	
	/**����������*/
	public Double getZjin() {
		return zjin;
	}

	/**����������*/
	public void setZjin(Double zjin) {
		this.zjin = zjin;
	}
	
	/**С������*/
	public Double getMin() {
		return min;
	}

	/**�е�����*/
	public void setMin(Double min) {
		this.min = min;
	}

	/**�е�����*/
	public Double getMout() {
		return mout;
	}

	/**�е�����*/
	public void setMout(Double mout) {
		this.mout = mout;
	}
	
	/**�е�������*/
	public Double getMjin() {
		return mjin;
	}

	/**�е�����*/
	public void setMjin(Double mjin) {
		this.mjin = mjin;
	}

	/**�е�����*/
	public Double getSin() {
		return sin;
	}

	/**�е�����*/
	public void setSin(Double sin) {
		this.sin = sin;
	}

	public Double getSout() {
		return sout;
	}

	public void setSout(Double sout) {
		this.sout = sout;
	}
	
	public Double getSjin() {
		return sjin;
	}

	public void setSjin(Double sjin) {
		this.sjin = sjin;
	}

	@Override
	public String toString() {
		return "CashFlow [date=" + date + ", zin=" + zin + ", zout=" + zout + ", zjin=" + zjin
				+ ", min=" + min + ", mout=" + mout + ", mjin=" + mjin + ", sin=" + sin + ", sout=" + sout + ", sjin="
				+ sjin + "]";
	}
}
