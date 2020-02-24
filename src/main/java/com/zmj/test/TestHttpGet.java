package com.zmj.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import com.zmj.utils.MyHttpClient;

public class TestHttpGet {
	@Test
	public void testGetNoParam() {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response;
		try {
			response = hc.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity,StandardCharsets.UTF_8);
			System.out.println("网页内容是：" + result);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
	@Test
	public void testGetHasParam() {
		CloseableHttpClient hc = HttpClients.createDefault();
		CloseableHttpResponse response;
		try {
			URIBuilder builder = new URIBuilder("https://www.sogou.com/web");
			builder.addParameter("query", "西游记");
			HttpGet httpGet = new HttpGet(builder.build());
			httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
			
			response = hc.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity,StandardCharsets.UTF_8);
			System.out.println("网页内容是：" + result);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			MyHttpClient.close(hc);
		}
	}
}
