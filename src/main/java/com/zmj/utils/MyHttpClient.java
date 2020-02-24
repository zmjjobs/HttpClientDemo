package com.zmj.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {//基于httpclient4.5.10版本的
	/**
	 * @param connectTimeout  设置连接时间 毫秒
	 * @param socketTimeout   设置读取时间 毫秒
	 * @param proxy
	 * @return
	 */
	public static RequestConfig getRequestConfig(Integer connectTimeout,Integer socketTimeout,HttpHost proxy) {
		Builder custom = RequestConfig.custom();
		if (connectTimeout != null && connectTimeout > 0) {
			custom = custom.setConnectTimeout(connectTimeout);
		}
		if (socketTimeout != null && socketTimeout > 0) {
			custom = custom.setSocketTimeout(socketTimeout);
		}
		if (proxy != null) {
			custom = custom.setProxy(proxy);
		}
		return custom.build();
	}
	
	public static HttpGet getHttpGet(String url,RequestConfig config,Map<String,String> paramMap,Map<String,String> headerMap) {
		HttpGet httpGet = null;
		if (paramMap != null) {
			try {
				URIBuilder uriBuilder = new URIBuilder(url);
				for(Map.Entry<String, String> entry : paramMap.entrySet()) {
					uriBuilder.addParameter(entry.getKey(), entry.getValue());
				}
				URI uri = uriBuilder.build();
				httpGet = new HttpGet(uri);
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		} else {
			httpGet = new HttpGet(url);
		}
		if (config != null) {
			httpGet.setConfig(config);
		}
		if (headerMap != null) {
			for(Map.Entry<String, String> entry : headerMap.entrySet()) {
				httpGet.setHeader(entry.getKey(),entry.getValue());
			}
		}
		return httpGet;
	}
	public static HttpGet getHttpGet(String url) {
		return getHttpGet(url, null, null, null);
	}
	public static HttpGet getHttpGet(String url,RequestConfig config) {
		return getHttpGet(url, config, null, null);
	}
	/**
	 * 
	 * @param url 请求路径
	 * @param encode 字符编码 比如  StandardCharsets.UTF_8
	 * @return
	 */
	public static String doGetStr(String url,String encode,HttpGet httpGet) {
		CloseableHttpClient hc = HttpClients.createDefault();
		CloseableHttpResponse response;
		String result = null;
		try {
			response = hc.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if(entity != null) {
					result = EntityUtils.toString(entity,encode);
				}
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			close(hc);
		}
		return result;
	}
	
	/**
	 * 
	 * @param url 请求路径
	 * @param params 请求参数   params.add(new BasicNameValuePair("key","value"))
	 * @param encode 字符编码 比如 org.apache.http.protocol.HTTP.UTF_8
	 * @param connectTimeout  设置连接时间 毫秒
	 * @param socketTimeout   设置读取时间 毫秒
	 * @return
	 */
	public static String doPostStr(String url,List<NameValuePair> params,String encode,int connectTimeout,int socketTimeout,HttpHost proxy) {
		CloseableHttpClient hc = HttpClients.createDefault();
		try {
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(params,encode));
			CloseableHttpResponse resp=hc.execute(httppost);
			HttpEntity entity=resp.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(hc);
		}
		return null;
	}
	
	/**
	 * 
	 * @param url 请求路径
	 * @param paramStr  参数字符串
	 * @param encode 字符编码 比如 org.apache.http.protocol.HTTP.UTF_8
	 * @param connectTimeout  设置连接时间 毫秒
	 * @param socketTimeout   设置读取时间 毫秒
	 * @return
	 */
	public static String doPostStr(String url,String paramStr,String encode,int connectTimeout,int socketTimeout,HttpHost proxy) {
		CloseableHttpClient hc = HttpClients.createDefault();
        try {
        	HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(paramStr,encode));
            CloseableHttpResponse response = hc.execute(httpPost);
            return EntityUtils.toString(response.getEntity(),encode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			close(hc);
		}
        return null;
	}
	
	public static void close(CloseableHttpClient hc) {
		try {
			if (hc != null)  hc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
