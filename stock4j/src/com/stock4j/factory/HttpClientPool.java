package com.stock4j.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stock4j.exception.ErrorHttpException;

/**
 * 网络连接池
 * @author qq: 2429298470<br>http://www.sigmagu.com/
 * @version 0.1
 */
public class HttpClientPool {
	
	public static Logger logger = LoggerFactory.getLogger(HttpClientPool.class);

	private static PoolingHttpClientConnectionManager cm = null;
	private static CloseableHttpClient httpClient = null;
	private static IdleConnectionEvictor evictor = null;
	// 从连接池中获取到连接的最长时间
	private static int connectionRequestTimeout = Integer.parseInt(System.getProperty("stock4j.connectionRequestTimeout", "5000"));
	// 设置链接超时
	private static int connectTimeout = Integer.parseInt(System.getProperty("stock4j.connectTimeout", "5000"));
	// 数据传输的最长时间
	private static int socketTimeout = Integer.parseInt(System.getProperty("stock4j.socketTimeout", "30000"));
	// 设置连接总数
	private static int maxTotal = Integer.parseInt(System.getProperty("stock4j.maxTotal", "200"));
	// 设置每个地址的并发数
	private static int defaultMaxPerRoute = Integer.parseInt(System.getProperty("stock4j.defaultMaxPerRoute", "100"));
	// 设置定时清除无效链接时间，1分钟
	private static int maxIdleTime = Integer.parseInt(System.getProperty("stock4j.maxIdleTime", "60000"));

	/**
	 * HttpClientPool配置
	 */
	public void config() {
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectionRequestTimeout(connectionRequestTimeout)
				.setConnectTimeout(connectTimeout).build();
		httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(requestConfig).build();
		//定期清理无效的连接
		evictor = new IdleConnectionEvictor(cm, maxIdleTime);
		evictor.start();
	}
	
	/**
	 * HTTP Get 获取内容
	 * @param url 请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset   编码格式
	 * @return 页面内容
	 * @throws ErrorHttpException
	 */
	public String get(String url, Map<String, String> params, String charset) throws ErrorHttpException {
		CloseableHttpResponse response = null;
		String result = null;
		try {
			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, "UTF-8"));
			}
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpGet.abort();
				throw new ErrorHttpException("获取数据失败：" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
		} catch (ParseException | IOException e) {
			throw new ErrorHttpException("获取数据失败：" + e.getMessage());
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * HTTP Post 获取内容
	 * @param url  请求的url地址 ?之前的地址
	 * @param params  请求的参数
	 * @param charset  编码格式
	 * @return 页面内容
	 * @throws ErrorHttpException
	 */
	public String post(String url, Map<String, String> params, String charset) throws ErrorHttpException {
		CloseableHttpResponse response = null;
		String result = null;
		try {
			result = null;
			List<NameValuePair> pairs = null;
			if (params != null && !params.isEmpty()) {
				pairs = new ArrayList<NameValuePair>(params.size());
				for (Map.Entry<String, String> entry : params.entrySet()) {
					String value = entry.getValue();
					if (value != null) {
						pairs.add(new BasicNameValuePair(entry.getKey(), value));
					}
				}
			}
			HttpPost httpPost = new HttpPost(url);
			if (pairs != null && pairs.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(pairs, "UTF-8"));
			}
			response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpPost.abort();
				throw new ErrorHttpException("获取数据失败：" + statusCode);
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				result = EntityUtils.toString(entity, charset);
			}
			EntityUtils.consume(entity);
		} catch (ParseException | IOException e) {
			throw new ErrorHttpException("获取数据失败：" + e.getMessage());
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
