package com.zmj.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import com.zmj.utils.MyHttpClient;

public class TestHttpPost {
	@Test
	public void testPostNoParam() {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/HttpClientDemo/myPostDemo/postNoParam");
		try {
			CloseableHttpResponse response = hc.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity,StandardCharsets.UTF_8);
			System.out.println("内容是：" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
	@Test
	public void testPostHasParam() {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/HttpClientDemo/myPostDemo/postHasParam");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name","张三丰"));
		params.add(new BasicNameValuePair("pwd","1q2w3e4r"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params,StandardCharsets.UTF_8);
		httpPost.setEntity(entity);
		try {
			CloseableHttpResponse response = hc.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("状态码="+statusCode);
			HttpEntity httpEntity = response.getEntity();
			String content = EntityUtils.toString(httpEntity,StandardCharsets.UTF_8);
			System.out.println("content="+content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
	@Test
	public void testPostWithJson() {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/HttpClientDemo/myPostDemo/postWithJson");
		String jsonStr = "{\"name\":\"张三丰\",\"pwd\":\"1q2w3e4r\"}";
		StringEntity entity = new StringEntity(jsonStr,ContentType.APPLICATION_JSON);
		httpPost.setEntity(entity);
		try {
			CloseableHttpResponse response = hc.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("状态码="+statusCode);
			HttpEntity httpEntity = response.getEntity();
			String content = EntityUtils.toString(httpEntity,StandardCharsets.UTF_8);
			System.out.println("content="+content);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
}
