package com.stock4j;

import java.time.LocalDate;

/**
 * ȨϢ���ݣ�����,ÿ���͹�,ÿ�����,��ɼ�,ÿ�ɺ���
 * XDΪexit divident����д����Ϣ
 * XRΪexit right����д,��Ȩ
 * DRΪexit divident and right,��Ȩ��Ϣ
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class DividentRight {

	private LocalDate rdate;
	private Double mgsg;
	private Double mgpg;
	private Double pgj;
	private Double mghl;

	public LocalDate getRdate() {
		return rdate;
	}

	public void setRdate(LocalDate rdate) {
		this.rdate = rdate;
	}

	public Double getMgsg() {
		return mgsg;
	}

	public void setMgsg(Double mgsg) {
		this.mgsg = mgsg;
	}

	public Double getMgpg() {
		return mgpg;
	}

	public void setMgpg(Double mgpg) {
		this.mgpg = mgpg;
	}

	public Double getPgj() {
		return pgj;
	}

	public void setPgj(Double pgj) {
		this.pgj = pgj;
	}

	public Double getMghl() {
		return mghl;
	}

	public void setMghl(Double mghl) {
		this.mghl = mghl;
	}

	@Override
	public String toString() {
		return "RightDivident [rdate=" + rdate + ", mgsg=" + mgsg + ", mgpg=" + mgpg + ", pgj=" + pgj + ", mghl=" + mghl
				+ "]";
	}

}
