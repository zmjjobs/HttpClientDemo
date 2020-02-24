package com.zmj.test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import com.zmj.utils.MyHttpClientOld;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

public class HttpClient_YouDaoFanYi {//有道翻译这个是基于httpclient4.1.2版本的
	/*
	 参数说明：
　		 type - 返回结果的类型，固定为data
		　doctype - 返回结果的数据格式，xml或json或jsonp
		　version - 版本，当前为1.1
		　q - 要翻译的文本，必须是UTF-8编码，字符长度不能超过200个字符，get请求有时需要进行urlencode编码
		　only - 可选参数，dict表示只获取词典数据，translate表示只获取翻译数据，默认为都获取
		　注： 词典结果只支持中英互译，翻译结果支持英日韩法俄西到中文的翻译以及中文到英语的翻译
	errorCode：
		　0  - 正常
		　20 - 要翻译的文本过长
		　30 - 无法进行有效的翻译
		　40 - 不支持的语言类型
		　50 - 无效的key
		　60 - 无词典结果，仅在获取词典结果生效 
	 */
	@Test
	public void testDoGet() {//如果是get请求，要注意特殊的字符，比如空格
		String queryWord = "测试";
		//queryWord = "test";
		//queryWord = "How old are you!"; queryWord=URLEncoder.encode(queryWord);//比如，它会把空格变成加号
		//queryWord = "你多大了？";
		//queryWord = "你 多 大 了？";  queryWord= queryWord.replace(" ", "");//中文不去除空格不会报错，但有道翻译的时候，会翻译错误 How big are you?
		
		String doGetUrl = "http://fanyi.youdao.com/openapi.do?"
				//+ "keyfrom=youdianbao&key=1661829537"
				+ "keyfrom=wangtuizhijia&key=1048394636"
				+ "&type=data&doctype=json&version=1.1&q="+queryWord;
		String result = MyHttpClientOld.doGetStr(doGetUrl,HTTP.UTF_8);
		System.out.println("doGetStr=" + result);
	}
	
	@Test
	public void testDoPost() {
		String queryWord = "测试";
		//queryWord = "test";
		//queryWord = "How old are you!";
		//queryWord = "你多大了？";
		//queryWord = "你 多 大 了？";  queryWord= queryWord.replace(" ", ""); //不去除空格不会报错，但有道翻译的时候，会翻译错误 How big are you?
		
		String doPostUrl = "http://fanyi.youdao.com/openapi.do";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("keyfrom","youdianbao"));
		//params.add(new BasicNameValuePair("key","1661829537"));
		params.add(new BasicNameValuePair("keyfrom","wangtuizhijia"));
		params.add(new BasicNameValuePair("key","1048394636"));
		
		params.add(new BasicNameValuePair("type","data"));
		params.add(new BasicNameValuePair("doctype","json"));
		params.add(new BasicNameValuePair("version","1.1"));
		params.add(new BasicNameValuePair("q",queryWord));
		String result = MyHttpClientOld.doPostStr(doPostUrl,params,HTTP.UTF_8);
		System.out.println("doPostStr=" + result);
	}
}