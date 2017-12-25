package com.stock4j;

import java.time.LocalDate;

/**
 * 资金流向
 * @author QQ2429298470
 * see(http://www.sigmagu.com/)
 */
public class CashFlow {
	
	/**日期*/
	private LocalDate date;
	/**主力流入*/
	private Double zin;
	/**主力流出*/
	private Double zout;
	/**主力净流入*/
	private Double zjin;
	/**中单流入*/
	private Double min;
	/**中单流出*/
	private Double mout;
	/**中单流入净额*/
	private Double mjin;
	/**小单流入*/
	private Double sin;
	/**小单流出*/
	private Double sout;
	/**小单流入净额*/
	private Double sjin;
	
	public CashFlow() {
		super();
	}

	/**日期*/
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**主力流入*/
	public Double getZin() {
		return zin;
	}
	
	/**主力流入*/
	public void setZin(Double zin) {
		this.zin = zin;
	}

	/**主力流出*/
	public Double getZout() {
		return zout;
	}

	/**主力流出*/
	public void setZout(Double zout) {
		this.zout = zout;
	}
	
	/**主力净流入*/
	public Double getZjin() {
		return zjin;
	}

	/**主力净流入*/
	public void setZjin(Double zjin) {
		this.zjin = zjin;
	}
	
	/**小单流入*/
	public Double getMin() {
		return min;
	}

	/**中单流入*/
	public void setMin(Double min) {
		this.min = min;
	}

	/**中单流出*/
	public Double getMout() {
		return mout;
	}

	/**中单流出*/
	public void setMout(Double mout) {
		this.mout = mout;
	}
	
	/**中单净流入*/
	public Double getMjin() {
		return mjin;
	}

	/**中单流入*/
	public void setMjin(Double mjin) {
		this.mjin = mjin;
	}

	/**中单流入*/
	public Double getSin() {
		return sin;
	}

	/**中单流入*/
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
