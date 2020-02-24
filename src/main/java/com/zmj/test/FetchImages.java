package com.zmj.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.zmj.utils.MyHttpClient;

public class FetchImages {
	public static void main(String[] args) {
		CloseableHttpClient hc = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.java1234.com/uploads/allimg/170123/1-1F123124629330.jpg");
		//设置连接时间为10秒钟    设置读取时间为10秒钟
		httpGet.setConfig(MyHttpClient.getRequestConfig(10000, 10000,null));
		httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36");
		CloseableHttpResponse response = null;
		try {
			response = hc.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream source = entity.getContent();
				FileUtils.copyToFile(source, new File("D:/"+System.currentTimeMillis()+".jpg"));
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyHttpClient.close(hc);
		}
	}
}
