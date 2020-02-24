package com.zmj.test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.zmj.utils.MyHttpClient;

public class HighLevelAnonymousAgent {//高匿代理

	public static void main(String[] args) {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://www.tuicool.com/");
		
		//配置代理IP 
		//有时候某个网站因为爬虫访问过度，而将IP纳入黑名单，
		//这时需要代理IP，作一个队列，哪怕封了一个，再来一个即可
		HttpHost proxy = new HttpHost("175.155.213.235",9999);
		httpGet.setConfig(MyHttpClient.getRequestConfig(10000, 10000, proxy));
		
		//如果不加这句话，会报    系统检测亲不是真人行为，因系统资源限制，我们只能拒绝你的请求
		//说明它只允许你通过浏览器请求，那么这里相当于模拟浏览器请求
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		
		CloseableHttpResponse response = null;
		try {
			response = hc.execute(httpGet);
			System.out.println("Status:" + response.getStatusLine());
			System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				//获取Content-Type
				System.out.println(entity.getContentType().getName() + ":" + entity.getContentType().getValue());
			}
			String result = EntityUtils.toString(entity,StandardCharsets.UTF_8);
			System.out.println("网页内容是：" + result);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
}
