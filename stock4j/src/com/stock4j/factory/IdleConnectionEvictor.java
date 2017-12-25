package com.stock4j.factory;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定期清理无效的http连接
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
            	logger.info("---------定期清理无效的连接-------------");
                synchronized (this) {
                    wait(waitTime);
                    // 关闭失效的连接
                    connMgr.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
        	logger.warn("清理失效连接失败");
        }
    }

    /**
     * 销毁释放资源
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}