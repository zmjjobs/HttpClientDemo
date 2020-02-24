package com.zmj.utils;

import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MyHttpClientOld {//基于httpclient4.1.2版本的
	/**
	 * 
	 * @param url 请求路径
	 * @param encode 字符编码 比如 org.apache.http.protocol.HTTP.UTF_8
	 * @return
	 */
	public static String doGetStr(String url,String encode) {
		DefaultHttpClient hc = new DefaultHttpClient();
		String result = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = hc.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null) {
				result = EntityUtils.toString(entity,encode);
			}
		} catch (Exception e) {
            e.printStackTrace();
        } finally {
			hc.getConnectionManager().shutdown();
		}
		return result;
	}
	
	/**
	 * 
	 * @param url 请求路径
	 * @param params 请求参数   params.add(new BasicNameValuePair("key","value"))
	 * @param encode 字符编码 比如 org.apache.http.protocol.HTTP.UTF_8
	 * @return
	 */
	public static String doPostStr(String url,List<NameValuePair> params,String encode) {
		DefaultHttpClient hc=new DefaultHttpClient();
		try {
			HttpPost httppost=new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(params,encode));
			HttpResponse resp=hc.execute(httppost);
			HttpEntity entity=resp.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hc.getConnectionManager().shutdown();
		}
		return null;
	}
	
	/**
	 * 
	 * @param url 请求路径
	 * @param paramStr  参数字符串
	 * @param encode 字符编码 比如 org.apache.http.protocol.HTTP.UTF_8
	 * @return
	 */
	public static String doPostStr(String url,String paramStr,String encode) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setEntity(new StringEntity(paramStr,encode));
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(),encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
}
