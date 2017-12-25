package com.stock4j.factory;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ����������Ч��http����
 */
public class IdleConnectionEvictor extends Thread {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HttpClientConnectionManager connMgr;
    
    private Integer waitTime;

    private volatile boolean shutdown;

    public IdleConnectionEvictor(HttpClientConnectionManager connMgr,Integer waitTime) {
        this.connMgr = connMgr;
        this.waitTime = waitTime;
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
            	logger.info("---------����������Ч������-------------");
                synchronized (this) {
                    wait(waitTime);
                    // �ر�ʧЧ������
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
        	logger.warn("����ʧЧ����ʧ��");
        }
    }

    /**
     * �����ͷ���Դ
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}