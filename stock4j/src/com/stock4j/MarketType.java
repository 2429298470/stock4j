package com.stock4j;

/**
 * �г����ͣ���֤A�ɡ���֤A��
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public enum MarketType {
	/** �Ϻ�֤ȯ������  */
	SH("�Ϻ�֤ȯ������"),
	/** ����֤ȯ������ */
	SZ("����֤ȯ������");

	private String name;
	
	private MarketType(String name){
		this.setName(name);
	}

	/**
	 * ������������
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**������������*/
	public void setName(String name) {
		this.name = name;
	}
}
