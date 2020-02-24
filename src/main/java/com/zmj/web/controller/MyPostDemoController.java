package com.zmj.web.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zmj.pojo.TestUser;

@Controller
@RequestMapping("/myPostDemo")
public class MyPostDemoController {
	@RequestMapping(value="/postNoParam",method=RequestMethod.POST)
	@ResponseBody
	public Object postNoParam() {
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("msg", "post is OK");
		return paramMap;
	}
	
	@RequestMapping(value="/postHasParam",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
	@ResponseBody
	public Object postHasParam(String name,String pwd) {
		System.out.println("传参 name=" + name + ",pwd="+pwd);
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("name", name);
		paramMap.put("pwd", pwd);
		return paramMap;
	}
	
	@RequestMapping(value="/postWithJson",method=RequestMethod.POST)
	@ResponseBody
	public Object postWithJson(@RequestBody TestUser user) {
		System.out.println("传参 name=" + user.getName() + ",pwd="+user.getPwd());
		Map<String,String> paramMap = new HashMap<>();
		paramMap.put("name", user.getName());
		paramMap.put("pwd", user.getPwd());
		return paramMap;
	}
}
